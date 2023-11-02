package com.example.controller;

import com.example.ourdictionary.HangMan;
import com.example.ourdictionary.Main;
import com.example.service.IOFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HangManController implements Initializable {
    private List<String> allWords= IOFile.readFromCommonWord();
    @FXML
    private GridPane gridPane=new GridPane();
    @FXML
    private GridPane currentWord;
    @FXML
    private AnchorPane parent=new AnchorPane();
    @FXML
    private AnchorPane resultPane=new AnchorPane();
    @FXML
    private Label result=new Label();
    private HangMan hangMan;

    public HangManController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double widthButton=(gridPane.getPrefWidth()-6*gridPane.getHgap())/7;
        double heightButton=(gridPane.getHeight()-3*gridPane.getVgap())/4;
        for(int i=0;i<7;i++){
            for(int j=0;j<4;j++){
                Button button=new Button();
                button.setPrefWidth(widthButton);
                button.setPrefHeight(heightButton);
                char c=(char)('A'+i*4+j);
                if(c<='Z'){
                    String s=""+c;
                    button.setText(s);
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEvent -> {
                        onGuessCharacterClick(mouseEvent);
                    });
                    gridPane.add(button,i,j);
                }
            }
        }
        currentWord=new GridPane();
        parent.getChildren().add(currentWord);
        hangMan=new HangMan(allWords);
        setNewCurrentWord(hangMan.getAnswer().length());
    }
    private void setNewCurrentWord(int n){
        currentWord=new GridPane();
        parent.getChildren().remove(parent.getChildren().size()-1);
        parent.getChildren().add(currentWord);
        int widthLabel=24;
        currentWord.setHgap(3);
        currentWord.setPrefWidth(n*widthLabel+(n-1)*currentWord.getHgap());
        currentWord.setPrefHeight(widthLabel);
        currentWord.setLayoutX(440-currentWord.getPrefWidth()/2);
        currentWord.setLayoutY(140);
        for(int i=0;i<n;i++){
            Label label=new Label();
            label.setPrefHeight(widthLabel-2);
            label.setPrefWidth(widthLabel-2);
            label.setText("_");
            label.setAlignment(Pos.BASELINE_CENTER);
            label.setBorder(Border.stroke(Paint.valueOf("black")));
            currentWord.add(label,i,0);
        }
    }
    private void onGuessCharacterClick(MouseEvent event){
        if(hangMan.isOver()|| hangMan.isWin()){
            return;
        }
        Button button= (Button) event.getSource();
        button.setVisible(false);
        hangMan.playerChoose(button.getText());
        if(hangMan.isOver()){
            resultPane.setVisible(true);
            result.setText("You died. Secret word is "+hangMan.getAnswer()+"!");
        }else if(hangMan.isWin()){
            resultPane.setVisible(true);
            result.setText("You won!");
        }
        setTextInCurrentWord(hangMan.getCurrentWord());
        setImage(hangMan.getCurrentWrong());
    }
    private void setTextInCurrentWord(List<Character> list){
        for(int i=0;i<list.size();i++){
            String tmp="";
            if(list.get(i)=='_'){
                tmp+='_';
            }else{
                tmp+=(char)(list.get(i));
            }
            ((Label)(currentWord.getChildren().get(i))).setText(tmp);
        }
    }
    @FXML
    protected void onBackClick() throws IOException {
        Main.changeScreen("chooseGame-view.fxml","chooseGame.css", parent.getWidth(), parent.getHeight());
    }
    @FXML
    private ImageView imageView=new ImageView();
    private void setImage(int i){
        Image image=new Image("D:\\Projects\\OurDictionary\\src\\main\\resources\\image\\pic"+ i +".png");
        imageView.setImage(image);
    }
    @FXML
    protected void onExitClick() throws IOException {
        Main.changeScreen("main-view.fxml","MainView.css", parent.getWidth(), parent.getHeight());
    }
    @FXML
    protected void onPlayAgainClick(){
        for(int i=0;i<26;i++){
            gridPane.getChildren().get(i).setVisible(true);
        }
        hangMan=new HangMan(allWords);
        setNewCurrentWord(hangMan.getAnswer().length());
        setImage(0);
    }
}
