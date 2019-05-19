package com.example.locationapp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {
    /** Initialing Fields **/
    private TextView CompassDetails = null;
    private SensorManager sensorManager = null;
    private Button backButton  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
    }

    private void goBack(){
        /** Creating the intent **/
        Intent switchToLogin = new Intent(this, MainMenu.class);
        /** Starting activity**/
        startActivity(switchToLogin);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /** Storing visual components in variables **/
        this.CompassDetails = findViewById(R.id.compassInfo);
        this.backButton = findViewById(R.id.compassBackButton);
        /** setting a back button to go to main menu **/
        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Go back to the main menu **/
                goBack();
            }
        });
        /** Initalising the sensor manager **/
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        /** Accessing the sensor to get compass reading **/
        this.sensorManager.registerListener(this, this.sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        /** When sensor value changes **/
        /** Update display to show sensor value **/
        this.CompassDetails.setText(event.values[0] + "°");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
