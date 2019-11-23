package com.room.notebook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NotesViewHolder> {

    private Context mCtx;
    private List<Note> noteList;

    public NoteAdapter(Context mCtx, List<Note> noteList) {
        this.mCtx = mCtx;
        this.noteList = noteList;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        Note t = noteList.get(position);
        holder.textViewNote.setText(t.getNote());
        holder.textViewSubj.setText(t.getSubject());
        holder.textViewBody.setText(t.getBody());


    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  textViewNote, textViewSubj, textViewBody;

        public NotesViewHolder(View itemView) {
            super(itemView);

            textViewNote = itemView.findViewById(R.id.textViewNote);
            textViewSubj = itemView.findViewById(R.id.textViewSubj);
            textViewBody = itemView.findViewById(R.id.textViewBody);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Note note = noteList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateNoteActivity.class);
            intent.putExtra("note", note);

            mCtx.startActivity(intent);
        }
    }
}