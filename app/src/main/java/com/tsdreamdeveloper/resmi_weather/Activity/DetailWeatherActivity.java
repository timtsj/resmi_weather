package com.tsdreamdeveloper.resmi_weather.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tsdreamdeveloper.resmi_weather.R;
import com.tsdreamdeveloper.resmi_weather.Utilities.NetworkClient;
import com.tsdreamdeveloper.resmi_weather.Utilities.WeatherAPIs;
import com.tsdreamdeveloper.resmi_weather.pojo.Weather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailWeatherActivity extends AppCompatActivity {

    public static final String EXTRA_KEY = "mCityName";
    private static final String OWM_UNITS = "metric";
    private static final int OWM_API_KEY = R.string.owm_api_key;

    String mCityName;
    TextView mWeatherDataTextView;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);

        if (getIntent().hasExtra(EXTRA_KEY)) {
            mCityName = getIntent().getStringExtra(EXTRA_KEY);
        }

        mWeatherDataTextView = findViewById(R.id.textView);
        mProgressBar = findViewById(R.id.pb_loading_indicator);
        fetchWeatherDetails();
        hideData();
    }

    /**
     * Get data from openweathermap
     */
    public void fetchWeatherDetails() {

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        WeatherAPIs wordPressService = retrofit.create(WeatherAPIs.class);

        Call<Weather> call = wordPressService.getWeatherByCity(mCityName, OWM_UNITS, DetailWeatherActivity.this.getString(OWM_API_KEY));
        Log.v("LOG", "response: " + call.request().url().toString());

        call.enqueue(new Callback<Weather>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Weather> call, @NonNull Response<Weather> response) {

                if (response.isSuccessful()) {
                    Calendar calendar = Calendar.getInstance();
                    DateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    String lastUpdatedTime = simpleDateFormat.format(calendar.getTime());

                    showData();
                    Weather weather = response.body();

                    assert weather != null;
                    mWeatherDataTextView.setText(
                            weather.getName() + ", " + weather.getSys().getCountry() + "\n" +
                                    "Today: " + weather.getWeather().get(0).getMain() + "\n" +
                                    "Temp: " + weather.getMain().getTemp() + "\n" +
                                    "Humidity: " + weather.getMain().getHumidity() + "\n" +
                                    "Pressure: " + weather.getMain().getPressure() + "\n" +
                                    "Last updated: " + lastUpdatedTime);

                } else if (response.code() >= 400 && response.code() <= 499) {

                    Log.e("myResponse:", "MSG" + response.errorBody());
                    Toast.makeText(getApplicationContext(), "Incorrect city name", Toast.LENGTH_LONG).show();
                    finish();
                } else if (response.code() >= 500 && response.code() <= 529) {

                    Log.e("myResponse:", "MSG" + response.errorBody());
                    Toast.makeText(getApplicationContext(), "Server is not available", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Weather> call, @NonNull Throwable t) {
                Log.e("myResponse:", "MSG" + t.toString());
                Toast.makeText(getApplicationContext(), "Incorrect city name", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    /**
     * Show City weather data
     */
    private void showData() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mWeatherDataTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Hide City weather data
     */
    private void hideData() {
        mProgressBar.setVisibility(View.VISIBLE);
        mWeatherDataTextView.setVisibility(View.INVISIBLE);
    }

    /**
     * This is where we inflate and set up the menu for this Activity.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.detail_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    /**
     * Callback invoked when a menu item was selected from this Activity's menu.
     *
     * @param item The menu item that was selected by the user
     * @return true if you handle the menu click here, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:
                fetchWeatherDetails();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
