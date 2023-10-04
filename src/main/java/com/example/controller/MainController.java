package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.ourdictionary.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
import static com.example.service.ParseJSON.fromJson;
import static com.example.service.SendRequest.downloadAudio;
import static com.example.service.SendRequest.sendRequest;


public class MainController {

    @FXML
    private ListView<String> listView = new ListView<>();
    @FXML
    private TextField textField;
    @FXML
    private WebView webView;
    private String currentWord="";
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
        currentWord=s;
        webView.getEngine().loadContent(meanings.get(s));

    }

    /**
     * dịch từ. 2 nghĩa tiếng viêtj và tiếng anh.
     *
     * @throws IOException
     */
    @FXML
    protected void onTranslate() throws IOException {
        vietMeaning = getInfoInVietnamese(textField.getText());
        currentWord=textField.getText();
        if (vietMeaning != null) {

            webView.getEngine().loadContent(vietMeaning);
            addToRecent(textField.getText());
        } else {
            vietMeaning = "";
            webView.getEngine().loadContent("không tìm thấy từ này trong từ điển tiếng việt");
        }
    }
    @FXML
    private Label engLabel;
    @FXML
    private Label vietLabel;
    private boolean gotMeanEnglish=false;
    /**
     * chuyển sang nghĩa tiêngs anh
     */
    @FXML
    protected void onEngLabelClick() throws IOException {
        engLabel.setStyle("-fx-background-color:white");
        vietLabel.setStyle("-fx-background-color:#dddddd");
        if (!gotMeanEnglish) {
            engMeaning = getInfoInEnglish(textField.getText());
        }
        webView.getEngine().loadContent(engMeaning);
    }

    /**
     * chuyển sang nghĩa tiếng việt.
     */
    @FXML
    protected void onVietLabelClick() {
        engLabel.setStyle("-fx-background-color:#dddddd");
        vietLabel.setStyle("-fx-background-color:white");
        webView.getEngine().loadContent(vietMeaning);
    }

    /**
     * just speak .
     * @throws IOException
     */
    @FXML
    protected void onSpeakerClick() throws IOException {
        File file_audio= new File("src\\main\\resources\\audio\\"+currentWord+".mp3");
        if(!file_audio.exists()){
            try{
                downloadAudio(currentWord);
            }catch (Exception e){
                System.out.println("cant download");
            }
        }try {
            media = new Media(file_audio.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            mediaPlayer.seek(mediaPlayer.getStartTime());
        }catch (Exception e){
            System.out.println("cant create media");
        }
    }
}
