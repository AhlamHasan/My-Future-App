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
 * Use the {@link webplans#newInstance} factory method to
 * create an instance of this fragment.
 */
public class webplans extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public CheckBox cbbbb1;
    public  CheckBox cbbbb2;
    public  CheckBox cbbbb3;


    private View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public webplans() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment webplan.
     */
    // TODO: Rename and change types and number of parameters
    public static webplans newInstance(String param1, String param2) {
        webplans fragment = new webplans();
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

                        int p1 = document.getLong("web1-ch").intValue();
                        int p2 = document.getLong("web2-ch").intValue();
                        int p3 = document.getLong("web3-ch").intValue();
                        if (p1 == 1) {
                            cbbbb1.setChecked(true);
                            cbbbb1.setEnabled(false);

                            if (p2 == 1) {
                                cbbbb2.setChecked(true);
                                cbbbb2.setEnabled(false);
                            } else{
                                cbbbb2.setChecked(false);
                                if (p1 == 1) {
                                    cbbbb2.setEnabled(true);
                                }

                            }

                            if (p3 == 1) {
                                cbbbb3.setChecked(true);
                                cbbbb3.setEnabled(false);
                            } else{
                                cbbbb3.setChecked(false);
                                if (p2 == 1) {
                                    cbbbb3.setEnabled(true);
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
        view = inflater.inflate(R.layout.fragment_webplans, container, false);
        cbbbb1 = (CheckBox) view.findViewById(R.id.cbbbb1);
        cbbbb2 = (CheckBox) view.findViewById(R.id.cbbbb2);
        cbbbb3 = (CheckBox) view.findViewById(R.id.cbbbb3);

        retrieveData();

        cbbbb1.setOnClickListener(new View.OnClickListener() {
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

                            firestore.collection("Users").document(Signup.UID).update("web1-ch",1);
                            cbbbb1.setChecked(true);
                            cbbbb1.setEnabled(false);
                            cbbbb2.setEnabled(true);
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Congrats! You are so brilliant");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else{
                            firestore.collection("Users").document(Signup.UID).update("web1-ch",0);
                            cbbbb1.setChecked(false);
                            cbbbb2.setEnabled(false);

                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cbbbb1.setChecked(false);
                        cbbbb2.setEnabled(false);
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        cbbbb2.setOnClickListener(new View.OnClickListener() {
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

                            firestore.collection("Users").document(Signup.UID).update("web2-ch",1);
                            cbbbb2.setChecked(true);
                            cbbbb2.setEnabled(false);
                            cbbbb3.setEnabled(true);
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Congrats! You are so brilliant");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else{
                            firestore.collection("Users").document(Signup.UID).update("web2-ch",0);
                            cbbbb2.setChecked(false);
                            cbbbb3.setEnabled(false);

                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cbbbb2.setChecked(false);
                        cbbbb3.setEnabled(false);
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        cbbbb3.setOnClickListener(new View.OnClickListener() {
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

                            firestore.collection("Users").document(Signup.UID).update("web3-ch",1);
                            cbbbb3.setChecked(true);
                            cbbbb3.setEnabled(false);
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Congrats! You are so brilliant");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else{
                            firestore.collection("Users").document(Signup.UID).update("web3-ch",0);
                            cbbbb3.setChecked(false);


                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cbbbb3.setChecked(false);
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        return view;
    }
}
