package sample;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.enums.CellType;
import model.enums.Type;
import model.game.GameManager;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.Map;
import model.game.sharedRecourses.View;
import model.spells.Arrows;
import model.spells.FireBall;
import model.spells.Rage;
import view.CRView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        CRView view = new CRView(root);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 576, 1024));
        primaryStage.show();
        View.initView(view);
        Game.initGameManager(1);
        Game.gameManager().start();
//        for (Type type : Type.values()){
//            if(View.CRView().isMutable(type)){
//                Button b = new Button(type.name());
//                b.setOnAction(event -> {
//                   Game.gameManager().spawnTroop(new Point2D(22,7),Game.gameManager().getPlayer(),type);
//                });
//                View.CRView().getChildren().add(b);
//            }
//        }
        int i = 1;
        for(Type type : Type.values()){
            if(View.CRView().isMutable(type)){
                Button button = new Button(type.name());
                Button gg = new Button(type.name());
                button.setOnAction(event -> {
                    Game.gameManager().spawnTroop(new Point2D(25,8),Game.gameManager().getPlayer(),type);
                });
                gg.setOnAction(e->{
                    Game.gameManager().spawnTroop(new Point2D(9,8),Game.gameManager().getBot(),type);
                });
                View.CRView().getGroup().getChildren().add(gg);
                View.CRView().getGroup().getChildren().add(button);
                button.setLayoutX(i);
                button.setLayoutY(1000);
                gg.setLayoutX(i);
                i+=70;
            }
        }

        Button b1 = new Button("RAGE");
        b1.setOnAction(e ->{
            Rage.attack(new Point2D(9,3), CellType.PLAYER);
        });
        b1.setLayoutY(950);
        View.CRView().getGroup().getChildren().add(b1);

        b1 = new Button("ARROWS");
        b1.setOnAction(e ->{
            Arrows.attack(new Point2D(9,3), CellType.PLAYER);
        });
        b1.setLayoutX(50);
        b1.setLayoutY(950);
        View.CRView().getGroup().getChildren().add(b1);

        b1 = new Button("FIREBALL");
        b1.setOnAction(e ->{
            FireBall.attack(new Point2D(9,3), CellType.PLAYER);
        });
        b1.setLayoutX(90);
        b1.setLayoutY(950);
        View.CRView().getGroup().getChildren().add(b1);


        Task task = new Task() {
            @Override
            protected Object call() {
                while (true){
                    Game.gameManager().tick();
                    View.CRView().render();
                    try {
                        Thread.sleep(30);
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
