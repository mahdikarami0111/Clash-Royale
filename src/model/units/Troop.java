package model.units;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import model.enums.CellType;
import model.enums.State;
import model.enums.Type;
import model.game.Cell;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.game.sharedRecourses.View;

import java.util.ArrayList;

public class Troop extends Unit {

    private Point2D pixelLocation;
    private Point2D dest;
    private Point2D prev;
    private Point2D step;
    private int movementSpeed;

    public Troop(Type type, CellType team, Point2D location){
        super(type,team,location);
        movementSpeed = Game.gameManager().getUnitInformationHashMap().get(type).movementSpeed;
        pixelLocation = new Point2D(getCurrentLocation().getX()*32,getCurrentLocation().getY()*32);
        dest = null;
    }

    @Override
    public void setState(State state){
        if(this.state == State.ATTACKING && state !=State.ATTACKING){
            timer = ((long) attackSpeed*1000)-30;
        }
        this.state = state;
        View.CRView().changeState(this);
    }

    public void findTarget(){
        //finds closest enemy tower
    }

    public void setDest() {
        int x = (int) currentLocation.getX();
        int y = (int) currentLocation.getY();
        if (team == CellType.PLAYER) {
            if (x > 3) {
                if (y > 9 && y != 14 && y != 15) {
                    if (Map.getMap()[x][y + 1].getCellType() == CellType.PATH) {
                        dest = new Point2D(x, y + 1);
                        return;
                    }
                    if (Map.getMap()[x - 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x - 1, y);
                        return;
                    }
                    if (Map.getMap()[x + 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x + 1, y);
                        return;
                    }
                    dest = null;
                    return;
                }
                if (y == 14) {
                    if (Map.getMap()[x - 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x - 1, y);
                        return;
                    }
                    if (Map.getMap()[x][15].getCellType() == CellType.PATH) {
                        dest = new Point2D(x, 15);
                        return;
                    }
                    dest = null;
                    return;
                }
                if (y == 15) {
                    if (Map.getMap()[x - 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x - 1, y);
                        return;
                    }
                    if (Map.getMap()[x][14].getCellType() == CellType.PATH) {
                        dest = new Point2D(x, 14);
                        return;
                    }
                    dest = null;
                    return;
                }
                if (y != 2 && y != 3) {
                    if (Map.getMap()[x][y - 1].getCellType() == CellType.PATH) {
                        dest = new Point2D(x, y - 1);
                        return;
                    }
                    if (Map.getMap()[x - 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x - 1, y);
                        return;
                    }
                    if (Map.getMap()[x + 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x + 1, y);
                        return;
                    }
                    dest = null;
                    return;
                }
                if (y == 3) {
                    if (Map.getMap()[x - 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x - 1, y);
                        return;
                    }
                    if (Map.getMap()[x][2].getCellType() == CellType.PATH) {
                        dest = new Point2D(x, 2);
                        return;
                    }
                    dest = null;
                    return;
                }
                if (y == 2) {
                    if (Map.getMap()[x - 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x - 1, y);
                        return;
                    }
                    if (Map.getMap()[x][3].getCellType() == CellType.PATH) {
                        dest = new Point2D(x, 3);
                        return;
                    }
                    dest = null;
                    return;
                }
            }
            if(y<9){
                if(Map.getMap()[x][y+1].getCellType()==CellType.PATH){
                    dest = new Point2D(x,y+1);
                    return;
                }
                if(x != 3 && Map.getMap()[x+1][y].getCellType() == CellType.PATH){
                    dest = new Point2D(x+1,y);
                    return;
                }
                if(x != 1 && Map.getMap()[x-1][y].getCellType()==CellType.PATH){
                    dest = new Point2D(x-1,y);
                    return;
                }
                dest = null;
                return;
            }
            if(y>= 9){
                if(Map.getMap()[x][y-1].getCellType()==CellType.PATH){
                    dest = new Point2D(x,y-1);
                    return;
                }
                if(x != 3 && Map.getMap()[x+1][y].getCellType() == CellType.PATH){
                    dest = new Point2D(x+1,y);
                    return;
                }
                if(x != 1 && Map.getMap()[x-1][y].getCellType()==CellType.PATH){
                    dest = new Point2D(x-1,y);
                    return;
                }
                dest = null;
                return;
            }
        }



        else if(team == CellType.BOT){
            if(x<28){
                if (y > 9 && y != 14 && y != 15) {
                    if (Map.getMap()[x][y + 1].getCellType() == CellType.PATH) {
                        dest = new Point2D(x, y + 1);
                        return;
                    }
                    if (Map.getMap()[x + 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x + 1, y);
                        return;
                    }
                    if (Map.getMap()[x - 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x - 1, y);
                        return;
                    }
                    dest = null;
                    return;
                }
                if (y == 14) {
                    if (Map.getMap()[x + 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x + 1, y);
                        return;
                    }
                    if (Map.getMap()[x][15].getCellType() == CellType.PATH) {
                        dest = new Point2D(x, 15);
                        return;
                    }
                    dest = null;
                    return;
                }
                if (y == 15) {
                    if (Map.getMap()[x + 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x + 1, y);
                        return;
                    }
                    if (Map.getMap()[x][14].getCellType() == CellType.PATH) {
                        dest = new Point2D(x, 14);
                        return;
                    }
                    dest = null;
                    return;
                }
                if (y != 2 && y != 3) {
                    if (Map.getMap()[x][y - 1].getCellType() == CellType.PATH) {
                        dest = new Point2D(x, y - 1);
                        return;
                    }
                    if (Map.getMap()[x + 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x + 1, y);
                        return;
                    }
                    if (Map.getMap()[x - 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x - 1, y);
                        return;
                    }
                    dest = null;
                    return;
                }
                if (y == 3) {
                    if (Map.getMap()[x + 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x + 1, y);
                        return;
                    }
                    if (Map.getMap()[x][2].getCellType() == CellType.PATH) {
                        dest = new Point2D(x, 2);
                        return;
                    }
                    dest = null;
                    return;
                }
                if (y == 2) {
                    if (Map.getMap()[x + 1][y].getCellType() == CellType.PATH) {
                        dest = new Point2D(x + 1, y);
                        return;
                    }
                    if (Map.getMap()[x][3].getCellType() == CellType.PATH) {
                        dest = new Point2D(x, 3);
                        return;
                    }
                    dest = null;
                    return;
                }
            }
            if(y<9){
                if(Map.getMap()[x][y+1].getCellType()==CellType.PATH){
                    dest = new Point2D(x,y+1);
                    return;
                }
                if(x != 30 && Map.getMap()[x+1][y].getCellType() == CellType.PATH){
                    dest = new Point2D(x+1,y);
                    return;
                }
                if(x != 28 && Map.getMap()[x-1][y].getCellType()==CellType.PATH){
                    dest = new Point2D(x-1,y);
                    return;
                }
                dest = null;
                return;
            }
            if(y>= 9){
                if(Map.getMap()[x][y-1].getCellType()==CellType.PATH){
                    dest = new Point2D(x,y-1);
                    return;
                }
                if(x != 30 && Map.getMap()[x+1][y].getCellType() == CellType.PATH){
                    dest = new Point2D(x+1,y);
                    return;
                }
                if(x != 28 && Map.getMap()[x-1][y].getCellType()==CellType.PATH){
                    dest = new Point2D(x-1,y);
                    return;
                }
                dest = null;
                return;
            }
        }
    }

