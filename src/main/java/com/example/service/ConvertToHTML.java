package com.example.service;

import com.example.ourdictionary.Definition;
import com.example.ourdictionary.Meaning;
import com.example.ourdictionary.Word;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.example.service.SendRequest.sendRequest;

public class ConvertToHTML {
    /**
     * tìm thông tin của từ (vẫn là tiếng anh)
     * @param word từ cần tìm
     * @param objectMapper công cụ để tìm
     * @return String (dạng html)
     * @throws IOException
     * @see ParseJSON#fromJson(String, ObjectMapper)
     * @see SendRequest#sendRequest(String)
     */
    public static String getInfoEng(String word, ObjectMapper objectMapper) throws IOException {
        String json = sendRequest(SendRequest.URL_PATH+word);
        Word word1 = ParseJSON.fromJson(json, objectMapper);
        return toHTML(word1);
    }

    /**
     * thêm các tag html để hiển thị từ
     * @param word từ cần hiể thị
     * @return String
     */
    public static String toHTML(Word word) {
        if (word == null) {
            return "can't find this word";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><ul>");
        stringBuilder.append("<li style=\"font-size:larger\">" + word.getWord() + "</li>");
        stringBuilder.append("<li style=\"font-style: italic;\">" + word.getText() + "</li>");
        for (Meaning meaning : word.getMeanings()) {

            stringBuilder.append("<li><dl><dt style=\"font-weight: bold;\">Type :" + meaning.partOfSpeech + "</dt>");
            for (Definition definition : meaning.definitions) {
                stringBuilder.append("<dt style=\"color: red;\">-definition : " + definition.definition + "</dt>");
                if (!definition.example.equals(""))
                    stringBuilder.append("<dd><i style=\"text-decoration: underline;\">Ex: </i>" + definition.example + "</dd>");
            }
            stringBuilder.append("</dl></li>");
        }
        stringBuilder.append("</ul></html>");
        return stringBuilder.toString();
    }
    public static String deleteWordInHTML(String word,String mean)
    {
        if(word==null||mean==null){
            return "";
        }
        int pos=0;
        for(int i=0;i<mean.length();i++){
            if(mean.charAt(i)=='<'&&mean.charAt(i+1)=='i'&&mean.charAt(i+2)=='>'){
                pos=i;
                break;
            }
        }
        mean=mean.substring(0,pos+3)+mean.substring(pos+word.length()+3,mean.length());
        return mean;
    }
}
