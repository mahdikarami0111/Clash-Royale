package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.utils.Account;
import model.utils.History;

import java.io.IOException;

/**
 * controller for history scene
 * display the result and time of each game
 */
public class HistoryCon1 {

    private Account account;
    @FXML
    private Hyperlink back = new Hyperlink();

    @FXML
    private VBox vbox = new VBox();

    @FXML
    void goBack(ActionEvent event) {
        Stage stage = (Stage) back.getScene().getWindow();
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

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setData(){
        for (History history : account.getHistory())
        {
            Label l = new Label(history.toString());
            l.setFont(new Font(20));
            vbox.getChildren().add(l);
        }
    }


}