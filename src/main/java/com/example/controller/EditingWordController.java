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

public class EditingWordController extends Controller implements Initializable {
    @FXML
    public Button backEditingWord;

    @FXML
    public Label cantEditWordLabel;

    @FXML
    public Label wordEditedLabel;

    @FXML
    public TextArea editedMeaningTextArea;

    @FXML
    public Label enterMeaningLabel;

    @FXML
    public Label enterWordToBeEditedLabel;

    @FXML
    public Button editWordButton;

    @FXML
    public TextArea editedWordTextArea;

    @FXML
    protected void backToModifyMenu() throws IOException {
        changeScreen("modify-view.fxml", "modifyView.css");
    }

    @FXML
    protected void editingWord() throws IOException {
        String word = editedWordTextArea.getText();
        String meaning = editedMeaningTextArea.getText();
        if (!meanings.containsKey(word)) {
            cantEditWordLabel.setVisible(true);
            return;
        }
        meanings.put(word, "<html>" + meaning);
        IOFile.writeToModifiedFile(word, meaning);
        dictionary.addWord(word);
        cantEditWordLabel.setVisible(false);
        wordEditedLabel.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.DARK_MODE) {
            rootPane.setStyle("-fx-background-color: #04293A; -fx-font-size: " + MainController.fontSize + ";");
        } else {
            rootPane.setStyle("-fx-font-size: " + MainController.fontSize + ";");

        }
        backEditingWord.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        editWordButton.setStyle("-fx-font-size: " + MainController.fontSize + ";");
    }
}
