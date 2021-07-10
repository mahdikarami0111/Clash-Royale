package model.items;

import java.awt.geom.Point2D;

public class FireBall extends Spell{

    private int damage;

    public FireBall(Point2D Location,int damage) {
        super(Location);
    }

    public void attack(){}
}
