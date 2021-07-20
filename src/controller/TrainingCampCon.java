package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleButton;
import utils.Account;

import java.util.ArrayList;

public class TrainingCampCon {
    private Account account;
    private ArrayList<Object> cards;
    private ToggleButton[] toggleButtons;

    @FXML
    private ToggleButton b0;

    @FXML
    private ToggleButton b1;

    @FXML
    private ToggleButton b2;

    @FXML
    private ToggleButton b3;

    @FXML
    private ToggleButton b4;

    @FXML
    private ToggleButton b5;

    @FXML
    private ToggleButton b6;

    @FXML
    private ToggleButton b7;

    @FXML
    private ToggleButton b8;

    @FXML
    private ToggleButton b9;

    @FXML
    private ToggleButton b10;

    @FXML
    private ToggleButton b11;

    @FXML
    private Hyperlink menu;

    @FXML
    private Button confirmation;


    public TrainingCampCon(){
        b0 = new ToggleButton();
        b1 = new ToggleButton();
        b2 = new ToggleButton();
        b3 = new ToggleButton();
        b4 = new ToggleButton();
        b5 = new ToggleButton();
        b6 = new ToggleButton();
        b7 = new ToggleButton();
        b8 = new ToggleButton();
        b9 = new ToggleButton();
        b10 = new ToggleButton();
        b11 = new ToggleButton();
        menu = new Hyperlink();
        confirmation = new Button();
        initArr();
    }



    @FXML
    void confirm(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) {

    }


    public void initArr(){
        ToggleButton[] toggleButtons = {b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11};
    }



}
