package com.example.controller;

import com.example.ourdictionary.Game;
import com.example.ourdictionary.MultiChoiceGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MultiChoiceGameController implements Initializable {
    private MultiChoiceGame game;
    @FXML
    private Label question;
    @FXML
    private Label score;
    @FXML
    private Button A=new Button();
    @FXML
    private Button B=new Button();
    @FXML
    private Button C=new Button();
    @FXML
    private Button D=new Button();
    List<Button> listButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            game = new MultiChoiceGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listButton=new ArrayList<>();
        listButton.add(A);
        listButton.add(B);
        listButton.add(C);
        listButton.add(D);
        newQuestion();
    }
    @FXML
    protected void onAnswer(ActionEvent event){
        Button button=(Button) event.getSource();
        if(game.checkAnswer(button.getText(),question.getText())){
            score.setText(game.getScore()+"/10");
        }
        newQuestion();
    }
    private void newQuestion(){
        List<String> list= game.getQuestion();
        question.setText(list.get(0));
        int tmp=0;
        for(int i=0;i<4;i++){
            listButton.get(i).setText(list.get(i+1));
        }
    }
}