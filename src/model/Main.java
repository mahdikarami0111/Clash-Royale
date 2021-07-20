package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Account;
import utils.History;
import utils.ProfileHandler;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/signup.fxml"));
        primaryStage.setScene(new Scene(parent));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("accounts.bin"))){
            HashMap<String,Account> accountHashMap = (HashMap<String, Account>) inputStream.readObject();
            for (String s : accountHashMap.keySet()){
                System.out.println(accountHashMap.get(s));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        launch(args);
    }
}
