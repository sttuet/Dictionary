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
    public static class Meaning {
        public String partOfSpeech;
        public ArrayList<Definition> definitions = new ArrayList<>();

        @Override
        public String toString() {
            return "partOfSpeech :" + partOfSpeech + "\n" + definitions.toString();
        }

        public String getPartOfSpeech() {
            return partOfSpeech;
        }

        public void setPartOfSpeech(String partOfSpeech) {
            this.partOfSpeech = partOfSpeech;
        }

        public List<Definition> getDefinitions() {
            return definitions;
        }

        public void setDefinitions(ArrayList<Definition> definitions) {
            this.definitions = definitions;
        }
        public static class Definition {
            public String definition;
            public String example;

            public Definition(String definition, String example) {
                this.definition = definition;
                if (example == null) {
                    this.example = "";
                } else {
                    this.example = example;
                }

            }

            @Override
            public String toString() {
                return "definition :" + definition + "\n" + "example :" + example + "\n";
            }

            public String getDefinition() {
                return definition;
            }

            public void setDefinition(String definition) {
                this.definition = definition;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }

}
