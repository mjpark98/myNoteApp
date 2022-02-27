package com.example.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    NotesAdapter notesAdapter;
    ArrayList<Notes> notes;

    private final View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            int noteID = notes.get(position).getNoteID();
            Intent intent = new Intent(ListActivity.this, MainActivity.class);
            intent.putExtra("noteID", noteID);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initSettingsButton();
        initNotesButton();
        initAddNoteButton();
        initDeleteSwitch();


    }

    @Override
    public void onResume() {
        super.onResume();


        String sortBy = getSharedPreferences("MyNotesPreferences",
                Context.MODE_PRIVATE).getString("sortitem", "subjectname");
        String sortOrder = getSharedPreferences("MyNotesPreferences",
                Context.MODE_PRIVATE).getString("sortorder", "ASC");

        NoteDataSource ds = new NoteDataSource(this);

        try {
            ds.open();
           notes = ds.getMyNotes(sortBy,sortOrder);
            ds.close();


            if (notes.size() > 0) {
                RecyclerView notesList = findViewById(R.id.rvNotes);
                notesAdapter = new NotesAdapter(notes, this);
                notesAdapter.setOnItemClickListener(onItemClickListener);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                notesList.setLayoutManager(layoutManager);
                notesList.setAdapter(notesAdapter);
            } else {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
        catch(Exception e){
            Toast.makeText(this, "Error retrieving notes", Toast.LENGTH_SHORT).show();
        }

    }


    private void initSettingsButton() {
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(ListActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initNotesButton() {
        ImageButton ibList = findViewById(R.id.imageButtonNote);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initAddNoteButton(){
        Button newNote = findViewById(R.id.buttonAddNote);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initDeleteSwitch(){
        Switch s = findViewById(R.id.switchDelete);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Boolean status = compoundButton.isChecked();
                notesAdapter.setDelete(status);
                notesAdapter.notifyDataSetChanged();
            }
        });
    }
}