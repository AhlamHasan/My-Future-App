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

import com.example.myfuture.Adapter.proAdapter;
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

public class addNewPro extends BottomSheetDialogFragment {

    public static final String TAG = "addNewPro";
    private EditText setTitle;
    private EditText setDescribe;
    private TextView setYear;
    private Button save;
    private FirebaseFirestore firestore;
    private Context context;
    private String titUpdate="";
    private View v;





    public static addNewPro newInstance(){

        return new addNewPro();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.add_new_project, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setTitle = view.findViewById(R.id.add_title);
        setDescribe = view.findViewById(R.id.add_des);
        setYear = view.findViewById(R.id.add_yearPro);
        save = view.findViewById(R.id.save_pro);

        firestore = FirebaseFirestore.getInstance();

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;

            setTitle.setText(bundle.getString("title"));
            titUpdate = setTitle.getText().toString();
            setDescribe.setText(bundle.getString("description"));
            setYear.setText(bundle.getString("date"));

            if (titUpdate.length()>0){
                save.setEnabled(false);
                save.setBackgroundColor(Color.GRAY);
            }


        }

        setTitle.addTextChangedListener(new TextWatcher() {
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

        setDescribe.addTextChangedListener(new TextWatcher() {
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
                                        String title = setTitle.getText().toString();
                                        String describe = setDescribe.getText().toString();
                                        String yearDate = setYear.getText().toString();

                                        if(!validateTitle() | !validateDes() ){
                                            return;
                                        }

                                        if(finalIsUpdate){
                                            firestore.collection("Projects").document(bundle.getString("id")).update("title",title,"description",describe,"date",yearDate);

                                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                            builder.setMessage("Project Updated");
                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                            getActivity().finish();

                                        }else {

                                            if (title.isEmpty() || describe.isEmpty() || yearDate.equals("Set the project completion date")) {
                                                Toast.makeText(context, "Empty project NOT allowed !!", Toast.LENGTH_SHORT).show();
                                                System.out.println(yearDate);
                                            } else {

                                                Map<String, Object> proMap = new HashMap<>();

                                                proMap.put("title", title);
                                                proMap.put("description", describe);
                                                proMap.put("year", yearDate);
                                                proMap.put("User_ID", Signup.UID);

                                                firestore.collection("Projects").add(proMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(context, "Project save", Toast.LENGTH_SHORT).show();
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

    private boolean validateTitle(){
        String Test = setTitle.getText().toString();
        String check ="[a-zA-Z].+";

        if(Test.matches(check)==false){
            setTitle.setError("Invalid Input!");
            return false;
        }else {
            setTitle.setError(null);
            return true;
        }
    }

    private boolean validateDes(){
        String Test = setDescribe.getText().toString();
        String check ="[a-zA-Z].+";

        if(Test.matches(check)==false){
            setDescribe.setError("Invalid Input!");
            return false;
        }else {
            setDescribe.setError(null);
            return true;
        }
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

