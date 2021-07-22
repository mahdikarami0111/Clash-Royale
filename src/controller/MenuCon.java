package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import model.utils.Account;
import model.utils.History;

import java.io.IOException;

/**
 * the main menu of the game with options to go to profile, history, battle deck and training camp
 */
public class MenuCon {

    private Account account;

    @FXML
    private Hyperlink profile = new Hyperlink();

    @FXML
    private Hyperlink battleDeck = new Hyperlink();

    @FXML
    private Hyperlink battleHistory = new Hyperlink();

    @FXML
    private Hyperlink tariningCamp = new Hyperlink();

    @FXML
    private Hyperlink exit = new Hyperlink();

    /**
     * navigate to next page based on the players choice
     * @param event
     */
    @FXML
    void changeScene(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        if (event.getSource() == profile){
            loader.setLocation(getClass().getResource("../view/profile.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            ProfileCon profileCon = loader.getController();
            profileCon.setAccount(account);
            profileCon.setData();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("profile");
            stage.show();
        }else if (event.getSource() == battleDeck){
            loader.setLocation(getClass().getResource("../view/battleDeck.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            BattleDeckCon battleDeckCon = loader.getController();
            battleDeckCon.setAccount(account);
            battleDeckCon.initArr();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("battle deck");
            stage.setResizable(false);
            stage.setX(250);
            stage.setY(150);
            stage.show();
        }else if(event.getSource() == battleHistory){
            loader.setLocation(getClass().getResource("../view/battleHistory1.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            HistoryCon1 historyCon = loader.getController();
            historyCon.setAccount(account);
            historyCon.setData();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("battle history");
            stage.show();


        }else if (event.getSource() == tariningCamp){
            loader.setLocation(getClass().getResource("../view/trainingCamp.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            TrainingCampCon con = loader.getController();
            if (con.getCards() == null || con.getCards().size() == 0)
                return;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("training camp");
            stage.show();
        }else if (event.getSource() == exit){
            loader.setLocation(getClass().getResource("../view/login.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("exit");
            stage.show();
        }else{
            System.out.println("ajab");
        }
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}