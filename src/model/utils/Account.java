package model.utils;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    private String username;
    private String passWord;
    private int level;
    private int xp;
    private ArrayList<History> history;

    /**
     *
     * @param username the username of the account
     * @param passWord the password of the account
     */
    public Account(String username, String passWord) {
        this.username = username;
        this.passWord = passWord;
        this.level = 1;
        this.xp = 0;
        history = new ArrayList<>();
    }

    public Account(String username, String passWord, int lvl){
        this(username, passWord);
        this.setLevel(lvl);
    }

    /**
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username is the username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return password
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     *
     * @param passWord set the password
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     *
     * @return user level
     */

    public int getLevel() {
        return level;
    }

    /**
     *
     * @param level is level to be set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     *
     * @return user xp
     */
    public int getXp() {
        return xp;
    }

    /**
     *
     * @param xp is xp to be set
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     *
     * @return list of player history
     */
    public ArrayList<History> getHistory() {
        return history;
    }

    /**
     *
     * @param history is list of history to be set
     */
    public void setHistory(ArrayList<History> history) {
        this.history = history;
    }

    @Override
    public String toString(){
        return String.format("userName: %s , passWord: %s , level: %d",username,passWord,level);
    }

    /**
     *
     * @param o is object to be compared with
     * @return true if o is instance of account and has the same username
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        return getUsername().equals(account.getUsername());
    }

}