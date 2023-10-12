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
                    stringBuilder.append("<dd> style=\"text-decoration: underline;\">Ex: " + definition.example + "</dd>");
            }
            stringBuilder.append("</dl></li>");
        }
        stringBuilder.append("</ul></html>");
        return stringBuilder.toString();
    }

    public static String deleteWordInHTML(String word, String mean) {
        if (word == null || mean == null) {
            return "";
        }
        int pos = 0;
        for (int i = 0; i < mean.length(); i++) {
            if (mean.charAt(i) == '<' && mean.charAt(i + 1) == 'i' && mean.charAt(i + 2) == '>') {
                pos = i;
                break;
            }
        }
        mean = mean.substring(0, pos + 3) + mean.substring(pos + word.length() + 3, mean.length());
        String[] temp = mean.split("'#cc0000'");
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < temp.length; i++) {
            str.append(temp[i]);
            if (i != temp.length - 1) {
                str.append("'#003366' style =\"font-family: Arial; font-size: 14;\"");
            }
        }
        Scanner sc = new Scanner(str.toString());
        sc.useDelimiter("<i>|</i>");
        String res = "";
        while (sc.hasNext()) {
            res += sc.next();
        }
        String[] rg = {
                "<b> ngoại động từ</b>", "<b> nội động từ</b>", "<b> danh từ</b>", "<b> tính từ</b>", "<b> phó từ</b>",
                "<b> trạng từ</b>", "<b> mạo từ</b>", "<b> giới từ</b>", "<b> danh từ, số nhiều as, a's</b>",
                "<b> tính từ sở hữu</b>", "<b> đại từ sở hữu</b>", "<b> liên từ</b>", "<b> đại từ phản thân</b>",
                "<b> thán từ</b>", "<b> từ cảm thán</b>", "<b> đại từ nghi vấn</b>", "<b> đại từ quan hệ</b>"
        };
        for (int i = 0; i < rg.length; i++) {
            res = res.replaceAll(rg[i], "<font color='#000000' " +
                    "style =\"font-family: Arial; font-size: 14;text-decoration: underline;\">" + rg[i] + "</font>");
        }
        String rg1 = "((<b>){1}([a-z ]+)[^\\-](</b>){1}:{1} (.[^\\-]+)(</li>){1})+";
        String rg2 = "<li><b>";
        String rg3 = "</li";
        res = res.replaceAll(rg2, "<li><font color='#000000' style =\"font-family: Arial; font-size: 14;\">");
        res = res.replaceAll(rg3, "</font></li");
        return res;
    }
}
