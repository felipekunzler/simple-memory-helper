package com.felipekunzler.simplememoryhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "wordsManager";

    private static final String TABLE_WORDS = "words";

    private static final String KEY_ID = "id";
    private static final String KEY_WORD = "word";
    private static final String KEY_MEANING = "meaning";
    private static final String KEY_LAST_NOTIFICATION_TIME = "lastNotification";

    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORDS_TABLE = "CREATE TABLE " + TABLE_WORDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_WORD + " TEXT,"
                + KEY_MEANING + " TEXT,"
                + KEY_LAST_NOTIFICATION_TIME + " INTEGER" + ")";
        db.execSQL(CREATE_WORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);

        onCreate(db);
    }

    public void addWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word.getWord());
        values.put(KEY_MEANING, word.getMeaning());
        values.put(KEY_LAST_NOTIFICATION_TIME, word.getLastTimeNotificationSent());

        db.insert(TABLE_WORDS, null, values);
        db.close();
    }

    public Word getWord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WORDS, new String[] { KEY_ID, KEY_WORD, KEY_MEANING, KEY_LAST_NOTIFICATION_TIME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Word word = new Word(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getLong(3));

        return word;
    }

    public ArrayList<Word> getAllWords() {
        ArrayList<Word> wordList = new ArrayList<Word>();

        String selectQuery = "SELECT  * FROM " + TABLE_WORDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setId(Integer.parseInt(cursor.getString(0)));
                word.setWord(cursor.getString(1));
                word.setMeaning(cursor.getString(2));
                word.setLastTimeNotificationSent(cursor.getLong(3));

                wordList.add(word);
            } while (cursor.moveToNext());
        }

        return wordList;
    }

    public int getWordsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WORDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word.getWord());
        values.put(KEY_MEANING, word.getMeaning());
        values.put(KEY_LAST_NOTIFICATION_TIME, word.getLastTimeNotificationSent());

        return db.update(TABLE_WORDS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(word.getId()) });
    }

    public void deleteWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORDS, KEY_ID + " = ?",
                new String[] { String.valueOf(word.getId()) });
        db.close();
    }
}
