package com.example.controller;

import com.example.ourdictionary.Main;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
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
    }

    public void onDarkModeSelect(ActionEvent event) {
        Main.DARK_MODE = true;
        this.vBoxSetting.setBackground(Background.fill(Paint.valueOf("black")));

    }

    public void onLightModeSelect(ActionEvent event) {
        Main.DARK_MODE = false;
        this.vBoxSetting.setBackground(Background.fill(Paint.valueOf("linear-gradient(to bottom, #efefbb, #d4d3dd)")));
    }

    public void onYesSelect(ActionEvent event) {
    }

    public void onNoSelect(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Main.DARK_MODE) {
            this.vBoxSetting.setBackground(Background.fill(Paint.valueOf("black")));
        }
        else {
            this.vBoxSetting.setBackground(Background.fill(Paint.valueOf("linear-gradient(to bottom, #efefbb, #d4d3dd)")));
        }
    }
}
