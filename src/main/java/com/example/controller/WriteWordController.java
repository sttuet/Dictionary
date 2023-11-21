package com.example.controller;

import com.example.ourdictionary.WriteWord;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class WriteWordController extends Controller implements Initializable {
    private final List<CharLabel> shuffleList = new ArrayList<>();
    private final Stack<CharLabel> answerStack = new Stack<>();
    @FXML
    private Label question;
    @FXML
    private TextField holder;
    @FXML
    private AnchorPane rootPane;
    private WriteWord game;
    private String currentAnswer;
    @FXML
    private AnchorPane endPane;

    private void setQuestion() {
        question.setText(game.getMeaning());
        holder.setPrefWidth(30 * game.getWord().length() + 4);
        currentAnswer = "";
        for (CharLabel charLabel : shuffleList) {
            charLabel.setTranslateX(0);
            charLabel.setTranslateY(0);
            charLabel.reset();
        }
        shuffleList.removeAll(shuffleList);
        answerStack.removeAll(answerStack);
        List<String> list = game.getShuffleButtons();
        int m = rootPane.getChildren().size();
        int n = list.size();
        for (int i = 0; i < n; i++) {
            CharLabel label = (CharLabel) rootPane.getChildren().get(i + m - 20);
            label.setText(list.get(i));
            label.setVisible(true);
            shuffleList.add(label);
        }
        for (int i = n; i < 20; i++) {
            rootPane.getChildren().get(i + m - 20).setVisible(false);
        }
    }

    private void checkAnswer() {
        if (!game.checkAnswer(currentAnswer)) {
            holder.setStyle("-fx-border-color:red;");
        } else {
            holder.setStyle("-fx-border-color:green;");
        }
        if (currentAnswer.equals(game.getWord())) {
            if (game.nextQuestion() == false) {
                endGame();
                return;
            }
            ;
            setQuestion();
        }
    }

    private void endGame() {
        endPane.setVisible(true);
    }

    @FXML
    private void onDelete() {
        if (answerStack.isEmpty()) {
            return;
        }
        CharLabel tmp = answerStack.peek();
        answerStack.pop();
        tmp.move();
    }

    private void onSpeak() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new WriteWord();
        holder.setText("");
        holder.setOnKeyPressed(keyEvent -> onAnswer(keyEvent));
        for (int i = 0; i < 20; i++) {
            rootPane.getChildren().add(new CharLabel(i, " "));
        }
        Platform.runLater(() -> holder.requestFocus());
        setQuestion();

    }

    @FXML
    public void onAnswer(KeyEvent event) {
        char c = event.getCode().getChar().charAt(0);
        if (event.getCode() == KeyCode.BACK_SPACE) {
            onDelete();
            return;
        }
        if (c <= 'Z' && c >= 'A') {
            String tmp = currentAnswer;
            for (int i = 0; i < shuffleList.size(); i++) {
                CharLabel charLabel = shuffleList.get(i);
                if ((char) (c - 'A' + 'a') == charLabel.getText().charAt(0) && charLabel.isOnInitPos()) {
                    charLabel.move();
                    break;
                }
            }
        }
    }

    @FXML
    protected void onSpeaker() throws IOException {
        super.onSpeakerClick(game.getWord());
    }

    @FXML
    protected void onBackClick() throws IOException {
        changeScreen("chooseGame-view.fxml", "chooseGame.css");
    }

    private class CharLabel extends Label {
        public final double POS_X_ANS = holder.getLayoutX() + 2;
        public final double POS_Y_ANS = holder.getLayoutY() + 2;
        private final double WIDTH = 25;
        private final double WIDTH_AND_GAP = 30;
        private final double POS_X = 310;
        private final double POS_Y = 248;
        private double initialPosX = 0;
        private double initialPosY = 0;
        private double currentPosX = 0;
        private double currentPosY = 0;

        public CharLabel(int i, String alphabet) {
            super(alphabet);
            super.setPrefSize(WIDTH, WIDTH);
            initialPosX = POS_X + (i % 10) * WIDTH_AND_GAP;
            initialPosY = POS_Y + i / 10 * WIDTH_AND_GAP;
            currentPosX = initialPosX;
            currentPosY = initialPosY;
            super.setLayoutX(initialPosX);
            super.setLayoutY(initialPosY);
            super.setStyle("-fx-border-color:black;");
            super.setAlignment(Pos.CENTER);
        }

        public boolean isOnInitPos() {
            return getTranslateX() == 0 && getTranslateY() == 0;
        }

        public void move() {
            double translateX = currentPosX;
            double translateY = currentPosY;
            if (isOnInitPos()) {
                int i = currentAnswer.length();
                currentPosX = POS_X_ANS + i * WIDTH_AND_GAP;
                currentPosY = POS_Y_ANS;
            } else {
                currentPosX = initialPosX;
                currentPosY = initialPosY;
            }
            if (isOnInitPos()) {
                currentAnswer = currentAnswer + this.getText();
            } else {
                currentAnswer = currentAnswer.substring(0, currentAnswer.length() - 1);
            }

            TranslateTransition transition = new TranslateTransition(Duration.millis(300), this);
            transition.setToX(!isOnInitPos() ? 0 : currentPosX - translateX);
            transition.setToY(!isOnInitPos() ? 0 : currentPosY - translateY);
            transition.setOnFinished(event -> {
                if (!isOnInitPos()) {
                    answerStack.push(this);
                }
                if (currentAnswer.length() == game.getWord().length() && answerStack.size() == game.getWord().length()) {
                    checkAnswer();

                }

            });
            transition.play();
        }

        public void reset() {
            currentPosX = initialPosX;
            currentPosY = initialPosY;
        }
    }
}