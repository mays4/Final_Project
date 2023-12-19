package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class InstructorDetailsActivity extends AppCompatActivity{
TextView name,city,phone,email,subject;
ImageView logo;
Button send_email;
Instructor details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_details);
        name=findViewById(R.id.name);
        logo = findViewById(R.id.logo);
        city=findViewById(R.id.city);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        send_email=findViewById(R.id.send_email);
        subject=findViewById(R.id.subject);
        if (savedInstanceState != null) {
            details = (Instructor) getIntent().getSerializableExtra("details");
        }

        details = (Instructor) getIntent().getSerializableExtra("details");
        if (details != null) {
            name.setText(details.name);
            city.setText(details.city);
            subject.setText(details.subject);
            email.setText(details.email);
            phone.setText(details.phoneNumber);

            Picasso
                    .get()
                    .load(details.image_url)
                    .into(logo);
        }
        send_email.setOnClickListener(view -> {

            Intent toEmailIntent = new Intent(InstructorDetailsActivity.this, EmailActivity.class);

            toEmailIntent .putExtra("details",details);
            startActivity(toEmailIntent);


        });

            }


    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("details", details);
    }
//
}