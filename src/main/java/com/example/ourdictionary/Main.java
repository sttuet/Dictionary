package com.example.ourdictionary;

import com.example.service.IOFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static com.example.service.ConvertToHTML.getInfoEng;

public class Main extends Application {
    public static Dictionary dictionary;
    public static ObjectMapper objectMapper;
    public static HashSet<String> favouriteList;
    public static LinkedList<String> recentList;
    public static Map<String, String> meanings;
    public static boolean DARK_MODE = false;

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
        favouriteList = (HashSet<String>) IOFile.readFromFavouriteFile();
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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 824, 537);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("MainView.css")).toExternalForm());
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
        new Thread(()->{
            try {
                loadData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
}