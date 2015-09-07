package com.felipekunzler.simplememoryhelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ManageWordsFragment extends Fragment {

    private WordCustomAdapter mWordsAdapter;
    private ListView mListView;

    public ManageWordsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_manage_words, container, false);

        DatabaseHandler db = new DatabaseHandler(getActivity());
        ArrayList<Word> reverseList = db.getAllWords();
        Collections.reverse(reverseList);

        mWordsAdapter = new WordCustomAdapter(getActivity(), reverseList);

        // Get a reference to the ListView, and attach this adapter to it.
        mListView = (ListView) rootView.findViewById(R.id.listview_words);
        mListView.setAdapter(mWordsAdapter);

        mListView.setClickable(true);
        mListView.setFocusable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(getActivity(), EditWordActivity.class);
                intent.putExtra(Word.WORD_ID, mWordsAdapter.getItemId(position));
                startActivity(intent);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {

                AlertDialog.Builder confirmAlert = new AlertDialog.Builder(getActivity());
                confirmAlert.setTitle(R.string.alert_delete_confirmation);
                confirmAlert.setMessage(R.string.altert_delete_message);
                confirmAlert.setPositiveButton(R.string.alert_delete_button, new DialogInterface.OnClickListener() {
                    int itemPosition = position;

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Word selectedWord = (Word) mWordsAdapter.getItem(itemPosition);

                        DatabaseHandler db = new DatabaseHandler(getActivity());
                        db.deleteWord(selectedWord);
                        db.close();

                        mWordsAdapter.remove(itemPosition);
                        Toast.makeText(getActivity(), R.string.toast_delete_word, Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });

                confirmAlert.setNegativeButton(R.string.alert_cancel_button, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                confirmAlert.show();

                return true;
            }
        });

        return rootView;
    }
}
