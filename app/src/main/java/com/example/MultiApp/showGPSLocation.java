package com.example.locationapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class showGPSLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_gpslocation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (getIntent().getExtras().getStringArrayList("ListTitle") != null) {
            ArrayList<String> titles = getIntent().getStringArrayListExtra("ListTitle");
            double[] lat = getIntent().getDoubleArrayExtra("ListLat");
            double[] lon = getIntent().getDoubleArrayExtra("ListLong");

            int j = 0;
            for(String x : titles){
                LatLng marker = new LatLng(lat[j], lon [j]);
                mMap.addMarker(new MarkerOptions().position(marker).title(titles.get(j)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
                j++;
            }
        }
        // Adds a marker.
        if(getIntent().getDoubleExtra("latitude",-100) != -100){
            LatLng marker = new LatLng(getIntent().getDoubleExtra("longitude",0), getIntent().getDoubleExtra("latitude",0));
            mMap.addMarker(new MarkerOptions().position(marker).title("Your location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        }

    }
}
