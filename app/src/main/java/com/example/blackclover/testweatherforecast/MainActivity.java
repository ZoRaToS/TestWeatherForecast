package com.example.blackclover.testweatherforecast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    //    String TAG = "WEATHER";
//    TextView tvTemp;
//    LinearLayout llForecast;
//    WeatherAPI.ApiInterface api;
//    EditText input;
//    ImageView tvImage;
    private Button getWeateherBtn;
    private static int timeOut = 3000;
    Timer t = new Timer();
    static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        addListenerOnButton();
        goToPageByTimer();
//        input = new EditText(this);
//        tvTemp = (TextView) findViewById(R.id.tvTemp);
//        llForecast = (LinearLayout) findViewById(R.id.llForecast);
//        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
//        tvImage = (ImageView) findViewById(R.id.ivImage);
    }
    public void goToPageByTimer(){
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        t.cancel();
                        Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }, timeOut, timeOut);
    }

    public void addListenerOnButton() {
       // getWeateherBtn = findViewById(R.id.getWeather);
        getWeateherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });
    }

//    public void getWeather(View v) {
//        String city_name = "Kyiv";
//        String units = "metric";
//        String language = "ua";
//        String api_key = WeatherAPI.API_KEY;
//        Log.d(TAG, "OK");
//
//        Call<WeatherDay> callToday = api.getToday(city_name, units, language, api_key);
//        callToday.enqueue(new Callback<WeatherDay>() {
//            @Override
//            public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {
//                Log.e(TAG, "onResponse");
//                WeatherDay data = response.body();
//                Log.d(TAG,response.toString());
//                Log.d(TAG,data.toString());
//                if (response.isSuccessful()) {
//                    tvTemp.setText(data.getCity() + " " + data.getTempInteger());
//                    Glide.with(MainActivity.this).load(data.getIconUrl()).into(tvImage);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WeatherDay> call, Throwable t) {
//                Log.e(TAG, "onFailure");
//                Log.e(TAG, t.toString());
//            }
//        });
//
//        // get weather forecast
//        Call<WeatherForecast> callForecast = api.getForecast(city_name, units, language, api_key);
//        callForecast.enqueue(new Callback<WeatherForecast>() {
//            @Override
//            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
//                //Log.e(TAG, "onResponse");
//                WeatherForecast data = response.body();
//                Log.d(TAG,response.toString());
//
//                if (response.isSuccessful()) {
//                    SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("E");
//                    LinearLayout.LayoutParams paramsTextView = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    LinearLayout.LayoutParams paramsImageView = new LinearLayout.LayoutParams(convertDPtoPX(40, MainActivity.this),
//                            convertDPtoPX(40, MainActivity.this));
//
//                    int marginRight = convertDPtoPX(15, MainActivity.this);
//                    LinearLayout.LayoutParams paramsLinearLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT);
//                    paramsLinearLayout.setMargins(0, 0, marginRight, 0);
//
//                    llForecast.removeAllViews();
//
//                    for (WeatherDay day : data.getItems()) {
//                        if (day.getDate().get(Calendar.HOUR_OF_DAY) == 15) {
//                            String date = String.format("%d.%d.%d %d:%d",
//                                    day.getDate().get(Calendar.DAY_OF_MONTH),
//                                    day.getDate().get(Calendar.WEEK_OF_MONTH),
//                                    day.getDate().get(Calendar.YEAR),
//                                    day.getDate().get(Calendar.HOUR_OF_DAY),
//                                    day.getDate().get(Calendar.MINUTE)
//                            );
//                            Log.d(TAG, date);
//                            Log.d(TAG, day.getTempInteger());
//                            Log.d(TAG, "---");
//
//                            // child view wrapper
//                            LinearLayout childLayout = new LinearLayout(MainActivity.this);
//                            childLayout.setLayoutParams(paramsLinearLayout);
//                            childLayout.setOrientation(LinearLayout.VERTICAL);
//
//                            // show day of week
//                            TextView tvDay = new TextView(MainActivity.this);
//                            String dayOfWeek = formatDayOfWeek.format(day.getDate().getTime());
//                            tvDay.setText(dayOfWeek);
//                            tvDay.setLayoutParams(paramsTextView);
//                            childLayout.addView(tvDay);
//
//                            // show image
//                            ImageView ivIcon = new ImageView(MainActivity.this);
//                            ivIcon.setLayoutParams(paramsImageView);
//                            Glide.with(MainActivity.this).load(day.getIconUrl()).into(ivIcon);
//                            childLayout.addView(ivIcon);
//
//                            // show temp
//                            TextView tvTemp = new TextView(MainActivity.this);
//                            tvTemp.setText(day.getTempWithDegree());
//                            tvTemp.setLayoutParams(paramsTextView);
//                            childLayout.addView(tvTemp);
//
//                            llForecast.addView(childLayout);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WeatherForecast> call, Throwable t) {
//                Log.e(TAG, "onFailure");
//                Log.e(TAG, t.toString());
//            }
//        });
//
//    }
//
//    public int convertDPtoPX(int dp, Context ctx) {
//        float density = ctx.getResources().getDisplayMetrics().density;
//        int px = (int) (dp * density);
//        return px;
//    }


}





