package com.example.blackclover.testweatherforecast;

import android.content.Context;

import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherAPI {

    //public static final String API_KEY = "ea7b1a34e6b303c75d56e8539489cca9";
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static Retrofit retrofit = null;

    public interface ApiInterface {
        @GET("weather")
        Call<WeatherDay> getToday(
                //@Query("lat") Double lat,
                //@Query("lon") Double lon,
                @Query("q") String cityName,
                @Query("units") String units,
                @Query("lang") String language,
                @Query("appid") String appid
        );

        @GET("forecast")
        Call<WeatherForecast> getForecast(
                //@Query("lat") Double lat,
                //@Query("lon") Double lon,
                @Query("q") String cityName,
                @Query("units") String units,
                @Query("lang") String language,
                @Query("appid") String appid
        );

    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .serializeNulls().excludeFieldsWithModifiers(Modifier.FINAL,
                                    Modifier.TRANSIENT, Modifier.STATIC).create())).build();

        }
        return retrofit;
    }


}
