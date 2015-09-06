package com.felipekunzler.simplememoryhelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WordCustomAdapter extends BaseAdapter
{
    private Activity activity;
    private ArrayList<Word> data;
    private LayoutInflater inflater = null;
    Context context;

    public WordCustomAdapter(Activity activity, ArrayList<Word> data)
    {
        this.activity = activity;
        this.context = activity.getBaseContext();
        this.data = data;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getItem(int position)
    {
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return this.data.get(position).getId();
    }

    @Override
    public int getCount()
    {
        return this.data.size();
    }

    @Override
    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        if(convertView == null) {
            view = inflater.inflate(R.layout.list_item_word, null);
        }

        TextView textViewWord = (TextView) view.findViewById(R.id.list_item_word_textview);
        TextView textViewMeaning = (TextView) view.findViewById(R.id.list_item_meaning_textview);

        Word word = data.get(position);

        textViewWord.setText(word.getWord());
        textViewMeaning.setText(word.getMeaning());

        return view;
    }

    public void remove(int index){

        this.data.remove(index);
        this.notifyDataSetChanged();
    }
}