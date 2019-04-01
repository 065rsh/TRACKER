package com.tracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class secondAnimal extends AppCompatActivity implements OnMapReadyCallback {

    public static double latitude = 13.31461,longitude = 77.12327;
    public static TextView secondAnimalTemp_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_animal);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);

        View actionBarView = getSupportActionBar().getCustomView();

        ImageButton imageButton = actionBarView.findViewById(R.id.backButton);
        secondAnimalTemp_TV = findViewById(R.id.secondAnimalTemp1_TV);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Handler handler = new Handler();
// Define the code block to be executed

        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                fetchDataSecond process = new fetchDataSecond();
                process.execute();

                mapFragment.getMapAsync(secondAnimal.this);
                handler.postDelayed(this, 2000);
            }
        };
// Start the initial runnable task by posting through the handler
        handler.post(runnableCode);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        // Add a marker in currentLocation and move the camera
        final Marker marker = googleMap.addMarker(new MarkerOptions()
                        .anchor(0.5f, 0.5f)
                        // to make the car center as the origin for the marker
                        .position(new LatLng(latitude,longitude))
                        // set the latitude and longitude parameters in the position function
                        .flat(true)
                        // marker will retain its size when the map is zoomed in or zoomed out
                        // get the green_car image and resize it using the function resizeImage which will return Bitmap image
                        .title("First Animal")
                // this title will appear when user clicks on the marker
        );
        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude,longitude))      // Sets the center of the map to Shambhavi hostel
                .zoom(17)                   // Sets the zoom
                .tilt(0)                    // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        marker.setPosition(new LatLng(latitude,longitude));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                marker.remove();
            }
        }, 3000);
    }
}
