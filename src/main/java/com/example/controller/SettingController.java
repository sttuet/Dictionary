package com.example.controller;

import com.example.ourdictionary.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    public Button backSettingButton;

    public MenuButton setFontButton;
    public Label topLabel;
    public AnchorPane SettingAnchorPane;
    public HBox autoPlayBox;
    public HBox darkModeBox;
    public CheckBox checkDarkMode;
    public CheckBox checkAutoPlay;
    public Label settingDarkModeLabel;
    public Label SettingAutoPlayLabel;


    @FXML
    protected void onBackClick(ActionEvent event) throws IOException {
        Main.changeScreen("main-view.fxml", "MainView.css", SettingAnchorPane.getWidth(), SettingAnchorPane.getHeight());
    }

    public void onDarkModeSelect(ActionEvent event) {
        if (Main.DARK_MODE) {
            checkDarkMode.setSelected(false);
            Main.DARK_MODE = false;
            SettingAnchorPane.setBackground(Background.fill(Paint.valueOf("#f8dad0")));
        } else {
            checkDarkMode.setSelected(true);
            Main.DARK_MODE = true;
            SettingAnchorPane.setBackground(Background.fill(Paint.valueOf("#04293A")));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.DARK_MODE) {
            checkDarkMode.setSelected(true);
            SettingAnchorPane.setBackground(Background.fill(Paint.valueOf("#04293A")));
        } else {
            SettingAnchorPane.setBackground(Background.fill(Paint.valueOf("#f8dad0")));
        }
        if(MainController.autoPlay) {
            checkAutoPlay.setSelected(true);
        }
        if(MainController.fontSize == 14) {
            settingDarkModeLabel.setStyle("-fx-font-size: 14");
            SettingAutoPlayLabel.setStyle("-fx-font-size: 14");
            setFontButton.setStyle("-fx-font-size: 14");
            backSettingButton.setStyle("-fx-font-size: 14");
        }
        if(MainController.fontSize == 18) {
            settingDarkModeLabel.setStyle("-fx-font-size: 18");
            SettingAutoPlayLabel.setStyle("-fx-font-size: 18");
            setFontButton.setStyle("-fx-font-size: 18");
            backSettingButton.setStyle("-fx-font-size: 18");
        }
    }

    public void onAutoPlaySelect(ActionEvent event) {
        if (MainController.autoPlay) {
            checkAutoPlay.setSelected(false);
            MainController.autoPlay = false;
        } else {
            MainController.autoPlay = true;
        }
    }

    public void onFontSize14(ActionEvent event) {
        MainController.fontSize = 14;
        settingDarkModeLabel.setStyle("-fx-font-size: 14");
        SettingAutoPlayLabel.setStyle("-fx-font-size: 14");
        setFontButton.setStyle("-fx-font-size: 14");
        backSettingButton.setStyle("-fx-font-size: 14");
    }

    public void onFontSize18(ActionEvent event) {
        MainController.fontSize = 18;
        settingDarkModeLabel.setStyle("-fx-font-size: 18");
        SettingAutoPlayLabel.setStyle("-fx-font-size: 18");
        setFontButton.setStyle("-fx-font-size: 18");
        backSettingButton.setStyle("-fx-font-size: 18");
    }
}
