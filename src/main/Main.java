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
//        Group root = new Group();
//        CRView view = new CRView(root);
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 576, 1024));
//        primaryStage.show();
//        View.initView(view);
//        Game.initGameManager(1);
//        Game.gameManager().start();
//        int i = 1;
//        for(Type type : Type.values()){
//            if(View.CRView().isMutable(type)){
//                Button button = new Button(type.name());
//                Button gg = new Button(type.name());
//                button.setOnAction(event -> {
//                    Game.gameManager().spawnTroop(new Point2D(25,8),Game.gameManager().getPlayer(),type);
//                });
//                gg.setOnAction(e->{
//                    Game.gameManager().spawnTroop(new Point2D(9,8),Game.gameManager().getBot(),type);
//                });
//                View.CRView().getGroup().getChildren().add(gg);
//                View.CRView().getGroup().getChildren().add(button);
//                button.setLayoutX(i);
//                button.setLayoutY(1000);
//                gg.setLayoutX(i);
//                i+=70;
//            }
//        }
//
//        Button b1 = new Button("RAGE");
//        b1.setOnAction(e ->{
//            Rage.attack(new Point2D(9,3), CellType.PLAYER);
//        });
//        b1.setLayoutY(950);
//        View.CRView().getGroup().getChildren().add(b1);
//
//        b1 = new Button("ARROWS");
//        b1.setOnAction(e ->{
//            Arrows.attack(new Point2D(9,3), CellType.PLAYER);
//        });
//        b1.setLayoutX(50);
//        b1.setLayoutY(950);
//        View.CRView().getGroup().getChildren().add(b1);
//
//        b1 = new Button("FIREBALL");
//        b1.setOnAction(e ->{
//            FireBall.attack(new Point2D(9,3), CellType.PLAYER);
//        });
//        b1.setLayoutX(90);
//        b1.setLayoutY(950);
//        View.CRView().getGroup().getChildren().add(b1);
//
//        b1 = new Button("CANNON");
//        b1.setOnAction(e ->{
//            Game.gameManager().spawnBuilding(new Point2D(20,4),Game.gameManager().getPlayer(),Type.CANNON);
//        });
//        b1.setLayoutX(150);
//        b1.setLayoutY(950);
//        View.CRView().getGroup().getChildren().add(b1);
//
//        b1 = new Button("INFERNO");
//        b1.setOnAction(e ->{
//            Game.gameManager().spawnBuilding(new Point2D(20,4),Game.gameManager().getPlayer(), Type.INFERNO_TOWER);
//        });
//        b1.setLayoutX(220);
//        b1.setLayoutY(950);
//        View.CRView().getGroup().getChildren().add(b1);
//
//
//        Task task = new Task() {
//            @Override
//            protected Object call() {
//                while (true){
//                    Game.gameManager().tick();
//                    View.CRView().render();
//                    try {
//                        Thread.sleep(30);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        new Thread(task).start();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/fxml/Game.fxml"));
        Parent root = loader.load();
        GameController gc = loader.getController();
        ArrayList<Type> deck = new ArrayList<>();
        deck.add(Type.BARBARIAN);
        deck.add(Type.VALKYRIE);
        deck.add(Type.ARCHER);
        deck.add(Type.WIZARD);
        deck.add(Type.CANNON);
        deck.add(Type.RAGE);
        deck.add(Type.BABY_DRAGON);
        deck.add(Type.MINI_PEKKA);
        gc.setDeck(deck);
        gc.setGameManager(1);
        gc.setView();
        primaryStage.setTitle("Clash Royal");
        primaryStage.setScene(new Scene(root,576,1124));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
