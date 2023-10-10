package com.example.ourdictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * 1 Meaning object bao gồm 1 partOfSpeech là loại từ ( noun, verb,...) và 1 list {@link Definition} .
 */
public class Meaning {
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
}
