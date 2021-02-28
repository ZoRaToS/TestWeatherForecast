package com.example.blackclover.testweatherforecast;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

public class WeatherDay {

    public class WeatherTemp {
        Double currentTemp;
        Double minTemp;
        Double maxTemp;
    }

    public class WeatherDescription {
        Integer actualID;
    }

    public class WeatherPreassure{
        Integer preassure;
    }
    public class WeatherHumidity{
        Integer humidity;
    }

    @SerializedName("main")
    private WeatherTemp temp;

    @SerializedName("main")
    private WeatherPreassure preassure;

    @SerializedName("main")
    private WeatherHumidity humidity;

    @SerializedName("weather")
    private List<WeatherDescription> description;

    @SerializedName("name")
    String city;

    @SerializedName("dt")
    private long timestamp;



    public WeatherDay(WeatherTemp temp,
                      List<WeatherDescription> description,
                      WeatherPreassure preassure,
                      WeatherHumidity humidity) {

        this.temp = temp;
        this.description = description;
        this.preassure = preassure;
        this.humidity = humidity;

    }
    public Calendar getDate() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(timestamp * 1000);
        return date;
    }

    public String getTemp() { return String.valueOf(temp.currentTemp); }

    public String getTempMin() { return String.valueOf(temp.minTemp); }

    public String getTempMax() { return String.valueOf(temp.maxTemp); }

    public String getTempInteger() { return String.valueOf(temp.currentTemp.intValue()); }

    public String getTempWithDegree() { return String.valueOf(temp.currentTemp.intValue()) + "\u00B0"; }

    public String getCity() { return city; }

    public void setCity(String city){
        this.city = city;
    }

    public Integer getActualID() { return description.get(0).actualID; }

    public Integer getPreassure() { return preassure.preassure.intValue(); }

    public Integer getHumidity() { return humidity.humidity.intValue(); }
}
