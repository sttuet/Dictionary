package com.example.controller;

import com.example.ourdictionary.Game;
import com.example.ourdictionary.Main;
import com.example.ourdictionary.MultiChoiceGame;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

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
    @FXML
    private GridPane gridPane;
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
        game.createQuestion();
        setQuestion();
    }
    @FXML
    protected void onAnswer(ActionEvent event){
        Button button=(Button) event.getSource();
        boolean res=game.checkAnswer(button.getText());
        showTrueAnswer(res,button);
        if(res==true){
            game.increaseScore();
            if(game.getScore()==10){
                replay();
                return;
            }
        }
        score.setText(game.getScore()+"/10");
        game.updateListQuestion(game.checkAnswer(button.getText()));
    }

    private void setQuestion(){
        List<String> list= game.getCurrentQuestion();
        question.setText(list.get(0));
        for(int i=0;i<4;i++){
            listButton.get(i).setText(list.get(i+2));
        }
    }
    private void showTrueAnswer(boolean isTrueAns,Button button){
        for(int i=0;i<4;i++){
            if(game.checkAnswer(listButton.get(i).getText())){
                Node node=(((AnchorPane)(gridPane.getChildren().get(i))).getChildren().get(2));
                Node falseNode=button.getParent().getChildrenUnmodifiable().get(1);
                if(!isTrueAns){
                    falseNode.setOpacity(1);
                }
                node.setOpacity(1);
                PauseTransition pause=new PauseTransition(Duration.millis(1000));
                pause.setOnFinished(event -> {
                    node.setOpacity(0);
                    if(!isTrueAns){
                        falseNode.setOpacity(0);
                    }
                    setQuestion();
                });
                pause.play();
            }
        }
    }
    private void replay(){

    }
    @FXML
    protected void onBackClick(ActionEvent event) throws IOException {
        Main.changeScreen("chooseGame-view.fxml","chooseGame.css");
    }
}