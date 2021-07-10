package model.units;

import View.CRView;
import model.GameManager;
import model.enums.Cell;
import model.enums.TargetType;
import model.enums.Type;
import model.informationObjects.UnitInformation;

public class Unit {
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

    private Cell[][] map;
    private GameManager gameManager;
    public Type name;
    public CRView view;


    public Unit(UnitInformation unitInformation,Type type,Cell team){
        //set fields to information on unitInformation
    }

    public void checkForAttack(){
        //checks for attack range
    }

    public void setState(){
        //sets state
        //view.change state
    }

    public void attack(){

    }
}
