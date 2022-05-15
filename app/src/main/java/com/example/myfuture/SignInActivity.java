package com.example.myfuture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SignInActivity extends AppCompatActivity {


    TextInputLayout Email, Password;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String strEmail,strPassword,TAG="null";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in2);

        Email= findViewById(R.id.emailContainer);
        Password=findViewById(R.id.passwordContainer);

    }

    public String hashGenerator(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = "Fg$234&344GGGHKL#rrt";
        String hash;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 500, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hashBytes = factory.generateSecret(spec).getEncoded();
        hash = new String(hashBytes);
        return hash;
    }

    public void callSignUpScrean (View view){
        Intent i=new Intent(SignInActivity.this,Signup.class);
        startActivity(i);
    }

    public void callHomeScreen(View view) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if(!validateEmail() | !validatePassword()){
            return;
        }

        db.collection("Users").whereEqualTo("Email", strEmail).whereEqualTo("Password",  hashGenerator(strPassword).toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty()){
                    Email.setError("Email is not found");
                    Password.setError("or password is not correct");
                    Log.d(TAG, "This account not exist");
                }else {
                    List<DocumentSnapshot> DocList= queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot snapshot: DocList){
                        Signup.UID= snapshot.getId();
                        Log.d(TAG, "DocumentSnapshot is found with ID: " + Signup.UID);
                    }

                    Intent i=new Intent(SignInActivity.this,HomeActivity.class);
                    startActivity(i);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, " onFailure "+ e);
            }
        });

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
        strPassword =Password.getEditText().getText().toString().trim();
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


}