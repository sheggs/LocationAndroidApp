package com.example.locationapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.autofill.UserData;

public class SQLDatabase extends SQLiteOpenHelper {


    private Context context;
    private static final String DB_NAME = "part3.db";

    public SQLDatabase(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
        createGPSStorage(db);
    }

    /**
     *
     * @param username The username of the user
     * @param password The password of the user
     * @param email The email of the uesr
     * @return a boolean value whether it has been successful.
     */
    public boolean createUserAccount(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("email", email);
        db.insert("useraccount", null, cv);
        return true;
    }

    /**
     *
     * @param longitude The longitude.
     * @param latitude The latitude
     * @param customName The name of the data you are saving.
     */
    public void addGPSData(String customName, double longitude, double latitude){
        /** Checking if user is logged in **/
        if(User.getUser() != null) {
            /** Inserting data into the database **/
            int user_id = User.getUser().getId();
            ContentValues cv = new ContentValues();
            cv.put("user_id",user_id);
            cv.put("gpsLatitutde",latitude);
            cv.put("gpsLongitude",longitude);
            cv.put("name",customName);
            this.getWritableDatabase().insert("gpsstorage",null,cv);
        }
    }
    /** Getting the users GPS data
     *  @return the users GPS data**/
    public Cursor getUserGPSData(){
        /** Initalising cursor**/
        Cursor userData  = null;
        /** Checking if the user is logged in **/
        if(User.getUser() != null){
            try {
                /** Querying for the users data **/
                userData = this.getReadableDatabase().rawQuery("SELECT * FROM gpsstorage WHERE user_id = '" + User.getUser().getId() + "'", null);
                userData.moveToFirst();
                return userData;
            }
            /** Catching any errors to prevent the app from crashing **/
            catch (CursorIndexOutOfBoundsException e){
                userData = null;
            }
        }
        return userData;
    }
    public Cursor getRow(String table, String column, String data){
        boolean checkingField = false;
        Cursor cData = null;
        SQLiteDatabase db =this.getReadableDatabase();
        try {
            cData = db.rawQuery("SELECT * FROM "+ table + " WHERE "+ column + " = '" + data +  "'", null);
            cData.moveToFirst();
        } catch(CursorIndexOutOfBoundsException e){
        }
        return cData;
    }
    /** Creating the user table **/
    public void createUserTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS useraccount (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(255),email VARCHAR(255),password VARCHAR(32))");
    }
    /** Creating the GPS Table **/
    public void createGPSStorage(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS gpsstorage(storage_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(255), gpsLatitutde DOUBLE, gpsLongitude DOUBLE, user_id INTEGER, FOREIGN KEY(user_id) REFERENCES useraccount(user_id) ON DELETE CASCADE )");
    }

    public boolean checkField(String table, String column, String row){
        boolean checkingField = false;
        SQLiteDatabase db =this.getReadableDatabase();
        try {
            Cursor c = db.rawQuery("SELECT * FROM "+ table + " WHERE "+ column + " = '" + row+  "'", null);
            c.moveToFirst();
            if (c.getString(1) == null) {
                checkingField =  false;
            }else{
                checkingField = true;
            }
        } catch(CursorIndexOutOfBoundsException e){
        }
        return checkingField;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM useraccount", null);
        return data;

    }
    public boolean checkCrednetials(String email, String password){
        SQLiteDatabase db =this.getReadableDatabase();
        try {
            Cursor c = db.rawQuery("SELECT * FROM useraccount WHERE email = '" + email + "' AND password = '" + password + "'", null);
            c.moveToFirst();
            if (c.getString(1) == null) {
                return false;
            }else{
                return true;
            }
        } catch(CursorIndexOutOfBoundsException e){
            return false;
        }
    }
    //    public boolean doesRowExist(String tablename,String column, String data){
//        Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM "+tablename+" WHERE "+column + " = '" + data+"'", null);
//        c.moveToFirst();
//        try {
//            if (c.getString(c.getColumnIndex(column)) != null) {
//                return true;
//            } else {
//                return false;
//            }
//        }catch (CursorIndexOutOfBoundsException e){
//            return false;
//        }
//    }


}
