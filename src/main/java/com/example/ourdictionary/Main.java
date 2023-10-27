package com.example.ourdictionary;

import com.example.service.IOFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.example.service.ConvertToHTML.getInfoEng;

public class Main extends Application {
    public static Dictionary dictionary;

    public static ObjectMapper objectMapper;
    public static Set<String> favouriteList;
    public static LinkedList<String> recentList;
    public static Map<String, String> meanings;
    public static boolean DARK_MODE = false;
    private static Stage primaryStage;

    /**
     * lấy nghĩa tiếng việt của từ.
     *
     * @param word tù cần tìm
     * @return String (html)
     */
    public static String getInfoInVietnamese(String word) {
        return meanings.get(word);
    }

    /**
     * lấy nghĩa tiếng anh của từ.
     *
     * @param word từ cần lấy
     * @return String (html)
     * @throws IOException
     */
    public static String getInfoInEnglish(String word) throws IOException {
        return getInfoEng(word, objectMapper);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void changeScreen(String fxml, String cssFile) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(cssFile)).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.getScene().setRoot(pane);
    }

    /**
     * tải data từ file E_V.txt vào 1 HashMap<Từ,Nghĩa> data để tra từ với nghĩa tiếng việt.
     * thêm các từ vào công cụ search {@link Dictionary}.
     * read data from Favourite.txt and Recent.txt to favlist and recentlist.
     *
     * @throws IOException
     */
    public void loadData() throws IOException {
        objectMapper = new ObjectMapper();
        dictionary = new Dictionary();
        meanings = IOFile.readFromE_VFile(dictionary);
        recentList = IOFile.readFromRecentFile();
        favouriteList = IOFile.readFromFavouriteFile();
    }

    /**
     * write data in favouritelist and recentlist to file txt.
     *
     * @throws IOException
     */
    @Override
    public void stop() throws IOException {
        System.out.println("app ended!");
        IOFile.writeToFavouriteFile(favouriteList);
        IOFile.writeToRecentFile(recentList);
    }

    /**
     * chạy chương trình
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        loadData();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add(getClass().getResource("MainView.css").toExternalForm());
        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}