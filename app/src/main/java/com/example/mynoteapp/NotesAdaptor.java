package com.example.mynoteapp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdaptor extends RecyclerView.Adapter {
    private ArrayList<Notes> notesData;
    private View.OnClickListener mOnitemClickListener;
    private Context parentContext;
    private boolean isDeleting;


    public NotesAdaptor(ArrayList<Notes> arrayList, Context context){
        notesData = arrayList;
        parentContext = context;
    }

public class NotesViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewSubject;
    public TextView textViewPriority;
    public Button deleteButton;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewSubject = itemView.findViewById(R.id.textSubject);
        textViewPriority = itemView.findViewById(R.id.textPriority);
        deleteButton = itemView.findViewById(R.id.buttonDeleteContact);
        itemView.setTag(this);
        itemView.setOnClickListener(mOnitemClickListener);
    }

    public TextView getTextViewSubject() {
        return textViewSubject;
    }

    public TextView getTextViewPriority() {
        return textViewPriority;
    }
    public Button getDeleteButton(){
        return deleteButton;
    }
}

    public void setOnitemClickListener(View.OnClickListener itemClickListener){
        mOnitemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
    NotesViewHolder nvh = (NotesViewHolder) holder;

    nvh.getTextViewSubject().setText(notesData.get(position).getSubject());
    nvh.getTextViewPriority().setText(notesData.get(position).getPriority());

        if(isDeleting){
            nvh.getDeleteButton().setVisibility(View.VISIBLE);
            nvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(position);
                }
            });
        }
        else{
            nvh.getDeleteButton().setVisibility(View.INVISIBLE);
        }
    }


    public void setDelete(boolean b) {isDeleting = b;}

    private void deleteItem(int position){

        Notes note = notesData.get(position);
        NoteDataSource ds = new NoteDataSource(parentContext);
        try{
            ds.open();
            boolean didDelete = ds.deleteNote(note.getNoteID());
            ds.close();
            if(didDelete){
                notesData.remove(position);
            }
            else{
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public int getItemCount() {
        return notesData.size();
    }
}