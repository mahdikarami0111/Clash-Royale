package model.units;

import model.enums.CellType;
import model.enums.Type;

import javafx.geometry.Point2D;
import model.game.sharedRecourses.Game;

import java.util.Timer;
import java.util.TimerTask;

public class Building extends Unit{
    private long lifespan;

    public Building(Type type, CellType team, Point2D location){
        super(type,team,location);
        this.lifespan = 1000L *Game.gameManager().getUnitInformationHashMap().get(type).lifespan;

    }

    public void tick(){
        lifespan -= 30;
        if(lifespan <= 0){
            this.hp = 0;
        }
    }

}
