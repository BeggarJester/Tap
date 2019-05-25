package com.example.tap.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tap.R;
import com.example.tap.adapters.NoteAdapter;
import com.example.tap.models.NoteModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final Context context = this;
    private FloatingActionButton button;
    protected Query noteQuery;
    private NoteAdapter adapter;
    private int color;
    private FirebaseAuth mAuth;
    private TextView nullText;
    private ImageView outBut;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (findViewById(R.id.add_note));
        outBut = findViewById(R.id.out_button);
        button.setOnClickListener(this);
        nullText = findViewById(R.id.null_note_text);
        outBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!isOnline()) {
                    Toast.makeText(com.example.tap.activities.MainActivity.this, "Проверьте подключение к интернету",
                            Toast.LENGTH_SHORT).show();
                } else {
                    mAuth = FirebaseAuth.getInstance();
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.delete_dialog, null);
                    final TextView deleteText = promptsView.findViewById(R.id.delete_dia_text);
                    deleteText.setText("Вы действительно хотите выйти?");
                    AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
                    mDialogBuilder.setView(promptsView);
                    mDialogBuilder
                            .setCancelable(false)
                            .setNegativeButton("Выйти   ",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            mAuth.signOut();
                                            Intent intent = new Intent(MainActivity.this, CheckInActivity.class);
                                            startActivity(intent);
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
        });
        updateList();

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void updateList() {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        RecyclerView recyclerView = findViewById(R.id.note_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase.getInstance()
                .getReference()
                .child(currentUser.getUid()).child("all").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    nullText.setText("У вас пока нет напоминаний");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        noteQuery = FirebaseDatabase.getInstance()
                .getReference()
                .child(currentUser.getUid())
                .child("all")
                .limitToLast(15);
        FirebaseRecyclerOptions<NoteModel> options = new FirebaseRecyclerOptions.Builder<NoteModel>()
                .setQuery(noteQuery, NoteModel.class)
                .build();
        adapter = new NoteAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }

    @Override
    protected void onStop() {

        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onClick(final View v) {
        if (!isOnline()) {
            Toast.makeText(com.example.tap.activities.MainActivity.this, "Проверьте подключение к интернету",
                    Toast.LENGTH_SHORT).show();
        } else {
            mAuth = FirebaseAuth.getInstance();
            final FirebaseUser currentUser = mAuth.getCurrentUser();
            color = 0;
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.add_item_dialog, null);
            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
            mDialogBuilder.setView(promptsView);
            final EditText noteHead = promptsView.findViewById(R.id.input_head);
            final EditText noteBody = promptsView.findViewById(R.id.input_body);
            final CheckBox checkBox = promptsView.findViewById(R.id.checkBox);
            final CardView whiteCard = promptsView.findViewById(R.id.white_choose_card);
            final CardView redCard = promptsView.findViewById(R.id.red_choose_card);
            final CardView yellowCard = promptsView.findViewById(R.id.yellow_choose_card);
            final CardView greenCard = promptsView.findViewById(R.id.green_choose_card);
            final CardView blueCard = promptsView.findViewById(R.id.blue_choose_card);
            final Button whiteBut = promptsView.findViewById(R.id.color_white_button);
            final Button redBut = promptsView.findViewById(R.id.color_red_button);
            final Button yellowBut = promptsView.findViewById(R.id.color_yellow_button);
            final Button greenBut = promptsView.findViewById(R.id.color_green_button);
            final Button blueBut = promptsView.findViewById(R.id.color_blue_button);
            whiteBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    color = 0;
                    whiteCard.setVisibility(View.VISIBLE);
                    redCard.setVisibility(View.GONE);
                    yellowCard.setVisibility(View.GONE);
                    greenCard.setVisibility(View.GONE);
                    blueCard.setVisibility(View.GONE);
                }
            });
            redBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    color = 1;
                    whiteCard.setVisibility(View.GONE);
                    redCard.setVisibility(View.VISIBLE);
                    yellowCard.setVisibility(View.GONE);
                    greenCard.setVisibility(View.GONE);
                    blueCard.setVisibility(View.GONE);
                }
            });
            yellowBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    color = 2;
                    whiteCard.setVisibility(View.GONE);
                    redCard.setVisibility(View.GONE);
                    yellowCard.setVisibility(View.VISIBLE);
                    greenCard.setVisibility(View.GONE);
                    blueCard.setVisibility(View.GONE);
                }
            });
            greenBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    color = 3;
                    whiteCard.setVisibility(View.GONE);
                    redCard.setVisibility(View.GONE);
                    yellowCard.setVisibility(View.GONE);
                    greenCard.setVisibility(View.VISIBLE);
                    blueCard.setVisibility(View.GONE);
                }
            });
            blueBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    color = 4;
                    whiteCard.setVisibility(View.GONE);
                    redCard.setVisibility(View.GONE);
                    yellowCard.setVisibility(View.GONE);
                    greenCard.setVisibility(View.GONE);
                    blueCard.setVisibility(View.VISIBLE);
                }
            });

            mDialogBuilder
                    .setCancelable(false)
                    .setNegativeButton("Добавить   ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Date date = new Date();
                                    NoteModel model = new NoteModel(noteHead.getText().toString(),
                                            noteBody.getText().toString(), color, date.toString(), checkBox.isChecked());
                                    FirebaseDatabase.getInstance()
                                            .getReference()
                                            .child(currentUser.getUid()).child("all").child(model.getId()).setValue(model);
                                    Intent intent = getIntent();
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(intent);
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


