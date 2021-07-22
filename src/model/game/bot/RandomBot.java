package model.game.bot;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import model.enums.CellType;
import model.enums.Type;
import model.game.Player;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.View;

import java.util.Random;

/**
 * extends for Bot class
 */
public class RandomBot extends Bot{

    public RandomBot(Player bot) {
        super(bot);
    }

    /**
     * randomly place units for bot
     */
    @Override
    public void play() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(bot.getElixir()>6){
                    Random random = new Random();
                    Point2D location = new Point2D(10,random.nextInt(8)+4);
                    Type t = Game.gameManager().getDeck().get(random.nextInt(8));
                    if(t == Type.RAGE){
                        Game.gameManager().rage(CellType.BOT,location);
                    }
                    else if(t == Type.FIREBALL){
                        Game.gameManager().fireball(CellType.BOT,location);
                    }
                    else if(t == Type.ARROWS){
                        Game.gameManager().arrows(CellType.BOT,location);
                    }
                    else if(View.CRView().isMutable(t)){
                        Game.gameManager().spawnTroop(location,bot,t);
                    }
                    else if(t == Type.CANNON){
                        Game.gameManager().spawnBuilding(location,bot,t);
                    }
                    else if(t == Type.INFERNO_TOWER){
                        Game.gameManager().spawnBuilding(location,bot,t);
                    }
                }
            }
        });
    }

}
