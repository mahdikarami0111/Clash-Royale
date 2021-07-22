package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.enums.Type;
import model.game.sharedRecourses.Game;
import model.game.sharedRecourses.View;
import model.utils.ProfileHandler;
import view.CRView;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * main controller of game
 */
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

    /**
     * starts the game
     * @param event
     */
    @FXML
    void start(ActionEvent event) {
        cardManager.initialize();
        c0.setImage(images.get(cardManager.getCards()[0]));
        c1.setImage(images.get(cardManager.getCards()[1]));
        c2.setImage(images.get(cardManager.getCards()[2]));
        c3.setImage(images.get(cardManager.getCards()[3]));
        c4.setImage(images.get(cardManager.getCards()[4]));
        Game.gameManager().start();
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(Game.gameManager().gameOver()){
                            t.cancel();
                            boolean answer = GameOverPrompt.show();
                            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                            if(answer){
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("../view/menu.fxml"));
                                try {
                                    loader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                MenuCon menuCon = loader.getController();
                                if (ProfileHandler.getCurrentUser() != null)
                                    menuCon.setAccount(ProfileHandler.getCurrentUser());
                                stage.setTitle("menu");
                                stage.setResizable(false);
                                stage.setScene(new Scene(loader.getRoot()));
                                stage.show();
                            }
                            if(!answer){
                                stage.close();
                            }
                        }
                        if(Game.getGamInstance() != null && View.getViewInstance()!= null){
                            Game.gameManager().tick();
                            View.CRView().render();
                        }
                    }
                });
            }
        };
        t.schedule(task,0,30);
    }

    /**
     *
     * @param deck is deck to be set
     */
    public void setDeck(ArrayList<Type> deck) {
        this.deck = deck;
        cardManager = new CardManager(deck);
    }

    /**
     * set game manager based on player level and chosen difficulty
     * @param lvl is player level
     * @param botDifficulty is chosen difficulty
     */
    public void setGameManager(int lvl, int botDifficulty){
        Game.initGameManager(lvl);
        Game.gameManager().setDeck(deck);
        Game.gameManager().setBotDifficulty(botDifficulty);
    }

    /**
     * initialize view
     */
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
