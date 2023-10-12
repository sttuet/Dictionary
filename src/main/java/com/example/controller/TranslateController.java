package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.ParseJSON;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class TranslateController   {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField inputText;
    @FXML
    private TextArea translateResult;
    @FXML
    protected void onCloseButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(Main.class.getResource("main-view.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onTranslateToViet() throws IOException {
        String s=inputText.getText();
        String res= ParseJSON.transToViet(s);
        translateResult.setText(res);
    }


}
