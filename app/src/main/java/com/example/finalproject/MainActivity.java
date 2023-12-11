package com.example.finalproject;
import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;
import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.LocationPuck2D;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,InstructorRecyclerAdapter.InstructorListClickListener, FireStoreManager.FireStoreListener {

        private MapView mapView;
       SearchView searchView;
        FloatingActionButton floatingActionButton;
       FireStoreManager fireStoreManager;
      ArrayList<Instructor> instructorData;
    RecyclerView instructorRecyclerView;
    InstructorRecyclerAdapter  adapter;


        private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
                if (result) {
                        Toast.makeText(MainActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
                }
        });

        private final OnIndicatorBearingChangedListener onIndicatorBearingChangedListener = new OnIndicatorBearingChangedListener() {
                @Override
                public void onIndicatorBearingChanged(double v) {
                        mapView.getMapboxMap().setCamera(new CameraOptions.Builder().bearing(v).build());
                }
        };

        private final OnIndicatorPositionChangedListener onIndicatorPositionChangedListener = new OnIndicatorPositionChangedListener() {
                @Override
                public void onIndicatorPositionChanged(@NonNull Point point) {
                        mapView.getMapboxMap().setCamera(new CameraOptions.Builder().center(point).zoom(10.0).build());
                        getGestures(mapView).setFocalPoint(mapView.getMapboxMap().pixelForCoordinate(point));
                }
        };

        private final OnMoveListener onMoveListener = new OnMoveListener() {
                @Override
                public void onMoveBegin(@NonNull MoveGestureDetector moveGestureDetector) {
                        getLocationComponent(mapView).removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                        getLocationComponent(mapView).removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                        getGestures(mapView).removeOnMoveListener(onMoveListener);
                        floatingActionButton.show();
                        addMarkersToMap();

                }
            private void addMarkersToMap() {
                if (instructorData != null && !instructorData.isEmpty()) {
                    for (Instructor instructor : instructorData) {
                        double latitude = instructor.getLang();
                        double longitude = instructor.getLit();
                        String title = instructor.getName();
                        addMarker(latitude, longitude, title);
                    }
                }
            }

            private void addMarker(double latitude, double longitude, String title) {
//                mapView.getMapboxMap().addMarker(new MarkerOptions()
//                        .position(Point.fromLngLat(longitude, latitude))
//                        .title(title));
            }

                @Override
                public boolean onMove(@NonNull MoveGestureDetector moveGestureDetector) {
                        return false;
                }

                @Override
                public void onMoveEnd(@NonNull MoveGestureDetector moveGestureDetector) {
                    Point centerPoint = mapView.getMapboxMap().getCameraState().getCenter();
                    Log.d("MapMove", "Center Point: " + centerPoint.latitude() + ", " + centerPoint.longitude());
                }
        };

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
            instructorData = ((MyApp)getApplication()).listOfInstructors;

                mapView = findViewById(R.id.mapView);
                 searchView = findViewById(R.id.search_view);
            floatingActionButton = findViewById(R.id.focusLocation);

            searchView.setQueryHint("search near by instructor");
            instructorRecyclerView = findViewById(R.id.instructor_list);

            adapter = new InstructorRecyclerAdapter(instructorData, this);
            adapter.listener = this; // step 4
            instructorRecyclerView.setAdapter(adapter);
            FirebaseApp.initializeApp(this);
            fireStoreManager  = new FireStoreManager();
            fireStoreManager.listener = this;
            instructorRecyclerView.setLayoutManager(new LinearLayoutManager(this));



            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Handle the search query submission here
                    performSearch(query);
                    return true;
                }

                private void performSearch(String query) {

                    Log.d("name",query);
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Handle the search query text change here (optional)
                    return false;
                }
            });
            Button addButton = findViewById(R.id.submit);

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fireStoreManager.getAllInstructors();

                }
            });


                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }

                floatingActionButton.hide();

                mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, style -> {
                        mapView.getMapboxMap().setCamera(new CameraOptions.Builder().zoom(10.0).build());
                        LocationComponentPlugin locationComponentPlugin = getLocationComponent(mapView);
                        locationComponentPlugin.setEnabled(true);
                        LocationPuck2D locationPuck2D = new LocationPuck2D();
                    locationPuck2D.setBearingImage(ContextCompat.getDrawable(this,  R.drawable.baseline_location_on_24));
                        locationComponentPlugin.setLocationPuck(locationPuck2D);
                        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                        getGestures(mapView).addOnMoveListener(onMoveListener);

                        floatingActionButton.setOnClickListener(view -> {
                                locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
                                locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
                                getGestures(mapView).addOnMoveListener(onMoveListener);
                                floatingActionButton.hide();
                        });
                });
        }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void FireStoreMangerFinishWithListOfInstructors(ArrayList<Instructor> list2) {
        instructorData = list2;
        ((MyApp) getApplication()).listOfInstructors = instructorData;
        adapter = new InstructorRecyclerAdapter(instructorData, this);
        adapter.listener = this;
        instructorRecyclerView.setAdapter(adapter);

//        adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed

    }



    @Override
    public void FireStoreMangerFinishUpdating(boolean statues) {
        if (statues)
            fireStoreManager.getAllInstructors();
    }

//    @Override
//    public void onInstructorSelected(Instructor selectedInstructor) {
//
//    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onInstructorclicked(int i) {
        Intent toInstructorDetails = new Intent(this, InstructorDetails.class);

        //  the details of the selected item in the Intent as an extra
        Instructor selectedInstructor = instructorData.get(i);
        toInstructorDetails.putExtra("details", selectedInstructor );
        startActivity(toInstructorDetails);
    }


}







