package com.example.ourdictionary;

import com.example.service.DictionaryDao;
import com.example.service.IOFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public class Main extends Application {
    public static Dictionary dictionary;
    public static ObjectMapper objectMapper;
    public static HashSet<String> favouriteList;
    public static LinkedList<String> recentList;
    public static Map<String, String> modifiedWord;
    public static Map<String, String> meanings;
    public static boolean DARK_MODE = false;
    public static boolean isGuest = false;
    public static String USERNAME, PASSWORD;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * tải data từ file E_V.txt vào 1 HashMap<Từ,Nghĩa> data để tra từ với nghĩa tiếng việt.
     * thêm các từ vào công cụ search {@link Dictionary}.
     * read data from Favourite.txt and Recent.txt to favlist and recentlist.
     *
     * @throws IOException if file not found
     */
    public static void loadData() throws IOException {
        objectMapper = new ObjectMapper();
        dictionary = new Dictionary();
        modifiedWord = IOFile.readFromModifiedFile(dictionary);
        meanings = IOFile.readFromE_VFile(dictionary);
        recentList = IOFile.readFromRecentFile();
        try {
            System.out.println("is guest " + isGuest);
            if (isGuest) {
                favouriteList = (HashSet<String>) IOFile.readFromFavouriteFile();
            } else {
                DictionaryDao dictionaryDao = new DictionaryDao();
                favouriteList = dictionaryDao.getAllWord(USERNAME);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * kiểm tra kết nối mạng.
     * @return trả về true nếu có mạng, false nếu không có mạng
     */
    public static boolean checkInternetConnection() {
        String url = "http://www.google.com";

        try {
            URL targetUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();

            // Thực hiện GET request
            connection.setRequestMethod("GET");

            // Kiểm tra mã phản hồi
            int statusCode = connection.getResponseCode();

            if (statusCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Kết nối mạng thành công!");
                return true;
            } else {
                System.out.println("Không thể kết nối mạng! Mã phản hồi: " + statusCode);
                return false;
            }
        } catch (IOException e) {
            System.out.println("Không thể kết nối mạng! Lỗi: " + e.getMessage());
            return false;
        }
    }

    /**
     * write data in favouritelist and recentlist to file txt.
     *
     * @throws IOException exception if can not write data against
     */
    @Override
    public void stop() throws IOException {
        System.out.println("app ended!");
        if (favouriteList != null && isGuest) {
            IOFile.writeToFavouriteFile(favouriteList);
        }
        if (recentList != null) {
            IOFile.writeToRecentFile(recentList);
        }
        if (!isGuest) {
            if (checkInternetConnection()) {
                DictionaryDao dictionaryDao = new DictionaryDao();
                if (Main.favouriteList != null) {
                    dictionaryDao.updateListWord();
                }
            }
        }
        if (Main.modifiedWord != null) {
            IOFile.writeToModifiedFile();
        }
    }

    /**
     * chạy chương trình
     *
     * @param stage stage
     * @throws IOException i don't know
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Log-in.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("Login.css")).toExternalForm());
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }
}