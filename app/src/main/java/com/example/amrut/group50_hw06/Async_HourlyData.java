package com.example.amrut.group50_hw06;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by amrut on 10/19/2016.
 */

public class Async_HourlyData extends AsyncTask<String,Void,ArrayList<ArrayList<Weather>>> {

    CityWeatherActivity activity;
    public Async_HourlyData(CityWeatherActivity activity){
        this.activity=activity;
    }

    @Override
    protected ArrayList<ArrayList<Weather>> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }
                Log.d("hourly data json", sb.toString());
                return Hourly_Forecast_Util.forecastJSONParser.parseHourlyForecast(sb.toString());
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.progressDialog=new ProgressDialog(activity);
        activity.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        activity.progressDialog.setMessage("Loading data...");
        activity.progressDialog.setCancelable(false);
        activity.progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<ArrayList<Weather>>  weathers) {
        super.onPostExecute(weathers);
        activity.progressDialog.dismiss();
        if(weathers!=null){
            Log.d("WeatherData",weathers.toString());
            activity.showWeatherList(weathers);
        }
        else{
            Toast.makeText(activity,"No City matches your query",Toast.LENGTH_LONG).show();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                public void run() {
                    Intent intent=new Intent(activity,MainActivity.class);
                    intent.putExtra("city",activity.searchCity);
                    intent.putExtra("country",activity.searchCountry);
                    activity.startActivity(intent);
                }
            }, 1000);


        }

    }

}
