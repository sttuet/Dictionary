package com.example.controller;

import com.example.ourdictionary.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class ChooseGameController {
    @FXML
    protected void backToMain(ActionEvent event) throws IOException {
        Main.changeScreen("main-view.fxml","MainView.css");
    }
    @FXML
    protected void goToMultiChoiceGame(ActionEvent event) throws IOException {
        Main.changeScreen("multiChoiceGameView.fxml","multiChoiceGame.css");
    }
    @FXML
    protected void goToHangMan(ActionEvent event) throws IOException {
        Main.changeScreen("hangMan-view.fxml","hangMan.css");
    }
}
