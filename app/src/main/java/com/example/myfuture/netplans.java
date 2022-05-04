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
 * Use the {@link netplans#newInstance} factory method to
 * create an instance of this fragment.
 */
public class netplans extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public CheckBox cbb1;
    public  CheckBox cbb2;
    public  CheckBox cbb3;
    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public netplans() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment netplans.
     */
    // TODO: Rename and change types and number of parameters
    public static netplans newInstance(String param1, String param2) {
        netplans fragment = new netplans();
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

                        int p1 = document.getLong("network1-ch").intValue();
                        int p2 = document.getLong("network2-ch").intValue();
                        int p3 = document.getLong("network3-ch").intValue();
                        if (p1 == 1) {
                            cbb1.setChecked(true);
                            cbb1.setEnabled(false);

                            if (p2 == 1) {
                                cbb2.setChecked(true);
                                cbb2.setEnabled(false);
                            } else{
                                cbb2.setChecked(false);
                                if (p1 == 1) {
                                    cbb2.setEnabled(true);
                                }

                            }

                            if (p3 == 1) {
                                cbb3.setChecked(true);
                                cbb3.setEnabled(false);
                            } else{
                                cbb3.setChecked(false);
                                if (p2 == 1) {
                                    cbb3.setEnabled(true);
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
        view = inflater.inflate(R.layout.netplans, container, false);
        cbb1 = (CheckBox) view.findViewById(R.id.cbb1);
        cbb2 = (CheckBox) view.findViewById(R.id.cbb2);
        cbb3 = (CheckBox) view.findViewById(R.id.cbb3);

        retrieveData();

        cbb1.setOnClickListener(new View.OnClickListener() {
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

                            firestore.collection("Users").document(Signup.UID).update("network1-ch",1);
                            cbb1.setChecked(true);
                            cbb1.setEnabled(false);
                            cbb2.setEnabled(true);
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Congrats! You are so brilliant");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else{
                            firestore.collection("Users").document(Signup.UID).update("network1-ch",0);
                            cbb1.setChecked(false);
                            cbb2.setEnabled(false);

                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cbb1.setChecked(false);
                        cbb2.setEnabled(false);
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        cbb2.setOnClickListener(new View.OnClickListener() {
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

                            firestore.collection("Users").document(Signup.UID).update("network2-ch",1);
                            cbb2.setChecked(true);
                            cbb2.setEnabled(false);
                            cbb3.setEnabled(true);
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Congrats! You are so brilliant");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else{
                            firestore.collection("Users").document(Signup.UID).update("network2-ch",0);
                            cbb2.setChecked(false);
                            cbb3.setEnabled(false);

                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cbb2.setChecked(false);
                        cbb3.setEnabled(false);
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        cbb3.setOnClickListener(new View.OnClickListener() {
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

                            firestore.collection("Users").document(Signup.UID).update("network3-ch",1);
                            cbb3.setChecked(true);
                            cbb3.setEnabled(false);
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setMessage("Congrats! You are so brilliant");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else{
                            firestore.collection("Users").document(Signup.UID).update("network3-ch",0);
                            cbb3.setChecked(false);


                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cbb3.setChecked(false);
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        return view;
    }


}
        // Inflate the layout for this fragment
