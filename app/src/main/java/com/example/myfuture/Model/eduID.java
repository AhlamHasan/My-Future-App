package com.example.myfuture.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class eduID {
    @Exclude
    public String eduID;

    public <E extends eduID> E withId(@NonNull final String id){
        this.eduID = id;
        return (E) this;
    }
}
