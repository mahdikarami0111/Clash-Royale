package model.units;

import model.enums.Cell;
import model.enums.Type;
import model.informationObjects.UnitInformation;

import java.awt.geom.Point2D;

public class Building extends Unit{
    private int lifespan;
    private Point2D location;

    public Building(UnitInformation unitInformation, Type type, Cell team,Point2D location){
        super(unitInformation,type,team);
        //set lifespan
    }

    public void tick(){
        //reduces lifespan until it reaches 0
    }

    @Override
    public void attack(){
        //find closest enemy and reduce his hp

        // if range bigger than 1 summon projectile
//        Projectile p =new Projectile(location,enemy loc,this.type);
        //gameManager.spawn projectile P
    }
}
