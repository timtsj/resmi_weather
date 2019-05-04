package com.tsdreamdeveloper.resmi_weather.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tsdreamdeveloper.resmi_weather.Adapter.CityAdapter;
import com.tsdreamdeveloper.resmi_weather.R;
import com.tsdreamdeveloper.resmi_weather.pojo.City;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mCityRecyclerView;
    private CityAdapter mCityAdapter;
    private EditText mEditText;
    private ImageButton mImageButton;
    List<City> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setInitialData();
        initRecyclerView();
    }

    /**
     * Initial view
     */
    private void initView() {
        mEditText = findViewById(R.id.editText);
        mImageButton = findViewById(R.id.addCityButton);
        mCityRecyclerView = findViewById(R.id.cityRecyclerView);
    }

    /**
     * Implement RecyclerView and CityAdapter
     */
    private void initRecyclerView() {
        mCityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCityAdapter = new CityAdapter(this, cities, new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City city) {
                Intent weatherDetailIntent = new Intent(MainActivity.this, DetailWeatherActivity.class);
                weatherDetailIntent.putExtra(DetailWeatherActivity.EXTRA_KEY, city.getName());
                startActivity(weatherDetailIntent);
            }
        });
        mCityRecyclerView.setAdapter(mCityAdapter);

    }

    /**
     * Add DummyData and new City
     */
    private void setInitialData() {
        cities.add(new City("Astana"));
        cities.add(new City("London"));
        cities.add(new City("Moscow"));

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mCityAdapter.setCities(mEditText.getText().toString());
                    mEditText.getText().clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
