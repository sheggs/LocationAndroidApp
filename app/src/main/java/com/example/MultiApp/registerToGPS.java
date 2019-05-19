package com.example.locationapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class registerToGPS extends AppCompatActivity {
    /** Initalising the variable **/
    private TextView GPSData = null;
    private TextView GPSLocation = null;
    private Button SubmitButton = null;
    private TextView customName = null;
    private Button backButton = null;
    private LocationListener locationListener = null;
    private LocationManager locationManager = null;
    private double longitude = 0;
    private double latitude = 0;
    /** The permissions needed **/
    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.ACCESS_COARSE_LOCATION};

    private void goBack(){
        /** Creating the intent **/
        Intent switchToLogin = new Intent(this, MainMenu.class);
        /** Starting activity**/
        startActivity(switchToLogin);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_to_gps);
        getSupportActionBar().hide();

    }
    private void checkPermissions() {
        /** Looping through every permission and requesting it **/
        for(int i = 0; i<permissions.length;i++){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(PackageManager.PERMISSION_GRANTED != checkSelfPermission(permissions[i])){
                    requestPermissions(permissions,1);
                }
            }
        }
    }
    /**
     * Getting the users GPS location.
     */
    public void getGPSData() {
        /** setting up location manager **/
        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    /** Ensuring user has permissions **/
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(this.permissions,10);
            }
        }
        /** setting up location listenere **/
        this.locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                GPSData.setText(location.getLongitude() + " " + location.getLatitude());
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };
        /** Setting request update **/
        this.locationManager.requestLocationUpdates("gps",5000,10,this.locationListener);

    }

    /**
     * Go to the GPS Listing Activity
     */
    public void goToGPSListings(){
        /** Starting the intent and the activity **/
        Intent goTOGPSLISTING = new Intent(this, GPSActivity.class);
        startActivity(goTOGPSLISTING);
    }
    @Override
    protected void onStart() {
        super.onStart();
        /** Storing the refrences of each component to a variable **/
        this.GPSData = findViewById(R.id.currentGPSLocation);
        this.GPSLocation = findViewById(R.id.locationGPSText);
        this.SubmitButton = findViewById(R.id.submitGPSData);
        this.customName = findViewById(R.id.customGPSName);
        this.backButton = findViewById(R.id.back_to_mainmenu);
        this.GPSData.setText("No GPS Data. Wait a few seconds");
        /** setting up back button **/
        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        /** Setting up GPS information **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /** Requesting permissions **/
            requestPermissions(this.permissions,10);
        }
        /** Double checking the permissions **/
        checkPermissions();
        /** Get the GPS data **/
        getGPSData();

        /** when the submit button is pressed **/
        this.SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Checking if the field is completed **/
                if(customName.getText().toString().length() > 1){
                    /** Checking if GPS data is obtained yet **/
                    if((latitude != 0) || (longitude != 0)){
                        /** Adding the GPS data to the users account **/
                        new SQLHandler(getApplicationContext()).addGPSData(customName.getText().toString(),longitude,latitude);
                        goToGPSListings();
                    }else{
                        /** Snackbar message showing that the longitude and latitude is not set **/
                        Snackbar.make(v, "Longitude and Latitude not set!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }else{
                    /** Snack bar message showing that the custom name is not set **/
                    Snackbar.make(v, "Custom name is not set", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }
}
