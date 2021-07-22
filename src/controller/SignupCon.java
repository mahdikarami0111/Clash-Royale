package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.utils.Account;
import model.utils.ProfileHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * signup a player if the conditions are met( unique username...)
 */
public class SignupCon {

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    void logIn(ActionEvent event) {
        Stage stage = (Stage) userName.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/login.fxml"));
        try {
            loader.load();
        }catch (Exception e){
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.setTitle("log in");
        stage.show();
    }

    /**
     * create a new user if username is unique and password and confirm password fields match
     * a new account corresponding to the user will be created and will be directed to menu
     * @param event
     */
    @FXML
    void signUp(ActionEvent event) {
        ProfileHandler.initialize();
        HashMap<String , Account> map = ProfileHandler.getUsernameMap2Account();
        String un = userName.getText().trim();
        String pw = password.getText().trim();
        String cpw = confirmPassword.getText().trim();

        if (un.equals("") || pw.equals("") || cpw.equals("") || ProfileHandler.usernameExists(un) || !(cpw.equals(pw))){
            showPrompt();
            clearFields();
            return;
        }

        Account account = new Account(un, pw);
        ProfileHandler.addAccount(account);
        ProfileHandler.setCurrentUser(account);
        ProfileHandler.writeAccounts();
        Stage stage = (Stage) userName.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/menu.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        MenuCon menuCon = loader.getController();
        menuCon.setAccount(account);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("menu");
        stage.show();
    }


    /**
     * show prompt upon misuse
     */
    private void showPrompt(){
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/prompt.fxml"));
            loader.load();
            stage.setScene(new Scene(loader.getRoot()));
            stage.setResizable(false);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void clearFields(){
        userName.clear();
        password.clear();
        confirmPassword.clear();
    }


}