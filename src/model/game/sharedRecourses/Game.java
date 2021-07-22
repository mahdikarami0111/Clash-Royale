package model.game.sharedRecourses;

import model.game.GameManager;

public class Game {
    private static Game gamInstance = null;
    private GameManager gameManager;

    /**
     *
     * @param lvl is user level
     *            game manager is created based on users level
     */
    private Game(int lvl){
        gameManager = new GameManager(lvl);
    }

    /**
     *
     * @param lvl user level
     *            initialize game manager
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
