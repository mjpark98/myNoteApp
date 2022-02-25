package com.example.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initNotesButton();
        initListButton();
    }
    private void initNotesButton() {
        ImageButton ibList = findViewById(R.id.imageButtonNote);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(SettingsActivity.this, ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}