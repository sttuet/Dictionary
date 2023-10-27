package com.example.controller;

import com.example.ourdictionary.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    public static boolean DarkMode_Settings = false;
    public Button backSettingButton;
    public MenuButton autoPlayButton;
    public MenuButton setDarkModeButton;
    public MenuButton setFontButton;
    public Label topLabel;
    public VBox vBoxSetting;
    private Stage stage;
    private Scene scene;

    @FXML
    protected void onBackClick(ActionEvent event) throws IOException {
        Main.changeScreen("main-view.fxml", "MainView.css");
    }

    public void onDarkModeSelect(ActionEvent event) {
        Main.DARK_MODE = true;
        vBoxSetting.setBackground(Background.fill(Paint.valueOf("#303030")));
    }

    public void onLightModeSelect(ActionEvent event) {
        Main.DARK_MODE = false;
        this.vBoxSetting.setBackground(Background.fill(Paint.valueOf("white")));
    }

    public void onYesSelect(ActionEvent event) {
    }

    public void onNoSelect(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.DARK_MODE) {
            this.vBoxSetting.setBackground(Background.fill(Paint.valueOf("black")));
        }
    }
}
