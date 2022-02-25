package com.example.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initSettingsButton();
        initNotesButton();
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
}