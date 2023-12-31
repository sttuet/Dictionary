package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.DictionaryDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingController extends Controller implements Initializable {
    public Button backSettingButton;
    public ChoiceBox<Integer> choiceBox;
    public Label topLabel;
    public HBox autoPlayBox;
    public HBox darkModeBox;
    public CheckBox checkDarkMode;
    public CheckBox checkAutoPlay;
    public Label settingDarkModeLabel;
    public Label SettingAutoPlayLabel;
    public Button logOut;
    public Label fontSize;

    /**
     * quay trở lại màn hình chính
     *
     * @throws IOException ngoại lệ input output
     */
    @FXML
    protected void onBackClick() throws IOException {
        super.changeScreen("main-view.fxml", "MainView.css");
    }

    /**
     * lựa chọn dark mode.
     */
    public void onDarkModeSelect() {
        if (Main.DARK_MODE) {
            checkDarkMode.setSelected(false);
            Main.DARK_MODE = false;
            rootPane.setBackground(Background.fill(Paint.valueOf("#f8dad0")));
        } else {
            checkDarkMode.setSelected(true);
            Main.DARK_MODE = true;
            rootPane.setBackground(Background.fill(Paint.valueOf("#04293A")));
        }
    }

    /**
     * khởi tạo.
     *
     * @param url            rl
     * @param resourceBundle nguồn
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.DARK_MODE) {
            checkDarkMode.setSelected(true);
            rootPane.setBackground(Background.fill(Paint.valueOf("#04293A")));
        } else {
            rootPane.setBackground(Background.fill(Paint.valueOf("#f8dad0")));
        }
        if (MainController.autoPlay) {
            checkAutoPlay.setSelected(true);
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 10; i < 18; i++) {
            list.add(i);
        }
        choiceBox.getItems().addAll(list);
        choiceBox.setValue(MainController.fontSize);
        choiceBox.getSelectionModel().selectedItemProperty().
                addListener((observableValue, integer, t1) -> onFontSizeClick(t1));
        onFontSizeClick(MainController.fontSize);
    }

    /**
     * lựa chọn auto play khi phát âm.
     */
    public void onAutoPlaySelect() {
        if (MainController.autoPlay) {
            checkAutoPlay.setSelected(false);
            MainController.autoPlay = false;
        } else {
            MainController.autoPlay = true;
        }
    }

    /**
     * thay đổi kích thước font chữ.
     *
     * @param size size mong muốn
     */
    public void onFontSizeClick(int size) {
        MainController.fontSize = size;
        fontSize.setStyle("-fx-font-size: " + size);
        settingDarkModeLabel.setStyle("-fx-font-size: " + size);
        SettingAutoPlayLabel.setStyle("-fx-font-size: " + size);
        logOut.setStyle("-fx-font-size: " + size);
        backSettingButton.setStyle("-fx-font-size: " + size);
    }

    /**
     * đăng xuất ra.
     *
     * @param event sự kiện chuột
     * @throws IOException ngoại lệ io
     */
    public void onLogOutButtonClick(ActionEvent event) throws IOException {
        if (!Main.isGuest) {
            DictionaryDao dictionaryDao = new DictionaryDao();
            dictionaryDao.updateListWord();
        }
        super.changeScreenFromMain("Log-in.fxml", "Login.css");
    }
}
