package com.tsdreamdeveloper.resmi_weather.Utilities;

import com.tsdreamdeveloper.resmi_weather.pojo.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by TS on 5/5/2019.
 */

public interface WeatherAPIs {
    /*
    Get request to fetch city weather.Takes in two parameter-city name and API key.
    */
    @GET("data/2.5/weather")
    Call<Weather> getWeatherByCity(@Query("q") String city, @Query("units") String units, @Query("appid") String apiKey);
}
