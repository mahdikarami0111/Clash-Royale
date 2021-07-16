package model.units;

import model.enums.Type;

import javafx.geometry.Point2D;

public class Projectile {
    private Point2D start;
    private Point2D end;
    private Point2D current;
    private Type unitType;

    public Projectile(Point2D start,Point2D end,Type unitType){
        //sets start and end of the projectile
    }

    public void move(){
        //moves projectile
    }

    public Type getUnitType() {
        return unitType;
    }

    public Point2D getStart() {
        return start;
    }

    public Point2D getEnd() {
        return end;
    }

    public Point2D getCurrent() {
        return current;
    }
}
