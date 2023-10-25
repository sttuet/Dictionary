package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.ConvertToHTML;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.ourdictionary.Main.*;
import static com.example.service.SendRequest.downloadAudio;


public class MainController implements Initializable {
    public Label vietLabel;
    public Label engLabel;
    @FXML
    public WebView webView = new WebView();
    public VBox MainWindow;
    @FXML
    FontAwesomeIconView addFavIcon = new FontAwesomeIconView(FontAwesomeIcon.HEART);
    @FXML
    private HBox hBox;
    @FXML
    private Button searchButton;
    @FXML
    private Button recentButton;
    @FXML
    private Button addFavWord;
    @FXML
    private Button settings;
    @FXML
    private Button translateTextButton;
    private Scene scene;
    @FXML
    private ListView<String> listView = new ListView<>();
    @FXML
    private TextField inputWord;
    @FXML
    private Label currentWord = new Label("");
    private String vietMeaning = "";
    private String engMeaning = "";
    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean isShowingFavWord = false;
    @FXML
    private Label speaker;
    @FXML
    private Button addFav;

//    public void setDarkMode() {
//        if (Main.DARK_MODE) {
//            System.out.println("aaaaa");
//            listView.setBackground(Background.fill(Paint.valueOf("black")));
//            webView.getEngine().loadContent("<html><body style=\"background-image: black;\"></body></html");
//        } else {
//            listView.setBackground(Background.fill(Paint.valueOf("linear-gradient(to bottom, #efefbb, #d4d3dd)")));
//        }
//    }

    /**
     * hiển thị các từ có tiền tố giống với phần nhập trong ô tìm kiếm bằng 1 static object {@link com.example.ourdictionary.Dictionary}
     */
    @FXML
    protected void onTypeWord() {
        isShowingFavWord = false;
        List<String> list = Main.dictionary.allWordsHas(inputWord.getText());
        listView.setItems(FXCollections.observableList(list));
    }

    protected void deleteWord(boolean FavOrRecent) {
        listView.setCellFactory(cell -> {
            return new ListCell<>() {
                final AnchorPane rootLayout = new AnchorPane() {{
                }};
                final Label title = new Label();
                FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                // The Button we'll include to order the book
                final Button deleteButton = new Button("", iconView);

                {
                    title.getStyleClass().add("label-anChor");
                }

                {
                    iconView.setFill(Paint.valueOf("black"));
                }

                {
                    //deleteButton.setAlignment(Pos.TOP_RIGHT);
                    deleteButton.setMinWidth(20);
                    deleteButton.setMinHeight(20);
                    deleteButton.setPrefSize(20, 20);
                    deleteButton.setAlignment(Pos.BOTTOM_CENTER);
                    deleteButton.getStyleClass().add("button-hover");
                    if (deleteButton.isHover()) {
                        iconView.setFill(Paint.valueOf("white"));
                    }
                }

                {
                    rootLayout.getChildren().addAll(title, deleteButton);
                    AnchorPane.setRightAnchor(deleteButton, 0.0);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item != null) {

                        title.setText(item);
                        // Simple onAction() method to print out the book we're purchasing.
                        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                if (!listView.getItems().isEmpty()) {
                                    final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
                                    if (selectedIdx != -1) {
                                        String itemToRemove = listView.getSelectionModel().getSelectedItem();

                                        final int newSelectedIdx =
                                                (selectedIdx == listView.getItems().size() - 1)
                                                        ? selectedIdx - 1
                                                        : selectedIdx;
                                        listView.getItems().remove(selectedIdx);
                                        if (FavOrRecent) {
                                            recentList.remove(itemToRemove);
                                        } else {
                                            favouriteList.remove(itemToRemove);
                                        }
                                        listView.getSelectionModel().select(newSelectedIdx);
                                    }
                                }

                            }
                        });

                        // Finally, set this cell's graphic to display the HBox
                        setGraphic(rootLayout);
                    } else {
                        // Not book in this cell, set the graphic to null
                        setGraphic(null);
                    }
                }

            };
        });
    }

    /**
     * bấm vào tù nào thì từ đó hiện lên thanh tìm kiếm
     */
    @FXML
    protected void onChooseWord() {
        String s = listView.getSelectionModel().getSelectedItem();

        if (s != null && !s.equals("")) {
            recentList.addFirst(s);
            inputWord.setText(s);
            currentWord.setText(s);
            showSpeakerAndHeart(true);
            vietMeaning = ConvertToHTML.vietMeaningToHTML(s, meanings.get(s));
            webView.getEngine().loadContent(vietMeaning);
        }

    }

    /**
     * dịch từ. 2 nghĩa tiếng viêtj và tiếng anh.
     */
    @FXML
    protected void onSearchButtonClick() {
        if (inputWord.getText().isEmpty()) {
            vietMeaning = "";
            webView.getEngine().loadContent(vietMeaning);
        } else {
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

    }

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

    /**
     * just speak .
     */
    @FXML
    protected void onSpeakerClick() {
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
        deleteWord(true);
    }

    /**
     * add word to favourite file when click on heart icon.
     */
    @FXML
    protected void addToFavourite() {
        String s = currentWord.getText();
        if (favouriteList.contains(s)) {
            favouriteList.remove(s);
            addFavIcon.setFill(Paint.valueOf("#FFFFFF"));
        } else {
            favouriteList.add(s);
            addFavIcon.setFill(Paint.valueOf("#003366"));
        }
        if (isShowingFavWord) {
            listView.setItems(FXCollections.observableList(new ArrayList<>(favouriteList)));
        }
    }

    /**
     * hiển thị từ trong favourite.
     */
    @FXML
    protected void showFavouriteWord() {
        isShowingFavWord = true;
        List<String> list = new ArrayList<>(favouriteList);
        listView.setItems(FXCollections.observableList(list));
        deleteWord(false);
    }

    /**
     * change to translate-view.
     *
     * @param event click on book button
     * @throws IOException
     */
    @FXML
    protected void onGoToTranslateViewButtonClick(ActionEvent event) throws IOException {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), hBox);
        translateTransition.setFromX(0);
        translateTransition.setToX(600);
        translateTransition.setOnFinished((ActionEvent event1) -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("translate-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("translateView.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        });
        translateTransition.play();
    }

    /**
     * thêm tooltip
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recentButton.setTooltip(new Tooltip("Recent words"));
        addFavWord.setTooltip(new Tooltip("Favourite words"));
        translateTextButton.setTooltip(new Tooltip("Translate sentences"));
        settings.setTooltip(new Tooltip("Settings"));
        searchButton.setTooltip(new Tooltip("Search"));
        engLabel.setTooltip(new Tooltip("English"));
        vietLabel.setTooltip(new Tooltip("Vietnamese"));

        // setDarkMode();
    }

    @FXML
    public void onSettingsButtonClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("settings-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("settingsView.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
