package com.example.locationapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    /**
     * Intialising Fields
     **/
    private TextView emailText = null;
    private TextView password = null;
    private TextView debugText = null;
    private Button submitButton = null;
    private Switch switchScreen = null;
    private SQLDatabase databaseHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(User.getUser() != null){
            openMainMenu();
        }
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main);



        // System.out.println(databaseHandler.toString());

        //     Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //  while(true) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                v.vibrate(VibrationEffect.createOneShot(500, 10));
//            }
        // }

    }

    /**
     *  Creating an intent to show the Register Activity
     */
    public void openRegisterActivity(){
        /** Creating the intent **/
        Intent switchToRegister = new Intent(this, RegisterActivity.class);
        /** Starting activity**/
        startActivity(switchToRegister);
    }

    /**
     *  Creating an intent to show the MainMenu
     */
    public void openMainMenu(){
        if(User.getUser() != null){
            /** Creating the intent **/
            Intent switchToLogin = new Intent(this, MainMenu.class);
            /** Starting activity**/
            startActivity(switchToLogin);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Not logged in", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        /** Getting all the data **/
        this.emailText = findViewById(R.id.emailField);
        this.password = findViewById(R.id.passwordField);
        this.switchScreen = findViewById(R.id.switchScreen);
        this.submitButton = findViewById(R.id.button);
        switchScreen.setChecked(false);
        switchScreen.setText("Register");
        databaseHandler = new SQLDatabase(this);
        /** Checking if data has been sent from the register activity **/
        String email_text = "";
        if(getIntent().getStringExtra("email") != null){
            email_text = getIntent().getStringExtra("email");
        }
        emailText.setText(email_text);
        switchScreen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                System.out.println("ASDASD0");
                openRegisterActivity();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(databaseHandler.checkCrednetials(emailText.getText().toString(), password.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(), "Login is valid", Toast.LENGTH_SHORT);
                    toast.show();
                    System.out.println(databaseHandler.getRow("useraccount","email",emailText.getText().toString()).getInt(0));
                    new User(databaseHandler.getRow("useraccount","email",emailText.getText().toString()).getInt(0),databaseHandler.getRow("useraccount","email",emailText.getText().toString()).getString(1) ,databaseHandler.getRow("useraccount","email",emailText.getText().toString()).getString(2) );
                    openMainMenu();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Login is invalid", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        //databaseHandler.createUserAccount("asd","asd","asd");
//        Cursor x = databaseHandler.getData();
//        while (x.moveToNext()) {
//            debugText.setText(debugText.getText() + x.getString(1));
//        }
        //System.out.println(databaseHandler.checkCrednetials("asd","asd"));
        //System.out.println("ASD");
        // System.out.println(databaseHandler.x());
        //System.out.print(databaseHandler.doesRowExist("useraccount","username","Test"));
    }

}
