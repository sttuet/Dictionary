package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.IOFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.ourdictionary.Main.*;

public class AddingWordController extends Controller implements Initializable {
    @FXML
    public Button backAddingWord;

    @FXML
    public Label cantAddWordLabel;

    @FXML
    public Label wordAddedLabel;

    @FXML
    public TextArea englishTextArea;

    @FXML
    public Label enterEnglishLabel;

    @FXML
    public Label enterVietnameseLabel;

    @FXML
    public Button saveWordButton;

    @FXML
    public TextArea vietnameseTextArea;

    @FXML
    protected void backToModifyMenu() throws IOException {
        changeScreen("modify-view.fxml", "modifyView.css");
    }

    @FXML
    protected void addingWord() throws IOException {
        String word = englishTextArea.getText();
        String meaning = vietnameseTextArea.getText();
        if (meanings.containsKey(word)) {
            cantAddWordLabel.setVisible(true);
            return;
        }
        meanings.put(word, "<html>" + meaning);
        IOFile.writeToModifiedFile(word, meaning);
        dictionary.addWord(word);
        cantAddWordLabel.setVisible(false);
        wordAddedLabel.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.DARK_MODE) {
            rootPane.setStyle("-fx-background-color: #04293A; -fx-font-size: " + MainController.fontSize + ";");
        } else {
            rootPane.setStyle("-fx-font-size: " + MainController.fontSize + ";");

        }
        backAddingWord.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        saveWordButton.setStyle("-fx-font-size: " + MainController.fontSize + ";");
    }
}
