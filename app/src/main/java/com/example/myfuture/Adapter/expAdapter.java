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
import com.example.myfuture.Model.expModel;
import com.example.myfuture.ProgPlan;
import com.example.myfuture.R;
import com.example.myfuture.SignInActivity;
import com.example.myfuture.Signup;
import com.example.myfuture.addNewExp;
import com.example.myfuture.experience;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class expAdapter extends RecyclerView.Adapter<expAdapter.MyViewHolder> {

    private List<expModel> exList;
    private experience activity;
    private FirebaseFirestore firestore;


    public expAdapter(experience exActivity , List<expModel> exList ){
        this.exList = exList;
        activity = exActivity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.each_exp, parent , false );
        firestore = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    public void deleteExp(int position){
        expModel eModel = exList.get(position);
        firestore.collection("Experiences").document(eModel.expID).delete();
        exList.remove(position);
        notifyItemRemoved(position);
    }

    public void editExp(int position){
        expModel eModel = exList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("company", eModel.getCompany());
        bundle.putString("job title", eModel.getJob());
        bundle.putString("description", eModel.getJobDes());
        bundle.putString("start date", eModel.getStartDate());
        bundle.putString("end date", eModel.getEndDate());
        bundle.putString("id", eModel.expID);

        addNewExp addExp = new addNewExp();
        addExp.setArguments(bundle);
        addExp.show(activity.getSupportFragmentManager() , addExp.getTag());


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        expModel emodel = exList.get(position); // to return correct item

        holder.company.setText(emodel.getCompany());
        holder.job.setText(emodel.getJob());
        holder.jobDes.setText(emodel.getJobDes());
        holder.startDate.setText(emodel.getStartDate());
        holder.endDate.setText(emodel.getEndDate());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are You Sure?").setTitle("Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteExp(position);
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

                editExp(position);

            }
        });



    }

    @Override
    public int getItemCount() {
        return exList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        EditText company,job,jobDes,startDate,endDate;
        Button edit,delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            company = itemView.findViewById(R.id.exp_company);
            job = itemView.findViewById(R.id.exp_job);
            jobDes = itemView.findViewById(R.id.exp_jobDescribe);
            startDate = itemView.findViewById(R.id.exp_startYear);
            endDate = itemView.findViewById(R.id.exp_endYear);
            edit = itemView.findViewById(R.id.exp_edit);
            delete = itemView.findViewById(R.id.exp_delete);
        }
    }

}
