package com.example.myfuture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;


import com.example.myfuture.Adapter.proAdapter;
import com.example.myfuture.Model.proModel;
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

public class project extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton mFab;
    private FirebaseFirestore firestore;
    private proAdapter adapter;
    private List<proModel> pList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu);

        recyclerView = findViewById(R.id.recycerlview);
        mFab = findViewById(R.id.floatingActionButton);
        firestore = FirebaseFirestore.getInstance();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(project.this));

        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addNewPro.newInstance().show(getSupportFragmentManager(), addNewPro.TAG);
            }
        });

        pList = new ArrayList<>();
        adapter = new proAdapter(project.this, pList);

        recyclerView.setAdapter(adapter);
        showData();



    }

    private void showData(){
        firestore.collection("Projects").whereEqualTo("User_ID",Signup.UID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if(value!=null){
                for(DocumentChange doc : value.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        String id = doc.getDocument().getId();
                        proModel pModel = doc.getDocument().toObject(proModel.class).withId(id);

                        pList.add(pModel);
                        adapter.notifyDataSetChanged();

                    }
                }

                Collections.reverse(pList); }
            }
        });
    }


    public void returnCV (View view){
        this.finish();
    }

}

