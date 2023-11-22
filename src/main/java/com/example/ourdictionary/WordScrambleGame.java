package com.example.ourdictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.controller.WordScrambleController.QUESTION_NUMBER;

public class WordScrambleGame extends Game {
    private final List<String> wordList;
    private int currentQuestion;

    /**
     * khởi tạo game word Scramble.
     *
     * @param wordList list từ
     */
    public WordScrambleGame(List<String> wordList) {
        this.currentQuestion = 0;
        Collections.shuffle(wordList);
        List<String> randomizeWords = new ArrayList<>();
        for (int i = 0; i < QUESTION_NUMBER; ++i) {
            randomizeWords.add(wordList.get(i));
        }
        this.wordList = randomizeWords;
    }

    /**
     * câu hỏi tếp theo.
     *
     * @return
     */
    public boolean nextQuestion(int currentQ) {
        currentQuestion = currentQ;
        return currentQuestion < QUESTION_NUMBER;
    }

    /**
     * kiểm tra đáp án.
     *
     * @param answer đáp án
     * @return
     */
    public boolean checkAnswer(String answer) {
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) != wordList.get(currentQuestion).charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * tráo nút.
     *
     * @return trả về xâu chứa các câu hỏi.
     */
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


    public List<String> getListResult() {
        return wordList;
    }
}
