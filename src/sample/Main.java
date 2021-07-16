package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.enums.CellType;
import model.enums.Type;
import model.game.Cell;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.game.sharedRecourses.View;
import view.CRView;

import java.awt.*;
import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        CRView root = new CRView();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 576, 1024));
        primaryStage.show();
        View.initView(root);
        Game.initGameManager(1);
        Game.gameManager().start();
        Button b = new Button();
        b.setOnAction(event -> {
            Game.gameManager().spawnBuilding(new Point2D(20,7),Game.gameManager().getPlayer(),Type.CANNON);
        });
        root.getChildren().add(b);
        Task task = new Task() {
            @Override
            protected Object call() {
                while (true){
                    Game.gameManager().tick();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(task).start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
