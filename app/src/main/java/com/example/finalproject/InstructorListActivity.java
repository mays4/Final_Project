package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class InstructorListActivity extends AppCompatActivity implements InstructorRecyclerAdapter.InstructorListClickListener,
FireStoreManager.FireStoreListener{

    ArrayList<Instructor>instructorsList;
    RecyclerView instructorViewList;
    FireStoreManager fireStoreManager;

    InstructorRecyclerAdapter instructorRecyclerAdapter;
    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_list);

        instructorViewList = findViewById(R.id.recyclerlist);
        instructorsList = ((MyApp) getApplication()).listOfInstructors;

        instructorRecyclerAdapter = new InstructorRecyclerAdapter(instructorsList, this);
        instructorRecyclerAdapter.listener = this; // step 4
        instructorViewList.setAdapter(instructorRecyclerAdapter);
        fireStoreManager = new FireStoreManager();
        fireStoreManager.listener = this;

        instructorViewList.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NotNull RecyclerView recyclerView, RecyclerView.@NotNull ViewHolder viewHolder, RecyclerView.@NotNull ViewHolder target) {
                        return false;
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int position = viewHolder.getAdapterPosition();

                        AlertDialog.Builder builder = new AlertDialog.Builder(InstructorListActivity.this,R.style.AlertDialogStyle);
                        builder.setMessage("are you sure you want to delete this instructor").
                                setPositiveButton(getString(R.string.ok), (dialog, which) -> {

                            fireStoreManager.deleteInstructor(instructorsList.get(position));

                            instructorsList.remove(position);

                            instructorRecyclerAdapter.notifyDataSetChanged();


                        }).setNegativeButton(getString(R.string.no),(dialog, which) -> instructorRecyclerAdapter.notifyDataSetChanged());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(instructorViewList);


        instructorRecyclerAdapter = new InstructorRecyclerAdapter(instructorsList, this);
        instructorRecyclerAdapter.listener = this;

        instructorViewList.setAdapter(instructorRecyclerAdapter);
        fireStoreManager.getAllInstructors();




    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void FireStoreMangerFinishWithListOfInstructors(ArrayList<Instructor> list2) {


    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void FireStoreMangerFinishUpdating(boolean statues) {



    }


    @Override
    public void onInstructorclicked(int i) {

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onInstructorButtonclicked(int i) {
        Intent toInstructorDetails = new Intent(this, InstructorDetailsActivity.class);

        Instructor selectedInstructor = instructorsList.get(i);
        toInstructorDetails.putExtra("details", selectedInstructor);
        startActivity(toInstructorDetails);
        instructorRecyclerAdapter.notifyDataSetChanged();
    }
}