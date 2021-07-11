package model.game;

import model.enums.Cell;

public class Map {
    private static Map instance = null;

    private Cell[][] map;

    private Map(){
        //read map data from file and put in map variable
    }

    public static Cell[][] getMap(){
        if(instance == null){
            instance = new Map();
        }
        return instance.map;
    }
}
