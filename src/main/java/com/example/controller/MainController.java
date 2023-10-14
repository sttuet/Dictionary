package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.ConvertToHTML;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import de.jensd.fx.glyphs.fontawesome.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.ourdictionary.Main.*;
import static com.example.service.SendRequest.downloadAudio;


public class MainController {
    private Stage stage;
    private Scene scene;
    @FXML
    private ListView<String> listView = new ListView<>();
    @FXML
    private TextField inputWord;
    @FXML
    private WebView webView;
    @FXML
    private Label currentWord = new Label("");
    private String vietMeaning = "";
    private String engMeaning = "";
    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean isShowingFavWord = false;

    /**
     * hiển thị các từ có tiền tố giống với phần nhập trong ô tìm kiếm bằng 1 static object {@link com.example.ourdictionary.Dictionary}
     */
    @FXML
    protected void onTypeWord() {
        isShowingFavWord = false;
        List<String> list = Main.dictionary.allWordsHas(inputWord.getText());
        listView.setItems(FXCollections.observableList(list));
    }

    /**
     * bấm vào tù nào thì từ đó hiện lên thanh tìm kiếm
     */
    @FXML
    protected void onChooseWord() {
        String s = listView.getSelectionModel().getSelectedItem();

        if (s == null || s.equals("")) {
            return;
        } else {
            recentList.addFirst(s);
            inputWord.setText(s);
            currentWord.setText(s);
            showSpeakerAndHeart(true);
            vietMeaning=ConvertToHTML.vietMeaningToHTML(s, meanings.get(s));
            webView.getEngine().loadContent(vietMeaning);
        }

    }

    /**
     * dịch từ. 2 nghĩa tiếng viêtj và tiếng anh.
     *
     * @throws IOException ném ngoại lệ.
     */
    @FXML
    protected void onSearchButtonClick() throws IOException {
        vietMeaning = ConvertToHTML.vietMeaningToHTML(inputWord.getText(), getInfoInVietnamese(inputWord.getText()));
        currentWord.setText(inputWord.getText());
        if (vietMeaning != null) {
            showSpeakerAndHeart(true);
            webView.getEngine().loadContent(vietMeaning);
            recentList.add(currentWord.getText());
        } else {
            vietMeaning = "";
            showSpeakerAndHeart(false);
            webView.getEngine().loadContent("không tìm thấy từ này trong từ điển tiếng việt");
        }
    }

    @FXML
    FontAwesomeIconView addFavIcon = new FontAwesomeIconView(FontAwesomeIcon.HEART);

    private void showSpeakerAndHeart(boolean b) {
        speaker.setVisible(b);
        speaker.setDisable(!b);
        if (favouriteList.contains(currentWord.getText())) {
            addFavIcon.setFill(Paint.valueOf("#003366"));
        } else {
            addFavIcon.setFill(Paint.valueOf("#ffffff"));
        }
        addFav.setVisible(b);
        addFav.setDisable(!b);
    }

    @FXML
    private Label engLabel;
    @FXML
    private Label vietLabel;


    /**
     * chuyển sang nghĩa tiêngs anh
     */
    @FXML
    protected void onEngLabelClick() throws IOException {
        if (!currentWord.getText().equals("") && !(currentWord.getText() == null)) {
            engMeaning = getInfoInEnglish(inputWord.getText());
        }
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
    private Label speaker;

    /**
     * just speak .
     *
     * @throws IOException
     */
    @FXML
    protected void onSpeakerClick() throws IOException {
        File file_audio = new File("src\\main\\resources\\audio\\" + currentWord.getText() + ".mp3");
        if (!file_audio.exists()) {
            try {
                downloadAudio(currentWord.getText());
            } catch (Exception e) {
                System.out.println("cant download");
            }
        }
        try {
            media = new Media(file_audio.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            mediaPlayer.seek(mediaPlayer.getStartTime());
        } catch (Exception e) {
            System.out.println("cant create media");
        }
    }

    /**
     * trả về các từ vừa tra vào ListView.
     */
    @FXML
    protected void onRecentButtonClick() {
        listView.setItems(FXCollections.observableList(recentList));
    }

    /**
     * thêm từ yêu thích vào file Favourite.txt.
     *
     * @throws IOException ném ngoại lệ
     */
    @FXML
    private Button addFav;

    /**
     * add word to favourite file when click on heart icon.
     *
     * @throws IOException
     */
    @FXML
    protected void addToFavourite() throws IOException {
        String s = currentWord.getText();
        if (favouriteList.contains(s)) {
            favouriteList.remove(s);
            addFavIcon.setFill(Paint.valueOf("#FFFFFF"));
        } else {
            favouriteList.add(s);
            addFavIcon.setFill(Paint.valueOf("#003366"));
        }
        if (isShowingFavWord) {
            listView.setItems(FXCollections.observableList((List<String>) favouriteList.stream().collect(Collectors.toList())));
        }
    }

    /**
     * hiển thị từ trong favourite.
     *
     * @throws IOException
     */
    @FXML
    protected void showFavouriteWord() throws IOException {
        isShowingFavWord = true;
        List<String> list = new ArrayList<>();
        for (String s : favouriteList) {
            list.add(s);
        }
        listView.setItems(FXCollections.observableList(list));
    }

    /**
     * change to translate-view.
     *
     * @param event click on book button
     * @throws IOException
     */
    @FXML
    protected void onGoToTranslateViewButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("translate-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
