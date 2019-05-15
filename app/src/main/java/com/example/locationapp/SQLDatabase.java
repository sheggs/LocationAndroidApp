package com.example.locationapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDatabase extends SQLiteOpenHelper {
    private SQLiteDatabase db = null;
    private final static String DATABASE_NAME = null;
    public SQLDatabase(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME,factory, 1);
    }

    public boolean createUserAccount(SQLiteDatabase db, String username, String password, String email){
        db.execSQL("INSERT INTO useraccount (admin,username,email,password,date_created,isBanned,balance) VALUES (0,'"+ username+"','"+email+"','"+password+"',CURRENT_TIMESTAMP,0,0)");
        return true;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
    }



    public void createUserTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS useraccount (user_id AUTO_INCREMENT PRIMARY KEY, balance DOUBLE,admin SMALLINT,username VARCHAR(255),email VARCHAR(255),password VARCHAR(32),date_created TIMESTAMP,isBanned SMALLINT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }





}
