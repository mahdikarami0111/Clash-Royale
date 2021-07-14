package model.game;

import model.enums.CellType;
import model.enums.State;
import model.enums.Type;
import model.informationObjects.UnitInformation;
import model.units.Troop;
import model.units.Projectile;

import javafx.geometry.Point2D;
import model.units.Unit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class GameManager {

    private int timer;

    private int lvl;

    private Player player;
    private Player bot;

    private HashMap<Type, UnitInformation> unitInformationHashMap;

    private ArrayList<Projectile> projectiles;

    private ArrayList<Unit> units;

    public GameManager(int lvl){
        unitInformationHashMap = new HashMap<>();
        projectiles = new ArrayList<>();
        initializeUnitInfo(lvl);

        this.lvl = lvl;
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

    public void initializeSpells(){

    }

    public void initializeUnitInfo(int lvl){
        for (Type type: Type.values()){
            if(type == Type.RAGE||type == Type.ARROWS|| type==Type.FIREBALL)continue;
            try {
                FileInputStream fis = new FileInputStream("./src/recourses/UnitInformation/"+type.name()+"/"+lvl+".ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                UnitInformation u = (UnitInformation) ois.readObject();
                unitInformationHashMap.put(type,u);
                System.out.println(type.name());
                u.print();
                fis.close();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void spawnTroop(Point2D location, Player player,Type type){
        if(player.getElixir() >= unitInformationHashMap.get(type).cost){
            player.summonTroop(type,location);
        }
    }

    public void spawnBuilding(Point2D location, Player player, Type type){
        if(player.getElixir() >= unitInformationHashMap.get(type).cost){
            player.summonBuilding(type,location);
        }
    }

    public synchronized void spawnProjectile(Projectile projectile){
        projectiles.add(projectile);
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

    public ArrayList<Unit> getUnits() {
        return units;
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

    public Player getBot() {
        return bot;
    }

    public HashMap<Type, UnitInformation> getUnitInformationHashMap() {
        return unitInformationHashMap;
    }
}