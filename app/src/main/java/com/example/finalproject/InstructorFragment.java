package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class InstructorFragment extends DialogFragment {
    TextView name;
    ImageView instructor_logo;
    Button more_info;
    ArrayList<Instructor> list;
    InstructorRecyclerAdapter.InstructorListClickListener listener;

    public InstructorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the list from the arguments
//        if (getArguments() != null) {
//            list = getArguments().<Instructor>getParcelableArrayList("instructor");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.insturctors_list_row, container, false);

        name = view.findViewById(R.id.instructor_name_row);
        instructor_logo = view.findViewById(R.id.instructor_avatar_row);
        more_info = view.findViewById(R.id.more_info);

        // Check if the list is not null and has at least one item
//        if (list != null && !list.isEmpty()) {
//            // Set data for the fragment based on the list
           name.setText(list.get(0).getName());  // Assuming you want the first item's name
//
//            // Use Picasso to load the image into the ImageView
            Picasso
                    .get()
                    .load(list.get(0).image_url);
//                    .into(instructor_logo);
//        }

        // Set up a click listener for the more_info button
        more_info.setOnClickListener(v -> {
            // Perform the action you want when the button is clicked
            // You can use the listener to handle the click event or any other logic
            if (listener != null) {
                listener.onInstructorclicked(0);  // Assuming you want to pass the position 0
            }
        });

        return view;
    }


}
