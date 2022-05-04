package com.example.myfuture.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class expID {
    @Exclude
    public String expID;

    public <Ex extends expID> Ex withId(@NonNull final String id){
        this.expID = id;
        return (Ex) this;
    }
}
