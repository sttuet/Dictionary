package com.example.controller;

import com.example.ourdictionary.Dictionary;
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

public class DeletingWordController extends Controller implements Initializable {
    @FXML
    public Button backDeletingWord;

    @FXML
    public Label cantDeleteWordLabel;

    @FXML
    public Label wordDeletedLabel;

    @FXML
    public Label enterToBeDeletedWord;

    @FXML
    public Button deleteWordButton;

    @FXML
    public TextArea toBeDeletedWordTextArea;

    @FXML
    protected void backToModifyMenu() throws IOException {
        changeScreen("modify-view.fxml", "modifyView.css");
    }

    @FXML
    protected void deletingWord() throws IOException {
        String word = toBeDeletedWordTextArea.getText();
        if (!meanings.containsKey(word)) {
            cantDeleteWordLabel.setVisible(true);
            return;
        }

        IOFile.writeToModifiedFile(word, "!");
        dictionary = new Dictionary();
        modifiedWord = IOFile.readFromModifiedFile(dictionary);
        meanings = IOFile.readFromE_VFile(dictionary, modifiedWord);

        cantDeleteWordLabel.setVisible(false);
        wordDeletedLabel.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.DARK_MODE) {
            rootPane.setStyle("-fx-background-color: #04293A; -fx-font-size: " + MainController.fontSize + ";");
        } else {
            rootPane.setStyle("-fx-font-size: " + MainController.fontSize + ";");

        }
        deleteWordButton.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        backDeletingWord.setStyle("-fx-font-size: " + MainController.fontSize + ";");
    }
}
