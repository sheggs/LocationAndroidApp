package com.example.locationapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class GPSActivity extends AppCompatActivity {
    private Button backButton;

    public void goToRegisterGPS(){
        /** Creating the intent **/
        Intent goToRegisterGPS = new Intent(this, registerToGPS.class);
        /** Starting activity **/
        startActivity(goToRegisterGPS);
    }
    /** Go to the google maps **/
    /**
     *
     * @param latitude of locations
     * @param longitude of the location.
     */
    public void goToMaps(Double latitude, Double longitude){
        /** Starting the intent **/
        Intent goMap = new Intent(this, showGPSLocation.class);
        /** Inserting extra information **/
        goMap.putExtra("longitude",longitude);
        goMap.putExtra("latitude", latitude);
        /** Start the maps **/
        startActivity(goMap);
    }

    private void goToMenu(){
        /** Creating Intent and sending user to the main menu **/
        Intent backtoMenu = new Intent(this, MainMenu.class);
        startActivity(backtoMenu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        this.backButton = findViewById(R.id.back_button);
        /** Setting up back button **/
        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMenu();
            }
        });
        /** Hiding top bar **/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        /** Small plus button on the bottom right **/
        FloatingActionButton fab = findViewById(R.id.fab);
        /** When pressed **/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Register GPS Location", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                goToRegisterGPS();
            }
        });
        /** Initalising ArrayList that has the information **/
        final ArrayList<String> title = new ArrayList<String>();
        ArrayList<Double> longitude =  new ArrayList<Double>();
        final ArrayList<Double> latitude =  new ArrayList<Double>();
        /** Make first value empty **/
        title.add(null);
        longitude.add(0.0);
        latitude.add(0.0);
        ListView listView = findViewById(R.id.listViewGPS);;
        /** Getting SQL handler **/
        SQLHandler dbsql = new SQLHandler(getApplicationContext());
        /** Getting all the users data **/
        Cursor data = dbsql.getUserGPSData();
        /** Creating a pointer **/
        int count = data.getCount();
        /** Checking if Cursor is not empty **/
        if(data.getCount() > 0){
            /** Creating another pointer **/
            int i = 0;
            /** Loop through the cursor until last value **/
            while (i<data.getCount()){
                /** Add data into array List **/
                title.add(data.getString(data.getColumnIndex("name")));
                longitude.add(data.getDouble(data.getColumnIndex("gpsLongitude")));
                latitude.add(data.getDouble(data.getColumnIndex("gpsLatitutde")));
                /** Move cursor to point to the next row **/
                data.moveToNext();
                /** Increament pointer **/
                i++;

            }
            /** Convert arraylist into Array **/
            String[] titles = title.toArray(new String[title.size()]);
            final Double[] longitudes = longitude.toArray(new Double[longitude.size()]);
            final Double[] latitudes = latitude.toArray(new Double[latitude.size()]);
            /** setting list view adapter **/
            listView.setAdapter(new GPSListViewAdapter(this, titles, longitudes, latitudes));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /** GPS BUTTON**/
                    if(position != 0) {
                        goToMaps(longitudes[position], latitudes[position]);
                    }
                }
            });
        }else{

        }

    }

}
