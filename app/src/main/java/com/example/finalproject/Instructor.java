package com.example.finalproject;

import java.io.Serializable;
public class Instructor  implements Serializable {
    int id;
    String name,phoneNumber,email,city,subject,image_url;
    double Latitude,longitude;
    private final String task;

    public Instructor(String task,String name, String city, String subject, String email,String phoneNumber,  double Latitude, double longitude, String imageUrl) {

        this.task = task;
        this.name = name;
        this.city = city;
        this.subject = subject;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.Latitude = Latitude;
        this.longitude= longitude;
        this.image_url= imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return  Latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    private String  documentID;
    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getDocumentID() {
        return documentID;
    }


}
