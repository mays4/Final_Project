package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InstructorRecyclerAdapter   extends RecyclerView.Adapter<InstructorRecyclerAdapter.InstructorViewHolder>{



    interface InstructorListClickListener{
//        void onInstructorSelected(Instructor selectedInstructor);
        void onInstructorclicked(int i);
    }
    Context context;
    ArrayList<Instructor> list;
    InstructorListClickListener listener;

    public InstructorRecyclerAdapter(ArrayList<Instructor> list,Context context) {
        this.list = list;
        this.context = context;

    }
    public static class InstructorViewHolder extends RecyclerView.ViewHolder {
        public InstructorViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    @NonNull
    @Override
    public InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.insturctors_list_row,parent,false);
        return new InstructorViewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull InstructorRecyclerAdapter.InstructorViewHolder holder, int position) {
        TextView name =  holder.itemView.findViewById(R.id.instructor_name_row);
         ImageView instructor_logo = holder.itemView.findViewById(R.id.instructor_avatar_row);
          Button more_info = holder.itemView.findViewById(R.id.more_info);


        name.setText(list.get(position).name);
        
        Picasso
                .get()
                .load(list.get(position).image_url)
                .into(instructor_logo);
        // Setting up a click listener for the more_info button
        more_info.setOnClickListener(view -> {

            if (listener != null) {
                listener.onInstructorclicked(holder.getAdapterPosition());

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
