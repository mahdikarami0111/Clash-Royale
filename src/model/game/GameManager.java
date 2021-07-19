package model.game;

import model.enums.CellType;
import model.enums.State;
import model.enums.Type;
import model.game.sharedRecourses.Map;
import model.informationObjects.UnitInformation;
import model.spells.Arrows;
import model.spells.FireBall;
import model.spells.Rage;
import model.units.Troop;
import model.units.Projectile;

import javafx.geometry.Point2D;
import model.units.Unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.*;

public class GameManager {

    private int timer;

    private int lvl;

    private Player player;
    private Player bot;

    private HashMap<Type, UnitInformation> unitInformationHashMap;



    public GameManager(int lvl){
        this.lvl = lvl;
        unitInformationHashMap = new HashMap<>();
        initializeUnitInfo(lvl);
        initializeSpells();
    }

    public void start(){
        player = new Player(CellType.PLAYER);
        bot = new Player(CellType.BOT);
        timer = 0;
        startTimer();
        elixirMaker();
    }

    public void startTimer(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timer++;
            }
        };
        Timer t = new Timer();
        t.schedule(task,0,1000);
    }

    public void elixirMaker(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(timer>=120){
                    player.setElixirRate(4);
                    bot.setElixirRate(4);
                }
                player.addElixir();
                bot.addElixir();
            }
        };
        Timer t = new Timer();
        t.schedule(task,0,1000);
    }


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

    public void spawnTroop(Point2D location, Player player,Type type){
        if(player.getElixir() >= unitInformationHashMap.get(type).cost){
            if(unitInformationHashMap.get(type).count == 1 && isLocationValid(location,player.getTeam())){
                player.setElixir(player.getElixir()-unitInformationHashMap.get(type).cost);
                player.summonTroop(type,location);
            }
            else if(unitInformationHashMap.get(type).count > 1 && isLocationValid(location,player.getTeam())){
                ArrayList<Point2D> points = findLocations(player.getTeam(),unitInformationHashMap.get(type).count,location);
                if (points.size() !=unitInformationHashMap.get(type).count)return;
                player.setElixir(player.getElixir()-unitInformationHashMap.get(type).cost);
                for (Point2D point2D : points){
                    player.summonTroop(type,point2D);
                }
            }
        }
    }

    public void spawnBuilding(Point2D location, Player player, Type type){
        if(player.getElixir() >= unitInformationHashMap.get(type).cost){
            player.summonBuilding(type,location);
        }
    }


    public void tick(){
        player.action();
        bot.action();
    }

    public void updateCrowns(Player p, int crowns){
        if(p.getTeam() == CellType.PLAYER){
            bot.setCrown(bot.getCrown()+crowns);
        }else {
            player.setCrown(player.getCrown()+crowns);
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
}
