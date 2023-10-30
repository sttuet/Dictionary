package com.example.ourdictionary;

import com.example.service.IOFile;

import java.io.IOException;
import java.util.*;

public class MultiChoiceGame extends Game{
    private LinkedList<String> listQuestion=new LinkedList<>();
    private List<String> listQuestion2=new LinkedList<>();
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
    public List<String> getQuestion(){
        String s=listQuestion.get(0);
        List<String> question=new ArrayList<>();
        question.add(getMean(s));
        question.add(s);
        while (question.size()<5){
            int rand=(int)(Math.random()*NUM_QUESTION);
            if(!question.contains(listQuestion2.get(rand))){
                question.add(listQuestion2.get(rand));
            }
        }
        shuffle(question,1,5);
        return question;
    }

    @Override
    public boolean checkAnswer(String ans,String quest) {
        if(getMean(ans).equals(quest)){
            listQuestion.removeFirst();
            score++;
            System.out.println(listQuestion.get(0));
            return true;
        }else{
            Collections.swap(listQuestion,0,listQuestion.size()-1);
            System.out.println(listQuestion.get(0));
            return false;
        }
    }
    public int getScore(){
        return score;
    }
}