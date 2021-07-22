package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.utils.Account;
import model.utils.History;

import java.io.IOException;

/**
 * show user name, pass word, number of games played and number of games won and level
 */
public class ProfileCon {
    private Account account;

    @FXML
    private TextField username = new TextField();

    @FXML
    private TextField won = new TextField();

    @FXML
    private TextField played = new TextField();

    @FXML
    private TextField level = new TextField();;

    @FXML
    private TextField password = new TextField();

    @FXML
    private Hyperlink menu = new Hyperlink();

    /**
     * go to menu
     * @param event
     */
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
        if (account != null)
            menuCon.setAccount(account);
        stage.setTitle("menu");
        stage.setResizable(false);
        stage.setScene(new Scene(loader.getRoot()));
        stage.show();
    }

    /**
     *
     * @param account is the account to be set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * initialize the data
     */
    public void setData(){
        if (account == null)
            return;
        username.setText(account.getUsername());
        password.setText(account.getPassWord());
        level.setText(String.valueOf(account.getLevel()));
        played.setText(String.valueOf(account.getHistory().size()));
        int i = 0;
        for (History h : account.getHistory()){
            if (h.isWon()){
                i++;
            }
        }
        won.setText(String.valueOf(i));

    }
}