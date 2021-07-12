package model.units;

import javafx.geometry.Point2D;
import model.enums.Cell;
import model.enums.CellType;
import model.enums.Type;
import model.informationObjects.UnitInformation;

public class KingTower extends Unit {

    private boolean canAttack;


    public KingTower(CellType team, Point2D location) {
        super( Type.KING_TOWER, team, location);

    }

    @Override
    public void checkForAttack(){
        //checks for attack range
    }

    @Override
    public void attack(){

    }
}
