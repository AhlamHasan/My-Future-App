package com.example.myfuture.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfuture.HomeActivity;
import com.example.myfuture.Model.eduModel;
import com.example.myfuture.ProgPlan;
import com.example.myfuture.R;
import com.example.myfuture.SignInActivity;
import com.example.myfuture.Signup;
import com.example.myfuture.addNewEdu;
import com.example.myfuture.edu;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class eduAdapter extends RecyclerView.Adapter<eduAdapter.MyViewHolder> {

    private List<eduModel> eduList;
    private edu activity;
    private FirebaseFirestore firestore;


    public eduAdapter(edu eduActivity , List<eduModel> eduList ){
        this.eduList = eduList;
        activity = eduActivity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.each_edu, parent , false );
        firestore = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    public void deleteEdu(int position){
        eduModel eModel = eduList.get(position);
        firestore.collection("Education").document(eModel.eduID).delete();
        eduList.remove(position);
        notifyItemRemoved(position);
    }

    public void editEdu(int position){
        eduModel eModel = eduList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("degree", eModel.getDegree());
        bundle.putString("University", eModel.getUniversity());
        bundle.putString("grade", eModel.getGrade());
        bundle.putString("year", eModel.getYear());
        bundle.putString("id", eModel.eduID);

        addNewEdu addEdu = new addNewEdu();
        addEdu.setArguments(bundle);
        addEdu.show(activity.getSupportFragmentManager() , addEdu.getTag());


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        eduModel emodel = eduList.get(position); // to return correct item

        holder.degree.setText(emodel.getDegree());
        holder.uni.setText(emodel.getUniversity());
        holder.grade.setText(emodel.getGrade());
        holder.year.setText(emodel.getYear());
        System.out.println(emodel.getUniversity() +" "+emodel.getDegree());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are You Sure?").setTitle("Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteEdu(position);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editEdu(position);

            }
        });



    }

    @Override
    public int getItemCount() {
        return eduList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        EditText degree,uni,grade,year;
        Button edit,delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            degree = itemView.findViewById(R.id.edu_degree);
            uni = itemView.findViewById(R.id.edu_uni);
            grade = itemView.findViewById(R.id.edu_grade);
            year = itemView.findViewById(R.id.edu_year);
            edit = itemView.findViewById(R.id.edu_edit);
            delete = itemView.findViewById(R.id.edu_delete);
        }
    }

}
