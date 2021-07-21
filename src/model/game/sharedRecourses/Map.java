package model.game.sharedRecourses;

import model.game.Cell;
import model.enums.CellType;

public class Map {
    private static Map instance = null;

    private Cell[][] map;

    private Map(){
        map = new Cell[32][18];
        for(int i = 0;i<32;i++){
            for(int j = 0;j<18;j++){
                if(i==15 || i== 16){
                    if(j==2||j==3||j==14||j==15){
                        map[i][j] = new Cell(CellType.PATH);
                    }
                    else {
                        map[i][j] = new Cell(CellType.BLOCK);
                    }
                }
                else {
                    map[i][j] = new Cell(CellType.PATH);
                }
            }
        }
    }

    public static Cell[][] getMap(){
        if(instance == null){
            instance = new Map();
        }
        return instance.map;
    }

    public void print(){
        for(int i = 0;i<32;i++){
            for(int j = 0;j<18;j++){
                Cell cell = Map.getMap()[i][j];
                if(cell.getCellType()==CellType.BOT)System.out.print("B");
                if(cell.getCellType()==CellType.PLAYER)System.out.print("P");
                if(cell.getCellType()==CellType.PATH)System.out.print("O");
                if(cell.getCellType()==CellType.BLOCK)System.out.print("X");
            }
            System.out.println();
        }
    }

    public static void setInstance(Map instance) {
        Map.instance = instance;
    }
}
