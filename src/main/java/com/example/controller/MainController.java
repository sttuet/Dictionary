package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.ConvertToHTML;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.ourdictionary.Main.*;
import static com.example.service.SendRequest.downloadAudio;


public class MainController implements Initializable {
    public static boolean autoPlay = false;
    public static int fontSize = 14;
    @FXML
    public WebView webView = new WebView();
    public FontAwesomeIconView volumeIcon;
    public Button vietLabel;
    public Button engLabel;
    public Button speaker;
    public Button deleteWord;
    public Label GroupNameLabel;
    public HBox Pane;
    public AnchorPane SubAnchorPane;
    public AnchorPane mainAnchorPane;
    public HBox EngVietSpeaker;
    @FXML
    FontAwesomeIconView addFavIcon = new FontAwesomeIconView(FontAwesomeIcon.HEART);
    @FXML
    private Button searchButton;
    @FXML
    private Button recentButton;
    @FXML
    private Button addFavWord;
    @FXML
    private Button settings;
    @FXML
    private Button game;
    @FXML
    private Button translateTextButton;
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
    private Button addFav;

    public void setDarkMode() {
        mainAnchorPane.setStyle("-fx-background-color: #04293A;");
        listView.setStyle("-fx-background-color: #041C32; -fx-background-radius: 10px;-fx-border-radius: 10px;");
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                        listView.setBackground(Background.fill(Paint.valueOf("#041C32")));
                        listView.setStyle("-fx-background-color: #041C32; -fx-background-radius: 10px;-fx-border-radius: 10px;");
                        setFont(Font.font("Arial", fontSize));
                        setStyle("-fx-background-color: #041C32;-fx-border-radius: 10px;-fx-background-radius: 10px");
                        setTextFill(Paint.valueOf("white"));
                        if (isSelected()) {
                            setStyle("-fx-background-color: linear-gradient(to right, #eb3349, #f45c43);"
                                    + "-fx-text-fill: white;"
                                    + "    -fx-border-radius: 10;"
                                    + " -fx-border-color: white;"
                                    + " -fx-background-radius: 10;"
                                    + "    -fx-cursor: hand;");

                        }

                    }

                };
            }
        });
        webView.getEngine().loadContent("<html><body>" +
                " <style> body { background-color:#041C32; } </style></body></html");
        currentWord.setTextFill(Paint.valueOf("white"));
    }

    /**
     * hiển thị các từ có tiền tố giống với phần nhập trong ô tìm kiếm bằng 1 static object {@link com.example.ourdictionary.Dictionary}
     */
    @FXML
    protected void onTypeWord() {
        isShowingFavWord = false;
        List<String> list = Main.dictionary.allWordsHas(inputWord.getText());
        listView.setStyle("-fx-border-color: white; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        listView.setItems(FXCollections.observableList(list));
    }

    protected void deleteWord(boolean FavOrRecent) {
        if (DARK_MODE) {
            listView.setBackground(Background.fill(Paint.valueOf("#041C32")));
        }
        listView.setCellFactory(cell -> {
            return new ListCell<>() {
                final AnchorPane rootLayout = new AnchorPane() {{
                    setTextFill(Paint.valueOf("white"));
                    if (DARK_MODE) {
                        setStyle("-fx-background-color: #041C32; -fx-text-fill: white;");
                    }

                }};
                final Label title = new Label();
                final FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                // The Button we'll include to order the book
                final Button deleteButton = new Button("", iconView);

                {
                    title.setFont(Font.font("Arial", fontSize));
                }

                {
                    if (DARK_MODE) {
                        setStyle("-fx-background-color: transparent");
                        setStyle("-fx-border-color: white");
                    }
                }

                {
                    if (DARK_MODE) {
                        title.setBackground(Background.fill(Paint.valueOf("#041C32")));
                        title.setTextFill(Paint.valueOf("white"));
                        title.setStyle("-fx-background-color: #041C32; -fx-text-fill: white;");
                    }
                    // title.getStyleClass().add("label-anChor");
                }

                {
                    iconView.setFill(Paint.valueOf("black"));
                }

                {
                    deleteButton.setMinWidth(20);
                    deleteButton.setMinHeight(20);
                    deleteButton.setPrefSize(20, 20);
                    deleteButton.setAlignment(Pos.BOTTOM_CENTER);
                    deleteButton.setBackground(Background.fill(Paint.valueOf("transparent")));
                }

                {
                    rootLayout.getChildren().addAll(title, deleteButton);
                    AnchorPane.setRightAnchor(deleteButton, 0.0);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (DARK_MODE) {
                        setStyle("-fx-background-color: #041C32");
                    }
                    if (item != null) {
                        if (cell.isFocused()) {
                            setTextFill(Paint.valueOf("white"));
                        }
                        if (DARK_MODE) {
                            setOnMouseEntered(event -> {
                                rootLayout.setStyle("-fx-background-color: linear-gradient(to right, #eb3349, #f45c43);");
                                title.setStyle("-fx-background-color: linear-gradient(to right, #eb3349, #f45c43);");
                                setStyle(
                                        "  -fx-background-color: linear-gradient(to right, #eb3349, #f45c43);  " +
                                                "-fx-text-fill: white;" +
                                                "    -fx-border-radius: 10;" +
                                                " -fx-border-color: white;" +
                                                "    -fx-background-radius: 10;" +
                                                "    -fx-cursor: hand;" +
                                                "    -fx-text-alignment: LEFT;");
                            });

                            setOnMouseExited(event -> {

                                setStyle("-fx-background-color: #041C32;-fx-text-fill: white;" +
                                        " -fx-border-radius: 10;" +
                                        " -fx-background-radius: 10;");
                                rootLayout.setStyle("-fx-background-color: #041C32;");
                                title.setStyle("-fx-background-color: #041C32; -fx-text-fill: white;");

                            });
                        }

                        title.setText(item);
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
                        setGraphic(rootLayout);
                    } else {
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
            if (recentList.contains(s)) {
                recentList.remove(s);
            }
            recentList.addFirst(s);
            inputWord.setText(s);
            currentWord.setText(s);
            showSpeakerAndHeart(true);
            vietMeaning = ConvertToHTML.vietMeaningToHTML(s, meanings.get(s));
            webView.getEngine().loadContent(vietMeaning);
            if (autoPlay) {
                onSpeakerClick();
            }
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
            if (autoPlay) {
                onSpeakerClick();
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
        engLabel.setVisible(b);
        engLabel.setDisable(!b);
        vietLabel.setVisible(b);
        vietLabel.setDisable(!b);
        EngVietSpeaker.setVisible(b);
        EngVietSpeaker.setDisable(!b);

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
        favouriteList.add(s);
        addFavIcon.setFill(Paint.valueOf("#003366"));
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
    }

    /**
     * change to translate-view.
     *
     * @param event click on book button
     * @throws IOException
     */
    @FXML
    protected void onGoToTranslateViewButtonClick(ActionEvent event) throws IOException {
        Main.changeScreen("translate-view.fxml", "translateView.css", mainAnchorPane.getWidth(), mainAnchorPane.getHeight());
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
        game.setTooltip(new Tooltip("Game"));
        searchButton.setTooltip(new Tooltip("Search"));
        engLabel.setTooltip(new Tooltip("English"));
        vietLabel.setTooltip(new Tooltip("Vietnamese"));
        listView.setStyle("-fx-background-radius:10px; -fx-border-radius: 10px");
        if (Main.DARK_MODE) {
            setDarkMode();
        }
        inputWord.setStyle("-fx-font-size:" + fontSize);
        if (!DARK_MODE) {
            listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> param) {
                    return new ListCell<String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            setText(item);
                            setFont(Font.font("Arial", fontSize));
                        }
                    };
                }
            });
        }

    }

    @FXML
    protected void onGameButtonClick(ActionEvent event) throws IOException {
        Main.changeScreen("chooseGame-view.fxml", "chooseGame.css", mainAnchorPane.getWidth(), mainAnchorPane.getHeight());
    }

    @FXML
    protected void onSettingsButtonClick(ActionEvent actionEvent) throws IOException {
        Main.changeScreen("settings-view.fxml", "settingsView.css", mainAnchorPane.getWidth(), mainAnchorPane.getHeight());
    }

    public void onDeleteButtonClick(ActionEvent event) {
        boolean b = true;
        inputWord.setText("");
        listView.setItems(FXCollections.observableList(new ArrayList<>()));
        webView.getEngine().loadContent("");
        currentWord.setText("");
        EngVietSpeaker.setVisible(!b);
        EngVietSpeaker.setDisable(b);

    }
}
