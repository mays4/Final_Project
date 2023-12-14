package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InstructorList extends AppCompatActivity implements InstructorRecyclerAdapter.InstructorListClickListener,
FireStoreManager.FireStoreListener{

    ArrayList<Instructor>instructorsList;
    RecyclerView instructorViewList;
    FireStoreManager fireStoreManager;

    InstructorRecyclerAdapter instructorRecyclerAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_list);
        Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();

     instructorViewList= findViewById(R.id.recyclerlist);
       instructorsList = ((MyApp) getApplication()).listOfInstructors;

        instructorRecyclerAdapter = new InstructorRecyclerAdapter(instructorsList, this);
        instructorRecyclerAdapter.listener = this; // step 4
        instructorViewList.setAdapter( instructorRecyclerAdapter);
        fireStoreManager= new FireStoreManager();
        fireStoreManager.listener = this;

        instructorViewList.setLayoutManager(new LinearLayoutManager(this));

        instructorRecyclerAdapter = new InstructorRecyclerAdapter(instructorsList, this);
        instructorRecyclerAdapter.listener = this; // step 4

        instructorViewList.setAdapter(instructorRecyclerAdapter);
        fireStoreManager.getAllInstructors();



    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void FireStoreMangerFinishWithListOfInstructors(ArrayList<Instructor> list2) {
        instructorsList = list2;
        ((MyApp) getApplication()).listOfInstructors = instructorsList;
        // check if duplicated
//        instructorRecyclerAdapter = new InstructorRecyclerAdapter(instructorsList, this);
       instructorRecyclerAdapter.listener = this;
        instructorViewList.setAdapter(instructorRecyclerAdapter);


        instructorRecyclerAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed

    }

    @Override
    public void FireStoreMangerFinishUpdating(boolean statues) {

    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onInstructorclicked(int i) {
        Intent toInstructorDetails = new Intent(this, InstructorDetails.class);

        Instructor selectedInstructor = instructorsList.get(i);
        toInstructorDetails.putExtra("details", selectedInstructor);
        startActivity(toInstructorDetails);
        instructorRecyclerAdapter.notifyDataSetChanged();

    }
}