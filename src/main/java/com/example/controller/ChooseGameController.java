package com.example.controller;

import com.example.ourdictionary.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseGameController implements Initializable {
    public AnchorPane gameAnchorPane;
    public Button LetterSorting;
    public Button RandomWordLearning;
    public Button WordRevision;
    public Button backGameChooser;

    @FXML
    protected void backToMain(ActionEvent event) throws IOException {
        Main.changeScreen("main-view.fxml","MainView.css", gameAnchorPane.getWidth(), gameAnchorPane.getHeight());
    }
    @FXML
    protected void goToMultiChoiceGame(ActionEvent event) throws IOException {
        Main.changeScreen("multiChoiceGameView.fxml","multiChoiceGame.css", gameAnchorPane.getWidth(), gameAnchorPane.getHeight());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Main.DARK_MODE) {
            gameAnchorPane.setStyle("-fx-background-color: #04293A; -fx-font-size: " + MainController.fontSize + ";");
            LetterSorting.setStyle("-fx-font-size: " + MainController.fontSize+ ";");
            RandomWordLearning.setStyle("-fx-font-size: " + MainController.fontSize+ ";");
            WordRevision.setStyle("-fx-font-size: " + MainController.fontSize+ ";");
            backGameChooser.setStyle("-fx-font-size: " + MainController.fontSize+ ";");
        }
        else {
            gameAnchorPane.setStyle("-fx-font-size: " + MainController.fontSize+ ";");
            LetterSorting.setStyle("-fx-font-size: " + MainController.fontSize+ ";");
            RandomWordLearning.setStyle("-fx-font-size: " + MainController.fontSize+ ";");
            WordRevision.setStyle("-fx-font-size: " + MainController.fontSize+ ";");
            backGameChooser.setStyle("-fx-font-size: " + MainController.fontSize+ ";");

        }
    }
    @FXML
    protected void goToHangMan(ActionEvent event) throws IOException {
        Main.changeScreen("hangMan-view.fxml","hangMan.css");
    }
}
