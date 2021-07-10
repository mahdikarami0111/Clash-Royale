package model.items;

import model.units.Unit;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Rage extends Spell{
    private int duration;
    private ArrayList<Unit> effectedUnits;
    //becasue the units locations change we have to keep track of them

    public Rage(Point2D Location, int duration) {
        super(Location);
        this.duration = duration;
    }

    public void effect(){
        //takes the effect on units in location and saves them in effected units list
    }

    public void removeEffect(){
        //removes the effect from units in list
    }

    public void reduceDuration(){
        //reduces spell duration
    }
}
