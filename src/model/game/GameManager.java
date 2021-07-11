package model.game;

import View.CRView;
import model.enums.Type;
import model.informationObjects.UnitInformation;
import model.units.Troops;
import model.enums.Cell;
import model.units.Projectile;

import javafx.geometry.Point2D;
import model.units.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    private int timer;

    private int lvl;
    private Player player;
    private Player bot;

    private HashMap<Type, UnitInformation> unitInformationHashMap;

    private ArrayList<Unit> units;


    private CRView view;

    public GameManager(int lvl){

    }

    public void initializeSpells(){
        //set static variables for spells
    }

    public void initMap(){
        //read map paths from a text file and set it
    }

    public synchronized void spawnTroop(Troops troops){
        //adds new unit to lists
        //adds new unit to view
    }

    public synchronized void spawnBuilding(Point2D location, Cell team, Type type){
        //adds new unit to lists
        //adds new unit to view
    }

    public void spawnProjectile(Projectile projectile){
        //projectiles.add(projectile);
        //view add projectile
    }

    public void tick(){
        //for all soldiers
        //if hp = 0, set status dead
        //if in attack range attack
        //else move

        //for all buildings
        //if in attack range attack
        //building.tick

        //for all projectiles
        //projectile move
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

    public CRView getView() {
        return view;
    }
}
