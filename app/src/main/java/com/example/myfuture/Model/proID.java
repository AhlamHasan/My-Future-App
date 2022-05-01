package com.example.myfuture.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class proID {
    @Exclude
    public String proID;

    public <P extends proID> P withId(@NonNull final String id){
        this.proID = id;
        return (P) this;
    }
}
