/*Group 50
* Amrutha Pobbati (800935746)
* Sneha Vaishnavi Gandham*/
package com.example.amrut.group50_hw06;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.amrut.group50_hw06.R.id.recyclerViewHourlyList;

public class MainActivity extends AppCompatActivity implements SavedCitiesListAdapter.updateList{
    EditText searchCity,searchCountry;
    Button searchBtn;
    SharedPreferences sharedPreferences;
    final static String CITY="CITY";
    DataBaseDataManager dm;
    TextView listIsEmpty;
    RecyclerView recyclerViewSavedCities;
    SavedCitiesListAdapter adapter;
    String mySelectedTemp;
    LinearLayoutManager layoutManager;
    ArrayList<SavedCity> savedCitiesList;
    final static String COUNTRY="COUNTRY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchCity= (EditText) findViewById(R.id.searchCity);
        searchCountry= (EditText) findViewById(R.id.searchCountry);
        searchBtn= (Button) findViewById(R.id.searchBtn);
        listIsEmpty= (TextView) findViewById(R.id.textEmptyList);
        recyclerViewSavedCities= (RecyclerView) findViewById(R.id.recyclerViewSavedCities);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        dm=new DataBaseDataManager(this);


        if(getIntent().getExtras()!=null && getIntent().getExtras().containsKey("city")){
            searchCity.setText(getIntent().getExtras().get("city").toString().replaceAll("_"," "));
            searchCountry.setText(getIntent().getExtras().get("country").toString());
        }
        try {
            savedCitiesList=dm.getAllSavedCities();
            Log.d("savedCitiesList",savedCitiesList.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sortSavedCities(savedCitiesList);
        showSavedList();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchCity.getText().toString().equals("")){
                    searchCity.setError("Enter a valid City Name");
                }else if(searchCountry.getText().toString().equals("")){
                    searchCountry.setError("Enter a valid Country Name");
                }else {
                    Intent intent = new Intent(MainActivity.this, CityWeatherActivity.class);
                    intent.putExtra(CITY, searchCity.getText().toString());
                    intent.putExtra(COUNTRY, searchCountry.getText().toString().toUpperCase());
                    startActivity(intent);
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_option1:
                Intent intent= new Intent(this,MyPreferencesActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("demo","onresume");
        try {
            savedCitiesList=dm.getAllSavedCities();
            Log.d("savedList",savedCitiesList.toString());
            sortSavedCities(savedCitiesList);
            showSavedList();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("demo","onPause");
    }

    @Override
    protected void onDestroy() {
        dm.close();
        super.onDestroy();
    }

    @Override
    public void refreshList() {
        onResume();
        showSavedList();
    }

    public void showSavedList(){
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        mySelectedTemp=sharedPreferences.getString("tempPref","C");
            if(savedCitiesList.size()==0){
                listIsEmpty.setText(R.string.empty_list_label);
                recyclerViewSavedCities.setVisibility(View.INVISIBLE);
            }
            else {
                recyclerViewSavedCities.setVisibility(View.VISIBLE);
                listIsEmpty.setText("Saved Cities");
                adapter=new SavedCitiesListAdapter(MainActivity.this,savedCitiesList,mySelectedTemp);
                recyclerViewSavedCities.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerViewSavedCities.setLayoutManager(layoutManager);
            }

    }

    public static void sortSavedCities(ArrayList<SavedCity> savedCities){
        Collections.sort(savedCities, new Comparator<SavedCity>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(SavedCity o1, SavedCity o2) {
                return Boolean.compare(o2.isfavorite(),o1.isfavorite());
            }
        });
    }
}
