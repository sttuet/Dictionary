package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.ourdictionary.WordScrambleGame;
import com.example.service.IOFile;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class WordScrambleController extends Controller implements Initializable {
    public static final int QUESTION_NUMBER = 10;
    public static Topic currentTopic;
    private final List<CharLabel> shuffleList = new ArrayList<>();
    private final Stack<CharLabel> answerStack = new Stack<>();
    @FXML
    public AnchorPane answerPane;
    @FXML
    public HBox correctAnswerBox;
    @FXML
    public Button exitButton;
    @FXML
    public Label finalResultLabel;
    @FXML
    public Label finalScoreLabel;
    @FXML
    public Label finalScoreNumberLabel;
    @FXML
    public Button foodButton;
    @FXML
    public TextField holderField;
    @FXML
    public Button jobButton;
    @FXML
    public VBox listBookmarkBox;
    @FXML
    public VBox listWordsBox;
    @FXML
    public Button natureButton;
    @FXML
    public Button newGameButton;
    @FXML
    public Label questionNumberLabel;
    @FXML
    public AnchorPane resultPane;
    @FXML
    public AnchorPane rootPane;
    @FXML
    public Label scoreLabel;
    @FXML
    public Label scoreNumberLabel;
    @FXML
    public Button sportButton;
    @FXML
    public Label topicLabel;
    @FXML
    public AnchorPane topicPane;
    @FXML
    public HBox wrongAnswerBox;
    private String currentAnswer;
    private List<String> wordList;
    private int currentScore = 0;
    private WordScrambleGame game;

    /**
     * khởi tạo rỗng.
     *
     * @throws IOException ngoại lệ io
     */
    public WordScrambleController() throws IOException {
    }

    /**
     * tạo câu hỏi.
     */
    private void setQuestion() {
        holderField.setPrefWidth(30 * game.getWord().length() + 4);
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

    /**
     * kểm tra đáp án
     */
    private void checkAnswer() {
        if (!game.checkAnswer(currentAnswer)) {
            holderField.setStyle("-fx-border-color:red;");
            wrongAnswerBox.setVisible(true);
            fadeTransition(wrongAnswerBox);
        } else {
            holderField.setStyle("-fx-border-color:green;");
            correctAnswerBox.setVisible(true);
            fadeTransition(correctAnswerBox);
        }
        if (currentAnswer.equals(game.getWord())) {
            if (!game.nextQuestion()) {
                endGame();
                return;
            }
            PauseTransition pause = new PauseTransition(Duration.millis(2000));
            pause.setOnFinished(event -> {
                setQuestion();
            });
            pause.play();
        }
    }

    /**
     * kết thúc game.
     */
    private void endGame() {
        resultPane.setVisible(true);
    }

    /**
     * quay trở lại màn hình chọn game.
     *
     * @throws IOException
     */
    @FXML
    void onBackClick() throws IOException {
        changeScreen("chooseGame-view.fxml", "chooseGame.css");
    }

    /**
     * quay tr lại màn hình chọn game.
     *
     * @throws IOException
     */
    @FXML
    void onExitButtonClick() throws IOException {
        changeScreen("chooseGame-view.fxml", "chooseGame.css");
    }

    /**
     * chọn food.
     *
     * @throws IOException ngoại lệ io
     */
    @FXML
    void onFoodButtonClick() throws IOException {
        currentTopic = Topic.FOOD;
        topicPane.setVisible(false);
        answerPane.setVisible(true);
        wordList = IOFile.readFromFoodWords();
    }

    /**
     * chọn job.
     *
     * @throws IOException ngoại lệ io
     */
    @FXML
    void onJobButtonClick() throws IOException {
        currentTopic = Topic.JOB;
        topicPane.setVisible(false);
        answerPane.setVisible(true);
        wordList = IOFile.readFromJobWords();
    }

    /**
     * chọn nature.
     *
     * @throws IOException ngoại lệ io.
     */
    @FXML
    void onNatureButtonClick() throws IOException {
        currentTopic = Topic.NATURE;
        topicPane.setVisible(false);
        answerPane.setVisible(true);
        wordList = IOFile.readFromNatureWords();
    }

    /**
     * chọn thể thao.
     *
     * @throws IOException ngoại lệ
     */
    @FXML
    void onSportButtonClick() throws IOException {
        currentTopic = Topic.SPORT;
        topicPane.setVisible(false);
        answerPane.setVisible(true);
        wordList = IOFile.readFromSportWords();
    }

    /**
     * chọn game mới.
     *
     * @throws IOException ngoại lệ io
     */
    @FXML
    void onNewGameButtonClick() throws IOException {
        resultPane.setVisible(false);
        topicPane.setVisible(true);
        currentScore = 0;
    }

    /**
     * chọn đáp án.
     *
     * @param event sự kiện chuột
     */
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

    /**
     * xóa đáp án.
     */
    @FXML
    private void onDelete() {
        if (answerStack.isEmpty()) {
            return;
        }
        CharLabel tmp = answerStack.peek();
        answerStack.pop();
        tmp.move();
    }

    /**
     * khởi tạo khi chạy game.
     *
     * @param url            url
     * @param resourceBundle nguồn
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topicPane.setVisible(false);
        answerPane.setVisible(true);
        if (Main.DARK_MODE) {
            rootPane.setStyle("-fx-background-color: #04293A");
        }
        natureButton.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        foodButton.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        jobButton.setStyle("-fx-font-size: " + MainController.fontSize + ";");
        sportButton.setStyle("-fx-font-size: " + MainController.fontSize + ";");

        switch (currentTopic) {
            case NATURE:
                try {
                    wordList = IOFile.readFromNatureWords();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case SPORT:
                try {
                    wordList = IOFile.readFromSportWords();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case JOB:
                try {
                    wordList = IOFile.readFromJobWords();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case FOOD:
                try {
                    wordList = IOFile.readFromFoodWords();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }

        game = new WordScrambleGame(wordList);
        holderField.setText("");
        holderField.setOnKeyPressed(keyEvent -> onAnswer(keyEvent));
        for (int i = 0; i < 20; i++) {
            CharLabel charLabel = new CharLabel(i, " ");
            if (Main.DARK_MODE) {
                charLabel.setStyle("-fx-background-color: white");
            }
            rootPane.getChildren().add(charLabel);
        }
        Platform.runLater(() -> holderField.requestFocus());
        setQuestion();
    }

    enum Topic {
        NATURE,
        FOOD,
        JOB,
        SPORT
    }

    private class CharLabel extends Label {
        public final double POS_X_ANS = 204 + 2;
        public final double POS_Y_ANS = 177 + 2;
        private final double WIDTH = 25;
        private final double WIDTH_AND_GAP = 30;
        private final double POS_X = 204;
        private final double POS_Y = 224;
        private double initialPosX = 0;
        private double initialPosY = 0;
        private double currentPosX = 0;
        private double currentPosY = 0;

        /**
         * lớp label chứa char.
         *
         * @param i        i
         * @param alphabet chữ cái
         */
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

        /**
         * kiểm tra có ở vị trí ban đầu không.
         *
         * @return true or false
         */
        public boolean isOnInitPos() {
            return getTranslateX() == 0 && getTranslateY() == 0;
        }

        /**
         * di chuyển khối.
         */
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
                if (currentAnswer.length() == game.getWord().length() && answerStack.size()
                        == game.getWord().length()) {
                    checkAnswer();

                }

            });
            transition.play();
        }

        /**
         * reset vị trí.
         */
        public void reset() {
            currentPosX = initialPosX;
            currentPosY = initialPosY;
        }
    }
}
