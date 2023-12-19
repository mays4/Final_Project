package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EmailActivity extends AppCompatActivity {

    EditText editTextTo,editTextSubject,editTextMessage;
    Button send,cancel;
    Instructor details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);


        editTextTo=findViewById(R.id.editText1);
        editTextSubject=findViewById(R.id.editText2);
        editTextMessage=findViewById(R.id.editText3);
        send=findViewById(R.id.button1);
        cancel=findViewById(R.id.button2);
        details = (Instructor) getIntent().getSerializableExtra("details");

        send.setOnClickListener(arg0 -> {
            String to=editTextTo.getText().toString();
            String subject=editTextSubject.getText().toString();
            String message=editTextMessage.getText().toString();


            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);

            email.setType("message/rfc822");


            startActivity(Intent.createChooser(email, "Choose an Email client :"));
            editTextTo.getText().clear();
            editTextSubject.getText().clear();
            editTextMessage.getText().clear();

        });
        cancel.setOnClickListener(view -> {

            Intent toDetailsIntent = new Intent(this, InstructorDetailsActivity.class);

            toDetailsIntent  .putExtra("details",details);
            startActivity(toDetailsIntent );


        });

    }





}
