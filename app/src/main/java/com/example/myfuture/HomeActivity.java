package com.example.myfuture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
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

import java.net.URL;
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

    public void callTemplatePage(View view){
        Intent i=new Intent(HomeActivity.this,Template.class);
        startActivity(i);
    }

    public void proPage (View view){
        Intent i=new Intent(HomeActivity.this,project.class);
        startActivity(i);

    }

    public void expPage (View view){
        Intent i=new Intent(HomeActivity.this,experience.class);
        startActivity(i);

    }

    public void progPlans (View view){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ProgPlan()).commit();

    }

    public boolean urlVerification(String url){
        try{
            new URL(url).toURI();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void prog1 (View view){
        firestore.collection("plans").document("programming")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) { DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level1");
                        if(urlVerification(level)) {
                            Uri url = Uri.parse(level);
                            Intent i = new Intent();
                            i.setData(url);
                            i.setAction(Intent.ACTION_VIEW);
                            startActivity(i);
                        } else{
                            AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Sorry! you can't open this plan now");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // message to admin to handle url error
                            Log.d("LOGGER", "URL of Programming Level 1 not added correctly");
                        }
                    } else { Log.d("LOGGER", "No such document"); }
                } else { Log.d("LOGGER", "get failed with ", task.getException()); } } }); }

    public void prog2 (View view){
        firestore.collection("plans").document("programming").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level2");
                        if(urlVerification(level)) {
                            Uri url = Uri.parse(level);
                            Intent i = new Intent();
                            i.setData(url);
                            i.setAction(Intent.ACTION_VIEW);
                            startActivity(i);
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Sorry! you can't open this plan now");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // message to admin to handle url error
                            Log.d("LOGGER", "URL of Programming Level 2 not added correctly");
                        }

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
                        if(urlVerification(level)) {
                            Uri url = Uri.parse(level);
                            Intent i = new Intent();
                            i.setData(url);
                            i.setAction(Intent.ACTION_VIEW);
                            startActivity(i);
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Sorry! you can't open this plan now");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // message to admin to handle url error
                            Log.d("LOGGER", "URL of Programming Level 3 not added correctly");
                        }

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });


    }
    public void netPlan (View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new netplans()).commit();

    }
    public void net1 (View view){
        firestore.collection("plans").document("networking").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level1");
                        if(urlVerification(level)) {
                            Uri url = Uri.parse(level);
                            Intent i = new Intent();
                            i.setData(url);
                            i.setAction(Intent.ACTION_VIEW);
                            startActivity(i);
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Sorry! you can't open this plan now");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // message to admin to handle url error
                            Log.d("LOGGER", "URL of Networking Level 1 not added correctly");
                        }

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
    }

    public void net2 (View view){
        firestore.collection("plans").document("networking").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level2");
                        if(urlVerification(level)) {
                            Uri url = Uri.parse(level);
                            Intent i = new Intent();
                            i.setData(url);
                            i.setAction(Intent.ACTION_VIEW);
                            startActivity(i);
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Sorry! you can't open this plan now");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // message to admin to handle url error
                            Log.d("LOGGER", "URL of Networking Level 2 not added correctly");
                        }

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
    }

    public void net3 (View view){
        firestore.collection("plans").document("networking").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level3");
                        if(urlVerification(level)) {
                            Uri url = Uri.parse(level);
                            Intent i = new Intent();
                            i.setData(url);
                            i.setAction(Intent.ACTION_VIEW);
                            startActivity(i);
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Sorry! you can't open this plan now");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // message to admin to handle url error
                            Log.d("LOGGER", "URL of Networking Level 3 not added correctly");
                        }

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });


    }
    public void dataPlan (View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new dataplan()).commit();

    }
    public void data1 (View view){
        firestore.collection("plans").document("database").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level1");
                        if(urlVerification(level)) {
                            Uri url = Uri.parse(level);
                            Intent i = new Intent();
                            i.setData(url);
                            i.setAction(Intent.ACTION_VIEW);
                            startActivity(i);
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Sorry! you can't open this plan now");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // message to admin to handle url error
                            Log.d("LOGGER", "URL of Database Level 1 not added correctly");
                        }

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
    }

    public void data2 (View view){
        firestore.collection("plans").document("database").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level2");
                        if(urlVerification(level)) {
                            Uri url = Uri.parse(level);
                            Intent i = new Intent();
                            i.setData(url);
                            i.setAction(Intent.ACTION_VIEW);
                            startActivity(i);
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Sorry! you can't open this plan now");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // message to admin to handle url error
                            Log.d("LOGGER", "URL of Database Level 2 not added correctly");
                        }

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
    }

    public void data3 (View view){
        firestore.collection("plans").document("database").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level3");
                        if(urlVerification(level)) {
                            Uri url = Uri.parse(level);
                            Intent i = new Intent();
                            i.setData(url);
                            i.setAction(Intent.ACTION_VIEW);
                            startActivity(i);
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Sorry! you can't open this plan now");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // message to admin to handle url error
                            Log.d("LOGGER", "URL of Database Level 3 not added correctly");
                        }

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });


    }

    public void webPlan (View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new webplans()).commit();

    }
    public void web1 (View view){
        firestore.collection("plans").document("web").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level1");
                        if(urlVerification(level)) {
                            Uri url = Uri.parse(level);
                            Intent i = new Intent();
                            i.setData(url);
                            i.setAction(Intent.ACTION_VIEW);
                            startActivity(i);
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Sorry! you can't open this plan now");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // message to admin to handle url error
                            Log.d("LOGGER", "URL of Web Level 1 not added correctly");
                        }

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
    }

    public void web2 (View view){
        firestore.collection("plans").document("web").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level2");
                        if(urlVerification(level)) {
                            Uri url = Uri.parse(level);
                            Intent i = new Intent();
                            i.setData(url);
                            i.setAction(Intent.ACTION_VIEW);
                            startActivity(i);
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Sorry! you can't open this plan now");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // message to admin to handle url error
                            Log.d("LOGGER", "URL of Web Level 2 not added correctly");
                        }

                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
    }

    public void web3 (View view){
        firestore.collection("plans").document("web").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String level = document.getString("level3");
                        if(urlVerification(level)) {
                            Uri url = Uri.parse(level);
                            Intent i = new Intent();
                            i.setData(url);
                            i.setAction(Intent.ACTION_VIEW);
                            startActivity(i);
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Sorry! you can't open this plan now");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            // message to admin to handle url error
                            Log.d("LOGGER", "URL of Web Level 3 not added correctly");
                        }

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
