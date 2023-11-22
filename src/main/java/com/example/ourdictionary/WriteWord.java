package com.example.ourdictionary;

import com.example.service.ParseJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteWord extends Game {
    private List<String> listWord;
    private int currentQuestion;
    private String meaning;

    /**
     * khởi tạo game write word.
     */
    public WriteWord() {
        this.listWord = new ArrayList<>(Main.favouriteList);
        shuffle(this.listWord, 0, this.listWord.size());
        this.currentQuestion = 0;
        setMeaning();
    }

    /**
     * câu hỏi tiếp theo.
     *
     * @return true or false.
     */
    public boolean nextQuestion() {
        currentQuestion++;
        if (currentQuestion < listWord.size()) {
            setMeaning();
            return true;
        }
        return false;
    }

    /**
     * gán nghĩa.
     */
    public void setMeaning() {
        try {
            meaning = ParseJSON.transToViet(listWord.get(currentQuestion));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * kiểm tra đáp án.
     *
     * @param answer đáp án cần kểm tra
     * @return true or false
     */
    public boolean checkAnswer(String answer) {
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) != listWord.get(currentQuestion).charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public String getMeaning() {
        return meaning;
    }

    /**
     * tráo button.
     *
     * @return list word mới
     */
    public List<String> getShuffleButtons() {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < listWord.get(currentQuestion).length(); i++) {
            res.add(String.valueOf(listWord.get(currentQuestion).charAt(i)));
        }
        Game.shuffle(res, 0, res.size());
        return res;
    }

    public String getWord() {
        return listWord.get(currentQuestion);
    }
}
