package model.units;

import javafx.geometry.Point2D;
import model.enums.CellType;
import model.enums.Type;

public class KingTower extends Unit {

    private boolean canAttack;


    public KingTower(CellType team, Point2D location) {
        super( Type.KING_TOWER, team, location);

    }

    @Override
    public boolean checkForAttack(){
        //checks for attack range
        return true;
    }

    @Override
    public void attack(){

    }
}
