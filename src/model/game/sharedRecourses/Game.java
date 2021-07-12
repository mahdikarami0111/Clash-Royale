package model.game.sharedRecourses;

import model.game.GameManager;

public class Game {
    private static Game gamInstance = null;
    private GameManager gameManager;

    private Game(int lvl){
        gameManager = new GameManager(lvl);
    }

    public static void initGameManager(int lvl){
        if(gamInstance == null){
            gamInstance = new Game(lvl);
        }
    }

    public static GameManager gameManager(){
        return gamInstance.gameManager;
    }
}
