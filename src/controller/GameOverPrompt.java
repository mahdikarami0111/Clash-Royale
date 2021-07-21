package controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        window.setScene(new Scene(root,200,200));
        window.showAndWait();
        return menu;
    }
}
