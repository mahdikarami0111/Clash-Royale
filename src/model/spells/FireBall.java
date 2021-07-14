package model.spells;

import javafx.geometry.Point2D;
import model.enums.Cell;
import model.enums.CellType;
import model.game.sharedRecourses.Map;
import model.units.KingTower;
import model.units.QueenTower;
import model.units.Unit;

import java.util.ArrayList;

public class FireBall {
    private static int radius;
    private static int damage;
    private static int cost;


    public static void attack(Point2D location, CellType team) {
        //map length and width
        //convert cell to point2d( Cell class)
        Cell[][] map = Map.getMap();
        int mapLength = 32, mapWidth = 18;
        if (team == CellType.PLAYER) {
            for (int i = 0; i < mapLength; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    if (map[i][j] != null && map[i][j].getCellType() == CellType.BOT && inRange(location, new Point2D(i,j))) {
                        Unit unit = map[i][j].getUnit();
                        unit.decreaseHp(damage);
                    }
                }
            }
        } else if (team == CellType.BOT) {
            for (int i = 0; i < mapLength; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    if (map[i][j] != null && map[i][j].getCellType() == CellType.PLAYER && inRange(location, new Point2D(i,j))) {
                        Unit unit = map[i][j].getUnit();
                        unit.decreaseHp(damage);
                    }
                }
            }


        }
    }

    private static boolean inRange(Point2D location, Point2D enemy){
        return Math.abs(location.distance(enemy)) <= radius;
    }


}
