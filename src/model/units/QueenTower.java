package model.units;

import javafx.geometry.Point2D;
import model.enums.Cell;
import model.enums.Type;
import model.informationObjects.UnitInformation;

public class QueenTower extends Unit{


    public QueenTower(UnitInformation unitInformation, Type type, Cell team, Point2D location) {
        super(unitInformation, type, team, location);
    }
    @Override
    public void attack(){

    }
}
