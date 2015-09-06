package com.felipekunzler.simplememoryhelper;

public class Word {

    private int id;
    private String word;
    private String meaning;

    public Word(){

    }

    public Word(int id, String word, String meaning){
        this.id = id;
        this.word = word;
        this.meaning = meaning;
    }

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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
