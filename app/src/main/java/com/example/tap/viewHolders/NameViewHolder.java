package com.example.tap.viewHolders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tap.R;
import com.example.tap.models.NoteModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class NameViewHolder extends RecyclerView.ViewHolder {

    private TextView headLine;
    private TextView body;
    private ImageButton deleteBut;
    private FirebaseAuth mAuth;

    public NameViewHolder(@NonNull View itemView) {
        super(itemView);


        headLine = itemView.findViewById(R.id.note_headline);
        body = itemView.findViewById(R.id.note_body);
        deleteBut = itemView.findViewById(R.id.delete_note_but);
    }

    public Activity getActivity(View v) {
        Context context = v.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public boolean isOnline(View v) {

        ConnectivityManager cm =
                (ConnectivityManager) getActivity(v).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @SuppressLint("ResourceAsColor")
    public void onBind(final NoteModel post) {

        itemView.setTag(post.getId());
        headLine.setText(post.getHeadLine());
        body.setText(post.getBody());
        switch (post.getColor()) {
            case (0):
                if (post.isImportant()) {
                    itemView.findViewById(R.id.important_note_card0).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.important_note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card4).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card4).setVisibility(View.GONE);
                } else {
                    itemView.findViewById(R.id.note_card0).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.important_note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card4).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card4).setVisibility(View.GONE);
                }

                break;
            case (1):
                if (post.isImportant()) {
                    itemView.findViewById(R.id.important_note_card1).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.important_note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card4).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card4).setVisibility(View.GONE);
                } else {
                    itemView.findViewById(R.id.note_card1).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.important_note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card4).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card4).setVisibility(View.GONE);
                }

                break;
            case (2):
                if (post.isImportant()) {
                    itemView.findViewById(R.id.important_note_card2).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.important_note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card4).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card4).setVisibility(View.GONE);
                } else {
                    itemView.findViewById(R.id.note_card2).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.important_note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card4).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card4).setVisibility(View.GONE);
                }
                break;
            case (3):
                if (post.isImportant()) {
                    itemView.findViewById(R.id.important_note_card3).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.important_note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card4).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card4).setVisibility(View.GONE);
                } else {
                    itemView.findViewById(R.id.note_card3).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.important_note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card4).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card4).setVisibility(View.GONE);
                }
                break;
            case (4):
                if (post.isImportant()) {
                    itemView.findViewById(R.id.important_note_card4).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.important_note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card4).setVisibility(View.GONE);
                } else {
                    itemView.findViewById(R.id.note_card4).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.important_note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card3).setVisibility(View.GONE);
                    itemView.findViewById(R.id.important_note_card4).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card0).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card1).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card2).setVisibility(View.GONE);
                    itemView.findViewById(R.id.note_card3).setVisibility(View.GONE);
                }
                break;
        }

        deleteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setTag(post.getId());

                if (!isOnline(v)) {
                    Toast toast = Toast.makeText(getActivity(v).getApplicationContext(),
                            "Проверьте подключение к интернету" +
                                    "", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Context context = v.getContext();
                    if (v.getId() == R.id.delete_note_but) {
                        mAuth = FirebaseAuth.getInstance();
                        final FirebaseUser currentUser = mAuth.getCurrentUser();
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.delete_dialog, null);
                        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                        mDialogBuilder.setView(promptsView);
                        mDialogBuilder
                                .setCancelable(false)
                                .setNegativeButton("Удалить   ",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                FirebaseDatabase.getInstance()
                                                        .getReference()
                                                        .child(currentUser.getUid()).child("all")
                                                        .child(v.getTag().toString()).setValue(null);

                                            }
                                        })
                                .setPositiveButton("Отмена",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alertDialog = mDialogBuilder.create();
                        alertDialog.show();
                    }

                }
            }

        });
    }
}
