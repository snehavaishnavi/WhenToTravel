package com.example.amrut.group50_hw06;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amrut on 10/19/2016.
 */

public class Weather {
    Date date_time;
    String icon;
    String temperature;
    String condition;
    String pressure;
    String humidity;
    String windSpeed;
    String windDirection;

    static public Weather createWeatherDataObject(JSONObject js) throws JSONException, ParseException {

        Weather weather=new Weather();
        weather.setDate_time((String)js.get("dt_txt"));
        weather.setIcon((String)js.getJSONArray("weather").getJSONObject(0).get("icon"));
        weather.setTemperature(String.valueOf(js.getJSONObject("main").get("temp")));
        weather.setCondition((String)js.getJSONArray("weather").getJSONObject(0).get("description"));
        weather.setPressure(String.valueOf(js.getJSONObject("main").get("pressure")));
        weather.setHumidity(String.valueOf(js.getJSONObject("main").get("humidity")));
        weather.setWindSpeed(String.valueOf(js.getJSONObject("wind").get("speed")));
        weather.setWindDirection(String.valueOf(js.getJSONObject("wind").get("deg"))+"\u00B0");

        return weather;
    }
    public String getDate(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(date_time);
    }
    public String getTime(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm aaa");
        return dateFormat.format(date_time);
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date_time = format.parse(date_time);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date_time=" + date_time +
                ", icon='" + icon + '\'' +
                ", temperature='" + temperature + '\'' +
                ", condition='" + condition + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windDirection='" + windDirection + '\'' +
                '}';
    }
}
