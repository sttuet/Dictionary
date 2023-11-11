package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.ParseJSON;
import com.example.service.SendRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TranslateController extends Controller implements Initializable {
    public Label TranslateLabel;
    public Button closeButton;
    @FXML
    Label sourceLanguageLabel;
    @FXML
    Label targetLanguageLabel;
    @FXML
    private TextField inputText;
    @FXML
    private Label translateResult;
    private boolean engToViet = true;

    @FXML
    protected void onCloseButtonClick() throws IOException {
        changeScreen("main-view.fxml", "MainView.css");
    }

    @FXML
    protected void onTranslate() throws IOException {
        String s = inputText.getText();
        String res;
        if (engToViet) {
            res = ParseJSON.transToViet(s);
        } else {
            res = ParseJSON.transToEng(s);
        }
        translateResult.setText(res);

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
            rootPane.setStyle("-fx-background-color: #04293A");
            inputText.setStyle("-fx-background-color: #041C32; -fx-text-fill: #ADC4CE; -fx-font-size: "
                    + MainController.fontSize + ";");
            translateResult.setStyle("-fx-background-color: #041C32; -fx-text-fill: #ADC4CE; -fx-font-size: "
                    + MainController.fontSize + ";");
        } else {
            inputText.setStyle(" -fx-font-size: " + MainController.fontSize + ";");
            translateResult.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        }
    }
    @FXML
    protected void onSpeaker() throws IOException {
        SendRequest.downloadAudio(inputText.getText());
        super.onSpeakerClick(inputText.getText());
        File file=new File("src\\main\\resources\\audio\\" + inputText.getText() + ".mp3");
        try {
            file.delete();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
