package com.felipekunzler.simplememoryhelper;

public class Word {

    private int id;
    private String word;
    private String meaning;
    private long lastTimeNotificationSent;

    public Word(){

    }

    public Word(int id, String word, String meaning, long lastTimeNotificationSent){
        this.id = id;
        this.word = word;
        this.meaning = meaning;
        this.lastTimeNotificationSent = lastTimeNotificationSent;
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

    public long getLastTimeNotificationSent() {
        return this.lastTimeNotificationSent;
    }

    public void setLastTimeNotificationSent(long lastTimeNotificationSent) {
        this.lastTimeNotificationSent = lastTimeNotificationSent;
    }
}
