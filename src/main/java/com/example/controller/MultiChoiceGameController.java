package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.ourdictionary.MultiChoiceGame;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MultiChoiceGameController extends Controller implements Initializable {
    public HBox correctAnswer;
    public HBox wrongAnswer;
    public VBox resultSet;
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
    @FXML
    private AnchorPane finish = new AnchorPane();

    /**
     * khơi tạo các giá trị cho game.
     *
     * @param url            url
     * @param resourceBundle nguồn
     */
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
            question.setStyle("-fx-text-fill: white;");
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

    /**
     * trả lời đúng hay sai. Nếu đúng tăng điểm và hiển thị hiệu ứng.
     *
     * @param event
     */
    @FXML
    protected void onAnswer(ActionEvent event) {
        Button button = (Button) event.getSource();
        boolean res = game.checkAnswer(button.getText());
        showTrueAnswer(res, button);
        if (res) {
            game.increaseScore();
        }
        score.setText(game.getScore() + "/10");
        if (game.getScore() >= MultiChoiceGame.NUM_QUESTION) {
            showResult();
            return;
        }
        game.updateListQuestion(game.checkAnswer(button.getText()));

    }

    /**
     * tạo câu hỏi cho game.
     */
    private void setQuestion() {
        List<String> list = game.getCurrentQuestion();
        question.setText(list.get(0));
        for (int i = 0; i < 4; i++) {
            listButton.get(i).setText(list.get(i + 2));
        }
    }

    /**
     * tạo hiệu ứng, nếu đúng thì hiển thị hiêệu ứng correct, sai thì incorrect
     *
     * @param isTrueAns đáp án có đúng không
     * @param button    nút chứa đáp án
     */
    private void showTrueAnswer(boolean isTrueAns, Button button) {
        for (int i = 0; i < 4; i++) {
            if (game.checkAnswer(listButton.get(i).getText())) {
                if (!isTrueAns) {
                    wrongAnswer.setVisible(true);
                    fadeTransition(wrongAnswer);
                }
                if (isTrueAns) {
                    correctAnswer.setVisible(true);
                    fadeTransition(correctAnswer);
                }
                PauseTransition pause = new PauseTransition(Duration.millis(2000));
                pause.setOnFinished(event -> {
                    setQuestion();
                });
                pause.play();
            }
        }
    }

    /**
     * quay lại màn hình chọn game.
     *
     * @throws IOException ngoại lệ input output
     */
    @FXML
    protected void onBackClick() throws IOException {
        changeScreen("chooseGame-view.fxml", "chooseGame.css");
    }

    /**
     * chơi lại game.
     */
    @FXML
    protected void onAgainClick() {
        finish.setVisible(false);
        game.reset();
        setQuestion();
    }

    /**
     * chơi một ván mới.
     *
     * @throws IOException ngoại lệ input ouput
     */
    @FXML
    protected void onNewGameClick() throws IOException {
        finish.setVisible(false);
        game = new MultiChoiceGame();
        setQuestion();
    }

    /**
     * thoát khởi màn hình.
     *
     * @throws IOException ngoại lệ io
     */
    @FXML
    protected void onExitClick() throws IOException {
        finish.setVisible(false);
        changeScreen("chooseGame-view.fxml", "chooseGame.css");
    }

    /**
     * đưa ra kết quả trong màn hình.
     */
    public void showResult() {
        List<String> listResult = game.getListResult();
        List<Node> list = resultSet.getChildren();
        for (int i = 0; i < 10; i++) {
            Label label = (Label) (((HBox) list.get(i)).getChildren().get(0));
            label.setText(listResult.get(i));
            label.setStyle("-fx-text-fill: white; -fx-font-size: 12");
        }
        finish.setVisible(true);
        gridPane.setVisible(false);
    }

    /**
     * hiệu ứng khi bấm vào star.
     *
     * @param event event click chuột
     */
    public void showEffect(MouseEvent event) {
        FontAwesomeIconView icon = (FontAwesomeIconView) event.getSource();
        HBox hBox = (HBox) icon.getParent();
        Label newLabel = (Label) hBox.getChildren().get(0);
        int index = newLabel.getText().indexOf('\n');
        String word = newLabel.getText().substring(0, index);
        if (Main.favouriteList.contains(word)) {
            Main.favouriteList.remove(word);
            icon.setFill(Paint.valueOf("white"));
        } else {
            Main.favouriteList.add(word);
            icon.setFill(Paint.valueOf("#003366"));
        }
    }
}