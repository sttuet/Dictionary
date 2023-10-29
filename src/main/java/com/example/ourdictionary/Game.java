package com.example.ourdictionary;

import java.util.Collections;
import java.util.List;

public abstract class Game {
    public static <T> void shuffle(List<T> list,int begin,int end){
        for(int i=end-1;i>begin;i--){
            int random=(int)(Math.random()*(i-begin+1))+begin;
            Collections.swap(list,random,i);
        }
    }

}
