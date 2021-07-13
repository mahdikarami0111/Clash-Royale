package model.units;

import View.CRView;
import model.enums.*;
import model.game.GameManager;
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


    private CellType team;
    protected Point2D currentLocation;
    protected State state;
    public Type type;


    public Unit(Type type, CellType team, Point2D location){
        //set fields to information on unitInformation
    }

    public boolean checkForAttack(){
        //checks for attack range
        return true;
    }

    public void setState(State state){
        this.state = state;
    }

    public  void attack(){
        state = State.ATTACKING;
        //find closest enemy and reduce his hp
        //if range bigger than 1 summon projectile
        //Projectile p =new Projectile(location,enemy loc,this.type);
        //gameManager.spawn projectile P
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
    public void setHp(int hp) {
        if (hp < 0)
            return;
        this.hp = hp;
    }



    public void decreaseHp(int i){
        int tmp = this.hp - i;
        if (tmp < 0)
            tmp = 0;
        setHp(tmp);
    }

    public Point2D getCurrentLocation() {
        return currentLocation;
    }


}
