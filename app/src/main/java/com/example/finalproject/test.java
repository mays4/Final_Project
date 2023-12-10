//package com.example.finalproject;
//
//import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;
//import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.content.res.AppCompatResources;
//import androidx.core.app.ActivityCompat;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.mapbox.android.gestures.MoveGestureDetector;
//import com.mapbox.geojson.Feature;
//import com.mapbox.geojson.LineString;
//import com.mapbox.geojson.Point;
//import com.mapbox.maps.CameraOptions;
//import com.mapbox.maps.MapView;
//import com.mapbox.maps.Style;
//import com.mapbox.maps.extension.style.layers.generated.FillLayer;
//import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;
//import com.mapbox.maps.plugin.LocationPuck2D;
//import com.mapbox.maps.plugin.gestures.OnMoveListener;
//import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
//import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
//import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//    private MapView mapView;
//    FloatingActionButton floatingActionButton;
//
//    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
//        @Override
//        public void onActivityResult(Boolean result) {
//            if (result) {
//                Toast.makeText(MainActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    });
//
//    private final OnIndicatorBearingChangedListener onIndicatorBearingChangedListener = new OnIndicatorBearingChangedListener() {
//        @Override
//        public void onIndicatorBearingChanged(double v) {
//            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().bearing(v).build());
//        }
//    };
//
//    private final OnIndicatorPositionChangedListener onIndicatorPositionChangedListener = new OnIndicatorPositionChangedListener() {
//        @Override
//        public void onIndicatorPositionChanged(@NonNull Point point) {
//            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().center(point).zoom(20.0).build());
//            getGestures(mapView).setFocalPoint(mapView.getMapboxMap().pixelForCoordinate(point));
//        }
//    };
//
//    private final OnMoveListener onMoveListener = new OnMoveListener() {
//        @Override
//        public void onMoveBegin(@NonNull MoveGestureDetector moveGestureDetector) {
//            getLocationComponent(mapView).removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
//            getLocationComponent(mapView).removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
//            getGestures(mapView).removeOnMoveListener(onMoveListener);
//            floatingActionButton.show();
//        }
//
//        @Override
//        public boolean onMove(@NonNull MoveGestureDetector moveGestureDetector) {
//            return false;
//        }
//
//        @Override
//        public void onMoveEnd(@NonNull MoveGestureDetector moveGestureDetector) {
//
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mapView = findViewById(R.id.mapView);
//        floatingActionButton = findViewById(R.id.focusLocation);
//
//        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//
//        floatingActionButton.hide();
////        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
////        mapView.getMapboxMap().loadStyleUri("mapbox://styles/mapbox/streets-v11", new Style.OnStyleLoaded() {
////
////
////        @Override
////            public void onStyleLoaded(@NonNull Style style) {
////                mapView.getMapboxMap().setCamera(new CameraOptions.Builder().zoom(10.0).build());
////                LocationComponentPlugin locationComponentPlugin = getLocationComponent(mapView);
////                locationComponentPlugin.setEnabled(true);
////                LocationPuck2D locationPuck2D = new LocationPuck2D();
////                locationPuck2D.setBearingImage(AppCompatResources.getDrawable(MainActivity.this, R.drawable.baseline_location_on_24));
////                locationComponentPlugin.setLocationPuck(locationPuck2D);
////                locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
////                locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
////                getGestures(mapView).addOnMoveListener(onMoveListener);
////
////                floatingActionButton.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
////                        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
////                        getGestures(mapView).addOnMoveListener(onMoveListener);
////                        floatingActionButton.hide();
////                    }
////                });
////            }
////        });
////    }
//        mapView.getMapboxMap().getStyle(new Style.OnStyleLoaded() {
//            @Override
//            public void onStyleLoaded(@NonNull Style style) {
//
//                // Create a list to store our line coordinates.
//                List routeCoordinates = new ArrayList<Point>();
//                routeCoordinates.add(Point.fromLngLat(-118.394391, 33.397676));
//                routeCoordinates.add(Point.fromLngLat(-118.370917, 33.391142));
//
//                // Create the LineString from the list of coordinates and then make a GeoJSON FeatureCollection so that you can add the line to our map as a layer.
//
//                LineString lineString = LineString.fromLngLats(routeCoordinates);
//
//                Feature feature = Feature.fromGeometry(lineString);
//
////                GeoJsonSource geoJsonSource = new GeoJsonSource("geo-json-source", feature);
////                style.addStyleSource(geoJsonSource);
//            }
//        });
//    }}

