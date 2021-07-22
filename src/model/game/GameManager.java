package model.game;

import javafx.application.Platform;
import model.enums.CellType;
import model.enums.State;
import model.enums.Type;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.game.sharedRecourses.View;
import model.informationObjects.UnitInformation;
import model.spells.Arrows;
import model.spells.FireBall;
import model.spells.Rage;
import model.units.*;

import javafx.geometry.Point2D;
import view.CRView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.*;

/**
 * the game manager
 */
public class GameManager {

    private int timer;

    private int lvl;

    private Player player;
    private Player bot;

    private HashMap<Type, UnitInformation> unitInformationHashMap;

    private ArrayList<Type> deck;

    private int botDiffculty;


    /**
     * the game is initialized based on players level
     * @param lvl is users level
     */
    public GameManager(int lvl){
        this.lvl = lvl;
        unitInformationHashMap = new HashMap<>();
        initializeUnitInfo(lvl);
        initializeSpells();
    }

    /**
     * start the game
     */
    public void start(){
        player = new Player(CellType.PLAYER);
        bot = new Player(CellType.BOT);
        timer = 0;
        startTimer();
        elixirMaker();
    }

    /**
     * start timer
     */
    public void startTimer(){
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(Game.getGamInstance() == null){
                    t.cancel();
                }
                try {
                    timer++;
                    View.CRView().updateTimer(timer);
                }catch (NullPointerException e){

                }

            }
        };
        t.schedule(task,0,1000);
    }

    /**
     * generate elixir
     * in the last minute, the ratio will be doubled
     */
    public void elixirMaker(){
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(Game.getGamInstance() == null){
                    t.cancel();
                }
                try {
                    if(timer>=120){
                        player.setElixirRate(2);
                        bot.setElixirRate(2);
                    }
                    if(player.getElixir()<10){
                        player.addElixir();
                        View.CRView().updateElixir(player.getElixir());
                    }
                    if(bot.getElixir()<10){
                        bot.addElixir();
                    }
                }catch (NullPointerException r){

                }
            }
        };
        t.schedule(task,0,2000);
    }

    /**
     * unit attributes are based on user level and initialized as so
     * @param lvl user level
     */
    public void initializeUnitInfo(int lvl){
        for (Type type: Type.values()){
            if(type == Type.RAGE||type == Type.ARROWS|| type==Type.FIREBALL)continue;
            try {
                FileInputStream fis = new FileInputStream("./src/recourses/UnitInformation/"+type.name()+"/"+lvl+".ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                UnitInformation u = (UnitInformation) ois.readObject();
                unitInformationHashMap.put(type,u);
//                System.out.println(type);
//                u.print();
                fis.close();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param location is where troop will be spawned
     * @param player is the player spawning troops
     * @param type is troop type
     * @return true upon successful spawn
     */

    public boolean spawnTroop(Point2D location, Player player,Type type){
        if(player.getElixir() >= unitInformationHashMap.get(type).cost){
            if(unitInformationHashMap.get(type).count == 1 && isLocationValid(location,player.getTeam())){
                player.setElixir(player.getElixir()-unitInformationHashMap.get(type).cost);
                player.summonTroop(type,location);
                return true;
            }
            else if(unitInformationHashMap.get(type).count > 1 && isLocationValid(location,player.getTeam())){
                ArrayList<Point2D> points = findLocations(player.getTeam(),unitInformationHashMap.get(type).count,location);
                if (points.size() !=unitInformationHashMap.get(type).count)return false;
                player.setElixir(player.getElixir()-unitInformationHashMap.get(type).cost);
                for (Point2D point2D : points){
                    player.summonTroop(type,point2D);
                }
                return true;
            }
        }return false;
    }

    /**
     *
     * @param location is where building will be spawned
     * @param player is the building spawning troops
     * @param type is building type
     * @return true upon successful spawn
     */
    public boolean spawnBuilding(Point2D location, Player player, Type type){
        if(player.getElixir() >= unitInformationHashMap.get(type).cost){
            player.summonBuilding(type,location);
            player.setElixir(player.getElixir()-unitInformationHashMap.get(type).cost);
            return true;
        }
        return false;
    }

    /**
     * make an attack
     */
    public void tick(){
        try {
            player.action();
            bot.action();
            if(botDiffculty == 2){
                smartBot();
            }else {
                dumbBot();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param p is the player
     * @param crowns is the crowns that will be added
     */
    public void updateCrowns(Player p, int crowns){
        if(p.getTeam() == CellType.PLAYER){
            bot.setCrown(bot.getCrown()+crowns);
            View.CRView().updateBotCrown(bot.getCrown());
        }else {
            player.setCrown(player.getCrown()+crowns);
            View.CRView().updatePlayerCrown(player.getCrown());
        }
    }


    public int getTimer() {
        return timer;
    }

    /**
     *
     * @return user level
     */
    public int getLvl() {
        return lvl;
    }

    /**
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @param team is the team
     * @return player if team is player or bot otherwise
     */
    public Player getPlayer(CellType team){
        if(team == CellType.PLAYER)return player;
        else {
            return bot;
        }
    }

    /**
     *
     * @return bot
     */
    public Player getBot() {
        return bot;
    }

    /**
     *
     * @return hash map that maps a unit(based on its type to its information
     */

    public HashMap<Type, UnitInformation> getUnitInformationHashMap() {
        return unitInformationHashMap;
    }

    public boolean isLocationValid(Point2D location,CellType team){
        if(location.getY()==0 || location.getY()==1 || location.getY()==16 || location.getY()==17)return false;
        if(Map.getMap()[(int) location.getX()][(int) location.getY()].getCellType() != CellType.PATH)return false;
        if(team == CellType.PLAYER){

            if(bot.getQueenTowers()[0].getState()==State.DEAD && bot.getQueenTowers()[1].getState()==State.DEAD){
                return location.getX() >= 11;
            }

            else if(bot.getQueenTowers()[0].getState()==State.DEAD){
                if(location.getX() >= 11 && location.getY()<=9)return true;
                return location.getX() >= 17;
            }

            else if(bot.getQueenTowers()[1].getState()==State.DEAD){
                if(location.getX() >= 11 && location.getY() >= 9)return true;
                return location.getX() >= 17;
            }

            else {
                return location.getX() >= 17;
            }
        }
        else {
            if(player.getQueenTowers()[0].getState() == State.DEAD && player.getQueenTowers()[1].getState() == State.DEAD){
                return location.getX() <= 20;
            }

            else if(player.getQueenTowers()[0].getState()==State.DEAD){
                if(location.getX()<=20 && location.getY()<=9)return true;
                return location.getX() <=14;
            }

            else if(player.getQueenTowers()[1].getState() == State.DEAD){
                if (location.getX()<=20 && location.getY()>=9)return true;
                return location.getX()<=14;
            }

            else {
                return location.getX()<=14;
            }
        }
    }

    public ArrayList<Point2D> findLocations(CellType team,int count,Point2D location){
        ArrayList<Point2D> points = new ArrayList<>();
        int counter = 1;
        points.add(location);
        for(int i =(int) location.getX();i<=location.getX()+1;i++){
            for (int j = (int)location.getY();j<=location.getY()+1;j++){
                if(count == counter || (i==location.getX()&& j==location.getY()))continue;
                if(isLocationValid(new Point2D(i,j),team)){
                    points.add(new Point2D(i,j));
                    counter++;
                }
            }
        }
        return points;
    }

    /**]
     * initialize spells
     */
    public void initializeSpells(){
        try {
            File f = new File("./src/recourses/SpellInformation/ARROWS/"+lvl+".txt");
            Scanner sc = new Scanner(f);
            Arrows.setDamage(Integer.parseInt(sc.nextLine()));
            f = new File("./src/recourses/SpellInformation/FIREBALL/"+lvl+".txt");
            sc = new Scanner(f);
            FireBall.setDamage(Integer.parseInt(sc.nextLine()));
            f = new File("./src/recourses/SpellInformation/RAGE/"+lvl+".txt");
            sc = new Scanner(f);
            Rage.setDuration(Double.parseDouble(sc.nextLine()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param team is the team using spell
     * @param location is the location spell will be used
     * @return true if enough elixir exists
     */
    public boolean rage(CellType team,Point2D location){
        if(getPlayer(team).getElixir()>=Rage.getCost()){
            Rage.attack(location,team);
            getPlayer(team).setElixir(getPlayer(team).getElixir()-Rage.getCost());
            return true;
        }
        return false;
    }

    /**
     *
     * @param team is the team using spell
     * @param location is the location spell will be used
     * @return true if enough elixir exists
     */
    public boolean fireball(CellType team, Point2D location){
        if(getPlayer(team).getElixir()>= FireBall.getCost()){
            FireBall.attack(location,team);
            getPlayer(team).setElixir(getPlayer(team).getElixir()-FireBall.getCost());
            return true;
        }
        return false;
    }

    /**
     *
     * @param team is the team using spell
     * @param location is the location spell will be used
     * @return true if enough elixir exists
     */
    public boolean arrows(CellType team,Point2D location){
        if(getPlayer(team).getElixir()>=Arrows.getCost()){
            Arrows.attack(location,team);
            getPlayer(team).setElixir(getPlayer(team).getElixir()-Arrows.getCost());
            return true;
        }
        return false;
    }

    /**
     *
     * @param deck is deck to be set
     */
    public void setDeck(ArrayList<Type> deck) {
        this.deck = deck;
    }

    /**
     * enemy is the dumb bot
     */
    public void dumbBot(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(bot.getElixir()>6){
                    Random random = new Random();
                    Point2D location = new Point2D(10,random.nextInt(8)+4);
                    Type t = deck.get(random.nextInt(8));
                    if(t == Type.RAGE){
                        Game.gameManager().rage(CellType.BOT,location);
                    }
                    else if(t == Type.FIREBALL){
                        Game.gameManager().fireball(CellType.BOT,location);
                    }
                    else if(t == Type.ARROWS){
                        Game.gameManager().arrows(CellType.BOT,location);
                    }
                    else if(View.CRView().isMutable(t)){
                        Game.gameManager().spawnTroop(location,bot,t);
                    }
                    else if(t == Type.CANNON){
                        Game.gameManager().spawnBuilding(location,bot,t);
                    }
                    else if(t == Type.INFERNO_TOWER){
                        Game.gameManager().spawnBuilding(location,bot,t);
                    }
                }
            }
        });
    }

    /**
     * enemy is the smart bot
     */
    public void smartBot(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(bot.getElixir()>6){
                    int left = 0;
                    int right = 0;
                    for(int i =0;i<32;i++){
                        for(int j = 0;j<18;j++){
                            if(Map.getMap()[i][j].getCellType()==CellType.PLAYER){
                                if(j>=9)right++;
                                else{
                                    left++;
                                }
                            }
                        }
                    }

                    Random random = new Random();
                    Point2D location = new Point2D(10,10);
                    Type t = deck.get(random.nextInt(8));


                    if(t == Type.RAGE){
                        boolean label = true;
                        for(int i =0;i<32&&label;i++){
                            for(int j = 0;j<18;j++){
                                if(Map.getMap()[i][j].getCellType()==CellType.BOT && !(Map.getMap()[i][j].getUnit() instanceof KingTower) && !(Map.getMap()[i][j].getUnit() instanceof QueenTower)){
                                    location = new Point2D(i,j);
                                    label = false;
                                    break;
                                }
                            }
                        }
                        Game.gameManager().rage(CellType.BOT,location);
                    }


                    else if(t == Type.FIREBALL){
                        boolean label = true;
                        for(int i =0;i<32 && label;i++){
                            for(int j = 0;j<18;j++){
                                if(Map.getMap()[i][j].getCellType()==CellType.PLAYER){
                                    location = new Point2D(i,j);
                                    label = false;
                                    break;
                                }
                            }
                        }
                        Game.gameManager().fireball(CellType.BOT,location);
                    }


                    else if(t == Type.ARROWS){
                        boolean label = true;
                        for(int i =0;i<32&&label;i++){
                            for(int j = 0;j<18;j++){
                                if(Map.getMap()[i][j].getCellType()==CellType.PLAYER){
                                    location = new Point2D(i,j);
                                    label = false;
                                    break;
                                }
                            }
                        }
                        Game.gameManager().arrows(CellType.BOT,location);
                    }


                    else if(View.CRView().isMutable(t)){
                        int y ,x;
                        if(left>= right){
                            y = random.nextInt(6)+3;
                        }
                        else{
                            y = random.nextInt(6)+8;
                        }
                        if(unitInformationHashMap.get(t).range>=2){
                            x = 7;
                        }
                        else {
                            x = 12;
                        }
                        location = new Point2D(x,y);
                        Game.gameManager().spawnTroop(location,bot,t);
                    }


                    else if(t == Type.CANNON){
                        int y;
                        if(left>= right){
                            y = random.nextInt(6)+3;
                        }
                        else{
                            y = random.nextInt(6)+8;
                        }
                        location  =new Point2D(7,y);
                        Game.gameManager().spawnBuilding(location,bot,t);
                    }


                    else if(t == Type.INFERNO_TOWER){
                        int y;
                        if(left>= right){
                            y = random.nextInt(6)+3;
                        }
                        else{
                            y = random.nextInt(6)+8;
                        }
                        location  =new Point2D(7,y);
                        Game.gameManager().spawnBuilding(location,bot,t);
                    }
                }
            }
        });
    }

    /**
     *
     * @return true if game is over
     */
    public boolean gameOver(){
        return player.getKingTower().getState() == State.DEAD || bot.getKingTower().getState() == State.DEAD || timer >= 180;
    }

    /**
     *
     * @param botDiffculty is the bot difficulty
     */
    public void setBotDiffculty(int botDiffculty) {
        this.botDiffculty = botDiffculty;
    }

    /**
     *
     * @return BOT if bot wins otherwise PLAYER
     */
    public CellType winner(){
        if(player.getKingTower().getState() == State.DEAD)return CellType.BOT;
        if(bot.getKingTower().getState() == State.DEAD)return CellType.PLAYER;
        if(player.getCrown() > bot.getCrown())return CellType.PLAYER;
        if(bot.getCrown() > player.getCrown())return CellType.BOT;
        if(player.getQueenTowers()[0].getHp()+ player.getQueenTowers()[1].getHp()+ player.getKingTower().getHp() >
                bot.getKingTower().getHp()+bot.getQueenTowers()[0].getHp()+bot.getQueenTowers()[1].getHp())return CellType.PLAYER;
        return CellType.BOT;
    }
}
