package model.informationObjects;

import model.enums.TargetType;

import java.io.Serializable;

public class UnitInformation implements Serializable {
    public int hp;
    public int damage;
    public int attackSpeed;
    public int movementSpeed;
    public int range;
    public int lifespan;
    public TargetType unitType;
    public TargetType targetType;
    public boolean areaSplash;
    public int cost;
    public int count;
}
