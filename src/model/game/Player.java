package model.game;

import javafx.geometry.Point2D;
import model.enums.Cell;
import model.enums.CellType;
import model.enums.Type;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.units.*;

import java.util.ArrayList;

public class Player {
    private int elixir;
    private int crown;
    private int elixirRate;
    private CellType team;
    private KingTower kingTower;
    private QueenTower[] queenTowers;
    private ArrayList<Troops> troops;
    private ArrayList<Building> buildings;
    private ArrayList<Projectile> projectiles;

    public Player(CellType team){
        elixir = 0;
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

    }

    public void summonTroop(Type type, Point2D location){
        Troops troop = new Troops(type,team,location);
        Map.getMap()[(int)location.getX()][(int)location.getY()].setUnit(troop);
        troops.add(troop);
    }

    public void summonBuilding(Type type,Point2D location){
        Building building = new Building(type,team,location);
        Game.gameManager().getUnits().add(building);
        buildings.add(building);
    }

    public void addElixir(){
        elixir += elixirRate;
    }

    public void setElixirRate(int elixirRate) {
        this.elixirRate = elixirRate;
    }
}
