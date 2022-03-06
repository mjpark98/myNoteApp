package com.example.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Collections;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initNotesButton();
        initListButton();
        initSortOrderClick();
        initSortItemClick();
        initSettings();
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


    private void initSettings(){

        String sortItem = getSharedPreferences("MyNotesApp",
                Context.MODE_PRIVATE).getString("sortitem", "date");

//        String sortPriority = getSharedPreferences("MyNotesApp",
//                Context.MODE_PRIVATE).getString("sortitem", "priority");

        String sortOrder = getSharedPreferences("MyNotesApp",
                Context.MODE_PRIVATE).getString("sortorder", "ASC");

        RadioButton rbDate = findViewById(R.id.radioButtonDate);
        RadioButton rbPriority = findViewById(R.id.radioButtonPriority);
        RadioButton rbSubject = findViewById(R.id.radioButtonSubject);

        if(sortItem.equalsIgnoreCase("date")){
            rbDate.setChecked(true);
        } else if(sortItem.equalsIgnoreCase("priority")){
            rbPriority.setChecked(true);
        } else{
            rbSubject.setChecked(true);
        }

        RadioButton rbAscending = findViewById(R.id.radioButtonAsc);
        RadioButton rbDescending = findViewById(R.id.radioButtonDes);

        if(sortOrder.equalsIgnoreCase("ASC")){
            rbAscending.setChecked(true);
        } else{
            rbDescending.setChecked(true);
        }
    }









 private void initSortItemClick(){
     RadioGroup rgSortItem = findViewById(R.id.radioGroup);
     rgSortItem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(RadioGroup radioGroup, int i) {
             RadioButton rbDate = findViewById(R.id.radioButtonDate);
             RadioButton rbPriority = findViewById(R.id.radioButtonPriority);

             if(rbDate.isChecked()){
                 getSharedPreferences("MyNotesApp", Context.MODE_PRIVATE).edit().putString("sortitem", "date").apply();
             }
             else if (rbPriority.isChecked()){
                 getSharedPreferences("MyNotesApp", Context.MODE_PRIVATE).edit().putString("sortitem", "priority").apply();
             }
             else{
                 getSharedPreferences("MyNotesApp", Context.MODE_PRIVATE).edit().putString("sortitem", "subjectname").apply();
             }
         }
     });
 }


 private void initSortOrderClick(){
        RadioGroup rgSortOrder = findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbAscending = findViewById(R.id.radioButtonAsc);
                if(rbAscending.isChecked()){
                    getSharedPreferences("MyNotesApp", Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").apply();
                } else{
                    getSharedPreferences("MyNotesApp", Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").apply();
                }

            }
        });

 }

}