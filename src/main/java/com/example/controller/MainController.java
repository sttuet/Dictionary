package com.example.controller;

import com.example.ourdictionary.Main;
import com.example.service.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.ourdictionary.Main.*;
import static com.example.service.ConvertToHTML.getInfoEng;


public class MainController extends Controller implements Initializable {
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
    public HBox EngVietSpeaker;
    @FXML
    FontAwesomeIconView addFavIcon = new FontAwesomeIconView(FontAwesomeIcon.STAR);
    @FXML
    AnchorPane editPane;
    @FXML
    Button refreshButton;
    @FXML
    Button saveButton;
    @FXML
    TextArea editTextArea;
    @FXML
    TextField editTextField;
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
    private Button modifyWordButton;
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
    private boolean isShowingFavWord = false;
    @FXML
    private Button addFav;
    private DictionaryDao dictionaryDao;

    public void setDarkMode() {
        rootPane.setStyle("-fx-background-color: #04293A;");
        listView.setStyle("-fx-background-color: #041C32; -fx-background-radius: 10px;-fx-border-radius: 10px;");
        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
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

    protected void deleteWord() {
        if (DARK_MODE) {
            listView.setBackground(Background.fill(Paint.valueOf("#041C32")));
        }
        listView.setCellFactory(cell -> new ListCell<>() {
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
                    deleteButton.setOnAction(event -> {
                        if (!listView.getItems().isEmpty()) {
                            final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
                            if (selectedIdx != -1) {
                                String itemToRemove = listView.getSelectionModel().getSelectedItem();

                                final int newSelectedIdx =
                                        (selectedIdx == listView.getItems().size() - 1)
                                                ? selectedIdx - 1
                                                : selectedIdx;

                                listView.getItems().remove(selectedIdx);
                                recentList.remove(itemToRemove);
                                listView.getSelectionModel().select(newSelectedIdx);
                            }
                        }

                    });
                    setGraphic(rootLayout);
                } else {
                    setGraphic(null);
                }
            }

        });
    }

    /**
     * bấm vào tù nào thì từ đó hiện lên thanh tìm kiếm
     */
    @FXML
    protected void onChooseWord() {
        String s = listView.getSelectionModel().getSelectedItem();
        if (s != null && !s.equals("")) {
            recentList.remove(s);
            recentList.addFirst(s);
            inputWord.setText(s);
            currentWord.setText(s);
            showSpeakerAndHeart(true);

            showWebView(s);
            {
                new Thread(() -> {
                    try {
                        if (!currentWord.getText().equals("") && !(currentWord.getText() == null)) {
                            SendRequest.downloadAudio(currentWord.getText());
                            if (autoPlay) {
                                onSpeakerClick();
                            }
                            engMeaning = getInfoEng(currentWord.getText(), objectMapper);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }

    }

    /**
     * get definition (viet or eng).
     */
    @FXML
    protected void onSearchButtonClick() {
        if (inputWord.getText().isEmpty()) {
            vietMeaning = "";
        } else {
            {
                new Thread(() -> {
                    try {
                        if (!currentWord.getText().equals("") && !(currentWord.getText() == null)) {
                            SendRequest.downloadAudio(currentWord.getText());
                            if (autoPlay) {
                                this.onSpeakerClick();
                            }
                            engMeaning = getInfoEng(currentWord.getText(), objectMapper);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
            currentWord.setText(inputWord.getText());
            showWebView(currentWord.getText());
            showSpeakerAndHeart(true);
        }

    }

    private void showSpeakerAndHeart(boolean b) {
        EngVietSpeaker.setVisible(b);
        if (b) {
            EngVietSpeaker.toFront();
        } else {
            EngVietSpeaker.toBack();
        }
        speaker.setVisible(b);
        addFav.setVisible(b);
        vietLabel.setVisible(b);
        engLabel.setVisible(b);
        if (favouriteList.contains(currentWord.getText())) {
            addFavIcon.setFill(Paint.valueOf("#003366"));
        } else {
            addFavIcon.setFill(Paint.valueOf("#ffffff"));
        }


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
        showWebView(currentWord.getText());
    }

    /**
     * trả về các từ vừa tra vào ListView.
     */
    @FXML
    protected void onRecentButtonClick() {
        listView.setItems(FXCollections.observableList(recentList));
        deleteWord();
    }

    /**
     * add word to favourite file when click on heart icon.
     */
    @FXML
    protected void addToFavourite() throws SQLException {
        String s = currentWord.getText();
        if (!favouriteList.contains(s) && meanings.containsKey(s)) {
            favouriteList.add(s);
            addFavIcon.setFill(Paint.valueOf("#003366"));
//            if (!isGuest) {
//                dictionaryDao.insertWord(s);
//            }
        } else {
            favouriteList.remove(s);
//            dictionaryDao.removeWord(s);
            addFavIcon.setFill(Paint.valueOf("white"));
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
    }

    /**
     * change to translate-view.
     *
     * @throws IOException exception
     */
    @FXML
    protected void onGoToTranslateViewButtonClick() throws IOException {
        changeScreen("translate-view.fxml", "translateView.css");
    }

    /**
     * add tooltip.
     *
     * @param url            url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyWordButton.setTooltip(new Tooltip("Modify words"));
        saveButton.setTooltip(new Tooltip("Save words changed"));
        refreshButton.setTooltip(new Tooltip("Reset words"));
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
            listView.setCellFactory(new Callback<>() {
                @Override
                public ListCell<String> call(ListView<String> param) {
                    return new ListCell<>() {
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
    protected void onGameButtonClick() throws IOException {
        changeScreen("chooseGame-view.fxml", "chooseGame.css");
    }

    @FXML
    protected void onSettingsButtonClick() throws IOException {
        super.changeScreen("settings-view.fxml", "settingsView.css");
    }

    public void onDeleteButtonClick() {
        inputWord.setText("");
        listView.setItems(FXCollections.observableList(new ArrayList<>()));
        showWebView("");
        currentWord.setText("");
        EngVietSpeaker.setVisible(false);

    }

    public void onSpeakerClick() throws IOException {
        if (checkInternetConnection()) {
            super.onSpeakerClick(currentWord.getText());
        }
        else {
            System.out.println("no internet connection");
        }
    }

    /**
     * hiển thị editPane bao gồm editTextField (từ đang sửa), editTextArea (nghĩa mình đang sửa)
     * saveButton (lưu từ sau khi sửa), refreshButton( trả lại nghĩa ban đầu của từ)
     */
    @FXML
    protected void onModifyButtonClick() {
        showSpeakerAndHeart(false);
        editPane.setVisible(true);
        String backGroundColor = "white";
        String textColor1 = "black";
        if (Main.DARK_MODE) {
            backGroundColor = "#041C32";
            textColor1 = "white";
        }
        editTextArea.setStyle("-fx-background-color:" + backGroundColor + ";-fx-text-fill:" + textColor1 + ";-fx-font-size:" + fontSize + ";");
        if (modifiedWord.containsKey(currentWord.getText())) {
            editTextArea.setText(modifiedWord.get(currentWord.getText()));
        } else {
            editTextArea.setText(GetContentOfHTML.parse(currentWord.getText()));
        }
        editTextField.setText(currentWord.getText());
    }

    /**
     * lưu từ sau khi sửa
     */
    @FXML
    protected void onSaveButtonClick() {
        if (IOFile.isValidWord(editTextField.getText())) { // từ phải đúng định dạng
            modifiedWord.put(editTextField.getText(), editTextArea.getText());
            currentWord.setText(editTextField.getText());
            if (!dictionary.has(editTextField.getText())) {
                dictionary.addWord(editTextField.getText());
            }
            editPane.setVisible(false);
            showWebView(editTextField.getText());
            showSpeakerAndHeart(true);
        }
    }

    @FXML
    protected void onRefreshButtonClick() {
        if (meanings.containsKey(currentWord.getText())) {
            editTextArea.setText(GetContentOfHTML.parse(currentWord.getText()));
            modifiedWord.remove(currentWord.getText());
            showWebView(currentWord.getText());
        }
    }

    /**
     * lúc chạy hàm webView.getEngine.loadContent , nếu từ đấy đã được thay đổi nghĩa thì load nghĩa trong modifiedWord
     * còn không thif vẫn load trong Main.meanings
     *
     * @param word từ hiển thị
     */
    private void showWebView(String word) {
        editPane.setVisible(false);
        if (word.equals("") || word == null) {
            vietMeaning = "";
            webView.getEngine().loadContent("");
        } else if (modifiedWord.containsKey(word)) {
            String backGroundColor = "white";
            String textColor1 = "black";
            if (Main.DARK_MODE) {
                backGroundColor = "#041C32";
                textColor1 = "white";
            }
            String content = "<html><body style=\"color:" + textColor1 + ";background-color:" + backGroundColor + ";font-family: Arial, Helvetica, sans-serif;font-size:" + fontSize +
                    ";\"><textarea disable style=\"width:" + webView.getWidth() + "px;height:" + webView.getHeight() + "px;\">"
                    + modifiedWord.get(word)
                    + "</textarea></body></html>";
            webView.getEngine().loadContent(content);
        } else {
            vietMeaning = ConvertToHTML.vietMeaningToHTML(word, meanings.get(word));
            webView.getEngine().loadContent(vietMeaning);
        }
    }

}
