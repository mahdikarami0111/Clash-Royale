package main;

import controller.GameController;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.enums.CellType;
import model.enums.Type;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.View;
import model.spells.Arrows;
import model.spells.FireBall;
import model.spells.Rage;
import view.CRView;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/login.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Clash Royal");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
