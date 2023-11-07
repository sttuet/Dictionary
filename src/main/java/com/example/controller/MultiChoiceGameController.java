package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.ourdictionary.MultiChoiceGame;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MultiChoiceGameController extends Controller implements Initializable {
    List<Button> listButton;
    private MultiChoiceGame game;
    @FXML
    private Label question;
    @FXML
    private Label score;
    @FXML
    private Button A = new Button();
    @FXML
    private Button B = new Button();
    @FXML
    private Button C = new Button();
    @FXML
    private Button D = new Button();
    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            game = new MultiChoiceGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listButton = new ArrayList<>();
        listButton.add(A);
        listButton.add(B);
        listButton.add(C);
        listButton.add(D);
        setQuestion();
        if (Main.DARK_MODE) {
            rootPane.setStyle("-fx-background-color: #04293A;");
            question.setTextFill(Paint.valueOf("#ADC4CE"));
            for (int i = 0; i < 4; i++) {
                listButton.get(i).setStyle("-fx-text-fill: #ADC4CE;-fx-font-size:" + MainController.fontSize
                        + ";");
            }
            question.setFont(Font.font((MainController.fontSize + 8)));
        } else {
            for (int i = 0; i < 4; i++) {
                listButton.get(i).setStyle("-fx-text-fill: #ADC4CE;-fx-font-size:" + MainController.fontSize
                        + ";");
            }
            question.setFont(Font.font((MainController.fontSize + 8)));
        }

    }

    @FXML
    protected void onAnswer(ActionEvent event) {
        Button button = (Button) event.getSource();
        boolean res = game.checkAnswer(button.getText());
        showTrueAnswer(res, button);
        if (res) {
            game.increaseScore();
        }
        score.setText(game.getScore() + "/10");
        if (game.getScore() >= 10) {
            finish.setVisible(true);
            return;
        }
        game.updateListQuestion(game.checkAnswer(button.getText()));

    }

    private void setQuestion() {
        List<String> list = game.getCurrentQuestion();
        question.setText(list.get(0));
        for (int i = 0; i < 4; i++) {
            listButton.get(i).setText(list.get(i + 2));
        }
    }

    private void showTrueAnswer(boolean isTrueAns, Button button) {
        for (int i = 0; i < 4; i++) {
            if (game.checkAnswer(listButton.get(i).getText())) {
                Node node = (((AnchorPane) (gridPane.getChildren().get(i))).getChildren().get(2));
                Node falseNode = button.getParent().getChildrenUnmodifiable().get(1);
                if (!isTrueAns) {
                    falseNode.setOpacity(1);
                }
                node.setOpacity(1);
                PauseTransition pause = new PauseTransition(Duration.millis(1000));
                pause.setOnFinished(event -> {
                    node.setOpacity(0);
                    if (!isTrueAns) {
                        falseNode.setOpacity(0);
                    }
                    setQuestion();
                });
                pause.play();
            }
        }
    }

    @FXML
    protected void onBackClick() throws IOException {
        changeScreen("chooseGame-view.fxml", "chooseGame.css");
    }

    @FXML
    private AnchorPane finish = new AnchorPane();

    @FXML
    protected void onAgainClick() {
        finish.setVisible(false);
        game.reset();
        setQuestion();
    }

    @FXML
    protected void onNewGameClick() throws IOException {
        finish.setVisible(false);
        game = new MultiChoiceGame();
        setQuestion();
    }

    @FXML
    protected void onExitClick() throws IOException {
        finish.setVisible(false);
        changeScreen("chooseGame-view.fxml", "chooseGame.css");
    }
}