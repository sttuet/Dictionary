package com.example.ourdictionary;


import java.util.ArrayList;
import java.util.List;

/**
 * 1 Word thì gồm 1 String word là từ đó, text là phát âm, 1 list meanings bao gồm các {@link Meaning} .
 */
public class Word {

    public String word;
    public String text;
    public String audio;
    public ArrayList<Meaning> meanings = new ArrayList<>();

    public Word() {

    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", text='" + text + '\'' +
                ", meanings=" + meanings.toString() +
                '}';
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(ArrayList<Meaning> meanings) {
        this.meanings = meanings;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
