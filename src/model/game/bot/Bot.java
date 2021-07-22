package model.game.bot;

import model.game.Player;

public abstract class Bot {
    protected Player bot;

    public Bot(Player bot) {
        this.bot = bot;
    }

    public abstract void play();
}
