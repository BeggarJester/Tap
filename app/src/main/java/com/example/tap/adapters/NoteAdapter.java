package com.example.tap.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tap.R;
import com.example.tap.models.NoteModel;
import com.example.tap.viewHolders.NoteViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class NoteAdapter extends FirebaseRecyclerAdapter<NoteModel, NoteViewHolder> {
    public NoteAdapter(@NonNull FirebaseRecyclerOptions<NoteModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull NoteModel model) {
        holder.onBind(model);
        System.out.println("Bind!");
        System.out.println(model);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new NoteViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_note, viewGroup, false));
    }
}
