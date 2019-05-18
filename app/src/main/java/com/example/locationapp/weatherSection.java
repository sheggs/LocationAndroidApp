package com.example.locationapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.Window;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;

public class weatherSection extends AppCompatActivity {
    /** Initialising Variables **/
    private TextView tempreture = null;
    private TextView min_temp = null;
    private TextView max_temp = null;
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;
    private JSONObject weatherData = null;
    /** Permissions needed**/
    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.ACCESS_COARSE_LOCATION};
    private double longitude;
    private double latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_section);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
       // Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//      requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
    }

    /**
     *
     * @param lat The latitude
     * @param lon The longitude
     * @return The JSON object of the weather data.
     */
    public JSONObject getWeatherJSONData(double lat, double lon){
        StringBuffer jsonStringData = new StringBuffer();
        /** The API Key**/
        String key = "e8ad15f5ec7d0e84a185356fac9dd6a4";
        /** The API URL **/
        String url = "http://api.openweathermap.org/data/2.5/weather?lat="+ lat+"&lon="+lon+"&appid="+key;
        try {
            /** Creating url OBJECT**/
            URL openweather_url = new URL(url);
            /** Creating HTTP Connection **/
            HttpURLConnection con = (HttpURLConnection) openweather_url.openConnection();
            /** Using GET request since the API advisies using GET **/
            con.setRequestMethod("GET");
            /** Reading the outbut **/
            BufferedReader url_output = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String url_content =url_output.readLine();
            /** Stroing each line of the response **/
            while(url_content != null) {
                jsonStringData.append(url_content);
                url_content = url_output.readLine();
            }
            /** Ending reader **/
            url_output.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            /** Creating JSON object **/
            this.weatherData = new JSONObject(jsonStringData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Return the JSON object **/
        return  this.weatherData;
    }
    /**
     * Getting the users GPS location.
     */
    public void getGPSData() {
        /** setting up location manager **/
        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        /** Ensuring user has permissions **/
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(this.permissions,10);
            }
        }
        /** setting up location listenere **/
        this.locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                /** Getting the longitude and latitude **/
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                /** Getting the JSON weather Data **/
                getWeatherJSONData(latitude,longitude);
                try {
                    JSONObject main_JSON = new JSONObject(getWeatherJSONData(17.919039, 42.920570).get("main").toString());
                    /** Setting tempreture text. Converting temp from kelvin to degrees **/
                    tempreture.setText("Tempreture " + ((Double.parseDouble(main_JSON.get("temp").toString()) - 273)+ "").substring(0,5) + "°C");
                    min_temp.setText("Tempreture " + ((Double.parseDouble(main_JSON.get("temp_min").toString()) - 273) + "").substring(0,5)+ "°C");
                    max_temp.setText("Tempreture " + ((Double.parseDouble(main_JSON.get("temp_max").toString())-273) + "").substring(0,5)+ "°C");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };
        /** Setting request update **/
        this.locationManager.requestLocationUpdates("gps",5000,10,this.locationListener);

    }
    @Override
    protected void onStart() {
        super.onStart();
        /** Setting up the visual components in variables**/
        this.tempreture = findViewById(R.id.tempreture);
        this.max_temp = findViewById(R.id.maxTemp);
        this.min_temp = findViewById(R.id.mintemp);
        /** Calling to get the GPS data **/
        getGPSData();

    }
}
