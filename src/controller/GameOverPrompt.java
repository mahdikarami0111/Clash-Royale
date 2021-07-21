package controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.enums.CellType;
import model.game.sharedRecourses.Game;
import model.utils.History;
import model.utils.ProfileHandler;

public class GameOverPrompt {


    public static boolean menu;

    public static boolean show(){
        Stage window  =new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game Over");
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Button b1 = new Button("Menu");
        Button b2 = new Button("Exit");
        Label label = new Label();
        label.setFont(Font.font("Arial", 15));
        label.setText("winner is : "+Game.gameManager().winner());
        if (Game.gameManager().winner() == CellType.PLAYER){
            ProfileHandler.getCurrentUser().getHistory().add(new History(true));
            ProfileHandler.getCurrentUser().setXp(ProfileHandler.getCurrentUser().getXp()+200);
        }
        else {
            ProfileHandler.getCurrentUser().getHistory().add(new History(false));
            ProfileHandler.getCurrentUser().setXp(ProfileHandler.getCurrentUser().getXp()+70);
        }
        int xp = ProfileHandler.getCurrentUser().getXp();
        System.out.println(xp);
        if(xp > 2500){
            ProfileHandler.getCurrentUser().setLevel(5);
        }
        else if(xp>1700){
            ProfileHandler.getCurrentUser().setLevel(4);
        }
        else if(xp>900){
            ProfileHandler.getCurrentUser().setLevel(3);
        }
        else if(xp>500){
            ProfileHandler.getCurrentUser().setLevel(2);
        }
        else if(xp<300){
            ProfileHandler.getCurrentUser().setLevel(1);
        }

        ProfileHandler.writeAccounts();

        root.getChildren().add(label);
        root.getChildren().add(b1);
        root.getChildren().add(b2);
        b1.setOnAction(e ->{
            menu = true;
            window.close();
        });
        b2.setOnAction(e ->{
            menu = false;
            window.close();
        });
        window.setScene(new Scene(root,200,150));
        window.showAndWait();
        return menu;
    }
}