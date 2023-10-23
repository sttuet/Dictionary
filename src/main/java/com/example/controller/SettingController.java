package com.example.controller;

import com.example.ourdictionary.Main;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SettingController {
    public Button backSettingButton;
    public MenuButton autoPlayButton;
    public MenuButton setDarkModeButton;
    public MenuButton setFontButton;
    public Label topLabel;
    public VBox vBoxSetting;
    private Stage stage;
    private Scene scene;

    @FXML
    protected void onBackClick(ActionEvent event) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), vBoxSetting);
        translateTransition.setFromX(0);
        translateTransition.setToX(300);
        translateTransition.setOnFinished((ActionEvent event1) -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene.getStylesheets().add(Main.class.getResource("MainView.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        });
        translateTransition.play();
    }

    @FXML
    protected void onSetFontSizeClick() {

    }

    @FXML
    protected void onAutoPlayClick() {

    }

    public void onDarkModeButtonClick(ActionEvent event) {

    }
}
