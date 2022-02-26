package com.example.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private Notes currentNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListButton();
        initSettingsButton();
        initSaveButton();
        initTextChangedEvents();
        currentNotes = new Notes();
        initPriorityButtons();
    }
    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initSettingsButton() {
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initSaveButton(){
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                boolean wasSuccessful;
                NoteDataSource ds = new NoteDataSource(MainActivity.this);

                try{
                    ds.open();
                    if(currentNotes.getNoteID() == -1){
                        wasSuccessful = ds.insertNote(currentNotes);
                        int newId = ds.getLastNoteID();
                        currentNotes.setNoteID(newId);
                        Intent intent = new Intent(MainActivity.this, ListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else{
                        wasSuccessful = ds.updateNote(currentNotes);
                        Intent intent = new Intent(MainActivity.this, ListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    ds.close();
                }
                catch(Exception e){
                    wasSuccessful = false;
                }
                if(wasSuccessful){
                    ToggleButton editToggle = findViewById(R.id.toggleButton);
                    editToggle.toggle();
                    setForEditing(false);
                }
            }
        });
    }
    private void initTextChangedEvents(){
        final EditText getNotesName = findViewById(R.id.editSubject);
        getNotesName.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                currentNotes.setSubject(getNotesName.getText().toString());
            }
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
        final EditText getNotesText = findViewById(R.id.editNotesText);
        getNotesText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                currentNotes.setNotes(getNotesText.getText().toString());
            }
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });
    }
    private void setForEditing(boolean enabled){

        EditText editSubject = findViewById(R.id.editSubject);
        EditText editNote = findViewById(R.id.editNotesText);

        editSubject.setEnabled(enabled);
        editNote.setEnabled(enabled);

        if(enabled){
            editSubject.requestFocus();
        }
        else{
            //nothing?
        }
    }

    private void initPriorityButtons(){
        RadioButton lowPriority = (RadioButton) findViewById(R.id.radioButtonLow);
        RadioButton midPriority = (RadioButton) findViewById(R.id.radioButtonMedium);
        RadioButton highPriority = (RadioButton) findViewById(R.id.radioButtonMedium);
        RadioGroup rgPriority = findViewById(R.id.radioGroupPriority);

        rgPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (lowPriority.isChecked()) {
                    currentNotes.setPriority("Low");
                }
                else if(midPriority.isChecked()){
                    currentNotes.setPriority("Medium");
                }
                else{
                    currentNotes.setPriority("High");
                }
            }
        });

    }
}