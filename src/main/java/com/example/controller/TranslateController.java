package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.ParseJSON;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class TranslateController {
    @FXML
    Label sourceLanguageLabel;
    @FXML
    Label targetLanguageLabel;
    @FXML
    private AnchorPane anchorPane;
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField inputText;
    @FXML
    private TextArea translateResult;
    private boolean engToViet = true;

    @FXML
    protected void onCloseButtonClick(ActionEvent event) throws IOException {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), anchorPane);
        translateTransition.setFromX(0);
        translateTransition.setToX(600);
        translateTransition.setOnFinished((ActionEvent event1) -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene.getStylesheets().add(Main.class.getResource("MainView.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        });
        translateTransition.play();

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


}
