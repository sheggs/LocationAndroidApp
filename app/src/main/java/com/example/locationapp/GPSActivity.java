package com.example.locationapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLData;
import java.util.ArrayList;

public class GPSActivity extends AppCompatActivity {


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
        Intent goMap = new Intent(this, showGPSLocation.class);
        goMap.putExtra("longitude",longitude);
        goMap.putExtra("latitude", latitude);
        /** Start the maps **/
        startActivity(goMap);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
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
        final ArrayList<String> title = new ArrayList<String>();
        ArrayList<Double> longitude =  new ArrayList<Double>();
        final ArrayList<Double> latitude =  new ArrayList<Double>();
        ListView listView = findViewById(R.id.listViewGPS);;

        SQLDatabase dbsql = new SQLDatabase(getApplicationContext());
        Cursor data = dbsql.getUserGPSData();
        int count = data.getCount();
        if(data.getCount() > 0){
            int i = 0;
            while (i<data.getCount()){
                title.add(data.getString(data.getColumnIndex("name")));
                longitude.add(data.getDouble(data.getColumnIndex("gpsLongitude")));
                latitude.add(data.getDouble(data.getColumnIndex("gpsLatitutde")));
                i++;

            }

            String[] titles = title.toArray(new String[title.size()]);
            final Double[] longitudes = longitude.toArray(new Double[longitude.size()]);
            final Double[] latitudes = latitude.toArray(new Double[latitude.size()]);
            System.out.println("Test" + titles.length);
            listView.setAdapter(new GPSListViewAdapter(this, titles, longitudes, latitudes));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /** GPS BUTTON**/
                    goToMaps(longitudes[position],latitudes[position]);
                }
            });
        }else{

        }
//        if(data != null){
//            System.out.println(data.getCount());
//            while(data.moveToNext()){
//                System.out.println(data.getString(1));
//
//                title.add(data.getString(1));
//                longitude.add(data.getDouble(data.getColumnIndex("gpsLongitude")));
//                latitude.add(data.getDouble(data.getColumnIndex("gpsLatitutde")));
//                System.out.println("ASD");
//
//            }
//            if(!(title.isEmpty()) || !(longitude.isEmpty()) || !(latitude.isEmpty())) {
////                ListView listView = findViewById(R.id.listViewGPS);
////                listView = findViewById(R.id.listview);
////                listView.setAdapter(new GPSListViewAdapter(this, title.toArray(new String[title.size()]), latitude.toArray(new Double[latitude.size()]), longitude.toArray(new Double[longitude.size()])));
////                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                    @Override
////                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                        /** GPS BUTTON**/
////                        if (position == 0) {
////                            Toast toast = Toast.makeText(getApplicationContext(), "GPS Selected", Toast.LENGTH_SHORT);
////                            toast.show();
////                        }
////                    }
////                });
//            }
        //}
    }

}
