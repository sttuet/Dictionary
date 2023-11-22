package com.example.ourdictionary;

import java.util.ArrayList;
import java.util.List;

public class HangMan {
    public static final int MAX_WRONG = 6;
    private String answer;
    private List<Character> currentWord;
    private int currentWrong = 0;

    /**
     * khởi tạo game Hangman.
     *
     * @param allWords các từ trong game
     */

    public HangMan(List<String> allWords) {
        answer = allWords.get((int) (Math.random() * allWords.size()));
        answer = answer.toUpperCase();
        currentWord = new ArrayList<>();
        for (int i = 0; i < answer.length(); i++) {
            currentWord.add('_');
        }
    }

    /**
     * game đã kết thúc hay chưa.
     *
     * @return true or false
     */
    public boolean isOver() {
        return currentWrong >= MAX_WRONG;
    }

    /**
     * thắng hay chưa.
     *
     * @return true hay false
     */
    public boolean isWin() {
        return !currentWord.contains('_');
    }

    /**
     * chọn kí tự, nếu chọn sai tăng giá trị sai lên, chn đúng thì set chữ cái hiện lên.
     *
     * @param character chữ cái đoán
     */
    public void playerChoose(String character) {
        if (!answer.contains(character)) {
            currentWrong++;
        } else {
            for (int i = 0; i < answer.length(); i++) {
                if (answer.charAt(i) == character.charAt(0)) {
                    currentWord.set(i, answer.charAt(i));
                }
            }
        }
    }

    /**
     * trả về đáp án.
     *
     * @return string đáp án
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * set đáp án.
     *
     * @param answer đáp án
     */
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
