package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.SendRequest;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class Controller {
    public Pane rootPane;

    protected void onSpeakerClick(String word) throws IOException {
        File file_audio = new File("src\\main\\resources\\audio\\" + word + ".mp3");
        if (!file_audio.exists()) {
            SendRequest.downloadAudio(word);
        }
        if (file_audio.exists()) {
            try {
                Media media = new Media(file_audio.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                mediaPlayer.seek(mediaPlayer.getStartTime());
            } catch (Exception e) {
                System.out.println("cant create media");
            }
        }
    }

    public void changeScreen(String fxml, String cssFile) throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
        Scene scene = new Scene(pane, rootPane.getWidth(), rootPane.getHeight());
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(cssFile)).toExternalForm());
        Stage stage = (Stage) (rootPane.getScene().getWindow());
        stage.setScene(scene);
        stage.show();

    }

    public void changeScreenFromLogin(String fxml, String cssFile) throws IOException {
        Main.loadData();
        Pane pane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
        Scene scene = new Scene(pane, 824, 537);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(cssFile)).toExternalForm());
        Stage stage = (Stage) (rootPane.getScene().getWindow());
        stage.setX(320);
        stage.setY(100);
        stage.setScene(scene);
        stage.show();

    }

    public void changeScreenFromMain(String fxml, String cssFile) throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
        Scene scene = new Scene(pane, 372, 543);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(cssFile)).toExternalForm());
        Stage stage = (Stage) (rootPane.getScene().getWindow());
        stage.setX(600);
        stage.setY(180);
        stage.setScene(scene);
        stage.show();

    }
    public void fadeTransition(HBox hBox) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), hBox);

        // Đặt giá trị alpha (độ trong suốt) từ 0 (biến mất) đến 1 (hiển thị)
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        // Đặt thời gian hiệu ứng xuất hiện và biến mất
        fadeTransition.setCycleCount(1);

        // Bắt đầu hiệu ứng
        fadeTransition.play();
    }
}
