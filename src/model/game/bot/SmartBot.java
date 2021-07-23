package model.game.bot;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import model.enums.CellType;
import model.enums.Type;
import model.game.Player;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.game.sharedRecourses.View;
import model.units.KingTower;
import model.units.QueenTower;

import java.util.Random;

/**
 * extends fromBot class
 */
public class SmartBot extends Bot{
    public SmartBot(Player bot) {
        super(bot);
    }

    /**
     * place units for bot with a relative intelligence
     */
    @Override
    public void play() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(bot.getElixir()>6){
                    int left = 0;
                    int right = 0;
                    for(int i =0;i<32;i++){
                        for(int j = 0;j<18;j++){
                            if(Map.getMap()[i][j].getCellType()== CellType.PLAYER){
                                if(j>=9)right++;
                                else{
                                    left++;
                                }
                            }
                        }
                    }

                    Random random = new Random();
                    Point2D location = new Point2D(10,10);
                    Type t = Game.gameManager().getDeck().get(random.nextInt(8));


                    if(t == Type.RAGE){
                        boolean label = true;
                        for(int i =0;i<32&&label;i++){
                            for(int j = 0;j<18;j++){
                                if(Map.getMap()[i][j].getCellType()==CellType.BOT && !(Map.getMap()[i][j].getUnit() instanceof KingTower) && !(Map.getMap()[i][j].getUnit() instanceof QueenTower)){
                                    location = new Point2D(i,j);
                                    label = false;
                                    break;
                                }
                            }
                        }
                        Game.gameManager().rage(CellType.BOT,location);
                    }


                    else if(t == Type.FIREBALL){
                        boolean label = true;
                        for(int i =0;i<32 && label;i++){
                            for(int j = 0;j<18;j++){
                                if(Map.getMap()[i][j].getCellType()==CellType.PLAYER){
                                    location = new Point2D(i,j);
                                    label = false;
                                    break;
                                }
                            }
                        }
                        Game.gameManager().fireball(CellType.BOT,location);
                    }


                    else if(t == Type.ARROWS){
                        boolean label = true;
                        for(int i =0;i<32&&label;i++){
                            for(int j = 0;j<18;j++){
                                if(Map.getMap()[i][j].getCellType()==CellType.PLAYER){
                                    location = new Point2D(i,j);
                                    label = false;
                                    break;
                                }
                            }
                        }
                        Game.gameManager().arrows(CellType.BOT,location);
                    }


                    else if(View.CRView().isMutable(t)){
                        int y ,x;
                        if(left>= right){
                            y = random.nextInt(5)+3;
                        }
                        else{
                            y = random.nextInt(5)+10;
                        }
                        if(Game.gameManager().getUnitInformationHashMap().get(t).range>=2){
                            x = 7;
                        }
                        else {
                            x = 12;
                        }
                        location = new Point2D(x,y);
                        Game.gameManager().spawnTroop(location,bot,t);
                    }


                    else if(t == Type.CANNON){
                        int y;
                        if(left>= right){
                            y = random.nextInt(5)+3;
                        }
                        else{
                            y = random.nextInt(5)+10;
                        }
                        location  =new Point2D(7,y);
                        Game.gameManager().spawnBuilding(location,bot,t);
                    }


                    else if(t == Type.INFERNO_TOWER){
                        int y;
                        if(left>= right){
                            y = random.nextInt(5)+3;
                        }
                        else{
                            y = random.nextInt(5)+10;
                        }
                        location  =new Point2D(7,y);
                        Game.gameManager().spawnBuilding(location,bot,t);
                    }
                }
            }
        });
    }
}
