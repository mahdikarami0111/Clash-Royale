package model.informationObjects;

import model.enums.TargetType;

import java.io.Serializable;

public class UnitInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    public int hp;
    public int damage;
    public double attackSpeed;
    public int movementSpeed;
    public double range;
    public int lifespan;
    public TargetType unitType;
    public TargetType targetType;
    public boolean areaSplash;
    public int cost;
    public int count;

    public UnitInformation(int hp, int damage, double attackSpeed, int movementSpeed, double range, int lifespan, TargetType unitType, TargetType targetType, boolean areaSplash, int cost, int count) {
        this.hp = hp;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.movementSpeed = movementSpeed;
        this.range = range;
        this.lifespan = lifespan;
        this.unitType = unitType;
        this.targetType = targetType;
        this.areaSplash = areaSplash;
        this.cost = cost;
        this.count = count;
    }

    public void print(){
        System.out.println(hp+"\n"+damage+"\n"+attackSpeed+"\n"+movementSpeed+"\n"+range+"\n"+lifespan+"\n"+unitType+"\n"+targetType+"\n"+areaSplash+"\n"+cost+"\n"+count);
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }
}
