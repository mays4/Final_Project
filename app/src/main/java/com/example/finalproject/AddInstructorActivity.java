package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddInstructorActivity extends AppCompatActivity implements View.OnClickListener, FireStoreManager.FireStoreListener{
  EditText name, city,subject,longitude,latitude,phone,email,image_url;

  Button send;
  FireStoreManager fireStoreManager;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);
        name = findViewById(R.id.add_name);
        city=findViewById(R.id.add_city);
        subject = findViewById(R.id.add_subject);
        latitude=findViewById(R.id.add_lat);
        longitude=findViewById(R.id.add_long);
         email=findViewById(R.id.add_email);
        phone = findViewById(R.id.add_phone);
         image_url=findViewById(R.id.add_url);
         send=  findViewById(R.id.send_info);
        fireStoreManager = new FireStoreManager();
        fireStoreManager.listener=this;
        send.setOnClickListener(view -> {
            String instructorName = name.getText().toString();
            String instructorCity = city.getText().toString();
            String instructorSubject = subject.getText().toString();
            String instructorEmail = email.getText().toString();
            String instructorPhone = phone.getText().toString();
            double instructorLat = Double.parseDouble(latitude.getText().toString());
            double instructorLang = Double.parseDouble(longitude.getText().toString());
            String instructorImageUrl = image_url.getText().toString();

            fireStoreManager.addInstructor(
                    instructorName,
                    instructorCity,
                    instructorSubject,
                    instructorEmail,
                    instructorPhone,
                    instructorLat,
                    instructorLang,
                    instructorImageUrl
            );
            FireStoreMangerFinishUpdating(true);
            Intent tomainIntent = new Intent(this, MainActivity.class);
            startActivity(tomainIntent);



        });
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public void FireStoreMangerFinishWithListOfInstructors(ArrayList<Instructor> list) {

    }

    @Override
    public void FireStoreMangerFinishUpdating(boolean statues) {
        if (statues)
            fireStoreManager.getAllInstructors();

    }
}