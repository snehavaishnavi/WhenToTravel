package com.example.amrut.group50_hw06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amrut on 10/16/2016.
 */

public class DataBaseDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private SavedCityDAO savedCityDAO;

    public DataBaseDataManager(Context mContext) {
        this.mContext = mContext;
        dbOpenHelper =new DatabaseOpenHelper(this.mContext);
        db= dbOpenHelper.getWritableDatabase();
        savedCityDAO = new SavedCityDAO(db);
    }

    public void close(){
        if(db!=null){
            db.close();
        }
    }

    public SavedCityDAO getCityDAO(){
        return this.savedCityDAO;
    }

    public long saveCity(SavedCity city) throws ParseException {
        return this.savedCityDAO.save(city);
    }

    public boolean updateCityTemp(SavedCity city){
        return this.savedCityDAO.updateTemp(city);
    }

    public boolean updateCityisFav(SavedCity city) throws ParseException {
        return this.savedCityDAO.updateisFav(city);
    }

    public boolean deleteSavedCity(SavedCity city){
        return this.savedCityDAO.delete(city);
    }

    public SavedCity getCity(String city,String country) throws ParseException {
        return this.savedCityDAO.get(city,country);
    }

    public ArrayList<SavedCity> getAllSavedCities() throws ParseException {
        return this.savedCityDAO.getAll();
    }

}
