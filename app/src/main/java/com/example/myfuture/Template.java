package com.example.myfuture;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class Template extends AppCompatActivity {
    public FirebaseFirestore db;
    private TextView Education_detail;
    private String isdegree;
    private String isgrade;
    private String isuniversity;
    private String isyear;

    private TextView personal_name;
    private TextView personal_detail;
    private String isname;
    private String isemail;
    private String isnumber;
    private String isgitgub;
    private String islinkedIn;

    private TextView Skills_detail;
    private String isSkills;
    private TextView Objective;
    private String isobjective;

   private TextView Projects_detail;
   private String isyear2;
   private String istitle;
   private String isdescription;

   private TextView Experience_detail;
    private String iscompany;
    private String isdescription2;
    private String isend_date;
    private String isstart_date;
    private String isjob;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        db = FirebaseFirestore.getInstance();

        Education_detail = findViewById(R.id.Education_detail);

        personal_name=findViewById(R.id.personal_name);
        personal_detail=findViewById(R.id.personal_detail);
        Skills_detail=findViewById(R.id.Skills_detail);
        Objective=findViewById(R.id.Objective);

        Projects_detail=findViewById(R.id.Projects_detail);

        Experience_detail=findViewById(R.id.Experience_detail);



        show_Data();

    }


    private void show_Data() {
        db.collection("Projects").whereEqualTo("User_ID",Signup.UID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        if (e !=null)
                        {

                        }

                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                        { isdescription =  documentChange.getDocument().getData().get("description").toString();
                            istitle =  documentChange.getDocument().getData().get("title").toString();
                            isyear2 =  documentChange.getDocument().getData().get("year").toString();
                            Projects_detail.setText(isdescription + "  " + istitle+ "\n" + isyear2);
                        }
                    }
                });
        db.collection("Education").whereEqualTo("User_ID",Signup.UID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        if (e !=null)
                        {


                        }

                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                        {

                            isdegree =  documentChange.getDocument().getData().get("degree").toString();
                            isgrade =  documentChange.getDocument().getData().get("grade").toString();
                            isuniversity =  documentChange.getDocument().getData().get("university").toString();
                            isyear =  documentChange.getDocument().getData().get("year").toString();
                            Education_detail.setText(isuniversity + "  " + isdegree+ "\n" + isgrade + "\n" +isyear);
                        }
                    }
                });
        db.collection("Experiences").whereEqualTo("User_ID",Signup.UID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                        if (e !=null)
                        {

                        }

                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                        {
                            iscompany =  documentChange.getDocument().getData().get("company").toString();
                            isdescription2 =  documentChange.getDocument().getData().get("description").toString();
                            isend_date =  documentChange.getDocument().getData().get("end date").toString();
                            isstart_date =  documentChange.getDocument().getData().get("start date").toString();
                            isjob =  documentChange.getDocument().getData().get("job title").toString();
                            Experience_detail.setText(iscompany + "  " + isdescription2+ "\n" + isend_date + " " +isstart_date+ " \n " +isjob);
                        }
                    }
                });
        db.collection("Users").document(Signup.UID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {

                            isname =  document.getString("First Name")+" "+document.getString("Last Name");
                            isemail = document.getString("Email");
                            isnumber =  document.getString("phone number");
                            isgitgub =  document.getString("github");
                            islinkedIn =  document.getString("linkedIn");
                            isSkills =  document.getString("skills");
                            isobjective = document.getString("objective");
                            personal_name.setText(isname);
                            personal_detail.setText(isemail + "  " + isnumber+ "\n" + isgitgub + "  " +islinkedIn);
                            Skills_detail.setText(isSkills);
                            Objective.setText(isobjective);
                            } else {
                                Log.d("LOGGER", "No such document");
                            }
                        } else {
                            Log.d("LOGGER", "get failed with ", task.getException());
                        }
                    }
                });
    }

    public void returnhomeCV (View view){
        Intent i=new Intent(Template.this,HomeActivity.class);
        startActivity(i);
    }

}
