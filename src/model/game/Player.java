package model.game;

import javafx.geometry.Point2D;
import model.enums.Cell;
import model.enums.Type;
import model.units.Projectile;
import model.units.Building;
import model.units.Troops;

import java.util.ArrayList;

public class Player {
    private int elixir;
    private int crown;
    private int elixirRate;
    private Cell team;
    //king tower
    //queen towers
    private ArrayList<Troops> troops;
    private ArrayList<Building> buildings;
    private ArrayList<Projectile> projectiles;
    private GameManager gameManager;

    public Player(Cell team,GameManager gameManager){
        elixir = 0;
        elixirRate = 2;
        this.team = team;
        //create king and queen towers
    }

    public void summonTroop(Type type, Point2D location){
        Troops troop = new Troops(gameManager.getUnitInformationHashMap().get(type),type,team,location);
        gameManager.getUnits().add(troop);
        troops.add(troop);
    }

    public void summonBuilding(Type type,Point2D location){
        Building building = new Building(gameManager.getUnitInformationHashMap().get(type),type,team,location);
        gameManager.getUnits().add(building);
        buildings.add(building);
    }
}
