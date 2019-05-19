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

public class RegisterActivity  extends AppCompatActivity {
    /** Initalising variables. Set to null so they can be garbage collected if needed**/
    private Switch switchScreen = null;
    private TextView email = null;
    private TextView password = null;
    private TextView confirmPassword = null;
    private TextView username = null;
    private Button submitButton = null;
    private SQLHandler databaseHandler = null;
    /**
     *  Creating an intent to show the Register Activity
     */
    public void openLoginActivity(){
        /** Creating the intent **/
        Intent switchToRegister = new Intent(this, MainActivity.class);
        /** Sending the username **/
        switchToRegister.putExtra("email",email.getText().toString());
        /** Starting activity**/
        startActivity(switchToRegister);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.register_layout);


        // System.out.println(databaseHandler.toString());

        //     Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //  while(true) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                v.vibrate(VibrationEffect.createOneShot(500, 10));
//            }
        // }

    }

    @Override
    protected void onStart() {
        super.onStart();
        /** Setting the visual element of the slider to be set to true and changing text **/
        switchScreen = findViewById(R.id.switchScreen);
        switchScreen.setChecked(true);
        switchScreen.setText("Login");
        databaseHandler = new SQLHandler(this);

        /** Storing each visual component as a variable **/
        username = findViewById(R.id.username);
        password = findViewById(R.id.passwordField2);
        confirmPassword = findViewById(R.id.passwordField3);
        email = findViewById(R.id.emailField2);
        submitButton = findViewById(R.id.registerSubmit);

        switchScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
        /** When you click the submit button**/

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void registerUser(){
        /** Check if email and username already exists **/
        if(!databaseHandler.checkField("useraccount","email",email.getText().toString()) || !databaseHandler.checkField("useraccount","username",username.getText().toString())){
            /** Checking if password field are equal **/
            if(password.getText().toString().equals(confirmPassword.getText().toString())){
                /** Checking if fields are not empty **/
                if((password.getText().toString().length() > 0) || (username.getText().toString().length() > 0) || (email.getText().toString().length() >0)){
                    /** Creating the user account **/
                    databaseHandler.createUserAccount(username.getText().toString(),password.getText().toString(),email.getText().toString());
                    openLoginActivity();
                }else{
                    /** Fields cannot be empty **/
                    Toast toast = Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }else{
                /** Password does not match **/
                Toast toast = Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            /** Showing error since detials have been used**/
            Toast toast = Toast.makeText(getApplicationContext(), "Already used details", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
