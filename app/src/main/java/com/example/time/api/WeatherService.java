package com.example.time.api;

/*
 * description:服务器api接口定义
 * auther:龙斌
 * email:2275201369@qq.com
 * date:2025-02-23
 * */
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