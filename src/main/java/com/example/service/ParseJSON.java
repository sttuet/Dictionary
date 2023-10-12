package com.example.service;

import com.example.ourdictionary.Definition;
import com.example.ourdictionary.Meaning;
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
    public static Word fromJson(String json, ObjectMapper objectMapper) throws JsonProcessingException {
        if (json == null) {
            return null;
        }else if(json.equals(SendRequest.NO_INTERNET)){
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
                System.out.println(jsonNode);
                if (jsonNode.get("text") != null && jsonNode.get("audio") != null && !jsonNode.get("audio").asText().equals("")) {
                    result.setText(jsonNode.get("text").asText());
                    result.setAudio(jsonNode.get("audio").asText());
                    break;
                }
            }
        }
        root.get("meanings").forEach(t -> {
            Meaning meaning = new Meaning();
            meaning.setPartOfSpeech(t.get("partOfSpeech").asText());

            for (JsonNode jn : t.get("definitions")) {

                Definition definition = new Definition(jn.get("definition").asText(), jn.get("example") == null ? null : jn.get("example").asText());
                meaning.definitions.add(definition);
            }
            result.getMeanings().add(meaning);
        });
        return result;
    }

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

    public static String transToViet(String text) throws IOException {
        return getTranslateText("en", "vi", text);
    }

    public static String transToEng(String text) throws IOException {
        return getTranslateText("vi", "en", text);
    }
}
