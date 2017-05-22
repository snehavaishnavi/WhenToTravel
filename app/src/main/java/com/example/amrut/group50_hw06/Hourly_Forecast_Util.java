package com.example.amrut.group50_hw06;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by amrut on 10/19/2016.
 */

public class Hourly_Forecast_Util {
    static public class forecastJSONParser{
        static ArrayList<ArrayList<Weather>>  parseHourlyForecast(String in) throws JSONException, ParseException {
            String date="";
            int index=0;
            ArrayList<ArrayList<Weather>>  weatherList= new ArrayList<ArrayList<Weather>>();
            JSONObject root=new JSONObject(in);
            weatherList.add(new ArrayList<Weather>());
            JSONArray forecastArray=root.getJSONArray("list");
            for (int i=0;i<forecastArray.length();i++){
                JSONObject weatherDataObject=forecastArray.getJSONObject(i);
                Weather weather=Weather.createWeatherDataObject(weatherDataObject);
                if(date==""){
                    date=weather.getDate();
                }
                if(date.compareTo(weather.getDate().toString())==0){
                    weatherList.get(index).add(weather);
                }else if(date.compareTo(weather.getDate().toString())<0){
                    weatherList.add(new ArrayList<Weather>());
                    index++;
                    date=weather.getDate().toString();
                    weatherList.get(index).add(weather);
                }
            }
            return weatherList;
        }
    }
}
