package com.example.finalproject;


import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FireStoreManager {

    interface FireStoreListener{
        void FireStoreMangerFinishWithListOfInstructors(ArrayList<Instructor> list);
       void FireStoreMangerFinishUpdating(boolean statues);
    }
    FireStoreListener listener;
    FirebaseFirestore db =FirebaseFirestore.getInstance();



void deleteInstructor(Instructor instructorDelete){
    MyApp.executorService.execute(() -> {
        String id =  instructorDelete.getDocumentID();
        DocumentReference docRef = db.collection("Instructor").document(id);
        docRef.delete();

    });

}


    void addInstructor(String name, String city, String subject, String phoneNumber, String email,  double latitude, double longitude, String imageUrl) {
        {
            Map<String, Object> instructorData = new HashMap<>();
            instructorData.put("name", name);
            instructorData.put("city", city);
            instructorData.put("subject", subject);
            instructorData.put("email", email);
            instructorData.put("phone", phoneNumber);
            instructorData.put("latitude",latitude);
            instructorData.put("longitude",longitude);
            instructorData.put("image_url", imageUrl);

            db.collection("Instructor")
                    .add(instructorData)
                    .addOnSuccessListener(documentReference -> {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        listener.FireStoreMangerFinishUpdating(true); // Notify the listener about the success
                    })
                    .addOnFailureListener(e -> {
                        Log.w("tag", "Error adding document", e);
                        listener.FireStoreMangerFinishUpdating(false); // Notify the listener about the failure
                    });
        }
    }


    void getAllInstructors() {
        ArrayList<Instructor> listFromFireStore = new ArrayList<>(0);
        MyApp.executorService.execute(() -> db.collection("Instructor")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            String city = document.getString("city");
                            String subject = document.getString("subject");
                            String email = document.getString("email");
                            String phone = document.getString("phone");
                            Double latitude = document.getDouble("latitude");
                            Double longitude = document.getDouble("longitude");
                            String imageUrl = document.getString("image_url");



                            Instructor instructor = new Instructor((String) document.get("task"),
                                    name,
                                    city,
                                    subject,
                                    email,
                                    phone,
                                    latitude != null ? latitude: 0.0,
                                    longitude != null ? longitude : 0.0,
                                    imageUrl !=null ? imageUrl :"");

                            instructor.setDocumentID(document.getId());
                            listFromFireStore.add(instructor);
                        }

                        MyApp.mainLooperHandler.post(() -> listener.FireStoreMangerFinishWithListOfInstructors(listFromFireStore));

                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                }));
    }

}
