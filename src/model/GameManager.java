package model;

import View.CRView;
import model.enums.Type;
import model.informationObjects.UnitInformation;
import model.units.Building;
import model.units.Troops;
import model.enums.Cell;
import model.items.Projectile;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    private ArrayList<Troops> soldiers;
    private ArrayList<Building> buildings;
    private HashMap<Type, UnitInformation> unitInformationHashMap;

    private ArrayList<Projectile> projectiles;

    private CRView view;
    private Cell[][] map;



    public void initMap(){
        //read map paths from a text file and set it
    }

    public synchronized void spawnSoldier(Point2D location, Cell team, Type type){
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
}
