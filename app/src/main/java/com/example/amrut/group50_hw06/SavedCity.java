package com.example.amrut.group50_hw06;

/**
 * Created by amrut on 10/16/2016.
 */

public class SavedCity {
    long id;
    String cityName,countryName,temp;
    boolean isfavorite;
    String date;

    public SavedCity(){

    }

    public SavedCity(long id,String cityName,String countryName,String temp,boolean isfavorite){
        this.id=id;
        this.cityName=cityName;
        this.countryName=countryName;
        this.temp=temp;
        this.isfavorite=isfavorite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isfavorite() {
        return isfavorite;
    }

    public void setIsfavorite(boolean isfavorite) {
        this.isfavorite = isfavorite;
    }

    @Override
    public String toString() {
        return "SavedCity{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", temp='" + temp + '\'' +
                ", isfavorite=" + isfavorite +
                ", date='" + date + '\'' +
                '}';
    }
}
