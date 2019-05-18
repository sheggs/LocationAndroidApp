package com.example.locationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * Intialising Fields
     **/
    private TextView emailText = null;
    private TextView password = null;
    private TextView debugText = null;
    private Button submitButton = null;
    private Switch switchScreen = null;
    private SQLHandler databaseHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** Checking if user is logged in. If so send them to main menu. **/
        if(User.getUser() != null){
            openMainMenu();
        }
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main);

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
        databaseHandler = new SQLHandler(this);
        /** Checking if data has been sent from the register activity **/
        String email_text = "";
        /** Checking if the email is sent from the register panel **/
        if(getIntent().getStringExtra("email") != null){
            email_text = getIntent().getStringExtra("email");
        }
        /** setting the text of the email box **/
        emailText.setText(email_text);
        /** Setting switch to switch from login to register activity **/
        switchScreen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
        /**
         *  When the submit button is sent make the user login.
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Checking if the user's credientials are valid **/
                if(databaseHandler.checkCredentials(emailText.getText().toString(), password.getText().toString())){
                    /** Showing success message and loading the users account **/
                    Toast toast = Toast.makeText(getApplicationContext(), "Login is valid", Toast.LENGTH_SHORT);
                    toast.show();
                    /** Caches the user account into a static object **/
                    new User(databaseHandler.getRow("useraccount","email",emailText.getText().toString()).getInt(0),databaseHandler.getRow("useraccount","email",emailText.getText().toString()).getString(1) ,databaseHandler.getRow("useraccount","email",emailText.getText().toString()).getString(2) );
                    /** Open main menu **/
                    openMainMenu();
                }else{
                    /** Show a toast with the error **/
                    Toast toast = Toast.makeText(getApplicationContext(), "Login is invalid", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


    }

}
