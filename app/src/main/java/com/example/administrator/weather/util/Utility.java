package com.example.administrator.weather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.administrator.weather.model.City;
import com.example.administrator.weather.model.Country;
import com.example.administrator.weather.model.Province;
import com.example.administrator.weather.model.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

/**
 * Created by Administrator on 2016/8/19.
 */
public class Utility {
    public synchronized static boolean handleProvincesResponse(Weather weatherDB , String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if(allProvinces != null && allProvinces.length >  0){
                for (String p : allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    public  static boolean handleCitiesResponse(Weather weatherDB, String response ,int provinceId){
        if(!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if(allCities != null && allCities.length > 0){
                for (String c : allCities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    weatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    public static boolean handleCountiesResponse(Weather weatherDB , String response , int cityId){
        if(!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if(allCounties != null && allCounties.length > 0 ){
                for (String c :allCounties){
                    String[] array = c.split("\\|");
                    Country country = new Country();
                    country.setCoutryCode(array[0]);
                    country.setCoutryName(array[1]);
                    country.setCityId(cityId);
                    weatherDB.saveCountry(country);
                }
                return true;
            }
        }
        return false;
    }
    public static void handleWeatherResponse(Context context,String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1  = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context , cityName , weatherCode , temp1 , temp2 , weatherDesp , publishTime);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1,
                                        String temp2, String weatherDesp, String publishTime) {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected" , true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1" , temp1);
        editor.putString("temp2" , temp2);
        editor.putString("weather_desp" , weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date" , sdf.format(new Date()));
        editor.commit();
    }
}
