package com.example.todo_list.KeepNote.ProxyNote;

import com.example.todo_list.KeepNote.Listdata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataProxy implements ValueEventListener {
    private DatabaseReference databaseReference;
    private ValueEventListener listener;
    private OnDataChangedListener onDataChangedListener;

    public DataProxy(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public void startListening() {
        if (listener == null) {
            listener = databaseReference.addValueEventListener(this);
        }
    }

    public void stopListening() {
        if (listener != null) {
            databaseReference.removeEventListener(listener);
            listener = null;
        }
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<Listdata> dataList = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Listdata listdata = snapshot.getValue(Listdata.class);
            dataList.add(listdata);
        }
        if (onDataChangedListener != null) {
            onDataChangedListener.onDataChanged(dataList);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        if (onDataChangedListener != null) {
            onDataChangedListener.onCancelled(databaseError);
        }
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.onDataChangedListener = listener;
    }

    public interface OnDataChangedListener {
        void onDataChanged(List<Listdata> dataList);

        void onCancelled(DatabaseError databaseError);
    }
}