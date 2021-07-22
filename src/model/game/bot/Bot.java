package model.game.bot;

import model.game.Player;

/**
 * Bot class represents a player that will perform actions for the bot
 */
public abstract class Bot {
    protected Player bot;

    public Bot(Player bot) {
        this.bot = bot;
    }

    /**
     * play for bot
     */
    public abstract void play();
}
