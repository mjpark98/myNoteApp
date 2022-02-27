package com.example.mynoteapp;

import java.util.Calendar;

public class Notes {
    private int noteID;
    private String subject;
    private String notes;
    private String priority;
    private Calendar date;

    public Notes() {

        noteID = -1;
        date = Calendar.getInstance();
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPriority(String priority) { this.priority = priority; }
    public String getPriority(){ return priority;}

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
