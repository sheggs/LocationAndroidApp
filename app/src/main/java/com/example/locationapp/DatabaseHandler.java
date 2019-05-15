package com.example.locationapp;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler {
    private SQLDatabase db = null;
    public DatabaseHandler(Context context){
        db = new SQLDatabase(context, null );
    }
    public boolean doesRowExist(String tablename,String column, String data){
        Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM "+tablename+" WHERE "+column + " = '" + data+"'", null);
        c.moveToFirst();
        try {
            if (c.getString(c.getColumnIndex(column)) != null) {
                return true;
            } else {
                return false;
            }
        }catch (CursorIndexOutOfBoundsException e){
            return false;
        }
    }
    public String x() {
        Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM USERACCOUNT", null);
        c.moveToFirst();
        String a = "";
        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("username")) != null) {
                a += c.getString(c.getColumnIndex("username"));
            }
        }
        return a;
    }






}
