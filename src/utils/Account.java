package utils;

import java.util.ArrayList;

public class Account {
    private String username;
    private String passWord;
    private int level;
    private int xp;
    private ArrayList<History> history;

    public Account(String username, String passWord) {
        this.username = username;
        this.passWord = passWord;
        this.level = 1;
        this.xp = 0;
        history = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public ArrayList<History> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<History> history) {
        this.history = history;
    }
}
