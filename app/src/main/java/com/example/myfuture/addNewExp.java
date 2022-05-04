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

public class addNewExp extends BottomSheetDialogFragment {

    public static final String TAG = "addNewExp";
    private EditText setComp;
    private EditText setJob;
    private EditText setDes;
    private TextView setStartYear;
    private TextView setEndYear;
    private Button save;
    private FirebaseFirestore firestore;
    private Context context;
    private String compUpdate="";
    private View v;





    public static addNewExp newInstance(){

        return new addNewExp();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.add_new_exp, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setComp = view.findViewById(R.id.add_company);
        setJob = view.findViewById(R.id.add_job);
        setDes = view.findViewById(R.id.add_jobDescribe);
        setStartYear = view.findViewById(R.id.add_startYear);
        setEndYear = view.findViewById(R.id.add_endYear);
        save = view.findViewById(R.id.save_exp);

        firestore = FirebaseFirestore.getInstance();

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;

            setComp.setText(bundle.getString("company"));
            compUpdate = setComp.getText().toString();
            setJob.setText(bundle.getString("job title"));
            setDes.setText(bundle.getString("description"));
            setStartYear.setText(bundle.getString("start date"));
            setEndYear.setText(bundle.getString("end date"));

            if (compUpdate.length()>0){
                save.setEnabled(false);
                save.setBackgroundColor(Color.GRAY);
            }


        }

        setComp.addTextChangedListener(new TextWatcher() {
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

        setJob.addTextChangedListener(new TextWatcher() {
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

        setDes.addTextChangedListener(new TextWatcher() {
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

        setStartYear.setOnClickListener(new View.OnClickListener() {
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
                        setStartYear.setText(day + "/" + month + "/" + year);

                    }
                }, y , m , d);

                date.show();
            }
        });

        setEndYear.setOnClickListener(new View.OnClickListener() {
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
                        setEndYear.setText(day + "/" + month + "/" + year);

                    }
                }, y , m , d);

                date.show();
            }
        });


        boolean finalIsUpdate = isUpdate;
        save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String company = setComp.getText().toString();
                                        String job = setJob.getText().toString();
                                        String jobDes = setDes.getText().toString();
                                        String startDate = setStartYear.getText().toString();
                                        String endDate = setEndYear.getText().toString();

                                        if(finalIsUpdate){
                                            firestore.collection("Experiences").document(bundle.getString("id")).update("company",company,"job title",job,"description",jobDes,"start date",startDate,"end date",endDate);

                                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                            builder.setMessage("Experience Updated");
                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                            getActivity().finish();

                                        }else {

                                            if (company.isEmpty() || job.isEmpty() || startDate.equals("Set start date") || endDate.equals("Set end date") ) {
                                                Toast.makeText(context, "Empty education NOT allowed !!", Toast.LENGTH_SHORT).show();
                                            } else {

                                                Map<String, Object> expMap = new HashMap<>();

                                                expMap.put("company", company);
                                                expMap.put("job title", job);
                                                expMap.put("description", jobDes);
                                                expMap.put("start date", startDate);
                                                expMap.put("end date", endDate);
                                                expMap.put("User_ID", Signup.UID);

                                                firestore.collection("Experiences").add(expMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(context, "Experience save", Toast.LENGTH_SHORT).show();
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

