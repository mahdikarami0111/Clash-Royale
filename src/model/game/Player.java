package model.game;

import javafx.geometry.Point2D;
import model.enums.CellType;
import model.enums.State;
import model.enums.Type;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.game.sharedRecourses.View;
import model.units.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    private ArrayList<Projectile> projectiles;

    public Player(CellType team){
        elixir = 4;
        crown = 0;
        elixirRate = 1;
        this.team = team;
        queenTowers = new QueenTower[2];
        troops = new ArrayList<>();
        buildings = new ArrayList<>();
        projectiles = new ArrayList<>();

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

        else {
            kingTower = new KingTower(team,new Point2D(1,7));
            queenTowers[0] = new QueenTower(team,new Point2D(3,1));
            queenTowers[1] = new QueenTower(team,new Point2D(3,14));
            for(int i = 0 ; i<4;i++){
                for(int j = 0;j<4;j++){
                    Map.getMap()[1+i][7+j].setCellType(CellType.BOT);
                    Map.getMap()[1+i][7+j].setUnit(kingTower);
                }
            }
            for(int i =0 ;i<3;i++){
                for(int j=0;j<3;j++){
                    Map.getMap()[3+i][1+j].setCellType(CellType.BOT);
                    Map.getMap()[3+i][1+j].setUnit(queenTowers[0]);
                }
            }
            for(int i =0 ;i<3;i++){
                for(int j=0;j<3;j++){
                    Map.getMap()[3+i][14+j].setCellType(CellType.BOT);
                    Map.getMap()[3+i][14+j].setUnit(queenTowers[1]);
                }
            }
        }
        View.CRView().spawnBuilding(kingTower);
        View.CRView().spawnBuilding(queenTowers[0]);
        View.CRView().spawnBuilding(queenTowers[1]);

    }

    public synchronized void summonTroop(Type type, Point2D location){
        Troop troop = new Troop(type,team,location);
        Map.getMap()[(int)location.getX()][(int)location.getY()].setUnit(troop);
        Map.getMap()[(int)location.getX()][(int)location.getY()].setCellType(team);
        troops.add(troop);
        View.CRView().spawnTroop(troop);
    }

    public synchronized void summonBuilding(Type type,Point2D location){
        Building building = new Building(type,team,location);
        Map.getMap()[(int)location.getX()][(int)location.getY()].setUnit(building);
        Map.getMap()[(int)location.getX()][(int)location.getY()].setCellType(team);
        buildings.add(building);
        View.CRView().spawnBuilding(building);
    }

    public synchronized void summonProjectile(Projectile projectile){
        projectiles.add(projectile);
        View.CRView().spawnProjectile(projectile);
    }

    public synchronized void action(){


        for (Iterator<Troop> it = troops.iterator(); it.hasNext();){
            Troop temp  = it.next();
            if(temp.getHp()<=0){
                View.CRView().removeTroop(temp);
                temp.setState(State.DEAD);
                Map.getMap()[(int)temp.getCurrentLocation().getX()][(int)temp.getCurrentLocation().getY()].setUnit(null);
                Map.getMap()[(int)temp.getCurrentLocation().getX()][(int)temp.getCurrentLocation().getY()].setCellType(CellType.PATH);
                it.remove();
                continue;
            }
            if(temp.checkForAttack()){
                temp.attack();
            }else {
                temp.move();
            }
        }



        for (Iterator<Building> it = buildings.iterator();it.hasNext();){
            Building temp = it.next();
            temp.tick();
            if(temp.getHp()<=0){
                View.CRView().removeBuilding(temp);
                temp.setState(State.DEAD);
                Map.getMap()[(int)temp.getCurrentLocation().getX()][(int)temp.getCurrentLocation().getY()].setUnit(null);
                Map.getMap()[(int)temp.getCurrentLocation().getX()][(int)temp.getCurrentLocation().getY()].setCellType(CellType.PATH);
                it.remove();
                continue;
            }
            if(temp.checkForAttack()){
                temp.attack();
            }else {
                temp.setState(State.IDLE);
            }
        }


        for (Iterator<Projectile> it = projectiles.iterator();it.hasNext();){
            Projectile temp = it.next();
            if(temp.hasReached()){
                View.CRView().removeProjectile(temp);
                it.remove();
            }
            else {
                temp.move();
            }
        }


        if(kingTower.getState() != State.DEAD){
            if (kingTower.getHp()<=0){
                kingTower.setState(State.DEAD);
                clearKingMap();
                Game.gameManager().updateCrowns(this,3);
                View.CRView().removeBuilding(kingTower);
            }
            else if(kingTower.checkForAttack())kingTower.attack();

            else {
                kingTower.setState(State.IDLE);
            }
        }



        for (QueenTower queenTower : queenTowers){
            if(queenTower.getState() == State.DEAD)continue;
            if(queenTower.getHp()<=0){
                queenTower.setState(State.DEAD);
                clearQueenMap(queenTower);
                Game.gameManager().updateCrowns(this,1);
                View.CRView().removeBuilding(queenTower);
            }
            else if(queenTower.checkForAttack())queenTower.attack();
            else {
                queenTower.setState(State.IDLE);
            }
        }
    }

    public void clearKingMap(){
        for (int i = 0;i<4;i++){
            for (int j = 0;j<4;j++){
                Map.getMap()[(int)kingTower.getCurrentLocation().getX()+i][(int)kingTower.getCurrentLocation().getY()+j].setUnit(null);
                Map.getMap()[(int)kingTower.getCurrentLocation().getX()+i][(int)kingTower.getCurrentLocation().getY()+j].setCellType(CellType.PATH);
            }
        }
    }

    public void clearQueenMap(QueenTower queenTower){
        for (int i = 0;i<3;i++){
            for (int j = 0;j<3;j++){
                Map.getMap()[(int)queenTower.getCurrentLocation().getX()+i][(int)queenTower.getCurrentLocation().getY()+j].setUnit(null);
                Map.getMap()[(int)queenTower.getCurrentLocation().getX()+i][(int)queenTower.getCurrentLocation().getY()+j].setCellType(CellType.PATH);
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

    public synchronized void setElixir(int elixir) {
        this.elixir = elixir;
    }

    public boolean queenIsDead(){
        for (QueenTower queenTower : queenTowers){
            if(queenTower.getState() == State.DEAD)return true;
        }
        return false;
    }
}
