package com.example.service;

import com.example.ourdictionary.Word;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Scanner;

public class ParseJSON {
    /**
     * Phân tích Json trả về từ sendRequest
     *
     * @param json         json (string)
     * @param objectMapper công cụ để phân tích json
     * @return {@link Word}
     * @throws JsonProcessingException
     */
    public static Word fromJson(String json, ObjectMapper objectMapper) throws IOException {
        if (json == null) {
            return null;
        } else if (json.equals(SendRequest.NO_INTERNET)) {
            return null;
        }
        JsonNode root = objectMapper.readTree(json).get(0);
        Word result = new Word();
        result.setWord(root.get("word").asText());


        if (root.get("phonetic") != null) {
            result.setText(root.get("phonetic").asText());
        }
        if (root.get("phonetics") != null) {
            for (JsonNode jsonNode : root.get("phonetics")) {
                if (jsonNode.get("text") != null && jsonNode.get("audio") != null
                        && !jsonNode.get("audio").asText().equals("")) {
                    result.setText(jsonNode.get("text").asText());
                    result.setAudio(jsonNode.get("audio").asText());
                    break;
                }
            }
        }
        root.get("meanings").forEach(t -> {
            Word.Meaning meaning = new Word.Meaning();
            meaning.setPartOfSpeech(t.get("partOfSpeech").asText());

            for (JsonNode jn : t.get("definitions")) {

                Word.Meaning.Definition definition = new Word.Meaning.Definition(jn.get("definition").asText()
                        , jn.get("example") == null ? null : jn.get("example").asText());
                meaning.definitions.add(definition);
            }
            result.getMeanings().add(meaning);
        });
        return result;
    }

    /**
     * lấy nghĩa được dịch.
     *
     * @param from từ ngôn ngữ này
     * @param to   sang ngôn ngữ này
     * @param text text
     * @return xâu mới.
     * @throws IOException ngoại ệ io
     */
    public static String getTranslateText(String from, String to, String text) throws IOException {
        String translateResult = SendRequest.getJsonTranslate(from, to, text);
        Scanner scanner = new Scanner(translateResult);
        scanner.useDelimiter("<div class=\"result-container\">");
        scanner.next();
        StringBuilder stringBuilder = new StringBuilder();
        String s = scanner.next();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '<') {
                stringBuilder.append(s.charAt(i));
            } else break;
        }
        return stringBuilder.toString();
    }

    /**
     * dịch sang tiếng việt.
     *
     * @param text text cần dịch
     * @return xâu mới
     * @throws IOException ngoại lệ io
     */
    public static String transToViet(String text) throws IOException {
        return getTranslateText("en", "vi", text);
    }

    /**
     * dịch sang Tiếng Anh.
     *
     * @param text text cần dịch
     * @return xâu mới
     * @throws IOException ngoại lệ io
     */
    public static String transToEng(String text) throws IOException {
        return getTranslateText("vi", "en", text);
    }
}
