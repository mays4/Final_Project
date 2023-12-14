package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class InstructorDetails extends AppCompatActivity{
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
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        send_email=findViewById(R.id.send_email);
        subject=findViewById(R.id.subject);

        details = (Instructor) getIntent().getSerializableExtra("details");
        if (details != null) {
            name.setText(details.name);
            city.setText(details.city);
            subject.setText(details.subject);
            phone.setText(details.phoneNumber);
            email.setText(details.email);


            Picasso
                    .get()
                    .load(details.image_url)
                    .into(logo);
        }
        send_email.setOnClickListener(view -> {

            Intent toEmailIntent = new Intent(InstructorDetails.this, EmailActivity.class);

            startActivity(toEmailIntent);
//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//                emailIntent.setData(Uri.parse("mailto:")); // Only email apps should handle this
//
//                // Add email address (optional)
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
//
//                // Add subject (optional)
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject of the email");
//
//                // Add body text (optional)
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body of the email");
//                startActivity(emailIntent);
//
////                 Verify that the device has an app to handle the intent
////                if (emailIntent.resolveActivity(getPackageManager()) != null) {
//////                     Start the email client activity
////
////                } else {
////                    // Handle the case where no email client is available
////                    Toast.makeText(getApplicationContext(), "No email app installed", Toast.LENGTH_SHORT).show();
////                }
//            }
        });


//        return false;
//        return false;
            }



}