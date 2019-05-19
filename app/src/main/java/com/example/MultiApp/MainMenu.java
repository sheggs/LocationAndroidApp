package com.example.locationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {

    /** Initalising variables. Set to null so they can be garbage collected if needed**/
    private ListView listview = null;
    private String[] options = {"GPS Tracker", "Weather", "Compass","Look at all your GPS locations"};
    private String[] descriptions = {"Track yourself", "View the current weather", "Compass, use it for what ever you need!","All GPS locations that you have saved"};
    private Button logoutButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main_menu);


    }
    /**
     *
     * @param doubleArray The array you want to convert to double array.
     * @return  The converted arraylist.
     */
    private double[] toDoubleArray(ArrayList<Double> doubleArray){
        /** Intialising array **/
        double[] array = new double[doubleArray.size()];
        int i = 0;
        /** Looping through arrayList and adding it to the double array **/
        for(Double d : doubleArray){
            array[i] = d;
            i++;
        }
        return array;
    }
    public void returnToLogin(){
        /** Creating the intent **/
        Intent returnToLoginPage = new Intent(this, MainActivity.class);
        /** Starting activity**/
        startActivity(returnToLoginPage);
    }
    private void goToGPS(){
        /** Creating the intent **/
        Intent gotoGPS = new Intent(this, GPSActivity.class);
        /** Starting activity**/
        startActivity(gotoGPS);
    }
    private void goToMaps(){
        /** Creating the intent **/
        Intent gotoGPS = new Intent(this, showGPSLocation.class);
        /** Getting GPS DATA **/
        ParseGPSData GPSData = new ParseGPSData(this);
        System.out.println(GPSData.getLatitude().size());
        /** Sending GPS data to activity **/
        gotoGPS.putExtra("ListTitle",GPSData.getTitle());
        gotoGPS.putExtra("ListLat",toDoubleArray(GPSData.getLatitude()));
        gotoGPS.putExtra("ListLong",toDoubleArray(GPSData.getLongitude()));
        System.out.println(GPSData.getLatitude().toArray(new Double[10]).length);
        /** Starting activity**/
        startActivity(gotoGPS);
    }
    private void goToCompass(){
        /** Creating the intent **/
        Intent goToCompass = new Intent(this, CompassActivity.class);
        /** Starting activity**/
        startActivity(goToCompass);
    }
    private void goToWeather(){
        /** Creating the intent **/
        Intent gotoweather = new Intent(this, weatherSection.class);
        /** Starting activity**/
        startActivity(gotoweather);
    }
    @Override
    protected void onStart() {
        super.onStart();
        /** Creating the list view **/
        listview = findViewById(R.id.listview);
        listview.setAdapter(new MenuListViewAdapter(this, options,descriptions));
        logoutButton = findViewById(R.id.logout_button);
        /** When logout button is pressed **/
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.logout();
                returnToLogin();
            }
        });
        /** when the list is pressed it should take the user somewhere **/
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /** GPS BUTTON**/
                if(position == 0){
                    goToGPS();
                    Toast toast = Toast.makeText(getApplicationContext(), "GPS Selected", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(position == 1){
                    goToWeather();
                    Toast toast = Toast.makeText(getApplicationContext(), "Weather Selected", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (position == 2){
                    goToCompass();
                    Toast toast = Toast.makeText(getApplicationContext(), "Compass Selected", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(position == 3){
                    goToMaps();
                    Toast toast = Toast.makeText(getApplicationContext(), "All GPS locations are shown in the map", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

}
