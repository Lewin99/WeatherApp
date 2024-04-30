package org.me.gcu.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.me.gcu.weatherapp.R;

public class DhakaMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhaka_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dhaka_map);
        mapFragment.getMapAsync(this);

        // Retrieve latitude and longitude from the intent
        if (getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")) {
            latitude = getIntent().getDoubleExtra("latitude", 0);
            longitude = getIntent().getDoubleExtra("longitude", 0);
            Log.d("MapActivity", "Latitude: " + latitude + ", Longitude: " + longitude);
        }
        Button doneButton = findViewById(R.id.done_button);

        // Set an OnClickListener for the "Done" button
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the MapActivity and return to the previous screen
                finish();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Create a LatLng object for the location
        LatLng location = new LatLng(latitude, longitude);

        // Add a marker for the location and move the camera
        mMap.addMarker(new MarkerOptions().position(location).title("Dhaka"));

        // Combine camera update actions into a single call
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15)); // Adjust zoom level as needed
    }
}
