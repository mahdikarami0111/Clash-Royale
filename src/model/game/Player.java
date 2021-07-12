package model.game;

import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.geometry.Point2D;
import model.enums.CellType;
import model.enums.State;
import model.enums.Type;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.game.sharedRecourses.View;
import model.units.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {
    private int elixir;
    private int crown;
    private int elixirRate;
    private CellType team;
    private KingTower kingTower;
    private QueenTower[] queenTowers;
    private ArrayList<Troop> troops;
    private ArrayList<Building> buildings;

    public Player(CellType team){
        elixir = 0;
        crown = 0;
        elixirRate = 2;
        this.team = team;
        queenTowers = new QueenTower[2];

        if(team == CellType.PLAYER){

            kingTower = new KingTower(CellType.PLAYER,new Point2D(27,7));
            queenTowers[0] = new QueenTower(CellType.PLAYER,new Point2D(26,1));
            queenTowers[1] = new QueenTower(CellType.PLAYER,new Point2D(26,14));

            for(int i = 0 ; i<4;i++){
                for(int j = 0;j<4;j++){
                    Map.getMap()[27+i][7+j].setCellType(CellType.PLAYER);
                    Map.getMap()[27+i][7+j].setUnit(kingTower);
                }
            }
            for(int i =0 ;i<3;i++){
                for(int j=0;j<3;j++){
                    Map.getMap()[26+i][1+j].setCellType(CellType.PLAYER);
                    Map.getMap()[26+i][1+j].setUnit(queenTowers[0]);
                }
            }
            for(int i =0 ;i<3;i++){
                for(int j=0;j<3;j++){
                    Map.getMap()[26+i][14+j].setCellType(CellType.PLAYER);
                    Map.getMap()[26+i][14+j].setUnit(queenTowers[0]);
                }
            }
        }

        else if(team == CellType.BOT){
            kingTower = new KingTower(team,new Point2D(1,7));
            queenTowers[0] = new QueenTower(team,new Point2D(3,1));
            queenTowers[1] = new QueenTower(team,new Point2D(3,14));
            for(int i = 0 ; i<4;i++){
                for(int j = 0;j<4;j++){
                    Map.getMap()[1+i][7+j].setCellType(CellType.PLAYER);
                    Map.getMap()[1+i][7+j].setUnit(kingTower);
                }
            }
            for(int i =0 ;i<3;i++){
                for(int j=0;j<3;j++){
                    Map.getMap()[3+i][1+j].setCellType(CellType.PLAYER);
                    Map.getMap()[3+i][1+j].setUnit(queenTowers[0]);
                }
            }
            for(int i =0 ;i<3;i++){
                for(int j=0;j<3;j++){
                    Map.getMap()[3+i][14+j].setCellType(CellType.PLAYER);
                    Map.getMap()[3+i][14+j].setUnit(queenTowers[0]);
                }
            }
        }
        View.CRview().spawnBuilding(kingTower);
        View.CRview().spawnBuilding(queenTowers[0]);
        View.CRview().spawnBuilding(queenTowers[1]);

    }

    public synchronized void summonTroop(Type type, Point2D location){
        Troop troop = new Troop(type,team,location);
        Map.getMap()[(int)location.getX()][(int)location.getY()].setUnit(troop);
        troops.add(troop);
    }

    public synchronized void summonBuilding(Type type,Point2D location){
        Building building = new Building(type,team,location);
        Game.gameManager().getUnits().add(building);
        buildings.add(building);
    }

    public void action(){
        for (Iterator<Troop> it = troops.iterator(); it.hasNext();){
            Troop temp  = it.next();
            if(temp.getHp()<0){
                View.CRview().removeTroop(temp);
                temp.setState(State.DEAD);
                it.remove();
                continue;
            }
            if(temp.checkForAttack()){
                if(temp.getState()==State.ATTACKING)continue;
                temp.attack();
            }else {
                temp.move();
            }
        }
        for (Iterator<Building> it = buildings.iterator();it.hasNext();){
            Building temp = it.next();
            if(temp.getHp()<0){
                View.CRview().removeBuilding(temp);
                temp.setState(State.DEAD);
                it.remove();
                continue;
            }
            if(temp.checkForAttack()){
                if(temp.getState() == State.ATTACKING)continue;
                temp.attack();
            }else {
                temp.setState(State.IDLE);
            }
        }
        if (kingTower.getHp()<0){
            kingTower.setState(State.DEAD);
            View.CRview().removeBuilding(kingTower);
        }
        else if(kingTower.checkForAttack() && kingTower.getState()== State.IDLE)kingTower.attack();

        else {
            kingTower.setState(State.IDLE);
        }
        for (QueenTower queenTower : queenTowers){
            if(queenTower.getHp()<0){
                queenTower.setState(State.DEAD);
                View.CRview().removeBuilding(queenTower);
            }
            else if(queenTower.checkForAttack() && queenTower.getState() == State.IDLE)queenTower.attack();
            else {
                queenTower.setState(State.IDLE);
            }
        }
    }

    public void addElixir(){
        elixir += elixirRate;
    }

    public void setElixirRate(int elixirRate) {
        this.elixirRate = elixirRate;
    }

    public int getElixir() {
        return elixir;
    }

    public int getCrown() {
        return crown;
    }

    public QueenTower[] getQueenTowers() {
        return queenTowers;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public int getElixirRate() {
        return elixirRate;
    }

    public CellType getTeam() {
        return team;
    }

    public KingTower getKingTower() {
        return kingTower;
    }

    public void setCrown(int crown) {
        this.crown = crown;
    }

}
