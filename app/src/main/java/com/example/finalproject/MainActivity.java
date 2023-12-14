package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,InstructorRecyclerAdapter.InstructorListClickListener, FireStoreManager.FireStoreListener {
    MapView map;
   SearchView searchView;

    FireStoreManager fireStoreManager;
    ArrayList<Instructor> instructorData;

    RecyclerView instructorRecyclerView;
    InstructorRecyclerAdapter  adapter;

//    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
//        if (result) {
//            Toast.makeText(MainActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
//        }
//    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instructorData = ((MyApp)getApplication()).listOfInstructors;
//        filteredInstructorData= new ArrayList<>();
//        filteredInstructorData = ((MyApp)getApplication()).listOfInstructors;
        searchView = findViewById(R.id.search_view);
        searchView.setQueryHint("search near by instructor");
        instructorRecyclerView = findViewById(R.id.instructor_list);
        adapter = new InstructorRecyclerAdapter(instructorData, this);
        adapter.listener = this; // step 4
        instructorRecyclerView.setAdapter(adapter);
        FirebaseApp.initializeApp(this);
        fireStoreManager  = new FireStoreManager();
//        fireStoreManager=((MyApp)getApplication()).fireStoreManager;
        fireStoreManager.listener = this;
        instructorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InstructorRecyclerAdapter(instructorData, this);
        adapter.listener = this; // step 4
        instructorRecyclerView.setAdapter(adapter);
//        FirebaseApp.initializeApp(this);
//
//        fireStoreManager.listener = this;
//       instructorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fireStoreManager.getAllInstructors();
//        Button addButton = findViewById(R.id.submit);

//        addButton.setOnClickListener(view -> fireStoreManager.getAllInstructors());





        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                performSearch(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle the search query text change here (optional)
                return false;
            }
    });



        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        map = findViewById(R.id.map);
        map.getTileProvider().clearTileCache();
        Configuration.getInstance().setCacheMapTileCount((short) 12);
        Configuration.getInstance().setCacheMapTileOvershoot((short) 12);

        // Create a custom tile source
        map.setTileSource(new OnlineTileSourceBase("", 1, 20, 512, ".png",
                new String[]{"https://a.tile.openstreetmap.org/"}) {
            @Override
            public String getTileURLString(long pMapTileIndex) {
                return getBaseUrl()
                        + MapTileIndex.getZoom(pMapTileIndex)
                        + "/" + MapTileIndex.getX(pMapTileIndex)
                        + "/" + MapTileIndex.getY(pMapTileIndex)
                        + mImageFilenameEnding;
            }
        });




       map.setMultiTouchControls(true);
        map.setTileSource(TileSourceFactory.MAPNIK);
        IMapController mapController = map.getController();
        GeoPoint startPoint = new GeoPoint(43.70, -79.42);

        mapController.setZoom(10.0);
        mapController.setCenter(startPoint);
        addMarkerForCity("New York","john");
        map.setTileSource(TileSourceFactory.MAPNIK);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable customMarkerDrawable = getResources().getDrawable(R.drawable.baseline_location_on_24);
        Marker torontoMarker = new Marker(map);
        torontoMarker.setPosition(startPoint);
        torontoMarker.setIcon(customMarkerDrawable);
//        torontoMarker.setAnchor(Marker.ANCHOR_TOP, Marker.ANCHOR_BOTTOM);
        torontoMarker.setTitle("Toronto");
        map.getOverlays().add(torontoMarker);
