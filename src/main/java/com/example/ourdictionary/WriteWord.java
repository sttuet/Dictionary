package com.example.ourdictionary;

import com.example.service.ParseJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteWord extends Game{
    private List<String> listWord;
    private int currentQuestion;
    private String meaning;

    public WriteWord() {
        this.listWord = new ArrayList<>(Main.favouriteList);
        this.currentQuestion = 0;
        setMeaning();
    }
    public boolean nextQuestion(){
        currentQuestion++;
        if(currentQuestion<listWord.size()){
            setMeaning();return true;
        }
        return false;
    }
    public void setMeaning(){
        try {
            meaning=ParseJSON.transToViet(listWord.get(currentQuestion));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean checkAnswer(String answer){
        for(int i=0;i<answer.length();i++){
            if(answer.charAt(i)!=listWord.get(currentQuestion).charAt(i)){
                return false;
            }
        }
        return true;
    }
    public String getMeaning(){
        return meaning;
    }
    public List<String> getShuffleButtons(){
        List<String> res=new ArrayList<>();
        for(int i=0;i<listWord.get(currentQuestion).length();i++){
            res.add(String.valueOf(listWord.get(currentQuestion).charAt(i)));
        }
        Game.shuffle(res,0,res.size());
        return res;
    }
    public String getWord(){
        return listWord.get(currentQuestion);
    }
}