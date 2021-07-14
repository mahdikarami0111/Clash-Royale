package model.units;

import javafx.geometry.Point2D;
import model.enums.Cell;
import model.enums.CellType;
import model.enums.Type;
import model.informationObjects.UnitInformation;

public class QueenTower extends Unit{


    public QueenTower(CellType team, Point2D location) {
        super(Type.QUEEN_TOWER, team, location);
    }
    @Override
    public void attack(){

    }
}