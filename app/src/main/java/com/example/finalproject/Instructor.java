package com.example.finalproject;

import java.io.Serializable;

//@Entity
public class Instructor  implements Serializable {
//   @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String phoneNumber;
    String email;
    String city;
    String subject;
    double Latitude;
    double longitude ;

    String image_url;
    private final String task;


    public Instructor(String task,String name, String city, String subject, String email,String phoneNumber,  double Latitude, double longitude, String imageUrl) {

        this.task = task;
        this.name = name;
        this.city = city;
        this.subject = subject;
        this.phoneNumber = phoneNumber;
        this.email = email;
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
