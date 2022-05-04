package com.example.myfuture;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dataplan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dataplan extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public CheckBox cbbb1;
    public  CheckBox cbbb2;
    public  CheckBox cbbb3;
    private View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public dataplan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dataplan.
     */
    // TODO: Rename and change types and number of parameters
    public static dataplan newInstance(String param1, String param2) {
        dataplan fragment = new dataplan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void retrieveData() {

        firestore.collection("Users").document(Signup.UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        int p1 = document.getLong("database1-ch").intValue();
                        int p2 = document.getLong("database2-ch").intValue();
                        int p3 = document.getLong("database3-ch").intValue();
                        if (p1 == 1) {
                            cbbb1.setChecked(true);
                            cbbb1.setEnabled(false);

                            if (p2 == 1) {
                                cbbb2.setChecked(true);
                                cbbb2.setEnabled(false);
                            } else{
                                cbbb2.setChecked(false);
                                if (p1 == 1) {
                                    cbbb2.setEnabled(true);
                                }

                            }

                            if (p3 == 1) {
                                cbbb3.setChecked(true);
                                cbbb3.setEnabled(false);
                            } else{
                                cbbb3.setChecked(false);
                                if (p2 == 1) {
                                    cbbb3.setEnabled(true);
                                }

                            }
                        }

                    }
                    else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dataplans, container, false);
        cbbb1 = (CheckBox) view.findViewById(R.id.cbbb1);
        cbbb2 = (CheckBox) view.findViewById(R.id.cbbb2);
        cbbb3 = (CheckBox) view.findViewById(R.id.cbbb3);

        retrieveData();

        cbbb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Certification Credential");
                final EditText input = new EditText(view.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!input.getText().toString().equals("")) {
                            System.out.println("input "+input.getText().toString());

                            firestore.collection("Users").document(Signup.UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document != null) {
                                            int p = document.getLong("Point").intValue();
                                            AccountFragment.points = p;
                                            AccountFragment.points+=20;
                                            firestore.collection("Users").document(Signup.UID).update("Point",AccountFragment.points);

                                        } else {
                                            Log.d("LOGGER", "No such document");
                                        }
                                    } else {
                                        Log.d("LOGGER", "get failed with ", task.getException());
                                    }
                                }
                            });

                            firestore.collection("Users").document(Signup.UID).update("database1-ch",1);
                            cbbb1.setChecked(true);
                            cbbb1.setEnabled(false);
                            cbbb2.setEnabled(true);
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Congrats! You are so brilliant");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else{
                            firestore.collection("Users").document(Signup.UID).update("database1-ch",0);
                            cbbb1.setChecked(false);
                            cbbb2.setEnabled(false);

                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cbbb1.setChecked(false);
                        cbbb2.setEnabled(false);
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        cbbb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Certification Credential");
                final EditText input = new EditText(view.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!input.getText().toString().equals("")) {

                            firestore.collection("Users").document(Signup.UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document != null) {
                                            int p = document.getLong("Point").intValue();
                                            AccountFragment.points = p;
                                            AccountFragment.points+=20;
                                            firestore.collection("Users").document(Signup.UID).update("Point",AccountFragment.points);

                                        } else {
                                            Log.d("LOGGER", "No such document");
                                        }
                                    } else {
                                        Log.d("LOGGER", "get failed with ", task.getException());
                                    }
                                }
                            });

                            firestore.collection("Users").document(Signup.UID).update("database2-ch",1);
                            cbbb2.setChecked(true);
                            cbbb2.setEnabled(false);
                            cbbb3.setEnabled(true);
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Congrats! You are so brilliant");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else{
                            firestore.collection("Users").document(Signup.UID).update("database2-ch",0);
                            cbbb2.setChecked(false);
                            cbbb3.setEnabled(false);

                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cbbb2.setChecked(false);
                        cbbb3.setEnabled(false);
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        cbbb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Certification Credential");
                final EditText input = new EditText(view.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!input.getText().toString().equals("")) {

                            firestore.collection("Users").document(Signup.UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document != null) {
                                            int p = document.getLong("Point").intValue();
                                            AccountFragment.points = p;
                                            AccountFragment.points+=20;
                                            firestore.collection("Users").document(Signup.UID).update("Point",AccountFragment.points);

                                        } else {
                                            Log.d("LOGGER", "No such document");
                                        }
                                    } else {
                                        Log.d("LOGGER", "get failed with ", task.getException());
                                    }
                                }
                            });

                            firestore.collection("Users").document(Signup.UID).update("database3-ch",1);
                            cbbb3.setChecked(true);
                            cbbb3.setEnabled(false);
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Congrats! You are so brilliant");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else{
                            firestore.collection("Users").document(Signup.UID).update("database3-ch",0);
                            cbbb3.setChecked(false);


                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cbbb3.setChecked(false);
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        return view;
    }



       // return inflater.inflate(R.layout.fragment_dataplan, container, false);

}
