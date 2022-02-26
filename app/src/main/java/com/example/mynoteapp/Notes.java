package com.example.mynoteapp;

public class Notes {
    private int noteID;
    private String subject;
    private String notes;
    private String priority;

    public Notes() {

        noteID = -1;
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
}
