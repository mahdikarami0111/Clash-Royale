package model.game;

import javafx.application.Platform;
import model.enums.CellType;
import model.enums.State;
import model.enums.Type;
import model.game.bot.Bot;
import model.game.bot.RandomBot;
import model.game.bot.SmartBot;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.game.sharedRecourses.View;
import model.informationObjects.UnitInformation;
import model.spells.Arrows;
import model.spells.FireBall;
import model.spells.Rage;
import model.units.*;

import javafx.geometry.Point2D;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;

/**
 * main Model class is game Manager it manages basis of the game and players
 */
public class GameManager {

    private int timer;

    private int lvl;

    private Player player;
    private Player bot;

    private HashMap<Type, UnitInformation> unitInformationHashMap;

    private ArrayList<Type> deck;

    private int botDifficulty;
    private Bot botPlayer;



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
     * starts the game starts making elixir and setting the bot and also starts timer
     */
    public void start(){
        player = new Player(CellType.PLAYER);
        bot = new Player(CellType.BOT);
        if(botDifficulty == 1){
            botPlayer = new RandomBot(bot);
        }else if(botDifficulty == 2){
            botPlayer = new SmartBot(bot);
        }
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
                fis.close();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * if its possible to spawn the troop in the given location for given player spawns it
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
     * causes all player units and bot units to take proper actions based on game state
     */
    public void tick(){
        try {
            player.action();
            bot.action();
            botPlayer.play();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * changes crowns of the players, gets called upon aking or queen tower destruction
     * @param p is the player calling the method
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
     * @param team retursn player based on their team (player or bot)
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

    /**
     * checks if a location is valid to place units on from teh given team or not
     * @param location Point2D  location to be checked
     * @param team which player wants to place in this location
     * @return boolean  true if valid false if not
     */
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

    /**
     * gets valid location for units that need more than one cell
     * @param team which player wants to place units
     * @param count int  how many player wants to place
     * @param location Point2D  location which player wants to place the units
     * @return and array list with size of units count around the given location
     */
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

    /**
     * initialize spells data
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
     *
     * @return true if game is over
     */
    public boolean gameOver(){
        return player.getKingTower().getState() == State.DEAD || bot.getKingTower().getState() == State.DEAD || timer >= 180;
    }

    /**
     *
     * @param botDifficulty int  difficulty of the bot
     */
    public void setBotDifficulty(int botDifficulty) {
        this.botDifficulty = botDifficulty;
    }

    /**
     * determines the winner of the game if game is over
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

    public ArrayList<Type> getDeck() {
        return deck;
    }
}
