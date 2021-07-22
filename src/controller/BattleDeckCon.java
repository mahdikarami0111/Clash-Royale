package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import model.enums.Type;
import model.utils.Account;

import java.io.IOException;
import java.util.ArrayList;

/**
 * controller for battle deck,
 * choose 8 cards to take to battle
 */
public class BattleDeckCon {
    private Account account;
    private ArrayList<Type> cards;
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



    /**
     *
     * @param event
     * go to training camp after battle deck selection and initialization
     */
    @FXML
    void confirm(ActionEvent event) {
        if (!correctAmount()){
            Stage stage = new Stage();
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("../view/prompt2.fxml"));
                stage.setScene(new Scene(parent));
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        for (int i = 0; i < toggleButtons.length ; i++) {
            if (!toggleButtons[i].isSelected())
                continue;
            switch (i){
                case 0:
                    cards.add(Type.ARCHER);
                    break;
                case 1:
                    cards.add(Type.ARROWS);
                    break;
                case 2:
                    cards.add(Type.BABY_DRAGON);
                    break;
                case 3:
                    cards.add(Type.BARBARIAN);
                    break;
                case 4:
                    cards.add(Type.CANNON);
                    break;
                case 5:
                    cards.add(Type.FIREBALL);
                    break;
                case 6:
                    cards.add(Type.GIANT);
                    break;
                case 7:
                    cards.add(Type.INFERNO_TOWER);
                    break;
                case 8:
                    cards.add(Type.MINI_PEKKA);
                    break;
                case 9:
                    cards.add(Type.RAGE);
                    break;
                case 10:
                    cards.add(Type.VALKYRIE);
                    break;
                case 11:
                    cards.add(Type.WIZARD);
                    break;
            }
        }
        Stage stage = (Stage) menu.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/trainingCamp.fxml"));
        try {
            loader.load();
            TrainingCampCon trainingCampCon =  loader.getController();
            trainingCampCon.setCards(cards);
            trainingCampCon.setAccount(account);
            stage.setScene(new Scene(loader.getRoot()));
            stage.setTitle("training camp");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * go back to main menu
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
     * initialize cards and toggle button arrays
     */
    public void initArr(){
        cards = new ArrayList<>();
        toggleButtons = new ToggleButton[]{b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11};
    }

    /**
     *
     * @return true if 8 cards have been chosen
     */
    private boolean correctAmount(){
        int i = 0;
        for (ToggleButton tb : toggleButtons){
            if (tb.isSelected())
                i++;
        }
        return i == 8;
    }

    /**
     *
     * @param account is account to be set
     */
    public void setAccount(Account account) {
        this.account = account;
    }
}