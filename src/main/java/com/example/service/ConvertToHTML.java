package com.example.service;

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
        if (word == null) {
            return "can't find this word";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><ul>");
        stringBuilder.append("<li >" + word.getWord() + "</li>");
        stringBuilder.append("<li >" + word.getText() + "</li>");
        for (Word.Meaning meaning : word.getMeanings()) {

            stringBuilder.append("<li><dl><dt >Type :" + meaning.partOfSpeech + "</dt>");
            for (Word.Meaning.Definition definition : meaning.definitions) {
                stringBuilder.append("<dt >-definition : " + definition.definition + "</dt>");
                if (!definition.example.equals(""))
                    stringBuilder.append("<dd style=\"text-decoration: underline;\">Ex: " + definition.example + "</dd>");
            }
            stringBuilder.append("</dl></li>");
        }
        stringBuilder.append("</ul></html>");
        return stringBuilder.toString();
    }

    public static String vietMeaningToHTML(String word, String mean) {
        String backGroundColor = "white";
        String textColor1 = "black";
        String textColor2 = "#003366";
        int fontSize = 14;
        if (Main.DARK_MODE) {
            backGroundColor = "#303030";
            textColor1 = "white";
            textColor2 = "#00BFFF";
        }
        if (word == null || mean == null) {
            return "";
        }
        StringBuilder ans = new StringBuilder();
        mean = mean.substring(6, mean.length() - 7);
        ans.append("<html><body style=\"color:" + textColor1 + ";background-color:" + backGroundColor
                + ";font-family: Arial, Helvetica, sans-serif;font-size:"
                + fontSize + "px;" +  ";color=" + textColor1 + ";\">");
        mean = mean.replaceAll("#cc0000", textColor2);
        mean = mean.replaceAll("<b>|</b>", "");
        mean = mean.replaceAll("<i>|</i>", "");
        ans.append(mean);
        ans.append("</body></html>");
        return ans.toString();
    }
}
