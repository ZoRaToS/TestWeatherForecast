package com.example.blackclover.testweatherforecast;

import android.app.Activity;
import android.content.SharedPreferences;

public class CityPreferences {
    SharedPreferences preferences;

    public CityPreferences(Activity activity){
        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    String getCity(){
        return null;
    }
}
