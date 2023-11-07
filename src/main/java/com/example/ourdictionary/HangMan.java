package com.example.ourdictionary;

import java.util.ArrayList;
import java.util.List;

public class HangMan {
    private String answer;
    private List<Character> currentWord;
    public static final int MAX_WRONG=6;
    private int currentWrong=0;
    public HangMan(List<String> allWords){
        answer=allWords.get((int)(Math.random()*allWords.size()));
        answer=answer.toUpperCase();
        currentWord=new ArrayList<>();
        for(int i=0;i<answer.length();i++){
            currentWord.add('_');
        }
    }
    public boolean isOver(){
        return currentWrong>=MAX_WRONG;
    }
    public boolean isWin(){
        return !currentWord.contains('_');
    }
    public void playerChoose(String character){
        if(!answer.contains(character)){
            currentWrong++;
        }else {
            for(int i=0;i<answer.length();i++){
                if(answer.charAt(i)==character.charAt(0)){
                    currentWord.set(i,answer.charAt(i));
                }
            }
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Character> getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(List<Character> currentWord) {
        this.currentWord = currentWord;
    }

    public int getCurrentWrong() {
        return currentWrong;
    }

    public void setCurrentWrong(int currentWrong) {
        this.currentWrong = currentWrong;
    }
}
