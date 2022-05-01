package com.example.myfuture;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myfuture.Adapter.eduAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addNewEdu extends BottomSheetDialogFragment {

    public static final String TAG = "addNewEdu";
    private EditText setDegree;
    private EditText setDegree2;
    private EditText setGrade;
    private TextView setYear;
    private Button save;
    private FirebaseFirestore firestore;
    private Context context;
    private String degUpdate="";
    private View v;





    public static addNewEdu newInstance(){

        return new addNewEdu();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.add_new_edu, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setDegree = view.findViewById(R.id.add_degree);
        setDegree2 = view.findViewById(R.id.add_degree2);
        setGrade = view.findViewById(R.id.add_grade);
        setYear = view.findViewById(R.id.add_year);
        save = view.findViewById(R.id.save_edu);

        firestore = FirebaseFirestore.getInstance();

         boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;

            setDegree.setText(bundle.getString("degree"));
            degUpdate = setDegree.getText().toString();
            setDegree2.setText(bundle.getString("degree2"));
            setGrade.setText(bundle.getString("grade"));
            setYear.setText(bundle.getString("year"));

            if (degUpdate.length()>0){
                save.setEnabled(false);
                save.setBackgroundColor(Color.GRAY);
            }


        }

        setDegree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")){
                    save.setEnabled(false); // to ensure the text field is not empty
                    save.setBackgroundColor(Color.GRAY);
                }else{
                    save.setEnabled(true);
                    save.setBackgroundColor(getResources().getColor(R.color.base_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setDegree2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")){
                    save.setEnabled(false); // to ensure the text field is not empty
                    save.setBackgroundColor(Color.GRAY);
                }else{
                    save.setEnabled(true);
                    save.setBackgroundColor(getResources().getColor(R.color.base_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setGrade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")){
                    save.setEnabled(false); // to ensure the text field is not empty
                    save.setBackgroundColor(Color.GRAY);
                }else{
                    save.setEnabled(true);
                    save.setBackgroundColor(getResources().getColor(R.color.base_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calender = Calendar.getInstance();

                int m = calender.get(Calendar.MONTH);
                int y = calender.get(Calendar.YEAR);
                int d = calender.get(Calendar.DATE);

                DatePickerDialog date = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        setYear.setText(day + "/" + month + "/" + year);

                    }
                }, y , m , d);

                date.show();
            }
        });
        boolean finalIsUpdate = isUpdate;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String degree = setDegree.getText().toString();
                String degree2 = setDegree2.getText().toString();
                String grade = setGrade.getText().toString();
                String yearDate = setYear.getText().toString();

                if(finalIsUpdate){
                    firestore.collection("Education").document(bundle.getString("id")).update("degree",degree,"grade",grade,"degree2",degree2,"year",yearDate);

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Education Updated");
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    getActivity().finish();

                }else {

                    if (degree.isEmpty() || degree2.isEmpty() || grade.isEmpty() || yearDate.equals("Set the graduation date")) {
                        Toast.makeText(context, "Empty education NOT allowed !!", Toast.LENGTH_SHORT).show();
                        System.out.println(yearDate);
                    } else {

                        Map<String, Object> eduMap = new HashMap<>();

                        eduMap.put("degree", degree);
                        eduMap.put("degree2", degree2);
                        eduMap.put("grade", grade);
                        eduMap.put("year", yearDate);
                        eduMap.put("User_ID", Signup.UID);
                        //eduMap.put("time", FieldValue.serverTimestamp());

                        firestore.collection("Education").add(eduMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Education save", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
                dismiss();
            }
        }
        );

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}

