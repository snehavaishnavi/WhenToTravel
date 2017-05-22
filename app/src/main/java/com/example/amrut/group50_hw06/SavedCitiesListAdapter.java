package com.example.amrut.group50_hw06;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by amrut on 10/19/2016.
 */

public class SavedCitiesListAdapter extends RecyclerView.Adapter<SavedCitiesListAdapter.ViewHolder> {
    private ArrayList<SavedCity> mSavedCitiesList;
    private Context mContext;
    String selectedTempUnit;
    updateList mListener;

    static DataBaseDataManager dm;

    public SavedCitiesListAdapter(Context context, ArrayList<SavedCity> savedCities,String selectedTempSettings) {
        mSavedCitiesList = savedCities;
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
        View savedCityView = inflater.inflate(R.layout.row_item_layout_saved_cities, parent, false);
        ViewHolder viewHolder=new ViewHolder(savedCityView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SavedCity savedCity=mSavedCitiesList.get(position);
        holder.savedCity=savedCity;

        TextView textLoc=holder.textLoc;
        TextView textTemp=holder.textTemperature;
        ImageView imgIsFav=holder.imgIsFav;
        TextView textDate=holder.textDate;

        textLoc.setText(savedCity.getCityName()+","+savedCity.getCountryName());
        if(selectedTempUnit.equals("C")){
            textTemp.setText(savedCity.getTemp()+"\u00B0"+"C");
        }else {
            textTemp.setText(String.valueOf((float)Math.round((((Double.parseDouble(savedCity.getTemp())*9)/5+32)*100))/100)+"\u00B0"+"F");
        }
        textDate.setText("Updated on:"+savedCity.getDate());
        if(savedCity.isfavorite()){
            imgIsFav.setImageResource(R.drawable.star_gold);
        }else {
            imgIsFav.setImageResource(R.drawable.star_gray);
        }
    }

    @Override
    public int getItemCount() {
        return mSavedCitiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textTemperature,textLoc,textDate;
        public ImageView imgIsFav;
        SavedCity savedCity;
        public ViewHolder(View itemView) {
            super(itemView);
            mListener= (updateList) mContext;
            dm = new DataBaseDataManager(itemView.getContext());

            textLoc= (TextView) itemView.findViewById(R.id.textFavoriteLocation);
            imgIsFav= (ImageView) itemView.findViewById(R.id.imageIsFav);
            textTemperature= (TextView) itemView.findViewById(R.id.textFavCityTemp);
            textDate= (TextView) itemView.findViewById(R.id.textUpdatedOn);

            imgIsFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(savedCity.isfavorite()){
                        savedCity.setIsfavorite(false);
                        savedCity.setDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
                        imgIsFav.setImageResource(R.drawable.star_gray);

                    }else {
                        savedCity.setIsfavorite(true);
                        savedCity.setDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
                        imgIsFav.setImageResource(R.drawable.star_gold);

                    }
                    try {
                        dm.updateCityisFav(savedCity);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,CityWeatherActivity.class);
                    intent.putExtra(MainActivity.CITY,savedCity.getCityName());
                    intent.putExtra(MainActivity.COUNTRY,savedCity.getCountryName());
                    v.getContext().startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    dm.deleteSavedCity(savedCity);
                    Toast.makeText(mContext,"City Deleted",Toast.LENGTH_SHORT).show();
                    mListener.refreshList();
                    return true;
                }
            });

        }
    }

    public interface updateList{
        public void refreshList();
    }

}
