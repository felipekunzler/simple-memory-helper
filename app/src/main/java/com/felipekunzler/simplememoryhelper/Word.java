package com.felipekunzler.simplememoryhelper;

public class Word {

    private String word;
    private String meaning;

    public Word(String word, String meaning){
        this.word = word;
        this.meaning = meaning;
    }

    public String getWord() {
        return this.word;
    }

    public String getMeaning() {
        return this.meaning;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
