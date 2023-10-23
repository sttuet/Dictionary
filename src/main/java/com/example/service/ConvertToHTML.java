package com.example.service;

import com.example.ourdictionary.Definition;
import com.example.ourdictionary.Meaning;
import com.example.ourdictionary.Word;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Scanner;

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
        return toHTML(word1);
    }

    /**
     * thêm các tag html để hiển thị từ
     *
     * @param word từ cần hiể thị
     * @return String
     */
    public static String toHTML(Word word) {
        if (word == null) {
            return "can't find this word";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><ul>");
        stringBuilder.append("<li >" + word.getWord() + "</li>");
        stringBuilder.append("<li >" + word.getText() + "</li>");
        for (Meaning meaning : word.getMeanings()) {

            stringBuilder.append("<li><dl><dt >Type :" + meaning.partOfSpeech + "</dt>");
            for (Definition definition : meaning.definitions) {
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
        if (word == null || mean == null) {
            return "";
        }
        int pos = 0;
        Scanner scanner = new Scanner(mean);
        scanner.useDelimiter("[\\n]");
        StringBuilder result = new StringBuilder();
        result.append("<html><style>\n" +
                "        body {\n" +
                "          background-image: linear-gradient(to right, #ffefba, #ffffff);\n" +
                "        }\n" +
                "    </style><div style=\"font-family: Arial, Helvetica, sans-serif;font-size:14;\">");
        String tmp;
        boolean find_idiom = false;
        int numTag = 0;
        char old = '@';
        char cur;
        while (scanner.hasNext()) {
            tmp = scanner.next();
            cur = tmp.charAt(0);
            tmp = tmp.substring(1);
            tmp = tmp.replaceAll("[+]", ":");
            if (cur == '@' || cur == '*') {
                for (int i = 0; i < numTag; i++) {
                    result.append("</li></ul>");
                }
                numTag = 0;
                result.append("<h3 style=\"text-decoration:underline;font-family: Arial; font-size:14\">" + tmp + "</h3>"); // loại từ
            } else {
                if (cur == '!' && !find_idiom) {
                    for (int i = 0; i < numTag; i++) {
                        result.append("</li></ul>");
                    }
                    numTag = 0;
                    result.append("<h3 style=\"text-decoration:underline;font-family: Arial; font-size:14\">Idioms</h3>"); // loại từ
                    find_idiom = true;
                }
                switch (numTag) {
                    case 0:
                        result.append("<ul style=\"color:#003366;font-family: Arial; font-size:14\"><li>" + tmp); // định nghĩa
                        numTag++;
                        old = cur;
                        break;
                    case 1:
                        if (cur == old) {
                            result.append("</li><li>" + tmp);
                        } else {
                            result.append("<ul style=\"color:black;font-family: Arial; font-size:14\"><li>" + tmp); // ví dụ
                            numTag++;
                        }
                        old = cur;
                        break;
                    case 2:
                        if (cur == old) {
                            result.append("</li><li>" + tmp);
                        } else {
                            result.append("</li></ul></li><li>" + tmp);
                            numTag--;
                        }
                        old = cur;
                        break;
                }
            }

        }
        for (int i = 0; i < numTag; i++) {
            result.append("</li></ul>");
        }
        result.append("</div></html>");
        return result.toString();
    }
}
