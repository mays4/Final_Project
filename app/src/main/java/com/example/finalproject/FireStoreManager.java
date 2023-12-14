package com.example.finalproject;


import android.util.Log;

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
//    public FireStoreManager ( ) {
//        if (this.db == null)
//
//            this.db = FirebaseFirestore.getInstance();
//
//
//    }
//    FirebaseStorage storage = FirebaseStorage.getInstance();
//
//    StorageReference storageRef = storage.getReference();




//    final StorageReference ref = storageRef.child("images/current-song.png");
//
//    private Uri file;
//    UploadTask uploadTask = ref.putFile(file);
//
//
//    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//        @Override
//        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//            if (!task.isSuccessful()) {
//                throw Objects.requireNonNull(task.getException());
//            }
//
//            // Continue with the task to get the download URL
//            return ref.getDownloadUrl();
//        }
//    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//        @Override
//        public void onComplete(@NonNull Task<Uri> task) {
//            if (task.isSuccessful()) {
//                Uri downloadUri = task.getResult();
//                Log.d("test", String.valueOf(downloadUri));
//            } else {
//                // Handle failures
//                // ...
//            }
//        }
//    });





    void addInstructor() {
        Map<String, Object> user = new HashMap<>();
        user.put("name", "Ada");
        user.put("city", "toronto");
        db.collection("Instructor")
                .add(user)
                .addOnSuccessListener(documentReference -> Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w("tag", "Error adding document", e));
    }



    void getAllInstructors() {
        ArrayList<Instructor> listFromFireStore = new ArrayList<>(0);
        MyApp.executorService.execute(() -> db.collection("Instructor")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("FirebaseData", "Image URL: " + document.getString("image_url"));
                            Log.d("FirebaseData", "Location1: " + document.getDouble("latitude"));
                            Log.d("FirebaseData", "Location2: " + document.getDouble("longitude"));

                            // Handle potential null values or missing keys
                            String name = document.getString("name");
                            String city = document.getString("city");
                            String subject = document.getString("subject");
                            String email = document.getString("email");
                            String phone = document.getString("phone");
                            Double latitude = document.getDouble("latitude");
                            Double longitude = document.getDouble("longitude");
                            String imageUrl = document.getString("image_url");

                            // Log the values to check if any are null
                            Log.d("FirebaseData", "Name: " + name);
                            Log.d("FirebaseData", "lat: " + latitude);
                            Log.d("FirebaseData", "lag: " + longitude);
                            // ... log other fields similarly ...

                            Instructor instructor = new Instructor((String) document.get("task"),
                                    name,
                                    city,
                                    subject,
                                    email,
                                    phone,
                                    latitude != null ? latitude : 0.0, // Set default value or handle accordingly
                                    longitude != null ? longitude : 0.0, // Set default value or handle accordingly
                                    imageUrl);

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
