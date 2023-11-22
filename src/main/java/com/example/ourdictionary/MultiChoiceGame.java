package com.example.ourdictionary;

import com.example.service.IOFile;
import com.example.service.ParseJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MultiChoiceGame extends Game {
    public static final int NUM_QUESTION = 10;
    private final List<String> listQuestion2 = new LinkedList<>();
    private LinkedList<String> listQuestion = new LinkedList<>();
    private List<String> currentQuestion = new ArrayList<>();
    private List<String> listResult = new ArrayList<>();
    private int score = 0;

    /**
     * khởi tạo game nhiều lựa chọn.
     *
     * @throws IOException ngoại lệ io
     */
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

    /**
     * lấy nghĩa.
     *
     * @param s từ cần lấy
     * @return xâu nghĩa
     */
    private String getMean(String s) {
        try {
            return ParseJSON.getTranslateText("en", "vi", s);
        } catch (IOException e) {
            System.out.println("cant translate");
        }
        return "";
    }

    /**
     * tạo câu hỏi.
     */
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
        if (!listResult.contains(currentQuestion.get(1) + "\n" + currentQuestion.get(0))) {
            listResult.add(currentQuestion.get(1) + "\n" + currentQuestion.get(0));
        }
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

    /**
     * cập nhật câu hỏi.
     *
     * @param trueAns đáp án đúng thì remove, sai thì đổi
     */
    public void updateListQuestion(boolean trueAns) {
        if (trueAns) {
            listQuestion.removeFirst();
        } else {
            Collections.swap(listQuestion, 0, listQuestion.size() - 1);
        }
        createQuestion();
    }

    /**
     * reset lại game
     */
    public void reset() {
        listQuestion = new LinkedList<>(listQuestion2);
        score = 0;
        createQuestion();
    }

    public List<String> getListResult() {
        return listResult;
    }
}