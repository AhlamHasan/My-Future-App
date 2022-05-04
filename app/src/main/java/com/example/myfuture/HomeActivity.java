package com.example.myfuture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.myfuture.Model.eduModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;

public class HomeActivity extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Homeragment()).commit();

         BottomNavigationView bottomNav=findViewById(R.id.bottomNavigationView);
         bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 Fragment SelectedFragment=null;
                 switch (item.getItemId()){
                     case R.id.Home:
                         SelectedFragment= new Homeragment();
                         break;
                     case R.id.Mycv:
                         SelectedFragment= new MyCVFragment();
                         break;
                     case R.id.MyAccount:
                         SelectedFragment= new AccountFragment();
                         break;
                 }

                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SelectedFragment).commit();
                 return true;
             }
         });


    }


    public void LogOut (View view){
        Intent i=new Intent(HomeActivity.this,SignInActivity.class);
        startActivity(i);

    }

    public void eduPage (View view){
        Intent i=new Intent(HomeActivity.this,edu.class);
        startActivity(i);

    }

    public void objPage (View view){
        Intent i=new Intent(HomeActivity.this,objective.class);
        startActivity(i);

    }

    public void skillsPage (View view){
        Intent i=new Intent(HomeActivity.this,skills.class);
        startActivity(i);

    }

    public void personalPage (View view){
        Intent i=new Intent(HomeActivity.this,PersonalDetails.class);
        startActivity(i);

    }

    public void expPage (View view){
        Intent i=new Intent(HomeActivity.this,experience.class);
        startActivity(i);

    }

    public void proPage (View view){
        Intent i=new Intent(HomeActivity.this,project.class);
        startActivity(i);

    }

    public void progPlans (View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgPlan()).commit();

    }


    public void prog1 (View view){
        firestore.collection("plans").document("programming").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level1");
                        Uri url = Uri.parse(level);
                        Intent i = new Intent();
                        i.setData(url);
                        i.setAction(Intent.ACTION_VIEW);
                        startActivity(i);

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
    }

    public void prog2 (View view){
        firestore.collection("plans").document("programming").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level2");
                        Uri url = Uri.parse(level);
                        Intent i = new Intent();
                        i.setData(url);
                        i.setAction(Intent.ACTION_VIEW);
                        startActivity(i);

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
    }

    public void prog3 (View view){
        firestore.collection("plans").document("programming").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level3");
                        Uri url = Uri.parse(level);
                        Intent i = new Intent();
                        i.setData(url);
                        i.setAction(Intent.ACTION_VIEW);
                        startActivity(i);

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });


    }



}