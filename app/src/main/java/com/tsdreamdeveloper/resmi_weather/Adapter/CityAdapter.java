package com.tsdreamdeveloper.resmi_weather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tsdreamdeveloper.resmi_weather.R;
import com.tsdreamdeveloper.resmi_weather.pojo.City;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    /**
     * The interface that receives onClick item.
     */
    public interface OnItemClickListener {
        void onItemClick(City city);
    }

    private LayoutInflater inflater;
    private List<City> cities;
    private final OnItemClickListener listener;

    public CityAdapter(Context context, List<City> cities, OnItemClickListener onItemClickListener) {
        this.cities = cities;
        this.inflater = LayoutInflater.from(context);
        listener = onItemClickListener;
    }

    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.city_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.ViewHolder holder, int position) {
        City city = cities.get(position);
        holder.nameView.setText(city.getName());
        holder.bind(city, listener);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;

        ViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.cityNameTextView);
        }

        void bind(final City item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public void setCities(String s) {
        cities.add(new City(s));
        notifyDataSetChanged();
    }


}
