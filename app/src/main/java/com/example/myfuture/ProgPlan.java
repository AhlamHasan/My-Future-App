package com.example.myfuture;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    public  CheckBox cb1 ;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_prog_plan, container, false);
        cb1 = (CheckBox) view.findViewById(R.id.cb1);
        cb2 = (CheckBox) view.findViewById(R.id.cb2);
        cb3 = (CheckBox) view.findViewById(R.id.cb3);

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cb2.setEnabled(true);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Certification Credential");
                final EditText input = new EditText(view.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(input.getText().toString()!= null) {
                            AccountFragment.points+=20;
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
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cb3.setEnabled(true);
                System.out.println("C "+cb3);

            }
        });

        return view;
    }


}