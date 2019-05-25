package com.example.tap.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tap.R;
import com.example.tap.models.NoteModel;
import com.example.tap.viewHolders.NameViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class NoteAdapter extends FirebaseRecyclerAdapter<NoteModel, NameViewHolder> {
    public NoteAdapter(@NonNull FirebaseRecyclerOptions<NoteModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NameViewHolder holder, int position, @NonNull NoteModel model) {
        holder.onBind(model);
        System.out.println("Bind!");
        System.out.println(model);
    }

    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new NameViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_note, viewGroup, false));
    }
}
