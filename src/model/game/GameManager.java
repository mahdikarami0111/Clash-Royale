package model.game;

import model.enums.CellType;
import model.enums.State;
import model.enums.Type;
import model.informationObjects.UnitInformation;
import model.units.Troop;
import model.units.Projectile;

import javafx.geometry.Point2D;
import model.units.Unit;

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
