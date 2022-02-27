package com.example.mynoteapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mynotes.db";
    private static final int DATABASE_VERSION = 2;

    //database creation sql statement
    private static final String CREATE_TABLE_NOTES =
            "CREATE TABLE notes (_id integer primary key autoincrement,"
            + "subjectname TEXT not null, note TEXT, priority TEXT, date TEXT);" ;

    public NotesDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_NOTES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(NotesDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all data");
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }
}
