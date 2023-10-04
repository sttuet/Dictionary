package com.example.service;

import com.example.ourdictionary.Definition;
import com.example.ourdictionary.Meaning;
import com.example.ourdictionary.Word;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParseJSON {
    /**
     * Phân tích Json trả về từ sendRequest
     * @param json json (string)
     * @param objectMapper công cụ để phân tích json
     * @return {@link Word}
     * @throws JsonProcessingException
     */
    public static Word fromJson(String json, ObjectMapper objectMapper) throws JsonProcessingException {
        if(json==null){
            return null;
        }
        JsonNode root=objectMapper.readTree(json).get(0);
        Word result=new Word();
        result.setWord(root.get("word").asText());


        if(root.get("phonetic")!=null){
            result.setText(root.get("phonetic").asText());
        }else{
            for(JsonNode jsonNode:root.get("phonetics")){
                if(jsonNode.get("text")!=null){
                    result.setText(jsonNode.get("text").asText());
                    break;
                }
            }
        }
        root.get("meanings").forEach(t->{
            Meaning meaning=new Meaning();
            meaning.setPartOfSpeech(t.get("partOfSpeech").asText());

            for(JsonNode jn: t.get("definitions")){

                Definition definition=new Definition(jn.get("definition").asText(),jn.get("example")==null?null:jn.get("example").asText());
                meaning.definitions.add(definition);
            }
            result.getMeanings().add(meaning);
        });
        return result;
    }
}