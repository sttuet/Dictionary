package com.example.controller;
import com.example.ourdictionary.Main;
import com.example.ourdictionary.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import java.util.List;

enum Current_view{
    MAINVIEW,ADD_WORD
}
public class MainController {
    private Current_view currentView=Current_view.MAINVIEW;
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
        currentView=Current_view.MAINVIEW;
    }
    @FXML
    protected void onTypeWord(){
        if(currentView==Current_view.MAINVIEW){
            List<String> list= Main.dictionary.allWordsHas(textField.getText());
            ObservableList<String> observableList= FXCollections.observableArrayList(list);
            listView.setItems(observableList);
        }

    }
    @FXML
    protected void onChooseWord(){
        if(currentView==Current_view.MAINVIEW) {
            String s = listView.getSelectionModel().getSelectedItem();
            textField.setText(s);
        }
    }
    @FXML
    protected void onTranslate(){
        if(currentView==Current_view.MAINVIEW){
            String mean= Main.dictionaryDao.getDefinitionOf(textField.getText());
            webView.getEngine().loadContent(mean);
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
