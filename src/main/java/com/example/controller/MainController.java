package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.ourdictionary.Word;
import com.example.service.ConvertToHTML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.controlsfx.glyphfont.FontAwesome;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static com.example.ourdictionary.Main.*;
import static com.example.service.ParseJSON.fromJson;
import static com.example.service.ParseJSON.transToViet;
import static com.example.service.SendRequest.downloadAudio;
import static com.example.service.SendRequest.sendRequest;


public class MainController {
    private Stage stage;
    private Scene scene;

    @FXML
    private ListView<String> listView = new ListView<>();
    @FXML
    private TextField textField;
    @FXML
    private WebView webView;
    @FXML
    private Label currentWord=new Label("");
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
    protected void onChooseWord()  {
        String s = listView.getSelectionModel().getSelectedItem();
        textField.setText(s);
        currentWord.setText(s);
        showSpeakerAndHeart(true);
        webView.getEngine().loadContent(ConvertToHTML.deleteWordInHTML(s,meanings.get(s)));
    }

    /**
     * dịch từ. 2 nghĩa tiếng viêtj và tiếng anh.
     *
     * @throws IOException ném ngoại lệ.
     */
    @FXML
    protected void onTranslate() throws IOException {

        vietMeaning = ConvertToHTML.deleteWordInHTML(textField.getText(),getInfoInVietnamese(textField.getText()));
        currentWord.setText(textField.getText());
        if (vietMeaning != null) {
            showSpeakerAndHeart(true);
            webView.getEngine().loadContent(vietMeaning);
            addToRecent(textField.getText());
        } else {
            vietMeaning = "";
            showSpeakerAndHeart(false);
            webView.getEngine().loadContent("không tìm thấy từ này trong từ điển tiếng việt");
        }
    }
    private void showSpeakerAndHeart(boolean b){
        speaker.setVisible(b);
        speaker.setDisable(!b);
        addFav.setVisible(b);
        addFav.setDisable(!b);
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
        File file_audio= new File("src\\main\\resources\\audio\\"+currentWord.getText()+".mp3");
        if(!file_audio.exists()){
            try{
                downloadAudio(currentWord.getText());
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

    /**
     * thêm từ yêu thích vào file Favourite.txt.
     *
     * @throws IOException ném ngoại lệ
     */
    @FXML
    private Button addFav;
    @FXML
    private Label speaker;
    @FXML
    protected void addToFavourite() throws IOException {
        fW = new FileWriter(fileFavourite, true);
        bW = new BufferedWriter(fW);
        addToRecent(textField.getText());
    }

    /**
     * hiển thị từ trong favourite.
     * @throws IOException
     */
    @FXML
    protected void showFavouriteWord() throws IOException {
        FileInputStream fIn = new FileInputStream("src\\main\\resources\\data\\Favourite.txt");
        LinkedList<String> favouriteList = new LinkedList<>();
        Scanner sc = new Scanner(fIn);
        while (sc.hasNext()) {
            favouriteList.addFirst(sc.nextLine());
        }
        listView.setItems(FXCollections.observableList(favouriteList));
    }
    @FXML
    protected void onTranslateTextButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(Main.class.getResource("translate-view.fxml"));
        stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
