package com.example.amrut.group50_hw06;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amrut on 10/16/2016.
 */

public class SavedCityDAO {

    private SQLiteDatabase db;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public SavedCityDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(SavedCity city) throws ParseException {
        ContentValues values=new ContentValues();
        values.put(SavedCitiesTable.COLUMN_CITY_NAME,city.getCityName());
        values.put(SavedCitiesTable.COLUMN_COUNTRY_NAME,city.getCountryName());
        values.put(SavedCitiesTable.COLUMN_TEMP,city.getTemp());
        values.put(SavedCitiesTable.COLUMN_UPDATE_DATE, city.getDate());
        values.put(SavedCitiesTable.COLUMN_ISFAVORITE,city.isfavorite());
        long rowAffected=db.insert(SavedCitiesTable.TABLE_NAME,null,values);
        ArrayList<SavedCity> savedCities=getAll();
        return rowAffected;

    }

    public boolean updateTemp(SavedCity city){
        ContentValues values=new ContentValues();
        values.put(SavedCitiesTable.COLUMN_TEMP,city.getTemp());
        values.put(SavedCitiesTable.COLUMN_UPDATE_DATE,city.getDate());
        return db.update(SavedCitiesTable.TABLE_NAME,values,
                SavedCitiesTable.COLUMN_CITY_NAME + " = ? AND " + SavedCitiesTable.COLUMN_COUNTRY_NAME + " = ?",
                new String[]{city.getCityName(),city.getCountryName()})>0;
    }

    public boolean updateisFav(SavedCity city){
        ContentValues values=new ContentValues();
        values.put(SavedCitiesTable.COLUMN_UPDATE_DATE,city.getDate());
        values.put(SavedCitiesTable.COLUMN_ISFAVORITE,city.isfavorite());

        return db.update(SavedCitiesTable.TABLE_NAME,values,
                SavedCitiesTable.COLUMN_CITY_NAME + " = ? AND " + SavedCitiesTable.COLUMN_COUNTRY_NAME + " = ?",
                new String[]{city.getCityName(),city.getCountryName()})>0;
    }

    public boolean delete(SavedCity city){
        return db.delete(SavedCitiesTable.TABLE_NAME,
                SavedCitiesTable.COLUMN_CITY_NAME + " = ? AND " + SavedCitiesTable.COLUMN_COUNTRY_NAME + " = ?",
                new String[]{city.getCityName(),city.getCountryName()})>0;
    }

    public SavedCity get(String city,String country) throws ParseException {
        SavedCity savedCity=null;
        Cursor c=db.query(true,SavedCitiesTable.TABLE_NAME,new String[]{SavedCitiesTable.COLUMN_ID,
                SavedCitiesTable.COLUMN_CITY_NAME,SavedCitiesTable.COLUMN_COUNTRY_NAME,SavedCitiesTable.COLUMN_TEMP,SavedCitiesTable.COLUMN_ISFAVORITE},
                SavedCitiesTable.COLUMN_CITY_NAME +" = ? AND "+ SavedCitiesTable.COLUMN_COUNTRY_NAME+" = ?",
                new String[]{city,country},null,null,null,null,null);
        if(c!=null && c.moveToFirst()){
            savedCity=buildNoteFromCursor(c);
            if(!c.isClosed()){
                c.close();
            }
        }
        return savedCity;
    }

    public ArrayList<SavedCity> getAll() throws ParseException {
        ArrayList<SavedCity> savedCities=new ArrayList<SavedCity>();
        Cursor c=db.query(SavedCitiesTable.TABLE_NAME,new String[]{
                        SavedCitiesTable.COLUMN_ID,
                        SavedCitiesTable.COLUMN_CITY_NAME,
                        SavedCitiesTable.COLUMN_COUNTRY_NAME,
                        SavedCitiesTable.COLUMN_TEMP,
                        SavedCitiesTable.COLUMN_UPDATE_DATE,
                        SavedCitiesTable.COLUMN_ISFAVORITE},
                null,null,null,null,null);
        if(c!=null && c.moveToFirst()){
            do{
                SavedCity city=buildNoteFromCursor(c);
                if(city!=null){
                    savedCities.add(city);
                }
            }while (c.moveToNext());

            if(!c.isClosed()){c.close();}
        }
        return savedCities;

    }

    private SavedCity buildNoteFromCursor(Cursor c) throws ParseException {
        SavedCity city=null;
        if(c!=null){
            city=new SavedCity();
            city.setId(c.getLong(0));
            city.setCityName(c.getString(1));
            city.setCountryName(c.getString(2));
            city.setTemp(c.getString(3));
            city.setDate(c.getString(4));
            boolean flag=false;
            if(c.getInt(5)==0){
                flag=false;
            }else  if(c.getInt(5)==1){
                flag=true;
            }
            city.setIsfavorite(flag);
        }
        return city;
    }
}
