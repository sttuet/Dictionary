package com.example.ourdictionary;


import java.util.ArrayList;
import java.util.List;

public class Word {
    public Word(String meaning, String word) {
        this.meaning = meaning;
        this.word = word;
    }

    public String meaning;
    public String word;
    public String text;
    public ArrayList<Meaning> meanings=new ArrayList<>();

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

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
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
}
