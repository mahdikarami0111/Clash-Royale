package controller;

import javafx.geometry.Point2D;
import model.enums.CellType;
import model.enums.Type;
import model.game.Player;
import model.game.sharedRecourses.Game;

import java.util.ArrayList;
import java.util.Random;
/**
 * card manager for bots
 */
public class CardManager {
    private Type[] cards;
    private ArrayList<Type> deck;
    /**
     *
     * @param deck initialize deck and instance cards
     */
    public CardManager(ArrayList<Type> deck) {
        this.deck = deck;
        cards = new Type[5];
    }
    /**
     * initialize cards
     */
    public void initialize(){
        for (int i = 0;i<5;i++){
            cards[i] = deck.get(i);
        }
    }
    /**
     * generate new random card
     * @return card (instance from enum Type)
     */
    public Type newCard(){
        Random rand = new Random();
        boolean done = false;
        Type temp = deck.get(0);
        while (!done){
            temp = deck.get(rand.nextInt(8));
            if(!isInDeck(temp)){
                done = true;
            }
        }
        return temp;
    }

    /**
     *
     * @param type card to be checked
     * @return true if this card exists in deck
     */
    public boolean isInDeck(Type type){
        for(int i = 0 ; i< 4;i++){
            if(cards[i]==type)return true;
        }
        return false;
    }

    /**
     *
     * @param type card to be checked
     * @return true if it is a troop such as barbarian, archer, wizard or ...
     */
    public boolean isTroop(Type type){
        return type != Type.CANNON && type != Type.INFERNO_TOWER && type != Type.KING_TOWER && type != Type.QUEEN_TOWER && type != Type.ARROWS && type != Type.FIREBALL && type != Type.RAGE;
    }

    /**
     *
     * @param i is (i+1)th card to be chosen in game
     * @param location of deployment
     * upon successful deployment, a card on screen will be changed
     */
    public void draw(int i, Point2D location){
        Type t = cards[i];
        boolean done = false;
        if(t == Type.RAGE){
            done = Game.gameManager().rage(CellType.PLAYER,location);
        }
        else if(t == Type.FIREBALL){
            done = Game.gameManager().fireball(CellType.PLAYER,location);
        }
        else if(t == Type.ARROWS){
            done = Game.gameManager().arrows(CellType.PLAYER,location);
        }
        else if(isTroop(t)){
            done = Game.gameManager().spawnTroop(location,Game.gameManager().getPlayer(CellType.PLAYER),t);
        }
        else if(t == Type.CANNON){
            done = Game.gameManager().spawnBuilding(location,Game.gameManager().getPlayer(CellType.PLAYER),t);
        }
        else if(t == Type.INFERNO_TOWER){
            done = Game.gameManager().spawnBuilding(location,Game.gameManager().getPlayer(CellType.PLAYER),t);
        }
        if(done){
            cards[i] = cards[4];
            cards[4] = newCard();
        }
    }

    public Type[] getCards() {
        return cards;
    }
}
