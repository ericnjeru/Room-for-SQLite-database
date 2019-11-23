package com.room.notebook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateNoteActivity extends AppCompatActivity {

    private EditText editTextNoteTitle, editTextSubject, editTextBody;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);


        editTextNoteTitle = findViewById(R.id.editTextNoteTitle);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextBody = findViewById(R.id.editTextBody);




        final Note note = (Note) getIntent().getSerializableExtra("note");

        loadNote(note);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateNote(note);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateNoteActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteNote(note);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    private void loadNote(Note note) {
        editTextNoteTitle.setText(note.getNote());
        editTextSubject.setText(note.getSubject());
        editTextBody.setText(note.getBody());
    }

    private void updateNote(final Note note) {
        final String sNote = editTextNoteTitle.getText().toString().trim();
        final String sSubj = editTextSubject.getText().toString().trim();
        final String sBody = editTextBody.getText().toString().trim();

        if (sNote.isEmpty()) {
            editTextNoteTitle.setError("Note required");
            editTextNoteTitle.requestFocus();
            return;
        }

        if (sSubj.isEmpty()) {
            editTextSubject.setError("Desc required");
            editTextSubject.requestFocus();
            return;
        }

        if (sBody.isEmpty()) {
            editTextBody.setError("Finish by required");
            editTextBody.requestFocus();
            return;
        }

        class UpdateNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                note.setNote(sNote);
                note.setSubject(sSubj);
                note.setBody(sBody);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .notesDao()
                        .update(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateNoteActivity.this, MainActivity.class));
            }
        }

        UpdateNote ut = new UpdateNote();
        ut.execute();
    }


    private void deleteNote(final Note note) {
        class DeleteNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .notesDao()
                        .delete(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateNoteActivity.this, MainActivity.class));
            }
        }

        DeleteNote dt = new DeleteNote();
        dt.execute();

    }

}