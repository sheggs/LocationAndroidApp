package com.example.locationapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    /** Intialising Fields **/
    private TextView emailText = null;
    private TextView password = null;
    private Button submitButton = null;
    private Switch switchScreen = null;
    private DatabaseHandler databaseHandler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main);
        /** Getting all the data **/
        this.emailText = findViewById(R.id.emailField);
        this.password = findViewById(R.id.passwordField);
        this.switchScreen = findViewById(R.id.switchScreen);
        databaseHandler = new DatabaseHandler(this);
        System.out.println("ASD");
        System.out.println(databaseHandler.x());
        System.out.print(databaseHandler.doesRowExist("useraccount","username","Test"));
           // System.out.println(databaseHandler.toString());

        //     Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
      //  while(true) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                v.vibrate(VibrationEffect.createOneShot(500, 10));
//            }
       // }

    }
}
