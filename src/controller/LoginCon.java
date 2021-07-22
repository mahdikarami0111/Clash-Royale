package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.utils.Account;
import model.utils.History;
import model.utils.ProfileHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


/**
 * login scene controller
 * log a player in if the conditions are met
 * and get the corresponding account
 */

public class LoginCon {


    private Account account;


    @FXML
    private TextField userName = new TextField();

    @FXML
    private PasswordField passWord = new PasswordField();

    @FXML
    private Button loginButton = new Button();

    @FXML
    private Hyperlink signUpButton = new Hyperlink();

    /**
     * logs the player in
     * @param event event
     */
    @FXML
    void login(ActionEvent event) {

        ProfileHandler.initialize();
        String un = userName.getText().trim();
        String pw = passWord.getText().trim();
        if (!ProfileHandler.passwordMatch(un, pw)){
            showPrompt();
            clearFields();
            return;
        }
        Stage stage = (Stage) userName.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/menu.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Account a = ProfileHandler.getUsernameMap2Account().get(un);

        Parent parent = loader.getRoot();
        MenuCon menuCon = loader.getController();
        menuCon.setAccount(a);
        ProfileHandler.setCurrentUser(a);
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.setTitle("menu");
        stage.show();



    }

    /**
     * changes scene to sign up
     * @param event event
     */
    @FXML
    void signUp(ActionEvent event)  {
        Stage stage = (Stage) userName.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/signup.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.setTitle("sign up");
        stage.show();

    }

    private void showPrompt(){
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/prompt.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    private void clearFields(){
        userName.clear();
        passWord.clear();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}