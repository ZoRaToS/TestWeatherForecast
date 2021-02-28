package com.example.blackclover.testweatherforecast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String TAG = "WEATHER";
    TextView tvTemp;
    LinearLayout llForecast;
    WeatherAPI.ApiInterface api;
    EditText input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = new EditText(this);
        tvTemp = (TextView) findViewById(R.id.tvTemp);
        llForecast = (LinearLayout) findViewById(R.id.llForecast);
        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
    }

    public void getWeather(View v) {
        String city_name = "London";
        String units = "metric";
        String language = "ua";
        String api_key = WeatherAPI.API_KEY;
        Log.d(TAG, "OK");

        Call<WeatherDay> callToday = api.getToday(city_name, units, language, api_key);
        callToday.enqueue(new Callback<WeatherDay>() {
            @Override
            public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {
                Log.e(TAG, "onResponse");
                WeatherDay data = response.body();
                //Log.d(TAG,response.toString());

                if (response.isSuccessful()) {
                    tvTemp.setText(data.getCity() + " " + data.getTempWithDegree());
                    // Glide.with(MainActivity.this).load(data.getActualID()).into(tvImage);

                }
            }

            @Override
            public void onFailure(Call<WeatherDay> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
            }
        });
        /*
        // get weather forecast
        Call<WeatherForecast> callForecast = api.getForecast(String.valueOf(input), units, api_key);
        callForecast.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                Log.e(TAG, "onResponse");
                WeatherForecast data = response.body();
                //Log.d(TAG,response.toString());

                if (response.isSuccessful()) {
                    SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("E");
                    LinearLayout.LayoutParams paramsTextView = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    LinearLayout.LayoutParams paramsImageView = new LinearLayout.LayoutParams(convertDPtoPX(40, MainActivity.this),
                            convertDPtoPX(40, MainActivity.this));

                    int marginRight = convertDPtoPX(15, MainActivity.this);
                    LinearLayout.LayoutParams paramsLinearLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    paramsLinearLayout.setMargins(0, 0, marginRight, 0);

                    llForecast.removeAllViews();

                    for (WeatherDay day : data.getItems()) {
                        if (day.getDate().get(Calendar.HOUR_OF_DAY) == 15) {
                            String date = String.format("%d.%d.%d %d:%d",
                                    day.getDate().get(Calendar.DAY_OF_MONTH),
                                    day.getDate().get(Calendar.WEEK_OF_MONTH),
                                    day.getDate().get(Calendar.YEAR),
                                    day.getDate().get(Calendar.HOUR_OF_DAY),
                                    day.getDate().get(Calendar.MINUTE)
                            );
                            Log.d(TAG, date);
                            Log.d(TAG, day.getTempInteger());
                            Log.d(TAG, "---");

                            // child view wrapper
                            LinearLayout childLayout = new LinearLayout(MainActivity.this);
                            childLayout.setLayoutParams(paramsLinearLayout);
                            childLayout.setOrientation(LinearLayout.VERTICAL);

                            // show day of week
                            TextView tvDay = new TextView(MainActivity.this);
                            String dayOfWeek = formatDayOfWeek.format(day.getDate().getTime());
                            tvDay.setText(dayOfWeek);
                            tvDay.setLayoutParams(paramsTextView);
                            childLayout.addView(tvDay);

                            // show image
//                            ImageView ivIcon = new ImageView(MainActivity.this);
//                            ivIcon.setLayoutParams(paramsImageView);
//                            Glide.with(MainActivity.this).load(day.getIconUrl()).into(ivIcon);
//                            childLayout.addView(ivIcon);

                            // show temp
                            TextView tvTemp = new TextView(MainActivity.this);
                            tvTemp.setText(day.getTempWithDegree());
                            tvTemp.setLayoutParams(paramsTextView);
                            childLayout.addView(tvTemp);

                            llForecast.addView(childLayout);
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

    public int convertDPtoPX(int dp, Context ctx) {
        float density = ctx.getResources().getDisplayMetrics().density;
        int px = (int)(dp * density);
        return px;
    }

         */

    }
}




