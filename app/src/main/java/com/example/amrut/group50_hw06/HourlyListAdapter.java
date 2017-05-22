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

public class HourlyListAdapter extends RecyclerView.Adapter<HourlyListAdapter.ViewHolder> {
    private ArrayList<Weather> mHourlyList;
    private Context mContext;
    String selectedTempUnit;
    public HourlyListAdapter(Context context, ArrayList<Weather> hourlyList,String selectedTempSettings) {
        mHourlyList = hourlyList;
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
        View hourlyweatherView = inflater.inflate(R.layout.row_item_layout_hourly_forecast, parent, false);
        ViewHolder viewHolder=new ViewHolder(hourlyweatherView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       Weather weather=mHourlyList.get(position);
        TextView textTime=holder.textTime;
        textTime.setText(weather.getTime());

        TextView textTemp=holder.textTemp;
        if(selectedTempUnit.equals("C")){
            textTemp.setText(weather.getTemperature()+"\u00B0"+"C");
        }else {
            textTemp.setText(String.valueOf((float)Math.round((((Double.parseDouble(weather.getTemperature())*9)/5+32)*100))/100)+"\u00B0"+"F");
        }

        TextView textCondition=holder.textCondition;
        textCondition.setText(weather.getCondition());

        TextView textPressure=holder.textPressure;
        textPressure.setText(weather.getPressure()+" hPa");

        TextView textHumidity=holder.textHumidity;
        textHumidity.setText(weather.getHumidity()+" %");

        TextView textWind=holder.textWind;
        textWind.setText(weather.getWindSpeed()+" , "+ weather.getWindDirection());

        ImageView imgWeatherIcon=holder.imgWeatherIcon;
        Picasso.with(getContext()).load("http://openweathermap.org/img/w/"+weather.getIcon()+".png").into(imgWeatherIcon);

    }

    @Override
    public int getItemCount() {
        return mHourlyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textTime,textTemp,textCondition,textPressure,textHumidity,textWind;
        public ImageView imgWeatherIcon;
        public ViewHolder(View itemView) {

            super(itemView);

            textTime= (TextView) itemView.findViewById(R.id.textTimeValue);
            imgWeatherIcon= (ImageView) itemView.findViewById(R.id.imageIcon1);
            textTemp= (TextView) itemView.findViewById(R.id.textTempValue);
            textCondition=(TextView) itemView.findViewById(R.id.textCondValue);
            textPressure=(TextView) itemView.findViewById(R.id.textPressureValue);
            textHumidity=(TextView) itemView.findViewById(R.id.textHumidityValue);
            textWind=(TextView) itemView.findViewById(R.id.textWindValue);

        }
    }
}
