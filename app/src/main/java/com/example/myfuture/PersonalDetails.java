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

    private boolean validatePhone(){
        String Test = phone.getText().toString();
        String check ="[05][0-9]+";
        if(Test.isEmpty()){
            phone.setError("phone number can not be empty");
            return false;
        }else if(Test.length()!=10){
        phone.setError("Invalid phone number!");
        return false;
        } else if(Test.matches(check)==false){
            phone.setError("Invalid phone number!");
            return false;
        }else {
            phone.setError(null);
            return true;
        }
    }

    // check it later
    private boolean validateAccountG(){
        String Test = github.getText().toString();
        String check ="[a-zA-Z][.]*";

        if(Test.matches(check)==false){
            github.setError("Invalid Account!");
            return false;
        }else {
            github.setError(null);
            return true;
        }
    }

    private boolean validateAccountL(){
        String Test = linkedin.getText().toString();
        String check ="[a-zA-Z][.]+";

        if(Test.matches(check)==false){
            linkedin.setError("Invalid Account!");
            return false;
        }else {
            linkedin.setError(null);
            return true;
        }
    }



    public void saveInfo (View view){

        if((validatePhone()==true) && (validateAccountG()==true) && (validateAccountL()==true) ) {
            firestore.collection("Users").document(Signup.UID).update("phone number",phone.getText().toString(),"linkedIn",linkedin.getText().toString(),"github",github.getText().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Personal Details Saved");
            AlertDialog dialog = builder.create();
            dialog.show();
        }


    }
}

