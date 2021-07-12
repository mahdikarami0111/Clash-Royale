package model.game.sharedRecourses;

import model.game.GameManager;

public class Game {
    private static GameManager gameManager;

    public static GameManager gameManager(){
        return gameManager;
    }
}
