package model.units;

import model.enums.Cell;
import model.enums.Type;
import model.informationObjects.UnitInformation;

import javafx.geometry.Point2D;

public class Building extends Unit{
    private int lifespan;

    public Building(UnitInformation unitInformation, Type type, Cell team,Point2D location){
        super(unitInformation,type,team,location);
        //set lifespan
        //call timer method
    }

    public void timer(){
       //sets a timer task in between delays and destroys building
    }
}
