package com.example.controller;

import com.example.ourdictionary.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static com.example.ourdictionary.Main.*;


public class MainController {

    @FXML
    private ListView<String> listView = new ListView<>();
    @FXML
    private TextField textField;
    @FXML
    private WebView webView;
    private String vietMeaning = "";
    private String engMeaning = "";
    @FXML
    private Media media;
    private MediaPlayer mediaPlayer;

    /**
     * trả về các từ vừa tra vào ListView.
     */
    @FXML

    protected void onHistoryButtonClick() throws FileNotFoundException {
        FileInputStream fIn = new FileInputStream("src\\main\\resources\\data\\Recent.txt");
        LinkedList<String> historyList = new LinkedList<>();
        Scanner sc = new Scanner(fIn);
        while (sc.hasNext()) {
            historyList.addFirst(sc.nextLine());
        }
        listView.setItems(FXCollections.observableList(historyList));
    }

    /**
     * hiển thị các từ có tiền tố giống với phần nhập trong ô tìm kiếm bằng 1 static object {@link com.example.ourdictionary.Dictionary}
     */
    @FXML
    protected void onTypeWord() {
        List<String> list = Main.dictionary.allWordsHas(textField.getText());
        ObservableList<String> observableList = FXCollections.observableArrayList(list);
        listView.setItems(observableList);

    }

    /**
     * bấm vào tù nào thì từ đó hiện lên thanh tìm kiếm
     */
    @FXML
    protected void onChooseWord() {
        String s = listView.getSelectionModel().getSelectedItem();
        textField.setText(s);
        webView.getEngine().loadContent(data.get(s));

    }

    /**
     * dịch từ. 2 nghĩa tiếng viêtj và tiếng anh.
     *
     * @throws IOException
     */
    @FXML
    protected void onTranslate() throws IOException {
        vietMeaning = getInfoInVietnamese(textField.getText());
        if (vietMeaning != null) {
            webView.getEngine().loadContent(vietMeaning);
            addToRecent(textField.getText());
            media = new Media(new File("src\\main\\resources\\audio.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(media);
        } else {
            vietMeaning = "";
            webView.getEngine().loadContent("không tìm thấy từ này trong từ điển tiếng việt");
        }
        engMeaning = getInfoInEnglish(textField.getText());
    }

    /**
     * chuyển sang nghĩa tiêngs anh
     */
    @FXML
    protected void onEngLabelClick() {
        webView.getEngine().loadContent(engMeaning);
    }

    /**
     * chuyển sang nghĩa tiếng việt.
     */
    @FXML
    protected void onVietLabelClick() {
        webView.getEngine().loadContent(vietMeaning);
    }

    @FXML
    protected void onSpeakerClick() {
        mediaPlayer.play();
        mediaPlayer.seek(mediaPlayer.getStartTime());
    }
}
