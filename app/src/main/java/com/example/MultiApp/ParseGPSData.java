package com.example.locationapp;

import android.app.Activity;
import android.database.Cursor;

import java.util.ArrayList;

public class ParseGPSData {

    /** Intialising data **/
    private Cursor userGPSData = null;
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<Double> longitude =  new ArrayList<Double>();
    private ArrayList<Double> latitude =  new ArrayList<Double>();
    public ParseGPSData(Activity a){
        SQLHandler sqlHandler = new SQLHandler(a);
        this.userGPSData = sqlHandler.getUserGPSData();

        /** Creating a pointer **/
        int count = userGPSData.getCount();
        /** Checking if Cursor is not empty **/
        if(userGPSData.getCount() > 0) {
            /** Creating another pointer **/
            int i = 0;
            /** Loop through the cursor until last value **/
            while (i < userGPSData.getCount()) {
                /** Add data into array List **/
                title.add(userGPSData.getString(userGPSData.getColumnIndex("name")));
                longitude.add(userGPSData.getDouble(userGPSData.getColumnIndex("gpsLongitude")));
                latitude.add(userGPSData.getDouble(userGPSData.getColumnIndex("gpsLatitutde")));
                /** Move cursor to point to the next row **/
                userGPSData.moveToNext();
                /** Increament pointer **/
                i++;

            }
        }
    }

    /**
     *
     * @return List of latitudes
     */
    public ArrayList<Double> getLatitude() {
        return this.latitude;
    }

    /**
     *
     * @return List of longitudes
     */
    public ArrayList<Double> getLongitude() {
        return this.longitude;
    }

    /**
     *
     * @return List of titles
     */
    public ArrayList<String> getTitle() {
        return this.title;
    }
}
