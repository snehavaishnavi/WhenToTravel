package com.example.amrut.group50_hw06;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amrut on 10/19/2016.
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    private ArrayList<ArrayList<Weather>> mWeatherList;
    private Context mContext;
    String selectedTempUnit;
    public WeatherListAdapter(Context context, ArrayList<ArrayList<Weather>> weathers,String selectedTempSettings) {
        mWeatherList = weathers;
        mContext = context;
        this.selectedTempUnit=selectedTempSettings;
    }
    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View weatherView = inflater.inflate(R.layout.row_item_layout_day_forecast, parent, false);
        ViewHolder viewHolder = new ViewHolder(weatherView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       ArrayList<Weather> dayWeather=mWeatherList.get(position);

        TextView textViewDate=holder.textDate;
        textViewDate.setText(dayWeather.get(0).getDate());

        TextView textViewTemp=holder.textTemperature;
        if(selectedTempUnit.equals("C")){
            double avgTemp=getAvgTemp(dayWeather);
            textViewTemp.setText(String.valueOf(avgTemp)+"\u00B0"+"C");
        }else if(selectedTempUnit.equals("F")) {
            double value=Math.round((getAvgTemp(dayWeather)*9/5)*100);
            Log.d("F converted values",String.valueOf((value/100)+32));
            textViewTemp.setText(String.valueOf((value/100)+32)+"\u00B0"+"F");
        }
        int median=getMedian(dayWeather.size());
        ImageView imageViewIcon=holder.imgWeatherIcon;
        Picasso.with(getContext()).load("http://openweathermap.org/img/w/"+dayWeather.get(median).getIcon()+".png").into(imageViewIcon);

    }

    @Override
    public int getItemCount() {
        return mWeatherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textDate,textTemperature;
        public ImageView imgWeatherIcon;
        public ViewHolder(View itemView) {

            super(itemView);

            textDate= (TextView) itemView.findViewById(R.id.textDate);
            imgWeatherIcon= (ImageView) itemView.findViewById(R.id.imageWeatherIcon);
            textTemperature= (TextView) itemView.findViewById(R.id.textTemperature);
        }
    }

    public double getAvgTemp(ArrayList<Weather> list){
        double sumTemp=0;
        double avgTemp=0;
        for(int i=0;i<list.size();i++){
            sumTemp=sumTemp+Double.parseDouble(list.get(i).getTemperature());
        }
        Log.d("sum",sumTemp+"");
        avgTemp=sumTemp/list.size();
        avgTemp= (int)avgTemp*100;
        Log.d("avg temp",avgTemp/100+"");
        return avgTemp/100;
    }

    public int getMedian(int size){
        return size/2;
    }
}
