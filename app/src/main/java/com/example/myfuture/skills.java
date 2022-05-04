package com.example.myfuture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class skills extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public static EditText skill ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        skill = findViewById(R.id.skill);

        firestore.collection("Users").document(Signup.UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        skill.setText(document.getString("skills"));
                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });

    }

    public void returnCV (View view){
        this.finish();
    }

    public void clearSkills (View view){
        firestore.collection("Users").document(Signup.UID).update("skills","");

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Skills Cleared");
        AlertDialog dialog = builder.create();
        dialog.show();

        this.finish();
    }

    public void saveSkills (View view){
        firestore.collection("Users").document(Signup.UID).update("skills",skill.getText().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Skills saved");
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}

