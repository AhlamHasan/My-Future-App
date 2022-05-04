package com.example.myfuture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;


import com.example.myfuture.Adapter.expAdapter;
import com.example.myfuture.Model.expModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class experience extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton mFab;
    private FirebaseFirestore firestore;
    private expAdapter adapter;
    private List<expModel> eList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        recyclerView = findViewById(R.id.recycerlview);
        mFab = findViewById(R.id.floatingActionButton);
        firestore = FirebaseFirestore.getInstance();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(experience.this));

        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addNewExp.newInstance().show(getSupportFragmentManager(), addNewExp.TAG);
            }
        });

        eList = new ArrayList<>();
        adapter = new expAdapter(experience.this, eList);

        recyclerView.setAdapter(adapter);
        showData();
        
    }

    private void showData(){
        firestore.collection("Experiences").whereEqualTo("User_ID",Signup.UID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc : value.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        String id = doc.getDocument().getId();
                        expModel exModel = doc.getDocument().toObject(expModel.class).withId(id);

                        eList.add(exModel);
                        adapter.notifyDataSetChanged();

                    }
                }

                Collections.reverse(eList);
            }
        });
    }


    public void returnCV (View view){
        this.finish();
    }

}

