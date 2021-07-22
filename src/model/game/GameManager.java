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

public class GameManager {

    private int timer;

    private int lvl;

    private Player player;
    private Player bot;

    private HashMap<Type, UnitInformation> unitInformationHashMap;

    private ArrayList<Type> deck;

    private int botDifficulty;
    private Bot botPlayer;




    public GameManager(int lvl){
        this.lvl = lvl;
        unitInformationHashMap = new HashMap<>();
        initializeUnitInfo(lvl);
        initializeSpells();
    }

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

    public boolean spawnBuilding(Point2D location, Player player, Type type){
        if(player.getElixir() >= unitInformationHashMap.get(type).cost){
            player.summonBuilding(type,location);
            player.setElixir(player.getElixir()-unitInformationHashMap.get(type).cost);
            return true;
        }
        return false;
    }


    public void tick(){
        try {
            player.action();
            bot.action();
            botPlayer.play();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

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

    public int getLvl() {
        return lvl;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getPlayer(CellType team){
        if(team == CellType.PLAYER)return player;
        else {
            return bot;
        }
    }

    public Player getBot() {
        return bot;
    }

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

    public boolean rage(CellType team,Point2D location){
        if(getPlayer(team).getElixir()>=Rage.getCost()){
            Rage.attack(location,team);
            getPlayer(team).setElixir(getPlayer(team).getElixir()-Rage.getCost());
            return true;
        }
        return false;
    }

    public boolean fireball(CellType team, Point2D location){
        if(getPlayer(team).getElixir()>= FireBall.getCost()){
            FireBall.attack(location,team);
            getPlayer(team).setElixir(getPlayer(team).getElixir()-FireBall.getCost());
            return true;
        }
        return false;
    }

    public boolean arrows(CellType team,Point2D location){
        if(getPlayer(team).getElixir()>=Arrows.getCost()){
            Arrows.attack(location,team);
            getPlayer(team).setElixir(getPlayer(team).getElixir()-Arrows.getCost());
            return true;
        }
        return false;
    }

    public void setDeck(ArrayList<Type> deck) {
        this.deck = deck;
    }

    public boolean gameOver(){
        return player.getKingTower().getState() == State.DEAD || bot.getKingTower().getState() == State.DEAD || timer >= 180;
    }

    public void setBotDifficulty(int botDifficulty) {
        this.botDifficulty = botDifficulty;
    }

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
