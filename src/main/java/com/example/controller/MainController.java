package com.example.controller;
import com.example.ourdictionary.Main;
import com.example.ourdictionary.Word;
import com.example.service.ConvertToHTML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainController {
    enum Current_view{
        MAIN_VIEW,ADD_WORD
    }
    private Current_view currentView=Current_view.MAIN_VIEW;
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
    private Parent addWordContainer;
    @FXML
    private Button showAddWordContainerButton;
    @FXML
    private Button addWordButton;
    @FXML
    private Button closeAddWordContainerButton;
    @FXML
    private Button historyButton;
    private LinkedList<String> historyList=new LinkedList<>();
    @FXML
    protected void onHistoryButtonClick(){
        listView.setItems(FXCollections.observableList(historyList));
    }
    @FXML
    protected void onShowAddWordContainerButtonClick(){
        textField.setDisable(true);
        listView.setDisable(true);
        addWordContainer.setVisible(true);
        currentView=Current_view.ADD_WORD;
    }
    @FXML
    protected void onCloseAddWordContainerButtonClick(){
        textField.setDisable(false);
        listView.setDisable(false);
        addWordContainer.setVisible(false);
        currentView=Current_view.MAIN_VIEW;
    }
    @FXML
    protected void onTypeWord(){
        if(currentView==Current_view.MAIN_VIEW){
            List<String> list= Main.dictionary.allWordsHas(textField.getText());
            ObservableList<String> observableList= FXCollections.observableArrayList(list);
            listView.setItems(observableList);
        }

    }
    @FXML
    protected void onChooseWord(){
        if(currentView==Current_view.MAIN_VIEW) {
            String s = listView.getSelectionModel().getSelectedItem();
            textField.setText(s);
        }
    }
    @FXML
    protected void onTranslate() throws IOException {
        if(currentView==Current_view.MAIN_VIEW){
//            String mean= Main.dictionaryDao.getDefinitionOf(textField.getText());
            String mean= ConvertToHTML.getInfo(textField.getText(),Main.objectMapper);
            if(mean!=null){
                webView.getEngine().loadContent(mean);
                if(historyList.size()<20){
                    historyList.addFirst(textField.getText());
                }
            }
        }
    }
    @FXML
    protected void onAddWordButtonClick(){
        if(currentView==Current_view.ADD_WORD){
            Word word=new Word(addWordTextField.getText(),addMeaningTextField.getText());
            Main.dictionaryDao.addWord(word);
            Main.dictionary.addWord(word.getWord());
        }
    }
}
