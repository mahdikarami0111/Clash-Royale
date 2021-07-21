package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import model.enums.Type;
import model.utils.Account;
import model.utils.ProfileHandler;

import java.io.IOException;
import java.util.ArrayList;

public class TrainingCampCon {
    private ArrayList<Type> cards;
    private Account account;
    private int level;

    @FXML
    private Hyperlink menu;

    @FXML
    private Button easy;

    @FXML
    private Button normal;

    @FXML
    void goBack(ActionEvent event) {
        Stage stage = (Stage) menu.getScene().getWindow();
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

    @FXML
    void select(ActionEvent event) {
        if (event.getSource() == easy){
            level = 1;
        }else if (event.getSource() == normal){
            level = 2;
        }else
            return;
        ProfileHandler.setLevel(level);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/Game.fxml"));
        try {
            loader.load();
            Parent root  = loader.getRoot();
            GameController gc = loader.getController();
            gc.setDeck(cards);
            gc.setGameManager(account.getLevel(),level);
            gc.setView();
            Stage stage = (Stage) menu.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("clash royal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Type> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Type> cards) {
        this.cards = cards;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}