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

public class objective extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public static EditText obj ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective);

        obj = findViewById(R.id.obj);

        firestore.collection("Users").document(Signup.UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        obj.setText(document.getString("objective"));
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

    public void clearObj (View view){
        firestore.collection("Users").document(Signup.UID).update("objective","");

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Objective Cleared");
        AlertDialog dialog = builder.create();
        dialog.show();

        this.finish();
    }

    public void saveObj (View view){
        firestore.collection("Users").document(Signup.UID).update("objective",obj.getText().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Objective saved");
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}

