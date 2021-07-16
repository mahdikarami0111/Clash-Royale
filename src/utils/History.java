package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class History {
    private boolean won;
    private String time;
    private LocalDateTime localDateTime;

    public History(boolean won){
        localDateTime = LocalDateTime.now();
        this.won = won;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm");
        time = localDateTime.format(formatter);

    }

    @Override
    public String toString(){
        return String.format("date and time) %s , match outcome) %s",time,won);
    }



}
