package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.ProfileHandler;


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

    @FXML
    void signUp(ActionEvent event) {
        String un,pw,cPw;
        un = userName.getText();
        pw = password.getText();
        cPw = confirmPassword.getText();
        if(ProfileHandler.userNameExists(un) || un == null || pw == null || cPw == null){
            showPrompt();
            clearFields();
            return;
        }

        if ( (!pw.equals(cPw))){
            try {
               showPrompt();
            }catch (Exception e){
                e.printStackTrace();
            }
            clearFields();
            System.out.println(ProfileHandler.userNameExists(un));
            return;
        }
        ProfileHandler.addUnPw(un,pw);
        ProfileHandler.update();

    }


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
