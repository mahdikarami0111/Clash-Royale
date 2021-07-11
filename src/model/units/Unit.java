package model.units;

import View.CRView;
import model.game.GameManager;
import model.enums.Cell;
import model.enums.TargetType;
import model.enums.Type;
import model.informationObjects.UnitInformation;

import javafx.geometry.Point2D;

public  class Unit {
    private int hp;
    private int damage;
    private int attackSpeed;
    private int range;
    private TargetType unitType;
    private TargetType targetType;
    private boolean areaSplash;
    private int cost;
    private int count;


    private Cell team;
    protected Point2D currentLocation;


    public Type type;
    public CRView view;


    public Unit(UnitInformation unitInformation,Type type,Cell team,Point2D location){
        //set fields to information on unitInformation
    }

    public void checkForAttack(){
        //checks for attack range
    }

    public void setState(){
        //sets state
        //view.change state
    }

    public  void attack(){
        //find closest enemy and reduce his hp
        //if range bigger than 1 summon projectile
        //Projectile p =new Projectile(location,enemy loc,this.type);
        //gameManager.spawn projectile P
    }
}
