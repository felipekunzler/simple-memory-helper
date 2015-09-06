package com.felipekunzler.simplememoryhelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EditWordActivity extends AppCompatActivity {

    private Word mWord;
    private EditText mEditTextWord;
    private EditText mEditTextMeaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_word);

        mEditTextWord = (EditText) findViewById(R.id.editTextWord);
        mEditTextMeaning = (EditText) findViewById(R.id.editTextMeaning);

        mWord = new Word();
        mWord.setId((int) getIntent().getLongExtra("WORD_ID", -1));
        mWord.setWord("");
        mWord.setMeaning("");

        // When id is -1 a word is getting edited.
        if (mWord.getId() != -1) {
            DatabaseHandler db = new DatabaseHandler(this);
            mWord = db.getWord(mWord.getId());

            mEditTextWord.setText(mWord.getWord());
            mEditTextMeaning.setText(mWord.getMeaning());
        }
        else // If it is a new word, change the title
        {
            setTitle(R.string.title_new_word);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_save) {

            String wordText = mEditTextWord.getText().toString();
            String wordMeaning = mEditTextMeaning.getText().toString();

            if (!"".equals(wordText) && !"".equals(wordMeaning)) {

                DatabaseHandler db = new DatabaseHandler(this);
                mWord.setWord(wordText);
                mWord.setMeaning(wordMeaning);

                if (mWord.getId() == -1) {
                    db.addWord(mWord);
                } else {
                    db.updateWord(mWord);
                }

                Toast.makeText(this, R.string.toast_word_saved, Toast.LENGTH_SHORT).show();

                db.close();

                Intent intent = new Intent(this, ManageWordsActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, R.string.toast_empty_text, Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        else if (id == android.R.id.home){
            if (!alertDiscardChanges()){
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_word, menu);

        return true;
    }

    @Override
    public void onBackPressed() {

        if (alertDiscardChanges()){
            super.onBackPressed();
        };
    }

    private boolean alertDiscardChanges(){

        boolean dirty = !mWord.getWord().equals(mEditTextWord.getText().toString());
        dirty = dirty || !mWord.getMeaning().equals(mEditTextMeaning.getText().toString());

        if (dirty) {

            AlertDialog.Builder confirmAlert = new AlertDialog.Builder(this);
            confirmAlert.setTitle(R.string.alert_unsaved_changes);
            confirmAlert.setMessage(R.string.alert_discard_message);
            confirmAlert.setPositiveButton(R.string.alert_discard_button, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), ManageWordsActivity.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), R.string.toast_changes_discarded, Toast.LENGTH_SHORT).show();
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

            return false; // Don't discard, because AlertDialog events will handle the discard.
        }

        // No changes, allow go back
        return true;
    }
}
