package model.units;

import model.enums.Cell;
import model.enums.CellType;
import model.enums.Type;
import model.informationObjects.UnitInformation;

import javafx.geometry.Point2D;

public class Building extends Unit{
    private int lifespan;

    public Building(Type type, CellType team, Point2D location){
        super(type,team,location);
        //set lifespan
        //call timer method
    }

    public void timer(){
       //sets a timer task in between delays and destroys building
    }
}