//        return false;
    }

    private void addMarkerForCity(String cityName,String name) {
        GeoPoint cityLocation = getCityLocation(cityName);
        if (cityLocation != null) {
            createMarker(cityLocation, cityName,name);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private GeoPoint getCityLocation(String cityName) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(cityName, 1);
            assert addresses != null;
            if (!addresses.isEmpty()) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                return new GeoPoint(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void createMarker(GeoPoint position, String title, String name) {
        if (map == null) {
            return;
        }
        @SuppressLint("UseCompatLoadingForDrawables") Drawable customMarkerDrawable = getResources().getDrawable(R.drawable.baseline_location_on_24);
//        Marker marker = new Marker(map);
        Marker customMarker = new Marker(map);
        customMarker.setPosition(position);
        customMarker.setIcon(customMarkerDrawable);
//        customMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        customMarker.setTitle(title + "mame");
        customMarker.setPanToView(true);
        map.getOverlays().add(customMarker );
        map.invalidate();



// Create a custom marker with the custom drawable



//        customMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//        customMarker.setTitle("Custom Marker");

// Add the custom marker to the map
        map.getOverlays().add(customMarker);
        map.invalidate(); // Refresh the map
    }

            private void performSearch(String query) {

                if (!query.trim().isEmpty()) {

                    // Filter data based on the entered city
                    ArrayList<Instructor> filteredInstructors = new ArrayList<>();
                    for (Instructor instructor : instructorData) {
                        // Assuming you have a method getCity() in your Instructor class
                        if (instructor.getCity().toLowerCase().contains(query.toLowerCase())) {
                            filteredInstructors.add(instructor);
                        }
                    }
                    instructorData = filteredInstructors;

                    // Update the map or list with the filtered data
                    updateMapOrList(filteredInstructors);
                } else {
                    // If the query is empty, show all instructors
                    updateMapOrList(instructorData);
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            private void updateMapOrList(ArrayList<Instructor> updatedData) {
                ((MyApp) getApplication()).listOfInstructors = updatedData;
                adapter = new InstructorRecyclerAdapter(updatedData, MainActivity.this);
                adapter.listener = MainActivity.this;
                instructorRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }



    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void FireStoreMangerFinishWithListOfInstructors(ArrayList<Instructor> list2) {
        instructorData = list2;
        ((MyApp) getApplication()).listOfInstructors = instructorData;
        adapter = new InstructorRecyclerAdapter(instructorData, this);
        adapter.listener = this;
        instructorRecyclerView.setAdapter(adapter);
        for (Instructor instructor : instructorData) {
            GeoPoint instructorLocation = new GeoPoint(instructor.getLang(), instructor.getLit());
            createMarker(instructorLocation, instructor.getName(),instructor.getCity());

        }

        adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed

    }



    @Override
    public void FireStoreMangerFinishUpdating(boolean statues) {
        if (statues)
            fireStoreManager.getAllInstructors();
    }



    @Override
    public void onClick(View v) {

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onInstructorclicked(int i) {
        Intent toInstructorDetails = new Intent(this, InstructorDetails.class);

        Instructor selectedInstructor = instructorData.get(i);
        toInstructorDetails.putExtra("details", selectedInstructor);
        startActivity(toInstructorDetails);
        adapter.notifyDataSetChanged();

    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.instrouctorsList:
                Intent toInstructorList = new Intent(this, InstructorList.class);


                startActivity(toInstructorList);
//
//                InstructorFragment instructorFragment = new InstructorFragment();
//                instructorFragment.listener = MainActivity.this;
//                instructorFragment.show(getSupportFragmentManager(), "tag");
//                InstructorFragment instructorFragment = new InstructorFragment();
//                Instructor selectedInstructor = instructorData.get(0);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("instructor", (Parcelable) selectedInstructor);
//                instructorFragment.setArguments(bundle);
//
//                // Replace the current fragment with the new one
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frame_layout, instructorFragment)
//                        .commit();


//                fragment.listener = MainActivity.this;



//                res= fm.getResult(this).split("-");
//                int ans=Integer.parseInt(res[0].trim());
//                int q= Integer.parseInt(res[1].trim());
//                int att = Integer.parseInt(res[2].trim());
//
//                AlertFragment alertFragment = AlertFragment.newInstance(ans,q,att );
//                alertFragment.show(getSupportFragmentManager(), "AlertFragmentTag");
                return  true;

            case R.id.add_instructor:


//                showAlertDialogHowManyQuestions();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        instructorData = ((MyApp)getApplication()).listOfInstructors;
    }
}

