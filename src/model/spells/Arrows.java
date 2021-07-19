package model.spells;

import javafx.geometry.Point2D;
import model.enums.Type;
import model.game.Cell;
import model.enums.CellType;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.units.Projectile;
import model.units.Unit;

public class Arrows  {
    private static int damage;
    private static double radius = 4;
    private static int cost = 3;

    public static void attack(Point2D location, CellType team) {
        Cell[][] map = Map.getMap();
        int mapLength = 32, mapWidth = 18;
        if (team == CellType.PLAYER) {
            for (int i = 0; i < mapLength; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    if (map[i][j].getUnit() != null && map[i][j].getCellType() == CellType.BOT && inRange(location, new Point2D(i,j))) {
                        Unit unit = map[i][j].getUnit();
                        unit.decreaseHp(damage);
                    }
                }
            }
        } else if (team == CellType.BOT) {
            for (int i = 0; i < mapLength; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    if (map[i][j].getUnit() != null && map[i][j].getCellType() == CellType.PLAYER && inRange(location, new Point2D(i,j))) {
                        Unit unit = map[i][j].getUnit();
                        unit.decreaseHp(damage);
                    }
                }
            }
        }
        Game.gameManager().getPlayer(team).summonProjectile(new Projectile(location.add(-9,-8),location.add(-4,-3), Type.ARROWS));
    }

    public static void setDamage(int damage) {
        Arrows.damage = damage;
    }

    private static boolean inRange(Point2D location, Point2D enemy){
        return Math.abs(location.distance(enemy)) <= radius;
    }
}
