package com.example.blackclover.testweatherforecast;

import android.telephony.ims.ImsReasonInfo;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class WeatherDay {

    public class MeteorologicalElements {
        Double temp;
        Double temp_min;
        Double temp_max;
        Double feels_like;
        Integer pressure;
        Integer humidity;

    }

    public class WeatherDescription {
        int id;
        String icon;
        String description;
    }

    public class SunriseSunset {
        long sunset;
        long sunrise;
    }


    @SerializedName("main")
    MeteorologicalElements met_elements;

    @SerializedName("weather")
    private List<WeatherDescription> description;

    @SerializedName("name")
    String city;

    @SerializedName("dt")
    private long timestamp;

    @SerializedName("sys")
    SunriseSunset sunriseSunset;

    @SerializedName("timezone")
    int timezone;

    public WeatherDay(MeteorologicalElements met_elements, List<WeatherDescription> description) {

        this.met_elements = met_elements;
        this.description = description;

    }

    public Calendar getDate() {

        TimeZone tz = TimeZone.getTimeZone("UTC");
        Calendar date = Calendar.getInstance(tz);
        date.setTimeInMillis(timestamp * 1000);
        return date;
    }

    public String getDateForUpdate() {
        DateFormat df = DateFormat.getDateTimeInstance();
        String date = df.format(new Date(timestamp * 1000));
        //return new SimpleDateFormat("dd/MM/yy HH:mm").format(timestamp * 1000);
        return date;
    }

    public String getTemp() {
        return String.valueOf(met_elements.temp);
    }

    public String getTempMin() {
        return String.valueOf(met_elements.temp_min.intValue()) + "\u00B0";
    }

    public String getTempMax() {
        return String.valueOf(met_elements.temp_max.intValue()) + "\u00B0";
    }

    public String getTempInteger() {
        return String.valueOf(met_elements.temp.intValue());
    }

    public String getTempFellsLike() {
        return String.valueOf(met_elements.feels_like.intValue()) + "\u00B0";
    }

    public String getTempWithDegree() {
        return String.valueOf(met_elements.temp.intValue())
                + "\u00B0";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPreassure() {
        return met_elements.pressure;
    }

    public Integer getHumidity() {
        return met_elements.humidity;
    }

    public Integer getId() {
        return description.get(0).id;
    }

    public String getDescription() {
        return description.get(0).description;
    }

    public long getSunset() {
        return sunriseSunset.sunset * 1000;
    }

    public long getSunrise() {
        return sunriseSunset.sunrise * 1000;
    }

    public int getTimezone() {
        return timezone;
    }

}
