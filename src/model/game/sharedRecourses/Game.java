package model.game.sharedRecourses;

import model.game.GameManager;

public class Game {
    private static Game gamInstance = null;
    private GameManager gameManager;

    /**
     * constructor of the class creates an instance of game manager, is private to guarantee there can be only one at atime
     * @param lvl int  lvl of the player
     */
    private Game(int lvl){
        gameManager = new GameManager(lvl);
    }
    /**
     * initialize game manager
     * @param lvl int   level level of player
     */
    public static void initGameManager(int lvl){
        if(gamInstance == null){
            gamInstance = new Game(lvl);
        }
    }

    /**
     *
     * @return the game manager
     */
    public static GameManager gameManager(){
        return gamInstance.gameManager;
    }

    /**
     *
     * @param gamInstance is the game instance that will be set
     */
    public static void setGamInstance(Game gamInstance) {
        Game.gamInstance = gamInstance;
    }

    /**
     *
     * @return the game instance
     */
    public static Game getGamInstance() {
        return gamInstance;
    }
}
