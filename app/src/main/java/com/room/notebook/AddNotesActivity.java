package com.room.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNotesActivity extends AppCompatActivity {
    private EditText editTextNotes, editTextSubject, editTextBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextNotes = findViewById(R.id.editTextNoteTitle);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextBody = findViewById(R.id.editTextBody);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNotes();
            }
        });
    }

    private void saveNotes() {
        final String sNotes = editTextNotes.getText().toString().trim();
        final String sSubj = editTextSubject.getText().toString().trim();
        final String sBody = editTextBody.getText().toString().trim();

        if (sNotes.isEmpty()) {
            editTextNotes.setError("Note Title required");
            editTextNotes.requestFocus();
            return;
        }

        if (sSubj.isEmpty()) {
            editTextSubject.setError("Subject required");
            editTextSubject.requestFocus();
            return;
        }

        if (sBody.isEmpty()) {
            editTextBody.setError("Body required");
            editTextBody.requestFocus();
            return;
        }

        class SaveNotes extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a note
                Note note = new Note();
                note.setNote(sNotes);
                note.setSubject(sSubj);
                note.setBody(sBody);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .notesDao()
                        .insert(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveNotes st = new SaveNotes();
        st.execute();
    }

}
