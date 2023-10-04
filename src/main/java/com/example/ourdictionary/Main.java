package com.example.ourdictionary;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.example.service.ConvertToHTML.getInfoEng;

public class Main extends Application {
    public static Dictionary dictionary;
    public static Map<String, String> meanings;
    public static ObjectMapper objectMapper;
    public static String fileRecent = "src\\main\\resources\\data\\Recent.txt";
    public static FileWriter fW;
    public static BufferedWriter bW;

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

    public static void addToRecent(String word) throws IOException {
        bW.write(word + '\n');
        bW.flush();
    }

    /**
     * kiểm tra xem từ này có phải từ đơn hay k( chỉ chứa kí tự từ a-z) để add vào dictionary.
     *
     * @param s từ cần kiểm tra
     * @return
     */
    public static boolean isSingleEnglishWord(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < 'a' || s.charAt(i) > 'z') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * tải data từ file E_V.txt vào 1 HashMap<Từ,Nghĩa> data để tra từ với nghĩa tiếng việt.
     * thêm các từ vào công cụ search(tạm thời mới chỉ thêm các từ đơn) {@link Dictionary}.
     *
     * @throws IOException
     */
    public void loadData() throws IOException {
        objectMapper = new ObjectMapper();
        dictionary = new Dictionary();
        meanings = new HashMap<>();
        fW = new FileWriter(fileRecent, true);
        bW = new BufferedWriter(fW);
        FileReader fis = new FileReader("src\\main\\resources\\data\\E_V.txt");
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("<html>");
            String word = parts[0];
            String definition = "<html>" + parts[1];
            meanings.put(word, definition);

            if (isSingleEnglishWord(word)) {
                dictionary.addWord(word);
            }
        }
    }

    @Override
    public void stop() throws IOException {
        bW.close();
    }

    /**
     * chạy chương trình
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        loadData();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }
}