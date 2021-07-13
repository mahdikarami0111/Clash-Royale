package model.game.sharedRecourses;

import model.enums.Cell;
import model.enums.CellType;

public class Map {
    private static Map instance = null;

    private Cell[][] map;

    private Map(){
        //temporary map for testing
        //real map will be read froma file
        map = new Cell[32][18];
        for(int i = 0;i<32;i++){
            for(int j = 0;j<18;j++){
                map[i][j] = new Cell(CellType.PATH);
                map[i][j].setX(i);
                map[i][j].setY(j);
            }
        }
    }

    public static Cell[][] getMap(){
        if(instance == null){
            instance = new Map();
        }
        return instance.map;
    }
}
