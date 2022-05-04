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

public class PersonalDetails extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public static EditText name, email, phone, linkedin, github ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        linkedin = findViewById(R.id.linkedin);
        github = findViewById(R.id.github);

        firestore.collection("Users").document(Signup.UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        name.setText(document.getString("First Name")+" "+document.getString("Last Name"));
                        email.setText(document.getString("Email"));
                        phone.setText(document.getString("phone number"));
                        linkedin.setText(document.getString("linkedIn"));
                        github.setText(document.getString("github"));
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


    public void saveInfo (View view){
        firestore.collection("Users").document(Signup.UID).update("phone number",phone.getText().toString(),"linkedIn",linkedin.getText().toString(),"github",github.getText().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Personal Details Saved");
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}

