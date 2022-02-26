package com.example.mynoteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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
            initialValues.put("notes", n.getNotes());
            initialValues.put("priority", n.getPriority());

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
            Long rowId = (long) n.getNoteID();
            ContentValues updateValues =  new ContentValues();

            updateValues.put("subjectname", n.getSubject());
            updateValues.put("notes", n.getNotes());
            updateValues.put("priority", n.getPriority());

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

    public ArrayList<Notes> getNotes() {
        ArrayList<Notes> notes = new ArrayList<Notes>();
        try{
            String query = "SELECT * FROM notes";
            Cursor cursor = database.rawQuery(query,null);

            Notes newNotes;
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                newNotes = new Notes();
                newNotes.setNoteID(cursor.getInt(0));
                newNotes.setSubject(cursor.getString(1));
                newNotes.setNotes(cursor.getString(2));
                newNotes.setPriority(cursor.getString(3));
                notes.add(newNotes);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch(Exception e) {
            notes = new ArrayList<>();
        }
        return notes;
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
