package com.example.controller;

import com.example.ourdictionary.HangMan;
import com.example.ourdictionary.Main;
import com.example.service.IOFile;
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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HangManController extends Controller implements Initializable {
    private final List<String> allWords = IOFile.readFromCommonWord();
    @FXML
    private GridPane gridPane = new GridPane();
    @FXML
    private GridPane currentWord;
    @FXML
    private AnchorPane rootPane = new AnchorPane();
    @FXML
    private AnchorPane resultPane = new AnchorPane();
    @FXML
    private Label result = new Label();
    private HangMan hangMan;
    @FXML
    private ImageView imageView = new ImageView();

    public HangManController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double widthButton = (gridPane.getPrefWidth() - 6 * gridPane.getHgap()) / 7;
        double heightButton = (gridPane.getHeight() - 3 * gridPane.getVgap()) / 4;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                Button button = new Button();
                button.setPrefWidth(widthButton);
                button.setPrefHeight(heightButton);
                if (Main.DARK_MODE) {
                    button.setStyle("-fx-background-color:#95948F;-fx-border-radius: 5px;" +
                            "-fx-background-radius:5;-fx-font-size: " + (MainController.fontSize - 2) + ";");
                } else {
                    button.setStyle("-fx-background-color:#95948F;-fx-border-radius: 5px;-fx-background-radius:5;" +
                            "-fx-font-size: " + (MainController.fontSize - 2) + ";");
                }
                button.setOnMouseEntered(event -> {
                    button.setStyle("-fx-background-color: #adaaa8;");
                });
                button.setOnMouseExited(event -> {
                    button.setStyle("-fx-background-color: #95948F;");
                });
                    char c = (char) ('A' + i * 4 + j);
                    if (c <= 'Z') {
                        String s = String.valueOf(c);
                        button.setText(s);
                        button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onGuessCharacterClick);
                        gridPane.add(button, i, j);
                    }
                }
            }
            currentWord = new GridPane();
            rootPane.getChildren().add(currentWord);
                    hangMan = new HangMan(allWords);
            setNewCurrentWord(hangMan.getAnswer().length());
            if (Main.DARK_MODE) {
                rootPane.setStyle("-fx-background-color: #04293A");
                resultPane.setStyle("-fx-background-color: #04293A");
                result.setStyle("-fx-text-fill:white; -fx-font-size: 14;");
            } else {
                result.setStyle("-fx-font-size: 14;");
            }
            setImage(0);
        }

        private void setNewCurrentWord ( int n){
            currentWord = new GridPane();
            rootPane.getChildren().remove(rootPane.getChildren().size() - 1);
            rootPane.getChildren().add(currentWord);
            int widthLabel = 24;
            currentWord.setHgap(3);
            currentWord.setPrefWidth(n * widthLabel + (n - 1) * currentWord.getHgap());
            currentWord.setPrefHeight(widthLabel);
            currentWord.setLayoutX(440 - currentWord.getPrefWidth() / 2);
            currentWord.setLayoutY(140);
            for (int i = 0; i < n; i++) {
                Label label = new Label();
                label.setPrefHeight(widthLabel - 2);
                label.setPrefWidth(widthLabel - 2);
                if (Main.DARK_MODE) {
                    label.setStyle("-fx-text-fill: white;-fx-border-color: white;-fx-font-size: " + MainController.fontSize + ";");
                } else {
                    label.setStyle("-fx-border-color: black;-fx-font-size: " + MainController.fontSize + ";");
                }
                label.setText("_");
                label.setAlignment(Pos.BASELINE_CENTER);
                label.setBorder(Border.stroke(Paint.valueOf("black")));
                currentWord.add(label, i, 0);
            }
        }

        private void onGuessCharacterClick (MouseEvent event){
            if (hangMan.isOver() || hangMan.isWin()) {
                return;
            }
            Button button = (Button) event.getSource();
            button.setVisible(false);
            hangMan.playerChoose(button.getText());
            if (hangMan.isOver()) {
                resultPane.setVisible(true);
                currentWord.setVisible(false);
                result.setText("You died. Secret word is " + hangMan.getAnswer() + "!");
            } else if (hangMan.isWin()) {
                resultPane.setVisible(true);
                currentWord.setVisible(false);
                result.setText("You won!");
            }
            setTextInCurrentWord(hangMan.getCurrentWord());
            setImage(hangMan.getCurrentWrong());
        }

        private void setTextInCurrentWord (List < Character > list) {
            for (int i = 0; i < list.size(); i++) {
                String tmp = "";
                if (list.get(i) == '_') {
                    tmp += '_';
                } else {
                    tmp += (char) (list.get(i));
                }
                ((Label) (currentWord.getChildren().get(i))).setText(tmp);
            }
        }

        @FXML
        protected void onBackClick () throws IOException {
            changeScreen("chooseGame-view.fxml", "chooseGame.css");
        }

        private void setImage ( int i){
            File file = new File("src\\main\\resources\\image\\" + i + ".jpg");
            Image image = new Image(file.getAbsolutePath());
            imageView.setImage(image);
        }
        @FXML
        protected void onExitClick () throws IOException {
            changeScreen("main-view.fxml", "MainView.css");
        }

        @FXML
        protected void onPlayAgainClick () {
            for (int i = 0; i < 26; i++) {
                gridPane.getChildren().get(i).setVisible(true);
            }
            resultPane.setVisible(false);
            hangMan = new HangMan(allWords);
            setNewCurrentWord(hangMan.getAnswer().length());
            setImage(0);
        }
    }
