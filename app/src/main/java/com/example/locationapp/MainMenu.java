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
    public void goToGPS(){
        /** Creating the intent **/
        Intent gotoGPS = new Intent(this, GPSActivity.class);
        /** Starting activity**/
        startActivity(gotoGPS);
    }
    @Override
    protected void onStart() {
        super.onStart();
        /** Creating the list view **/
        listview = findViewById(R.id.listview);
        listview.setAdapter(new MenuListViewAdapter(this, options,descriptions));
        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.logout();
                returnToLogin();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /** GPS BUTTON**/
                if(position == 0){
                    goToGPS();
                    Toast toast = Toast.makeText(getApplicationContext(), "GPS Selected", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }
}
