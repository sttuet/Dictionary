package com.example.ourdictionary;

import com.example.dao.DictionaryDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class Main extends Application {
    public static Dictionary dictionary;
    public static DictionaryDao dictionaryDao;
    public static ObjectMapper objectMapper;
    @Override
    public void start(Stage stage) throws IOException {
        objectMapper=new ObjectMapper();
        dictionaryDao=new DictionaryDao();
        dictionary=new Dictionary();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}