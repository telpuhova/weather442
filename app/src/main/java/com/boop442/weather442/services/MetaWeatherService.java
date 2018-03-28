package com.boop442.weather442.services;

import android.util.Log;

import com.boop442.weather442.Constants;
import com.boop442.weather442.models.Forecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by boop442 on 3/22/2018.
 */

public class MetaWeatherService {

    public static void getWoeid(String location, Callback callback) {
        //creating client
        OkHttpClient client = new OkHttpClient.Builder().build();

        //constructing URL to send requests to
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();
        urlBuilder.addPathSegment(Constants.LOCATION_QUERY_PARAMETER);
        urlBuilder.addPathSegment("search");
        urlBuilder.addQueryParameter("query", location);
        String url = urlBuilder.build().toString();

        //making request using new url
        Request request = new Request.Builder().url(url).build();

        //executing request, by calling it asynchronously
        Call call = client.newCall(request);
        call.enqueue(callback);//call.execute() to do it synchronously
    }

    public static void findForecast(String location, Callback callback) {

        //creating client
        OkHttpClient client = new OkHttpClient.Builder().build();

        //constructing URL to send requests to
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();
//        urlBuilder.addQueryParameter(Constants.LOCATION_QUERY_PARAMETER, location);
        urlBuilder.addPathSegment(Constants.LOCATION_QUERY_PARAMETER);
        urlBuilder.addPathSegment(location);
        String url = urlBuilder.build().toString();

        //making request using new url
        Request request = new Request.Builder().url(url).build();

        //executing request, by calling it asynchronously
        Call call = client.newCall(request);
        call.enqueue(callback);//call.execute() to do it synchronously
    }

    public static String processWoeidCall(Response response) {
        int woeid = 1;

        try {
            String jsonData = response.body().string();
//            JSONObject woeidJSON = new JSONObject(jsonData);
            JSONArray woeidArrJSON = new JSONArray(jsonData);
            Log.v("weatherSERVICE", woeidArrJSON.toString());

            woeid = woeidArrJSON.getJSONObject(0).getInt("woeid");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String woeidStr = String.valueOf(woeid);
        Log.v("weatherSERVICE_woeid", woeidStr);
        return woeidStr;
    }

    public static ArrayList<Forecast> processResults(Response response) {

        ArrayList<Forecast> forecasts = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            JSONObject weatherJSON = new JSONObject(jsonData);
            JSONArray forecastsJSON = weatherJSON.getJSONArray("consolidated_weather");
            for (int i=0; i < forecastsJSON.length(); i++) {
                JSONObject forecastJSON = forecastsJSON.getJSONObject(i);

                String date = forecastJSON.getString("applicable_date");
                String state = forecastJSON.getString("weather_state_name");
                double t = forecastJSON.getDouble("the_temp");
                double min_t = forecastJSON.getDouble("min_temp");
                double max_t = forecastJSON.getDouble("max_temp");
                String wind_dir = forecastJSON.getString("wind_direction_compass");
                double wind_speed = forecastJSON.getDouble("wind_speed");
                int humidity = forecastJSON.getInt("humidity");
                double air_pressure = forecastJSON.getDouble("air_pressure");

                Forecast forecast = new Forecast(date, state, t, min_t, max_t, wind_dir, wind_speed, humidity, air_pressure);
                forecasts.add(forecast);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return forecasts;
    }
}
