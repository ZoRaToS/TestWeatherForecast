package com.example.blackclover.testweatherforecast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
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
    LinearLayout forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        cityName = findViewById(R.id.city_field);
        updatedField = findViewById(R.id.updated_field);
        weatherIcon = findViewById(R.id.weather_icon);
        detalesField = findViewById(R.id.details_field);
        currentTemp = findViewById(R.id.current_temperature_field);
        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weather.ttf");
        weatherIcon.setTypeface(weatherFont);
        forecast = findViewById(R.id.forecast);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_city) {
            showInputDialog();
        }
        return false;
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change city");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getWeather(v, input.getText().toString());
            }
        });
        builder.show();
    }

    /**
     * метод getWeather() отримує данні поточної погоди та данні прогнозу погоди з сервісу
     * openweathermap.org
     *
     * @param v
     */

    public void getWeather(View v, String city) {
//        // String city_name = "Khmelnytskyi";
        String units = "metric";
        String language = "ua";
        String api_key = WeatherAPI.API_KEY;
//        //get current weather
        Call<WeatherDay> callCurrentWeather = api.getToday(city, units, language, api_key);
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
                    cityName.setText(data.getCity());
                    updatedField.setText(data.getDateForUpdate());
                    weatherIcon.setText(setWeatherIcon(data.getId(), data.getSunrise(), data.getSunset()));
                    detalesField.setText(data.getDescription()
                            .toUpperCase()
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

        Call<WeatherForecast> callForecast = api.getForecast(city, units, language, api_key);
        callForecast.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                Log.d(TAG, "OnResponce");
                Log.d(TAG, response.toString());
                if (response.isSuccessful()) {
                    WeatherForecast dataForecast = response.body();
                    SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("E");

                    LinearLayout.LayoutParams paramsTextView =
                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);

                    LinearLayout.LayoutParams paramsImageView =
                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);

                    LinearLayout.LayoutParams paramsLinearLayout =
                            new LinearLayout.LayoutParams(200,
                                    500);
                    paramsLinearLayout.setMargins(50, 0, 50, 0);
                    forecast.removeAllViews();
                    for (WeatherDay weatherDay : dataForecast.getItems()) {
                        int hourTime = weatherDay.getDate().get(Calendar.HOUR_OF_DAY);
                        if (hourTime == 15) {
                            @SuppressLint("DefaultLocale")
                            String date = String.format("%d.%d.%d %d:%d",
                                    weatherDay.getDate().get(Calendar.DAY_OF_MONTH),
                                    weatherDay.getDate().get(Calendar.WEEK_OF_MONTH),
                                    weatherDay.getDate().get(Calendar.YEAR),
                                    weatherDay.getDate().get(Calendar.HOUR_OF_DAY),
                                    weatherDay.getDate().get(Calendar.MINUTE)
                            );
                            Log.d(TAG, date);
                            Log.d(TAG, weatherDay.getTempInteger());
                            Log.d(TAG, "---");
                            // child view wrapper
                            LinearLayout childLayout = new LinearLayout(WeatherActivity.this);
                            childLayout.setLayoutParams(paramsLinearLayout);
                            childLayout.setOrientation(LinearLayout.VERTICAL);

                            // show day of week
                            TextView tvDay = new TextView(WeatherActivity.this);
                            String dayOfWeek = formatDayOfWeek.format(weatherDay.getDate().getTime());
                            tvDay.setTextColor(Color.WHITE);
                            tvDay.setText(dayOfWeek);
                            tvDay.setLayoutParams(paramsTextView);
                            childLayout.addView(tvDay);

                            // show temp
                            TextView tvTemp = new TextView(WeatherActivity.this);
                            tvTemp.setText(weatherDay.getTempWithDegree());
                            tvTemp.setLayoutParams(paramsTextView);
                            tvTemp.setTextColor(Color.WHITE);
                            childLayout.addView(tvTemp);

                            // show image
                            TextView ivIcon = new TextView(WeatherActivity.this);
                            ivIcon.setTypeface(weatherFont);
                            ivIcon.setLayoutParams(paramsImageView);
                            ivIcon.setTextColor(Color.WHITE);
                            ivIcon.setText(setWeatherIcon(weatherDay.getId(), weatherDay.getSunrise(),
                                    weatherDay.getSunset()));
                            childLayout.addView(ivIcon);

                            forecast.addView(childLayout);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
            }
        });


    }


    private String setWeatherIcon(int actualID, long sunrise, long sunset) {
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
        return icon;
    }

}