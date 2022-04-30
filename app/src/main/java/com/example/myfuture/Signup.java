package com.example.myfuture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    TextInputLayout FirstNeme , LastName , Email, Password;
    String First_name,Last_name,strEmail,strPassword;
    String TAG="null";
    static public String UID="null";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirstNeme=findViewById(R.id.FristNameContainer);
        LastName=findViewById(R.id.LastNameContainer);
        Email=findViewById(R.id.Email_su_Container);
        Password=findViewById(R.id.Password_su_Container);


    }

    public void callSignInScrean (View view){
        Intent i=new Intent(Signup.this,SignInActivity.class);
        startActivity(i);
    }

    public void callHomeScrean (View view) {

        if(!validateEmail() | !validatePassword() | !validateFirstName() | !validateLastName()){
            return;
        }

        db.collection("Users").whereEqualTo("Email", strEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().isEmpty()){
                        SaveUser();
                    }else{
                        Email.setError("Email account is exist ");
                        Log.d(TAG, "Email account is already exist ");
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, " onFailure "+ e);
            }
        });
    }

    private boolean validateFirstName(){
        First_name=FirstNeme.getEditText().getText().toString().trim();

        if(First_name.isEmpty()) {
            FirstNeme.setError("Field can not be empty");
            return false;
        }else {
            FirstNeme.setError(null);
            FirstNeme.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateLastName(){
        Last_name=LastName.getEditText().getText().toString().trim();

        if(Last_name.isEmpty()) {
            LastName.setError("Field can not be empty");
            return false;
        }else {
            LastName.setError(null);
            LastName.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateEmail(){
        strEmail=Email.getEditText().getText().toString().trim();
        String checkEmail ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(strEmail.isEmpty()){
            Email.setError("Field can not be empty");
            return false;
        }else if(!strEmail.matches((checkEmail))){
            Email.setError("Invalid Email!");
            return false;
        }else {
            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(){
        strPassword=Password.getEditText().getText().toString().trim();
        String checkPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

        if(strPassword.isEmpty()){
            Password.setError("Field can not be empty");
            return false;
        } else if(!strPassword.matches((checkPassword))){
            Password.setError("Password should contain 8 characters,at least 1 digit, 1 special character");
            return false;
        } else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }
    }

    private void SaveUser(){

        Map<String, Object> dataToSave = new HashMap<>();
        dataToSave.put("First Name", First_name);
        dataToSave.put("Last Name", Last_name);
        dataToSave.put("Email", strEmail);
        dataToSave.put("Password", strPassword);
        dataToSave.put("Point", 0);
        String id = db.collection("collection_name").document().getId();
        System.out.println(id);
        db.collection("Users").add(dataToSave).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                UID = documentReference.getId();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });

        Intent i = new Intent(Signup.this, HomeActivity.class);
        startActivity(i);


    }
}