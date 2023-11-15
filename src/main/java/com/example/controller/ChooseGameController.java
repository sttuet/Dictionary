package com.example.controller;

import com.example.ourdictionary.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseGameController extends Controller implements Initializable {
    public Button LetterSorting;
    public Button RandomWordLearning;
    public Button WordRevision;
    public Button backGameChooser;

    @FXML
    protected void backToMain() throws IOException {
        changeScreen("main-view.fxml", "MainView.css");
    }

    @FXML
    protected void goToMultiChoiceGame() throws IOException {
        changeScreen("multiChoiceGameView.fxml", "multiChoiceGame.css");
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
        backGameChooser.setStyle("-fx-font-size: " + MainController.fontSize + ";");
    }

    @FXML
    protected void goToHangMan() throws IOException {
        changeScreen("hangMan-view.fxml", "hangMan.css");
    }
    @FXML
    protected void goToWriteWord() throws IOException {
        if(Main.favouriteList.size()<1){
            System.out.println("has no bookmark words to review");
        }
        changeScreen("writeWord-view.fxml","writeWord.css");
    }
}
