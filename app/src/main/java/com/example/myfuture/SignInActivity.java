package com.example.myfuture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends AppCompatActivity {

    TextInputLayout Email, Password;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in2);

        Email= findViewById(R.id.emailContainer);
        Password=findViewById(R.id.passwordContainer);

    }

    public void callSignUpScrean (View view){
        Intent i=new Intent(SignInActivity.this,Signup.class);
        startActivity(i);
    }

    public void callHomeScreen(View view) {
       if(!validateEmail() | !validatePassword()){
           return;
       }

        Intent i=new Intent(SignInActivity.this,HomeActivity.class);
        startActivity(i);


    }

    private boolean validateEmail(){
        String val=Email.getEditText().getText().toString().trim();
        String checkEmail ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            Email.setError("Field can not be empty");
            return false;
        }else if(!val.matches((checkEmail))){
            Email.setError("Invalid Email!");
            return false;
        }else {
            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(){
        String val=Password.getEditText().getText().toString().trim();
        String checkPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

        if(val.isEmpty()){
            Password.setError("Field can not be empty");
            return false;
        } else if(!val.matches((checkPassword))){
            Password.setError("Password should contain 8 characters,at least 1 digit, 1 special character");
            return false;
        } else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
             }
    }

}