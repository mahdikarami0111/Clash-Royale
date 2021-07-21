package model.spells;

import javafx.geometry.Point2D;
import model.enums.Type;
import model.game.Cell;
import model.enums.CellType;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.units.Projectile;
import model.units.Unit;

import java.util.ArrayList;

public class FireBall{
    private static double radius = 2.5;
    private static int damage;
    private static int cost = 4;

    public static void attack(Point2D location, CellType team) {
        Cell[][] map = Map.getMap();
        ArrayList<Unit> units = new ArrayList<>();
        int mapLength = 32, mapWidth = 18;
        if (team == CellType.PLAYER) {
            for (int i = 0; i < mapLength; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    if (map[i][j].getUnit() != null && map[i][j].getCellType() == CellType.BOT && inRange(location, new Point2D(i,j))) {
                        Unit unit = map[i][j].getUnit();
                        if(units.contains(unit))continue;
                        unit.decreaseHp(damage);
                        units.add(unit);
                    }
                }
            }
        } else if (team == CellType.BOT) {
            for (int i = 0; i < mapLength; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    if (map[i][j].getUnit() != null && map[i][j].getCellType() == CellType.PLAYER && inRange(location, new Point2D(i,j))) {
                        Unit unit = map[i][j].getUnit();
                        if(units.contains(unit))continue;
                        unit.decreaseHp(damage);
                        units.add(unit);
                    }
                }
            }
        }
        if(location.getY()>9){
            Game.gameManager().getPlayer(team).summonProjectile(new Projectile(location.add(-7,-9),location.add(-4,-3), Type.FIREBALL,3));
        }
        else if(location.getY()<=9){
            Game.gameManager().getPlayer(team).summonProjectile(new Projectile(location.add(-7,+3),location.add(-4,-2), Type.FIREBALL,3));
        }
    }




    private static boolean inRange(Point2D location, Point2D enemy){
        return Math.abs(location.distance(enemy)) <= radius;
    }

    public static void setDamage(int damage) {
        FireBall.damage = damage;
    }

    public static int getCost() {
        return cost;
    }
}
