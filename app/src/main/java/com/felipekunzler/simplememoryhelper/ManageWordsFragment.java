package com.felipekunzler.simplememoryhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ManageWordsFragment extends Fragment {

    private WordCustomAdapter mWordsAdapter;
    private ListView mListView;

    public ManageWordsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_manage_words, container, false);

        // Temporary mock data.
        ArrayList<Word> mockDataWords = new ArrayList<Word>();
        mockDataWords.add(new Word("Word1", "Meaning1 Meaning1 Meaning1 Meaning1 Meaning1 Meaning1  Meaning1 Meaning1 Meaning1    Meaning1 Meaning1 Meaning1"));
        mockDataWords.add(new Word("Word2", "Meaning2Meaning2"));
        mockDataWords.add(new Word("Word3", "Meaning3"));
        mockDataWords.add(new Word("Word4", "Meaning4"));
        mockDataWords.add(new Word("Word5", "Meaning5"));
        mockDataWords.add(new Word("Word6", "Meaning6"));
        mockDataWords.add(new Word("Word7", "Meaning37"));
        mockDataWords.add(new Word("Word8", "Meaning48"));
        mockDataWords.add(new Word("Word2", "Meaning2Meaning2"));
        mockDataWords.add(new Word("Word3", "Meaning3"));
        mockDataWords.add(new Word("Word4", "Meaning4"));
        mockDataWords.add(new Word("Word5", "Meaning5"));
        mockDataWords.add(new Word("Word6", "Meaning6"));
        mockDataWords.add(new Word("Word7", "Meaning37"));
        mockDataWords.add(new Word("Word8", "Meaning48"));

        mWordsAdapter = new WordCustomAdapter(getActivity(), mockDataWords);

        // Get a reference to the ListView, and attach this adapter to it.
        mListView = (ListView) rootView.findViewById(R.id.listview_words);
        mListView.setAdapter(mWordsAdapter);

        mListView.setClickable(true);
        mListView.setFocusable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            Intent intent = new Intent(getActivity(), EditWordActivity.class);
            startActivity(intent);
            }
        });

        return rootView;
    }
}
