package com.example.amrut.group50_hw06;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CityWeatherActivity extends AppCompatActivity {
    static String searchCity,searchCountry;
    ProgressDialog progressDialog;
    TextView cityCountry,date;
    RecyclerView recyclerViewWeatherList,recyclerViewHourlyList;
    LinearLayoutManager layoutManager,layoutManager1;
    WeatherListAdapter adapter;
    HourlyListAdapter hourlyListAdapter;
    SnapHelper snapHelper;
    DataBaseDataManager dm;
    List<SavedCity> savedCities;
    ArrayList<ArrayList<Weather>> weatherList;
    static final String KEY="4259ab30e3ec5eb17be2439d326167d5";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        date= (TextView) findViewById(R.id.textForecastDate);
        cityCountry= (TextView) findViewById(R.id.cityCountry);
        recyclerViewWeatherList= (RecyclerView) findViewById(R.id.recyclerViewWeatherList);
        recyclerViewHourlyList= (RecyclerView) findViewById(R.id.recyclerViewHourlyList);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        searchCity= (String) getIntent().getExtras().get(MainActivity.CITY);
        searchCountry= (String) getIntent().getExtras().get(MainActivity.COUNTRY);
        searchCity=searchCity.replaceAll(" ","_");
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewHourlyList);

        if(isConnectedOnline()){
            new Async_HourlyData(this).execute("http://api.openweathermap.org/data/2.5/forecast?q="+searchCity+","+searchCountry+"&mode=json&appid="+KEY+"&units=metric");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.city_weather_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        dm=new DataBaseDataManager(CityWeatherActivity.this);
        switch (item.getItemId()){
            case R.id.item_option1:
                try {
                    savedCities=dm.getAllSavedCities();
                    boolean isExists=false;
                    for(SavedCity city:savedCities){
                        if(city.getCityName().equalsIgnoreCase(searchCity) && city.getCountryName().equalsIgnoreCase(searchCountry)){
                            isExists=true;
                            break;
                        }
                    }
                    if(isExists){
                        SavedCity city=new SavedCity();
                        city.setCityName(searchCity);
                        city.setCountryName(searchCountry);
                        city.setTemp(weatherList.get(0).get(0).getTemperature());
                        city.setDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
                        city.setIsfavorite(false);

                        dm.updateCityTemp(city);
                        Toast.makeText(this,"City Updated",Toast.LENGTH_SHORT).show();
                    }else {
                        SavedCity city=new SavedCity();
                        city.setCityName(searchCity);
                        city.setCountryName(searchCountry);
                        city.setTemp(weatherList.get(0).get(0).getTemperature());
                        city.setDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
                        city.setIsfavorite(false);

                        dm.saveCity(city);
                        Toast.makeText(this,"City Saved",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(CityWeatherActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    return true;
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            case R.id.item_option2:
                Intent intent= new Intent(this,MyPreferencesActivity2.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    protected boolean isConnectedOnline(){
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    public void showWeatherList(final ArrayList<ArrayList<Weather>> weathers){
        if(weathers!=null){
            weatherList=weathers;
            String selectedTempSettings=sharedPreferences.getString("tempPref","C");
            Log.d("selectedTempSettings",selectedTempSettings);
            cityCountry.setText(searchCity+" , "+searchCountry);
            adapter=new WeatherListAdapter(this, weathers,selectedTempSettings);
            recyclerViewWeatherList.setAdapter(adapter);
            recyclerViewWeatherList.setLayoutManager(layoutManager);
            snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(recyclerViewWeatherList);
            showHourlyList(weathers.get(0));
            recyclerViewWeatherList.addOnItemTouchListener(new RecyclerItemClickListener(CityWeatherActivity.this,recyclerViewHourlyList, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.d("clicked item",weathers.get(position).toString());
                    showHourlyList(weathers.get(position));
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            }));

        }
    }
    public void showHourlyList(ArrayList<Weather> hourlyList){
        if(hourlyList!=null){
            String selectedTempSettings=sharedPreferences.getString("tempPref","C");
            date.setText(hourlyList.get(0).getDate());
            hourlyListAdapter=new HourlyListAdapter(this, hourlyList,selectedTempSettings);
            recyclerViewHourlyList.setAdapter(hourlyListAdapter);
            recyclerViewHourlyList.setLayoutManager(layoutManager1);

        }
    }


}
