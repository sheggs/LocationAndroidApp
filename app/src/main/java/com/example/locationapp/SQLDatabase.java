package com.example.locationapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDatabase extends SQLiteOpenHelper {


    private Context context;
    private static final String DB_NAME = "part2.db";

    public SQLDatabase(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
    }


    public boolean createUserAccount(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("email", email);
        db.insert("useraccount", null, cv);
        return true;
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

    public void createUserTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS useraccount (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(255),email VARCHAR(255),password VARCHAR(32))");
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
