package com.example.service;

import com.example.ourdictionary.Definition;
import com.example.ourdictionary.Meaning;
import com.example.ourdictionary.Word;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.example.service.SendRequest.sendRequest;

public class ConvertToHTML {
    public static String getInfo(String word, ObjectMapper objectMapper) throws IOException {
        String json=sendRequest(word);
        Word word1=ParseJSON.fromJson(json,objectMapper);
        return toHTML(word1);
    }
    public static String toHTML(Word word){
        if(word==null){
            return "<html>type correct word or check internet connection</html>";
        }
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("<html><ul>");
        stringBuilder.append("<li style=\"font-size:larger\">"+word.getWord()+"</li>");
        stringBuilder.append("<li style=\"font-style: italic;\">"+word.getText()+"</li>");
        for(Meaning meaning:word.getMeanings()){

            stringBuilder.append("<li><dl><dt style=\"font-weight: bold;\">Type :"+meaning.partOfSpeech+"</dt>");
            for(Definition definition:meaning.definitions){
                stringBuilder.append("<dt style=\"color: red;\">-definition : "+definition.definition+"</dt>");
                if(!definition.example.equals("")) stringBuilder.append("<dd><i style=\"text-decoration: underline;\">Ex: </i>"+definition.example+"</dd>");
            }
            stringBuilder.append("</dl></li>");
        }
        stringBuilder.append("</ul></html>");
        return stringBuilder.toString();
    }
}
