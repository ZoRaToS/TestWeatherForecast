package com.example.blackclover.testweatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {
    String TAG = "WEATHER";
    TextView cityName;
    TextView updatedField;
    TextView weatherIcon;
    TextView detalesField;
    TextView currentTemp;
    WeatherAPI.ApiInterface api;
    Typeface weatherFont;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        cityName =  findViewById(R.id.city_field);
        updatedField =  findViewById(R.id.updated_field);
        weatherIcon =  findViewById(R.id.weather_icon);
        detalesField =  findViewById(R.id.details_field);
        currentTemp =  findViewById(R.id.current_temperature_field);
        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weather.ttf");
        weatherIcon.setTypeface(weatherFont);
        getWeather(v);
    }

    /**
     * метод getWeather() отримує данні поточної погоди та данні прогнозу погоди з сервісу
     * openweathermap.org
     * @param v
     */

    public void getWeather(View v) {
        String city_name = "Khmelnytskyi";
        String units = "metric";
        String language = "ua";
        String api_key = WeatherAPI.API_KEY;
        //get current weather
        Call<WeatherDay> callCurrentWeather = api.getToday(city_name, units, language, api_key);
        callCurrentWeather.enqueue(new Callback<WeatherDay>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {
                Log.d(TAG, "onResponce");
                Log.d(TAG, response.toString());
                if (response.isSuccessful()) {
                    WeatherDay data = response.body();
                    Log.d(TAG, data.getTimezone() + "");
                    Log.d(TAG, response.toString());
                    cityName.setText(data.getCity()
                            .toUpperCase(new Locale("uk", "UA")));
                    updatedField.setText(data.getDateForUpdate());
                    setWeatherIcon(data.getId(), data.getSunrise(), data.getSunset());
                    detalesField.setText(data.getDescription()
                            .toUpperCase(new Locale("uk", "UA"))
                            + "\n" + "Вологість: " + data.getHumidity() + "%"
                            + "\n" + "Атмосферний тиск: " + data.getPreassure() + " hPa");
                    currentTemp.setText(data.getTempWithDegree());
                }
            }

            @Override
            public void onFailure(Call<WeatherDay> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
            }
        });

       //get forecast

    }



    private void setWeatherIcon(int actualID, long sunrise, long sunset) {
        int id = actualID / 100;
        String icon = "";
        if (actualID == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime <= sunset) {
                icon = getString(R.string.weather_sunny);
            } else {
                icon = getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }
}