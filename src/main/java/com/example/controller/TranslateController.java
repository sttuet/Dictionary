package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.ParseJSON;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TranslateController implements Initializable {
    public Label TranslateLabel;
    public Button closeButton;
    @FXML
    Label sourceLanguageLabel;
    @FXML
    Label targetLanguageLabel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField inputText;
    @FXML
    private Label translateResult;
    private boolean engToViet = true;

    @FXML
    protected void onCloseButtonClick(ActionEvent event) throws IOException {
        Main.changeScreen("main-view.fxml", "MainView.css", anchorPane.getWidth(), anchorPane.getHeight());

    }

    @FXML
    protected void onTranslate() throws IOException {
        String s = inputText.getText();
        if (engToViet) {
            String res = ParseJSON.transToViet(s);
            translateResult.setText(res);
        } else {
            String res = ParseJSON.transToEng(s);
            translateResult.setText(res);
        }

    }

    @FXML
    protected void exchangeLanguage() {
        engToViet = !engToViet;
        if (engToViet) {
            sourceLanguageLabel.setText("ANH");
            targetLanguageLabel.setText("VIET");
        } else {
            sourceLanguageLabel.setText("VIET");
            targetLanguageLabel.setText("ANH");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.DARK_MODE) {
            anchorPane.setStyle("-fx-background-color: #04293A");
            inputText.setStyle("-fx-background-color: #041C32; -fx-text-fill: #ADC4CE; -fx-font-size: "
                    + MainController.fontSize + ";");
            translateResult.setStyle("-fx-background-color: #041C32; -fx-text-fill: #ADC4CE; -fx-font-size: "
                    + MainController.fontSize + ";");
        }
        else {
            inputText.setStyle(" -fx-font-size: " + MainController.fontSize + ";");
            translateResult.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        }

    }
}
