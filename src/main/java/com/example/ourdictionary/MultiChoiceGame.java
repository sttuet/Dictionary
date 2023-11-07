package com.example.ourdictionary;

import com.example.service.IOFile;
import com.example.service.ParseJSON;

import java.io.IOException;
import java.util.*;

public class MultiChoiceGame extends Game {
    private LinkedList<String> listQuestion = new LinkedList<>();
    private final List<String> listQuestion2 = new LinkedList<>();
    private List<String> currentQuestion = new ArrayList<>();
    private int score = 0;
    public static final int NUM_QUESTION = 10;

    public MultiChoiceGame() throws IOException {
        List<String> allWord = IOFile.readFromCommonWord();
        while (listQuestion.size() < NUM_QUESTION) {
            int random = (int) (Math.random() * allWord.size());

            if (!Main.favouriteList.contains(allWord.get(random))) {
                listQuestion.add(allWord.get(random));
                listQuestion2.add(allWord.get(random));
            }
        }
        createQuestion();
    }

    private String getMean(String s) {
        try {
            return ParseJSON.getTranslateText("en", "vi", s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createQuestion() {
        String s = listQuestion.get(0);
        currentQuestion = new ArrayList<>();
        currentQuestion.add(getMean(s));
        currentQuestion.add(s);
        currentQuestion.add(s);
        while (currentQuestion.size() < 6) {
            int rand = (int) (Math.random() * NUM_QUESTION);
            if (!currentQuestion.contains(listQuestion2.get(rand))) {
                currentQuestion.add(listQuestion2.get(rand));
            }
        }
        shuffle(currentQuestion, 2, 6);
    }

    public List<String> getCurrentQuestion() {
        return currentQuestion;
    }

    public boolean checkAnswer(String ans) {
        return ans.equals(currentQuestion.get(1));
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        score++;
    }

    public void updateListQuestion(boolean trueAns) {
        if (trueAns) {
            listQuestion.removeFirst();
        } else {
            Collections.swap(listQuestion, 0, listQuestion.size() - 1);
        }
        createQuestion();
    }

    public void reset() {
        listQuestion = new LinkedList<>(listQuestion2);
        score = 0;
        createQuestion();
    }
}