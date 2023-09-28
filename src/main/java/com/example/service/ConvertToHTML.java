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
            return "<html>type correct word or check internet conection</html>";
        }
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("<html>");
        stringBuilder.append("<p>"+word.getWord()+"</p>");
        stringBuilder.append("<p>"+word.getText()+"</p>");
        for(Meaning meaning:word.getMeanings()){
            stringBuilder.append("<p>Type :"+meaning.partOfSpeech+"</p>");
            for(Definition definition:meaning.definitions){
                stringBuilder.append("<p>-definition : "+definition.definition+"</p>");
                stringBuilder.append("<p>example :"+definition.example+"</p>");
            }
        }
        stringBuilder.append("</html>");
        return stringBuilder.toString();
    }
}