    public void move(){
        if(dest == null || hasReached()){
            setDest();
            if(dest == null)return;
            setStep();
            Map.getMap()[(int) currentLocation.getX()][(int) currentLocation.getY()].setCellType(CellType.PATH);
            Map.getMap()[(int) currentLocation.getX()][(int) currentLocation.getY()].setUnit(null);

            Map.getMap()[(int) dest.getX()][(int) dest.getY()].setCellType(team);
            Map.getMap()[(int) dest.getX()][(int) dest.getY()].setUnit(this);
            currentLocation = dest;
        }
        pixelLocation = pixelLocation.add(step);
    }

    public void setStep(){
        step = new Point2D(0.5*movementSpeed*(dest.getX()-currentLocation.getX()),0.5*movementSpeed*(dest.getY()-currentLocation.getY()));
        if(step.getY()>0 && state != State.MOVING_RIGHT)setState(State.MOVING_RIGHT);
        if(step.getY()<0 && state != State.MOVING_LEFT)setState(State.MOVING_LEFT);
        if(step.getX()>0&& state != State.MOVING_RIGHT)setState(State.MOVING_RIGHT);
        if(step.getX()<0 && state != State.MOVING_LEFT)setState(State.MOVING_LEFT);
    }

    public Point2D getPixelLocation() {
        return pixelLocation;
    }

    @Override
    public void attack(){
        if(!hasReached()){
            pixelLocation = pixelLocation.add(step);
            return;
        }
        if(state != State.ATTACKING){
            setState(State.ATTACKING);
        }
        timer +=30;
        if(timer >= attackSpeed*1000){
            timer = 0;
            Point2D target = null;
            Point2D temp;
            for(int i = 0;i<32;i++){
                for (int j = 0;j<18;j++){
                    temp = new Point2D(i,j);
                    CellType t = Map.getMap()[i][j].getCellType();
                    Unit enemy = Map.getMap()[i][j].getUnit();
                    if(currentLocation.distance(temp)<range &&  t != team && t != CellType.BLOCK && t != CellType.PATH && attackTypeMatch(enemy)){
                        if(target == null){
                            target = temp;
                            continue;
                        }
                        if(currentLocation.distance(target)>currentLocation.distance(temp)){
                            target = temp;
                        }
                    }
                }
            }
            if(target != null){
                if(areaSplash){
                    ArrayList<Unit> units = new ArrayList<>();
                    for(int i =(int) target.getX()-1;i<target.getX()+2;i++){
                        for(int j =(int) target.getY()-1;j<target.getY()+2;j++){
                            CellType t = Map.getMap()[i][j].getCellType();
                            Unit unit = Map.getMap()[i][j].getUnit();
                            if(t != team && t != CellType.BLOCK && t != CellType.PATH &&unit != null && attackTypeMatch(unit)){
                                if(units.contains(unit))continue;
                                unit.decreaseHp(damage);
                                units.add(unit);
                            }
                        }
                    }
                }
                else {
                    Map.getMap()[(int)target.getX()][(int)target.getY()].getUnit().decreaseHp(damage);
                }
                if(range>=2){
                    Game.gameManager().getPlayer(team).summonProjectile(new Projectile(currentLocation,target,type));
                }
            }
        }
    }

    public boolean hasReached(){
        if (step == null)return false;
        if (step.getX()<0)return Math.ceil(pixelLocation.getX()/32)==currentLocation.getX();
        if(step.getX()>0)return Math.floor(pixelLocation.getX()/32)==currentLocation.getX();
        if (step.getY()<0)return Math.ceil(pixelLocation.getY()/32)==currentLocation.getY();
        return Math.floor(pixelLocation.getY()/32)==currentLocation.getY();
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
}
