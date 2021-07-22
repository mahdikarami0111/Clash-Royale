package model.utils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * the date and outcome of a match
 */
public class History implements Serializable {
    private boolean won;
    private String time;
    private transient LocalDateTime localDateTime;

    public History(boolean won){
        localDateTime = LocalDateTime.now();
        this.won = won;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm");
        time = localDateTime.format(formatter);
    }

    @Override
    public String toString(){
        return String.format("date and time) %s , match outcome) %s",time,won ? "won" : "lost");
    }

    /**
     *
     * @return true if match is won, false if not
     */
    public boolean isWon() {
        return won;
    }

    /**
     *
     * @return the time that a match was finished
     */
    public String getTime() {
        return time;
    }
}