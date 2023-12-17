
package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements

        InstructorRecyclerAdapter.InstructorListClickListener,
        FireStoreManager.FireStoreListener {

    MapView map;
    SearchView searchView;
    FireStoreManager fireStoreManager;
    ArrayList<Instructor> instructorData;
    RecyclerView instructorRecyclerView;
    InstructorRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instructorData = ((MyApp) getApplication()).listOfInstructors;
        searchView = findViewById(R.id.search_view);

        searchView.setQueryHint("Search nearby instructor");
        instructorRecyclerView = findViewById(R.id.instructor_list);
        adapter = new InstructorRecyclerAdapter(instructorData, this);
        adapter.listener = this;
        instructorRecyclerView.setAdapter(adapter);

        FirebaseApp.initializeApp(this);
        fireStoreManager = new FireStoreManager();
        fireStoreManager.listener = this;
        instructorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fireStoreManager.getAllInstructors();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onQueryTextSubmit(String query) {


//                int searchTextColor = getResources().getColor(android.R.color.black);



                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            // Clear the search and return to the original list
            addMarkersForInstructors(instructorData);
            updateMapOrList(instructorData);
            return false;
        });

        // Initialize the map
        initializeMap();

        // Add markers for all instructors
        addMarkersForInstructors(instructorData);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.instrouctorsList:
                Intent toInstructorList = new Intent(this, InstructorList.class);
                startActivity(toInstructorList);
                return true;

            case R.id.add_instructor:
                Intent toAddInstructor = new Intent(this, AddInstructorActivity.class);
                startActivity(toAddInstructor);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeMap() {
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        map = findViewById(R.id.map);
        map.getTileProvider().clearTileCache();
        Configuration.getInstance().setCacheMapTileCount((short) 12);
        Configuration.getInstance().setCacheMapTileOvershoot((short) 12);

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
        GeoPoint startPoint = new GeoPoint(43.7716, -79.5081);
        mapController.setZoom(8.0);
        mapController.setCenter(startPoint);
        addMarkersForInstructors(instructorData);
    }

    private void addMarkersForInstructors(List<Instructor> instructors) {
        for (Instructor instructor : instructors) {
            GeoPoint instructorLocation = new GeoPoint(instructor.getLatitude(), instructor.getLongitude());
            createMarker(instructorLocation, instructor.getName(), instructor.getCity());
        }
    }

    private void createMarker(GeoPoint position, String title, String name) {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable customMarkerDrawable = getResources().getDrawable(R.drawable.baseline_location_on_24);
        Marker customMarker = new Marker(map);
        customMarker.setPosition(position);
        customMarker.setIcon(customMarkerDrawable);
        customMarker.setTitle(title + " - " + name);
        map.getOverlays().add(customMarker);
    }

    private void performSearch(String query) {
        if (!query.trim().isEmpty()) {
            ArrayList<Instructor> filteredInstructors = new ArrayList<>();
            for (Instructor instructor : instructorData) {
                if (instructor.getCity().toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))) {
                    filteredInstructors.add(instructor);
                }
            }
            map.getOverlays().clear();

            // Add markers for filtered instructors
            addMarkersForInstructors(filteredInstructors);

            updateMapOrList(filteredInstructors);

            // Zoom to the first filtered instructor's location
            if (!filteredInstructors.isEmpty()) {
                Instructor firstInstructor = filteredInstructors.get(0);
                GeoPoint firstInstructorLocation = new GeoPoint(firstInstructor.getLatitude(), firstInstructor.getLongitude());
                map.getController().animateTo(firstInstructorLocation);
                map.getController().setZoom(18.0);
            }
        } else {
            // If the query is empty, show all instructors
            map.getOverlays().clear();
            addMarkersForInstructors(instructorData);
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
            GeoPoint instructorLocation = new GeoPoint(instructor.getLatitude(), instructor.getLongitude());
            createMarker(instructorLocation, instructor.getName(), instructor.getCity());
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void FireStoreMangerFinishUpdating(boolean statues) {
        if (statues)
            fireStoreManager.getAllInstructors();
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

    @Override
    protected void onResume() {
        super.onResume();
        instructorData = ((MyApp) getApplication()).listOfInstructors;
    }
}
