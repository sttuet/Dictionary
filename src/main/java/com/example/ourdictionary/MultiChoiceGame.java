package com.example.ourdictionary;

import com.example.service.IOFile;

import java.io.IOException;
import java.util.*;

public class MultiChoiceGame extends Game{
    private LinkedList<String> listQuestion=new LinkedList<>();
    private List<String> listQuestion2=new LinkedList<>();
    private List<String> currentQuestion=new ArrayList<>();
    private int score=0;
    public static final int NUM_QUESTION=10;

    public MultiChoiceGame() throws IOException {
        List<String> allWord= IOFile.readFromCommonWord();
        while (listQuestion.size()<NUM_QUESTION){
            int random=(int)(Math.random()*allWord.size());
            if(!Main.favouriteList.contains(allWord.get(random))){
                listQuestion.add(allWord.get(random));
                listQuestion2.add(allWord.get(random));

            }
        }
    }

    private String getMean(String s){
        String s1="<font color='#cc0000'><b>";
        String s2="</b></font>";
        String def=Main.meanings.get(s);
        int i1=def.indexOf(s1)+s1.length();
        int i2=def.indexOf(s2);
        return def.substring(i1,i2);
    }
    public void createQuestion(){
        String s=listQuestion.get(0);
        currentQuestion=new ArrayList<>();
        currentQuestion.add(getMean(s));
        currentQuestion.add(s);
        currentQuestion.add(s);
        while (currentQuestion.size()<6){
            int rand=(int)(Math.random()*NUM_QUESTION);
            if(!currentQuestion.contains(listQuestion2.get(rand))){
                currentQuestion.add(listQuestion2.get(rand));
            }
        }
        shuffle(currentQuestion,2,6);
    }
    public List<String> getCurrentQuestion(){
        return currentQuestion;
    }

    public boolean checkAnswer(String ans) {

        if(ans.equals(currentQuestion.get(1))){
            return true;
        }else{
            return false;
        }
    }
    public int getScore(){
        return score;
    }
    public void increaseScore(){
        score++;
    }
    public void updateListQuestion(boolean trueAns){
        if(trueAns){
            listQuestion.removeFirst();
        }else {
            Collections.swap(listQuestion,0,listQuestion.size()-1);
        }
        createQuestion();
    }
}