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
import com.example.myfuture.Model.proModel;
import com.example.myfuture.ProgPlan;
import com.example.myfuture.R;
import com.example.myfuture.SignInActivity;
import com.example.myfuture.Signup;
import com.example.myfuture.addNewPro;
import com.example.myfuture.project;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class proAdapter extends RecyclerView.Adapter<proAdapter.MyViewHolder> {

    private List<proModel> proList;
    private project activity;
    private FirebaseFirestore firestore;


    public proAdapter(project proActivity , List<proModel> proList ){
        this.proList = proList;
        activity = proActivity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.each_project, parent , false );
        firestore = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);
    }

    public void deletePro(int position){
        proModel pModel = proList.get(position);
        firestore.collection("Projects").document(pModel.proID).delete();
        proList.remove(position);
        notifyItemRemoved(position);
    }

    public void editPro(int position){
        proModel pModel = proList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("title", pModel.getTitle());
        bundle.putString("description", pModel.getDescribe());
        bundle.putString("date", pModel.getYear());
        bundle.putString("id", pModel.proID);

        addNewPro addPro = new addNewPro();
        addPro.setArguments(bundle);
        addPro.show(activity.getSupportFragmentManager() , addPro.getTag());


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        proModel pmodel = proList.get(position); // to return correct item
        holder.title.setText(pmodel.getTitle());
        holder.describe.setText(pmodel.getDescribe());
        holder.year.setText(pmodel.getYear());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are You Sure?").setTitle("Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deletePro(position);
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

                editPro(position);

            }
        });



    }

    @Override
    public int getItemCount() {
        return proList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        EditText title,describe,year;
        Button edit,delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.pro_title);
            describe = (EditText) itemView.findViewById(R.id.pro_des);
            year = itemView.findViewById(R.id.pro_year);
            edit = itemView.findViewById(R.id.pro_edit);
            delete = itemView.findViewById(R.id.pro_delete);
        }
    }

}
