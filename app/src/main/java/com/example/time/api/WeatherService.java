package com.example.time.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.example.time.bean.RealResponse;
import com.example.time.bean.DailyResponse;

public interface WeatherService {

    @GET("v2.6/pYLHWBhXj6bkqXUh/{lon},{lat}/realtime")
    Call<RealResponse> getRealResponse(@Path("lon") double lon, @Path("lat") double lat);

    @GET("v2.6/pYLHWBhXj6bkqXUh/{lon},{lat}/daily?dailysteps=3")
    Call<DailyResponse> getDailyResponse(@Path("lon") double lon, @Path("lat") double lat);
}