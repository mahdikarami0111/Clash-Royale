package model.units;

import model.enums.*;
import model.game.sharedRecourses.Game;
import model.informationObjects.UnitInformation;

import javafx.geometry.Point2D;

public  class Unit {
    private int hp;
    private int damage;
    private double attackSpeed;
    private double range;
    private TargetType unitType;
    private TargetType targetType;
    private boolean areaSplash;
    private int cost;
    private int count;


    private CellType team;
    protected Point2D currentLocation;
    protected State state;
    public Type type;


    public Unit(Type type, CellType team, Point2D location){
        setData(type);
        this.team = team;
        currentLocation = location;
        this.type = type;
    }

    public boolean checkForAttack(){

        return true;
    }

    public void setState(State state){
        this.state = state;
    }

    public void attack(){
        state = State.ATTACKING;
        //timertast(while ttacking)
        //each 1.7 second
        //find target
        //reduce hp
    }

    public void setData(Type type){
        UnitInformation info = Game.gameManager().getUnitInformationHashMap().get(type);
        this.hp = info.hp;
        this.damage = info.damage;
        this.attackSpeed = info.attackSpeed;
        this.range = info.range;
        this.targetType = info.targetType;
        this.unitType = info.unitType;
        this.areaSplash = info.areaSplash;
        this.cost = info.cost;
        this.count = info.count;

    }



    public CellType getTeam() {
        return team;
    }

    public State getState() {
        return state;
    }

    public int getHp() {
        return hp;
    }

    public Point2D getCurrentLocation() {
        return currentLocation;
    }

    public void decreaseHp(int damage){
        hp -= damage;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
