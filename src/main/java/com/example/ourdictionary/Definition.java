package com.example.ourdictionary;

public class Definition {
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