//package com.example.finalproject;
//
//import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;
//import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;
//
//import android.Manifest;
//
//import android.content.pm.PackageManager;
//
//import android.os.Bundle;
//
//
//import android.widget.SearchView;
//import android.widget.Toast;
//
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.content.res.AppCompatResources;
//import androidx.core.app.ActivityCompat;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.mapbox.android.gestures.MoveGestureDetector;
//
//
//import com.mapbox.geojson.Point;
//
//import com.mapbox.maps.CameraOptions;
//import com.mapbox.maps.MapView;
//import com.mapbox.maps.plugin.LocationPuck2D;
//import com.mapbox.maps.plugin.gestures.OnMoveListener;
//import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
//import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener;
//import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener;
//
//
//public class MainActivity extends AppCompatActivity  {
//    private MapView mapView;
//    FloatingActionButton floatingActionButton;
//
//
//    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
//        if (result) {
//            Toast.makeText(MainActivity.this, "Permission granted!", Toast.LENGTH_SHORT).show();
//        }
//    });
//
//    private final OnIndicatorBearingChangedListener onIndicatorBearingChangedListener = new OnIndicatorBearingChangedListener() {
//        @Override
//        public void onIndicatorBearingChanged(double v) {
//            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().bearing(v).build());
//        }
//    };
//
//    private final OnIndicatorPositionChangedListener onIndicatorPositionChangedListener = new OnIndicatorPositionChangedListener() {
//        @Override
//        public void onIndicatorPositionChanged(@NonNull Point point) {
//            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().center(point).zoom(20.0).build());
//            getGestures(mapView).setFocalPoint(mapView.getMapboxMap().pixelForCoordinate(point));
//        }
//    };
//
//    private final OnMoveListener onMoveListener = new OnMoveListener() {
//        @Override
//        public void onMoveBegin(@NonNull MoveGestureDetector moveGestureDetector) {
//            getLocationComponent(mapView).removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
//            getLocationComponent(mapView).removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
//            getGestures(mapView).removeOnMoveListener(onMoveListener);
//            floatingActionButton.show();
//        }
//
//        @Override
//        public boolean onMove(@NonNull MoveGestureDetector moveGestureDetector) {
//            return false;
//        }
//
//        @Override
//        public void onMoveEnd(@NonNull MoveGestureDetector moveGestureDetector) {
//
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mapView = findViewById(R.id.mapView);
//        SearchView searchView = findViewById(R.id.search_view);
//        floatingActionButton = findViewById(R.id.focusLocation);
//
//        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//
//        floatingActionButton.hide();
////        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
//        mapView.getMapboxMap().loadStyleUri("mapbox://styles/mapbox/streets-v11", style -> {
//            mapView.getMapboxMap().setCamera(new CameraOptions.Builder().zoom(10.0).build());
//            LocationComponentPlugin locationComponentPlugin = getLocationComponent(mapView);
//            locationComponentPlugin.setEnabled(true);
//            LocationPuck2D locationPuck2D = new LocationPuck2D();
//            locationPuck2D.setBearingImage(AppCompatResources.getDrawable(MainActivity.this, R.drawable.baseline_location_on_24));
//            locationComponentPlugin.setLocationPuck(locationPuck2D);
//            locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
//            locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
//            getGestures(mapView).addOnMoveListener(onMoveListener);
//
//            floatingActionButton.setOnClickListener(view -> {
//                locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener);
//                locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener);
//                getGestures(mapView).addOnMoveListener(onMoveListener);
//                floatingActionButton.hide();
//            });
//
//        });
//
//    }


//        MapboxGeocoding geocoding = MapboxGeocoding.builder()
//                .accessToken(R.string.mapbox_access_token)
//                .query("Washington")
//                .limit(5)
//                .build();
// Execute the forward geocoding request
//        geocoding.enqueueCall (callback);
//        SearchEngine searchEngine = SearchEngine.createSearchEngine(settings);
//        SearchOptions options = new SearchOptions.Builder()
//                .limit(5)
//                .build();
//      searchEngine.search("Washington",options,searchCallback);
//        MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
//                .accessToken(R.string.mapbox_access_token)
//                .query("1600 Pennsylvania Ave NW")
//                .build();


