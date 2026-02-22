package com.example.time.api;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.example.time.bean.WeatherResponse;

public interface HttpService {

    @GET
    Call<WeatherResponse> get(@Query("Daylisteps") int Daylisteps );
}