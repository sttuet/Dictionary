package com.example.ourdictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.controller.WordScrambleController.QUESTION_NUMBER;

public class WordScrambleGame extends Game {
    private int currentQuestion;
    private List<String> wordList;

    public WordScrambleGame(List<String> wordList) {
        this.currentQuestion = 0;
        this.wordList = wordList;
    }

    public boolean nextQuestion() {
        currentQuestion++;
        if (currentQuestion < QUESTION_NUMBER) {
            return true;
        }
        return false;
    }

    public boolean checkAnswer(String answer) {
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) != wordList.get(currentQuestion).charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public List<String> getShuffleButtons() {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < wordList.get(currentQuestion).length(); i++) {
            res.add(String.valueOf(wordList.get(currentQuestion).charAt(i)));
        }
        Game.shuffle(res, 0, res.size());
        return res;
    }

    public String getWord() {
        return wordList.get(currentQuestion);
    }

}
