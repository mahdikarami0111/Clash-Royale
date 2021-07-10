package model.informationObjects;

import model.enums.TargetType;

import java.io.Serializable;

public class UnitInformation implements Serializable {
    int hp;
    int damage;
    int attackSpeed;
    int movementSpeed;
    int range;
    int lifespan;
    TargetType unitType;
    TargetType targetType;
    boolean areaSplash;
    int cost;
    int count;
}
