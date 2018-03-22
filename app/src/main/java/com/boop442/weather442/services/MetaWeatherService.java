package com.boop442.weather442.services;

import com.boop442.weather442.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by boop442 on 3/22/2018.
 */

public class MetaWeatherService {
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
}
