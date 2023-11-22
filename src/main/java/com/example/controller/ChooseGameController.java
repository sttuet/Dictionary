package com.example.controller;

import com.example.ourdictionary.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
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

    /**
     * quay lại màn hình chính.
     *
     * @throws IOException ngoại lệ xử lý input output
     */
    @FXML
    protected void backToMain() throws IOException {
        changeScreen("main-view.fxml", "MainView.css");
    }

    /**
     * đi tới game 4 chọn.
     *
     * @throws IOException ngoại lệ xử lý input output
     */
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

    /**
     * khởi tạo khi chạy controller này.
     *
     * @param url            đường dẫn
     * @param resourceBundle nguồn
     */
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

    /**
     * đi tới game hangman.
     *
     * @throws IOException ngoại lệ xử lý input output
     */
    @FXML
    protected void goToHangMan() throws IOException {
        changeScreen("hangMan-view.fxml", "hangMan.css");
    }

    /**
     * đi tới game viết từ, nếu favouritelist rỗng thì đưa ra warning, nếu không có mạng cũng đưa ra warrning
     *
     * @throws IOException ngoại lệ xử lý input output
     */
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

    /**
     * đi tới game word scramble.
     *
     * @throws IOException ngoại lệ xứ lý input output
     */
    @FXML
    protected void goToWordScramble() throws IOException {
        LetterSorting.setVisible(false);
        RandomWordLearning.setVisible(false);
        WordRevision.setVisible(false);
        WordScramble.setVisible(false);
        topicPane.setVisible(true);
    }

    /**
     * chọn chủ đề food.
     *
     * @throws IOException ngoại lệ input output
     */
    @FXML
    void onFoodButtonClick() throws IOException {
        WordScrambleController.currentTopic = WordScrambleController.Topic.FOOD;
        changeScreen("wordScramble-view.fxml", "wordScramble.css");
    }

    /**
     * chọn chủ đề thể thao trong scramble game.
     *
     * @throws IOException ngoại lệ input output
     */
    @FXML
    void onSportButtonClick() throws IOException {
        WordScrambleController.currentTopic = WordScrambleController.Topic.SPORT;
        changeScreen("wordScramble-view.fxml", "wordScramble.css");
    }

    /**
     * chọn chủ đề nghề nghiệp trong scramble game.
     *
     * @throws IOException ngoại lệ input output
     */
    @FXML
    void onJobButtonClick() throws IOException {
        WordScrambleController.currentTopic = WordScrambleController.Topic.JOB;
        changeScreen("wordScramble-view.fxml", "wordScramble.css");
    }

    /**
     * chọn chủ đề nature trong scramble game.
     *
     * @throws IOException ngoại lệ input output
     */
    @FXML
    void onNatureButtonClick() throws IOException {
        WordScrambleController.currentTopic = WordScrambleController.Topic.NATURE;
        changeScreen("wordScramble-view.fxml", "wordScramble.css");
    }
}
