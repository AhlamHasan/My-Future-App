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


import com.example.myfuture.Adapter.eduAdapter;
import com.example.myfuture.Model.eduModel;
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

public class edu extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton mFab;
    private FirebaseFirestore firestore;
    private eduAdapter adapter;
    private List<eduModel> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu);

        recyclerView = findViewById(R.id.recycerlview);
        mFab = findViewById(R.id.floatingActionButton);
        firestore = FirebaseFirestore.getInstance();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(edu.this));

        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               addNewEdu.newInstance().show(getSupportFragmentManager(), addNewEdu.TAG);
            }
        });

        mList = new ArrayList<>();
        adapter = new eduAdapter(edu.this, mList);

        recyclerView.setAdapter(adapter);
        showData();



    }

    private void showData(){
        firestore.collection("Education").whereEqualTo("User_ID",Signup.UID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc : value.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        String id = doc.getDocument().getId();
                        eduModel eModel = doc.getDocument().toObject(eduModel.class).withId(id);

                        mList.add(eModel);
                        adapter.notifyDataSetChanged();

                    }
                }

                Collections.reverse(mList);
            }
        });
    }


    public void returnCV (View view){
        //getSupportFragmentManager().beginTransaction().add(android.R.id.content,new MyCVFragment()).commit(); calling fragment from activity
        Intent i=new Intent(edu.this,HomeActivity.class);
        startActivity(i);
    }

}

