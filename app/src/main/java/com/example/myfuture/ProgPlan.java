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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgPlan#newInstance} factory method to
 * create an instance of this fragment.
 */
// implements CompoundButton.OnCheckedChangeListener
public class ProgPlan extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public  CheckBox cb1;
    public  CheckBox cb2;
    public  CheckBox cb3;


    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ProgPlan() {


        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgPlan.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgPlan newInstance(String param1, String param2) {
        ProgPlan fragment = new ProgPlan();
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

                        int p1 = document.getLong("programming1-ch").intValue();
                        int p2 = document.getLong("programming2-ch").intValue();
                        int p3 = document.getLong("programming3-ch").intValue();
                        if (p1 == 1) {
                            cb1.setChecked(true);
                            cb1.setEnabled(false);

                            if (p2 == 1) {
                                cb2.setChecked(true);
                                cb2.setEnabled(false);
                            } else{
                                cb2.setChecked(false);
                                if (p1 == 1) {
                                    cb2.setEnabled(true);
                                }

                            }

                            if (p3 == 1) {
                                cb3.setChecked(true);
                                cb3.setEnabled(false);
                            } else{
                                cb3.setChecked(false);
                                if (p2 == 1) {
                                    cb3.setEnabled(true);
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
        view = inflater.inflate(R.layout.fragment_prog_plan, container, false);
        cb1 = (CheckBox) view.findViewById(R.id.cb1);
        cb2 = (CheckBox) view.findViewById(R.id.cb2);
        cb3 = (CheckBox) view.findViewById(R.id.cb3);

        retrieveData();

         cb1.setOnClickListener(new View.OnClickListener() {
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

                                        } else {
                                            Log.d("LOGGER", "No such document");
                                        }
                                    } else {
                                        Log.d("LOGGER", "get failed with ", task.getException());
                                    }
                                }
                            });

                            AccountFragment.points+=20;
                            firestore.collection("Users").document(Signup.UID).update("Point",AccountFragment.points);
                            firestore.collection("Users").document(Signup.UID).update("programming1-ch",1);
                            cb1.setChecked(true);
                            cb1.setEnabled(false);
                            cb2.setEnabled(true);
                            Toast.makeText(view.getContext(),"Congrats! You're brilliant", Toast.LENGTH_SHORT).show();
                        }else{
                            firestore.collection("Users").document(Signup.UID).update("programming1-ch",0);
                            cb1.setChecked(false);
                            cb2.setEnabled(false);

                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cb1.setChecked(false);
                        cb2.setEnabled(false);
                        dialogInterface.cancel();
                    }
                });

                builder.show();
             }
         });

         cb2.setOnClickListener(new View.OnClickListener() {
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
                        if(input.getText().toString()!= null) {

                            firestore.collection("Users").document(Signup.UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document != null) {
                                            int p = document.getLong("Point").intValue();
                                            AccountFragment.points = p;

                                        } else {
                                            Log.d("LOGGER", "No such document");
                                        }
                                    } else {
                                        Log.d("LOGGER", "get failed with ", task.getException());
                                    }
                                }
                            });

                            AccountFragment.points+=20;
                            firestore.collection("Users").document(Signup.UID).update("Point",AccountFragment.points);
                            firestore.collection("Users").document(Signup.UID).update("programming2-ch",1);
                            cb2.setChecked(true);
                            cb2.setEnabled(false);
                            cb3.setEnabled(true);
                            Toast.makeText(view.getContext(),"Congrats! You're brilliant", Toast.LENGTH_SHORT).show();
                        }else{
                            firestore.collection("Users").document(Signup.UID).update("programming2-ch",0);
                            cb2.setChecked(false);
                            cb3.setEnabled(false);

                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cb2.setChecked(false);
                        cb3.setEnabled(false);
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

         cb3.setOnClickListener(new View.OnClickListener() {
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
                        if(input.getText().toString()!= null) {

                            firestore.collection("Users").document(Signup.UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document != null) {
                                            int p = document.getLong("Point").intValue();
                                            AccountFragment.points = p;

                                        } else {
                                            Log.d("LOGGER", "No such document");
                                        }
                                    } else {
                                        Log.d("LOGGER", "get failed with ", task.getException());
                                    }
                                }
                            });

                            AccountFragment.points+=20;
                            firestore.collection("Users").document(Signup.UID).update("Point",AccountFragment.points);
                            firestore.collection("Users").document(Signup.UID).update("programming3-ch",1);
                            cb3.setChecked(true);
                            cb3.setEnabled(false);
                            Toast.makeText(view.getContext(),"Congrats! You're brilliant", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            firestore.collection("Users").document(Signup.UID).update("programming3-ch",0);
                            cb3.setChecked(false);


                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cb3.setChecked(false);
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        return view;
    }


}