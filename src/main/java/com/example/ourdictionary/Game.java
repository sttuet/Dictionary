package com.example.ourdictionary;

import java.util.Collections;
import java.util.List;

public abstract class Game {
    /**
     * tráo lộn.
     *
     * @param list  list
     * @param begin bắt đầu
     * @param end   kết thúc
     * @param <T>   generic
     */
    public static <T> void shuffle(List<T> list, int begin, int end) {
        for (int i = end - 1; i > begin; i--) {
            int random = (int) (Math.random() * (i - begin + 1)) + begin;
            Collections.swap(list, random, i);
        }
    }

}
