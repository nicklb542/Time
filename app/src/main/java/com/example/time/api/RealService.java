package com.example.time.api;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RealService {

    @GET("v2.6/pYLHWBhXj6bkqXUh/101.6656,39.2072/realtime")
    Call<RealResponse> get();
}