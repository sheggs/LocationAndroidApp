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

public class MainMenu extends AppCompatActivity {

    /** Initalising variables. Set to null so they can be garbage collected if needed**/
    private ListView listview = null;
    private String[] options = {"GPS Tracker", "Weather", "Compass"};
    private String[] descriptions = {"Track yourself", "View the current weather", "Compass, use it for what ever you need!"};
    private Button logoutButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main_menu);


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
            }
        });

    }

}
