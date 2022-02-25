package com.example.mynoteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
}
