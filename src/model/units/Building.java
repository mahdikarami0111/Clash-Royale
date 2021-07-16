package model.units;

import model.enums.CellType;
import model.enums.Type;

import javafx.geometry.Point2D;
import model.game.sharedRecourses.Game;

import java.util.Timer;
import java.util.TimerTask;

public class Building extends Unit{
    private int lifespan;

    public Building(Type type, CellType team, Point2D location){
        super(type,team,location);
//        this.lifespan = Game.gameManager().getUnitInformationHashMap().get(type).lifespan;
        this.lifespan = 5;
        timer();
    }

    public void timer(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setHp(0);
                System.out.println("DETONATED");
            }
        };
        Timer t = new Timer();
        t.schedule(task,lifespan* 1000L);
    }
}
