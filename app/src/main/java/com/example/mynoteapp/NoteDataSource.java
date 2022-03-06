package com.example.mynoteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class NoteDataSource {

    private SQLiteDatabase database;
    private NotesDBHelper dbHelper;

    public NoteDataSource(Context context){
        dbHelper = new NotesDBHelper(context);
    }
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }

    public boolean insertNote(Notes n){
        boolean didSucceed = false;
        try{
            ContentValues initialValues = new ContentValues();

            initialValues.put("subjectname", n.getSubject());
            initialValues.put("note", n.getNotes());
            initialValues.put("priority", n.getPriority());
            initialValues.put("date", String.valueOf(n.getDate()));

            didSucceed = database.insert("notes", null, initialValues) > 0;
        }
        catch(Exception e){
            //do nothing
        }
        return didSucceed;
    }
    public boolean updateNote(Notes n){
        boolean didSucceed = false;
        try{
            long rowId = (long) n.getNoteID();
            ContentValues updateValues =  new ContentValues();

            updateValues.put("subjectname", n.getSubject());
            updateValues.put("note", n.getNotes());
            updateValues.put("priority", n.getPriority());
            updateValues.put("date", String.valueOf(n.getDate()));

            didSucceed = database.update("notes", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e){
            //do nothing
        }
        return didSucceed;
    }

    public int getLastNoteID(){
        int lastId;
        try{
            String query = "Select MAX(_id) from notes";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e){
            lastId = -1;
        }
        return lastId;
    }

    public ArrayList<Notes> getMyNotes(String sortItem, String sortOrder) {
        ArrayList<Notes> notes = new ArrayList<>();




        try{
            String query = " SELECT * FROM notes ORDER BY " + sortItem + " " + sortOrder;
            Cursor cursor = database.rawQuery(query,null);

            Notes newNote;
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                newNote = new Notes();
                newNote.setNoteID(cursor.getInt(0));
                newNote.setSubject(cursor.getString(1));
                newNote.setNotes(cursor.getString(2));

                if (cursor.getString(3).equals("C_Low")) {
                    newNote.setPriority("Low");
                } else if (cursor.getString(3).equals("B_Medium")) {
                    newNote.setPriority("Medium");
                } else {
                    newNote.setPriority("High");
                }
                //newNote.setPriority(cursor.getString(3));


                notes.add(newNote);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch(Exception e) {
            notes = new ArrayList<>();
        }
        return notes;
    }
    public Notes getSpecificNote(int noteID){
        Notes note = new Notes();

        String query = "SELECT * FROM notes WHERE _id = " + noteID;
        Cursor cursor = database.rawQuery(query, null);


        if (cursor.moveToFirst()){
            note.setNoteID(cursor.getInt(0));
            note.setSubject(cursor.getString(1));
            note.setNotes(cursor.getString(2));
            note.setPriority(cursor.getString(3));
            cursor.close();
        }
        return note;
    }

    public boolean deleteNote(int noteID){
        boolean didDelete = false;
        try{
            didDelete = database.delete("notes", "_id= " + noteID , null) > 0;
        } catch (Exception e){
            //does nothing
        }
        return didDelete;
    }
}
