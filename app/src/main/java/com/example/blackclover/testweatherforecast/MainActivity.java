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

    public void goToPageByTimer() {
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
    //public void addListenerOnButton() {
    // getWeateherBtn = findViewById(R.id.getWeather);
//        getWeateherBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =
//                        new Intent(MainActivity.this, WeatherActivity.class);
//                startActivity(intent);
//            }
//        });
//   }


}





