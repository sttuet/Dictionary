package com.example.service;

import com.example.controller.MainController;
import com.example.ourdictionary.Main;
import com.example.ourdictionary.Word;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.example.service.SendRequest.sendRequest;

public class ConvertToHTML {
    /**
     * tìm thông tin của từ (vẫn là tiếng anh)
     *
     * @param word         từ cần tìm
     * @param objectMapper công cụ để tìm
     * @return String (dạng html)
     * @throws IOException
     * @see ParseJSON#fromJson(String, ObjectMapper)
     * @see SendRequest#sendRequest(String)
     */
    public static String getInfoEng(String word, ObjectMapper objectMapper) throws IOException {
        String json = sendRequest(SendRequest.URL_PATH + word);
        Word word1 = ParseJSON.fromJson(json, objectMapper);
        return engMeaningToHTML(word1);
    }

    /**
     * thêm các tag html để hiển thị từ
     *
     * @param word từ cần hiể thị
     * @return String
     */
    public static String engMeaningToHTML(Word word) {
        String backGroundColor = "white";
        String textColor1 = "black";
        String textColor2 = "#003366";
        if (Main.DARK_MODE) {
            backGroundColor = "#041C32";
            textColor1 = "white";
            textColor2 = "#00BFFF";
        }
        if (word == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><body style=\"color:").append(textColor1).append(";background-color:").append(backGroundColor).append(";font-family: Arial, Helvetica, sans-serif;font-size:").append(MainController.fontSize).append("px;").append(";color=").append(textColor1).append(";\"><ul>");
        stringBuilder.append("<li >").append(word.getWord()).append("</li>");
        stringBuilder.append("<li >").append(word.getText()).append("</li>");
        for (Word.Meaning meaning : word.getMeanings()) {
            stringBuilder.append("<li>Type :").append(meaning.partOfSpeech).append("</li><ul>");
            for (Word.Meaning.Definition definition : meaning.definitions) {
                stringBuilder.append("<li><font color=\">").append(textColor2).append("\">").append(definition.definition).append("</font></li>");
                if (!definition.example.equals(""))
                    stringBuilder.append("<ul><li>").append(definition.example).append("</li></ul>");
            }
            stringBuilder.append("</ul>");
        }
        stringBuilder.append("</ul></body></html>");

        return stringBuilder.toString();
    }

    public static String vietMeaningToHTML(String word, String mean) {
        String backGroundColor = "white";
        String textColor1 = "black";
        String textColor2 = "#003366";
        if (Main.DARK_MODE) {
            backGroundColor = "#041C32";
            textColor1 = "white";
            textColor2 = "#00BFFF";
        }
        if (word == null || mean == null) {
            return "";
        }
        StringBuilder ans = new StringBuilder();
        mean = mean.substring(6, mean.length() - 7);
        ans.append("<html><body style=\"color:").append(textColor1).append(";background-color:").append(backGroundColor).append(";font-family: Arial, Helvetica, sans-serif;font-size:").append(MainController.fontSize).append("px;").append(";color=").append(textColor1).append(";\">");
        mean = mean.replaceAll("#cc0000", textColor2);
        mean = mean.replaceAll("<b>|</b>", "");
        mean = mean.replaceAll("<i>|</i>", "");
        ans.append(mean);
        ans.append("</body></html>");
        return ans.toString();
    }
}
