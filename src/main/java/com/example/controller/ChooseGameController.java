package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.IOFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseGameController extends Controller implements Initializable {
    public Button LetterSorting;
    public Button RandomWordLearning;
    public Button WordRevision;
    public Button WordScramble;
    @FXML
    public Button foodButton;
    @FXML
    public Button jobButton;
    @FXML
    public Button natureButton;
    @FXML
    public Button sportButton;
    @FXML
    public Label topicLabel;
    @FXML
    public AnchorPane topicPane;
    public Button backGameChooser;
    public Label noInternet;
    @FXML
    private Label noWordWarning;

    @FXML
    protected void backToMain() throws IOException {
        changeScreen("main-view.fxml", "MainView.css");
    }

    @FXML
    protected void goToMultiChoiceGame() throws IOException {
        if (Main.checkInternetConnection()) {
            changeScreen("multiChoiceGameView.fxml", "multiChoiceGame.css");
        } else {
            noInternet.setVisible(false);
            noInternet.setLayoutY(355.0);
            noInternet.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.DARK_MODE) {
            rootPane.setStyle("-fx-background-color: #04293A; -fx-font-size: " + MainController.fontSize + ";");
        } else {
            rootPane.setStyle("-fx-font-size: " + MainController.fontSize + ";");

        }
        LetterSorting.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        RandomWordLearning.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        WordRevision.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        WordScramble.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        backGameChooser.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        jobButton.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        foodButton.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        natureButton.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        sportButton.setStyle("-fx-font-size: " + MainController.fontSize + ";");
    }

    @FXML
    protected void goToHangMan() throws IOException {
        changeScreen("hangMan-view.fxml", "hangMan.css");
    }

    @FXML
    protected void goToWriteWord() throws IOException {
        if (Main.favouriteList.size() < 1) {
            noWordWarning.setVisible(true);
            return;
        }
        if (Main.checkInternetConnection()) {
            changeScreen("writeWord-view.fxml", "writeWord.css");
        } else {
            noInternet.setLayoutY(255.0);
            noInternet.setVisible(true);
        }

    }

    @FXML
    protected void goToWordScramble() throws IOException {
        LetterSorting.setVisible(false);
        RandomWordLearning.setVisible(false);
        WordRevision.setVisible(false);
        WordScramble.setVisible(false);
        topicPane.setVisible(true);
    }

    @FXML
    void onFoodButtonClick() throws IOException {
        WordScrambleController.currentTopic = WordScrambleController.Topic.FOOD;
        changeScreen("wordScramble-view.fxml", "wordScramble.css");
    }

    @FXML
    void onSportButtonClick() throws IOException {
        WordScrambleController.currentTopic = WordScrambleController.Topic.SPORT;
        changeScreen("wordScramble-view.fxml", "wordScramble.css");
    }

    @FXML
    void onJobButtonClick() throws IOException {
        WordScrambleController.currentTopic = WordScrambleController.Topic.JOB;
        changeScreen("wordScramble-view.fxml", "wordScramble.css");
    }

    @FXML
    void onNatureButtonClick() throws IOException {
        WordScrambleController.currentTopic = WordScrambleController.Topic.NATURE;
        changeScreen("wordScramble-view.fxml", "wordScramble.css");
    }
}
