package com.example.controller;

import com.example.dao.DictionaryDao;
import com.example.ourdictionary.Main;
import com.example.ourdictionary.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.List;

public class MainController {
    @FXML
    private ListView<String> listView=new ListView<>();
    @FXML
    private TextField textField;
    @FXML
    private WebView webView;
    @FXML
    private TextField addWordTextField;
    @FXML
    private TextField addMeaningTextField;
    @FXML
    protected void onTypeWord(KeyEvent event){
        List<String> list= Main.dictionary.allWordsHas(textField.getText());
        ObservableList<String> observableList= FXCollections.observableArrayList(list);
        listView.setItems(observableList);
    }
    @FXML
    protected void onChooseWord(MouseEvent event){
        String s=listView.getSelectionModel().getSelectedItem();
        textField.setText(s);
    }
    @FXML
    protected void onTranslate(ActionEvent event){
        String mean= Main.dictionaryDao.getDefinitionOf(textField.getText());
        webView.getEngine().loadContent(mean);
    }
    @FXML
    protected void onAddButtonClick(){
        Word word=new Word(addWordTextField.getText(),addMeaningTextField.getText());
        Main.dictionaryDao.addWord(word);
    }
}
