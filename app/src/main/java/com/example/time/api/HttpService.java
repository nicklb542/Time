package com.example.time.api;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.example.time.bean.WeatherResponse;

public interface HttpService {

    @GET("v2.6/pYLHWBhXj6bkqXUh/101.6656,39.2072/weather?")
    Call<WeatherResponse> get(@Query("alert") String alert,@Query("daylisteps") int Daylisteps );
}