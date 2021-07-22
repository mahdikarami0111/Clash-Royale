package model.game.sharedRecourses;

import model.game.Cell;
import model.enums.CellType;

/**
 * the map of game is a 2D array of cells
 */
public class Map {
    private static Map instance = null;

    private Cell[][] map;

    /**
     * create a singleton map
     */
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

    /**
     *
     * @return the map
     */
    public static Cell[][] getMap(){
        if(instance == null){
            instance = new Map();
        }
        return instance.map;
    }

    /**
     *
     * @param instance is the map instance to be set
     */
    public static void setInstance(Map instance) {
        Map.instance = instance;
    }
}
