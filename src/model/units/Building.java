package model.units;

import model.enums.CellType;
import model.enums.Type;

import javafx.geometry.Point2D;
import model.game.sharedRecourses.Game;

import java.util.Timer;
import java.util.TimerTask;

/**
 * building extends unit
 */
public class Building extends Unit{
    private long lifespan;

    public Building(Type type, CellType team, Point2D location){
        super(type,team,location);
        this.lifespan = 1000L *Game.gameManager().getUnitInformationHashMap().get(type).lifespan;

    }

    /**
     * a tick happens every 30ms -> 30ms is reduced from building life spam
     */
    public void tick(){
        lifespan -= 30;
        if(lifespan <= 0){
            this.hp = 0;
        }
    }

}
