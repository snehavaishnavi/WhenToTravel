package com.example.amrut.group50_hw06;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by amrut on 10/16/2016.
 */

public class SavedCitiesTable {
    static final String TABLE_NAME="cities";
    static final String COLUMN_ID="id";
    static final String COLUMN_CITY_NAME="cityName";
    static  final String COLUMN_COUNTRY_NAME="countryName";
    static final String COLUMN_TEMP="temp";
    static final String COLUMN_UPDATE_DATE="date";
    static final String COLUMN_ISFAVORITE="isFavourite";

    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE "+ TABLE_NAME+" (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_CITY_NAME + " text not null, ");
        sb.append(COLUMN_COUNTRY_NAME + " text not null, ");
        sb.append(COLUMN_TEMP + " text not null, ");
        sb.append(COLUMN_UPDATE_DATE + " text not null, ");
        sb.append(COLUMN_ISFAVORITE + " integer not null);");
        try{
            db.execSQL(sb.toString());
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME);
        SavedCitiesTable.onCreate(db);
    }
}
