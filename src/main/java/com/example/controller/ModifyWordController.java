package com.example.controller;

import com.example.ourdictionary.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyWordController extends Controller implements Initializable {
    public Button addingWord;
    public Button editingWord;
    public Button deletingWord;
    public Button backModifyWord;
    @FXML
    private Label noWordToDelete;

    @FXML
    protected void backToMainMenu() throws IOException {
    }

    @FXML
    protected void onAddingWordButtonClick() throws IOException {
    }

    @FXML
    protected void onDeletingWordButtonClick() throws IOException {
    }

    @FXML
    protected void onEditingWordButtonClick() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.DARK_MODE) {
            rootPane.setStyle("-fx-background-color: #04293A; -fx-font-size: " + MainController.fontSize + ";");
        } else {
            rootPane.setStyle("-fx-font-size: " + MainController.fontSize + ";");

        }
        addingWord.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        editingWord.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        deletingWord.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        backModifyWord.setStyle("-fx-font-size: " + MainController.fontSize + ";");
    }
}