//        final SearchSelectionCallback searchCallback = new SearchSelectionCallback() {
//
//                @Override
//                public void onSuggestions(
//                        @NonNull List<SearchSuggestion> suggestions,
//                        @NonNull ResponseInfo responseInfo
//                ) {
//                        if (suggestions.isEmpty()) {
//                                Log.i("SearchApiExample", "No suggestions found");
//                        } else {
//                                SearchSuggestion suggestion = suggestions.get(0);
//                                Log.i("Tag", "Search suggestion: " + suggestion);
////                                task= searchEngine.select(suggestion, this);
//                        }
//                }
//
////                @Override
////                public void onResults(@NonNull SearchSuggestion searchSuggestion, @NonNull List<SearchResult> list, @NonNull SearchResult result, @NonNull ResponseInfo responseInfo) {
////                        Point location = result.getCoordinate();
////                        Log.d("TAG", "Search result's location: " + location);
////                }
//
//
//                @Override
//                public void onError(@NonNull Exception e) {
//                        Log.i("msg", "Search error: ", e);
//                }
//        };

//        @Override
//        public void onClick(View v) {
//                searchView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                                Intent intent = new PlaceAutocomplete() {
//                                        @Nullable
//                                        @Override
//                                        public Object suggestions(@NonNull String s, @Nullable BoundingBox boundingBox, @Nullable Point point, @NonNull PlaceAutocompleteOptions placeAutocompleteOptions, @NonNull Continuation<? super Expected<Exception, List<PlaceAutocompleteSuggestion>>> continuation) {
//                                                return null;
//                                        }
//
//                                        @Nullable
//                                        @Override
//                                        public Object suggestions(@NonNull Point point, @NonNull PlaceAutocompleteOptions placeAutocompleteOptions, @NonNull Continuation<? super Expected<Exception, List<PlaceAutocompleteSuggestion>>> continuation) {
//                                                return null;
//                                        }
//
//                                        @Nullable
//                                        @Override
//                                        public Object select(@NonNull PlaceAutocompleteSuggestion placeAutocompleteSuggestion, @NonNull Continuation<? super Expected<Exception, PlaceAutocompleteResult>> continuation) {
//                                                return null;
//                                        }
//                                }
////                                }.IntentBuilder()
////                                        .accessToken(R.string.mapbox_access_token)
////                                        .placeOptions(PlaceOptions.builder()
////                                                .backgroundColor(Color.parseColor("#EEEEEE"))
////                                                .limit(10)
////                                                .build(PlaceOptions.MODE_CARDS))
////                                        .build(MainActivity.this);
////                                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
//
//                        }
//                });
//
//        }
//}



//
//        GeocodingCriteria.Builder builder = new GeocodingCriteria.Builder()
//                                .text(getString(R.id.mapbox_access_token));
//                mapboxGeocoding = MapboxGeocoding.builder()
//                        .accessToken("YOUR_MAPBOX_ACCESS_TOKEN")
//                        .geocodingCriteria(builder.build())
//                        .build();
//        });

//        mapView.getMapboxMap().getStyle(new Style.OnStyleLoaded() {
//            @Override
//            public void onStyleLoaded(@NonNull Style style) {
//
//                // Create a list to store our line coordinates.
//                List routeCoordinates = new ArrayList<Point>();
//                routeCoordinates.add(Point.fromLngLat(-118.394391, 33.397676));
//                routeCoordinates.add(Point.fromLngLat(-118.370917, 33.391142));
//
//                // Create the LineString from the list of coordinates and then make a GeoJSON FeatureCollection so that you can add the line to our map as a layer.
//
//                LineString lineString = LineString.fromLngLats(routeCoordinates);
//
//                Feature feature = Feature.fromGeometry(lineString);
//
////                GeoJsonSource geoJsonSource = new GeoJsonSource("geo-json-source", feature);
////                style.addStyleSource(geoJsonSource);
//            }
//        });
//    }}
