package model.units;

import javafx.geometry.Point2D;
import model.enums.CellType;
import model.enums.State;
import model.enums.Type;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.View;

public class Troop extends Unit {

    private Point2D pixelLocation;
    private Point2D dest;
    private Point2D target;
    private int movementSpeed;

    public Troop(Type type, CellType team, Point2D location){
        super(type,team,location);
        movementSpeed = Game.gameManager().getUnitInformationHashMap().get(type).movementSpeed;
        pixelLocation = new Point2D(getCurrentLocation().getX()*32,getCurrentLocation().getY()*32);
    }

    @Override
    public void setState(State state){
        this.state = state;
        View.CRView().changeState(this);
    }

    public void findTarget(){
        //finds closest enemy tower
    }

    public void setDest(){
        //using Target finds next tile unit has to move to
    }

    public void move(){
        //state = MOVE_R or MOVE_L
        //if dest tile if full change dest
        //moves pixels towards dest
        //if pixel location matches destTile -> current = dest & set new dest
        //set status based on movement
    }

    public Point2D getPixelLocation() {
        return pixelLocation;
    }
}
