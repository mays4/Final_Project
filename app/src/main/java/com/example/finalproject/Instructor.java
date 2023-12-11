package com.example.finalproject;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

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

    double lang;
    double lit;
    String image_url;
    private  String task;

    public Instructor(String task,String name, String city, String subject, String phoneNumber, String email, double lang, double lit, String imageUrl) {
 
        this.task = task;
        this.name = name;
        this.city = city;
        this.subject = subject;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.lang = lang;
        this.lit = lit;
        this.image_url= imageUrl;
    }

//    protected Instructor(Parcel in) {
//        name = in.readString();
//        phoneNumber = in.readString();
//        email = in.readString();
//        city = in.readString();
//        subject = in.readString();
//        image_url =in.readString();
//        if (in.readByte() == 0) {
//            lang = null;
//        } else {
//            lang = in.readDouble();
//        }
//        if (in.readByte() == 0) {
//            lit = null;
//        } else {
//            lit = in.readDouble();
//        }
//    }
//
//    public static final Creator<Instructor> CREATOR = new Creator<Instructor>() {
//        @Override
//        public Instructor createFromParcel(Parcel in) {
//            return new Instructor(in);
//        }
//
//        @Override
//        public Instructor[] newArray(int size) {
//            return new Instructor[size];
//        }
//    };




    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getSubject() {
        return subject;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getId() {
        return id;
    }

    public double getLang() {
        return lang;
    }

    public double getLit() {
        return lit;
    }

    public String getTask() {
        return task;
    }
//    @Override
//    public int describeContents() {
//        return 0;
//    }

//    @Override
//    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeString(phoneNumber);
//        dest.writeString(email);
//        dest.writeString(city);
//        dest.writeString(subject);
//        dest.writeString(image_url);
//        if (lang == null) {
//            dest.writeByte((byte) 0);
//        } else {
//            dest.writeByte((byte) 1);
//            dest.writeDouble(lang);
//        }
//        if (lit == null) {
//            dest.writeByte((byte) 0);
//        } else {
//            dest.writeByte((byte) 1);
//            dest.writeDouble(lit);
//        }
//    }

    private String  documentID;
    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getDocumentID() {
        return documentID;
    }


}
