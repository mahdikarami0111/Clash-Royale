
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.ProfileHandler;


public class LoginCon {

//debug file
    @FXML
    private TextField userName = new TextField();

    @FXML
    private PasswordField passWord = new PasswordField();

    @FXML
    private Button loginButton = new Button();

    @FXML
    private Hyperlink signUpButton = new Hyperlink();

    @FXML
    void login(ActionEvent event) {
        String un = userName.getText();
        String pw = passWord.getText();
        if (!ProfileHandler.passwordMatch(un, pw)){
            showPrompt();
            clearFields();
            return;
        }
        //complete later...
    }

    @FXML
    void signUp(ActionEvent event)  {
        Stage stage = (Stage) userName.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/signup.fxml"));
        try {
            loader.load();
        }catch (Exception e){
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

}
