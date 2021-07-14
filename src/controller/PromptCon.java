package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PromptCon {

    @FXML
    private Button button;

    @FXML
    void ok(ActionEvent event) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

}
