package com.example.amrut.group50_hw06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amrut on 10/16/2016.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME="mySavedCities.db";
    static final int DB_VERSION=1;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        SavedCitiesTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        SavedCitiesTable.onUpgrade(db,oldVersion,newVersion);
    }
}
