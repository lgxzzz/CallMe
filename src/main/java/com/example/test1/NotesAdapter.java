package com.example.test1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private Context mContext;
    private List<Notes> mNotesList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView sentences;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            sentences=(TextView)view.findViewById(R.id.notes_sentences);
        }
    }

    public NotesAdapter(List<Notes> notesList){
        mNotesList=notesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if(mContext == null){
            mContext=parent.getContext();
        }
        View view_notes= LayoutInflater.from(mContext).inflate(R.layout.notes_item,
                parent,false);
        return new ViewHolder(view_notes);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes notes = mNotesList.get(position);
        holder.sentences.setText(notes.getSentences());
    }

    @Override
    public int getItemCount() {
        return mNotesList.size();
    }
}
