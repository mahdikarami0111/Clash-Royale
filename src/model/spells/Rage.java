package model.spells;

import javafx.geometry.Point2D;
import model.enums.CellType;
import model.game.sharedRecourses.Map;
import model.game.sharedRecourses.View;
import model.units.Troop;
import model.units.Unit;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Rage {
    private static double duration;
    private static int cost = 3;
    private static int radius = 5;


    public static  void attack(Point2D location, CellType team){
        ArrayList<Unit> affectedUnits = new ArrayList<>();
        for(int i = 0;i<32;i++){
            for (int j = 0;j<18;j++){
                CellType t = Map.getMap()[i][j].getCellType();
                Unit unit = Map.getMap()[i][j].getUnit();
                if(t==team && unit != null &&location.distance(new Point2D(i,j))<= radius){
                    if(affectedUnits.contains(unit))continue;
                    unit.setAttackSpeed(unit.getAttackSpeed()/1.4);
                    unit.setDamage((int) (unit.getDamage()*1.4));
                    affectedUnits.add(unit);
                    if(unit instanceof Troop){
                        ((Troop) unit).setMovementSpeed((int)(((Troop) unit).getMovementSpeed()*1.4));
                    }
                }
            }
        }
        View.CRView().rage(location);
        removeEffect(affectedUnits);
    }

    public static void setDuration(double duration) {
        Rage.duration = duration;
    }

    public static void removeEffect(ArrayList<Unit> units){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for(Unit unit : units){
                    unit.setDamage((int)(unit.getDamage()/1.4) );
                    unit.setAttackSpeed(unit.getAttackSpeed()*1.4);
                    if(unit instanceof Troop){
                        ((Troop) unit).setMovementSpeed((int) (((Troop) unit).getMovementSpeed()/1.4));
                    }
                }
            }
        };
        Timer t = new Timer();
        t.schedule(task,(long) duration*1000);
    }

    public static int getCost() {
        return cost;
    }
}
