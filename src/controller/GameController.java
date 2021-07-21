package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.enums.Type;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.View;
import model.units.KingTower;
import view.CRView;

import java.net.URL;
import java.util.*;

public class GameController implements Initializable {
    @FXML
    private Group group;

    @FXML
    private ImageView c4;

    @FXML
    private ImageView c3;

    @FXML
    private ImageView c2;

    @FXML
    private ImageView c1;

    @FXML
    private ImageView c0;


    @FXML
    private TextField PlayerCrown;

    @FXML
    private TextField BotCrown;

    @FXML
    private TextField timer;

    @FXML
    private TextField elixir;


    private ArrayList<Type> deck;
    private CardManager cardManager;
    private HashMap<Type, Image> images;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        images = new HashMap<>();
        for (Type type : Type.values()){
            if(type == Type.KING_TOWER || type==Type.QUEEN_TOWER)continue;
            images.put(type,new Image("recourses/Cards/"+type.name()+".png"));
        }
    }

    @FXML
    void start(ActionEvent event) {
        cardManager.initialize();
        c0.setImage(images.get(cardManager.getCards()[0]));
        c1.setImage(images.get(cardManager.getCards()[1]));
        c2.setImage(images.get(cardManager.getCards()[2]));
        c3.setImage(images.get(cardManager.getCards()[3]));
        c4.setImage(images.get(cardManager.getCards()[4]));
        Game.gameManager().start();
//        Task task = new Task() {
//            @Override
//            protected Object call() {
//                while (!Game.gameManager().gameOver()){
//                    Game.gameManager().tick();
//                    View.CRView().render();
//                    try {
//                        Thread.sleep(30);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                System.out.println("game over");
//                return null;
//            }
//        };
//        new Thread(task).start();
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(Game.gameManager().gameOver()){
                            t.cancel();
                            GameOverPrompt.show();
                        }
                        Game.gameManager().tick();
                        View.CRView().render();
                    }
                });
            }
        };
        t.schedule(task,0,30);
    }


    public void setDeck(ArrayList<Type> deck) {
        this.deck = deck;
        cardManager = new CardManager(deck);
    }

    public void setGameManager(int lvl, int botDifficulty){
        Game.gameManager().setBotDiffculty(botDifficulty);
        Game.initGameManager(lvl);
        Game.gameManager().setDeck(deck);
    }

    public void setView(){
        View.initView(new CRView(group));
        View.CRView().setBotCrown(BotCrown);
        View.CRView().setTimer(timer);
        View.CRView().setElixir(elixir);
        View.CRView().setPlayerCrown(PlayerCrown);
    }

    @FXML
    void card0(MouseEvent event) {
        group.setOnMouseClicked(e ->{
            int x,y;
            x = (int) Math.floor(e.getSceneY()/32);
            y = (int) Math.floor(e.getSceneX()/32);
            cardManager.draw(0,new Point2D(x,y));
            c0.setImage(images.get(cardManager.getCards()[0]));
            c4.setImage(images.get(cardManager.getCards()[4]));
        });
    }

    @FXML
    void card1(MouseEvent event) {
        group.setOnMouseClicked(e ->{
            int x,y;
            x = (int) Math.floor(e.getSceneY()/32);
            y = (int) Math.floor(e.getSceneX()/32);
            cardManager.draw(1,new Point2D(x,y));
            c1.setImage(images.get(cardManager.getCards()[1]));
            c4.setImage(images.get(cardManager.getCards()[4]));
        });

    }

    @FXML
    void card2(MouseEvent event) {
        group.setOnMouseClicked(e ->{
            int x,y;
            x = (int) Math.floor(e.getSceneY()/32);
            y = (int) Math.floor(e.getSceneX()/32);
            cardManager.draw(2,new Point2D(x,y));
            c2.setImage(images.get(cardManager.getCards()[2]));
            c4.setImage(images.get(cardManager.getCards()[4]));
        });
    }

    @FXML
    void card3(MouseEvent event) {
        group.setOnMouseClicked(e ->{
            int x,y;
            x = (int) Math.floor(e.getSceneY()/32);
            y = (int) Math.floor(e.getSceneX()/32);
            cardManager.draw(3,new Point2D(x,y));
            c3.setImage(images.get(cardManager.getCards()[3]));
            c4.setImage(images.get(cardManager.getCards()[4]));
        });
    }
}
