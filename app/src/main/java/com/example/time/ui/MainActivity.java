package com.example.time.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.time.R;
import com.example.time.api.RealService;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder().baseUrl("https://api.caiyunapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        realService= retrofit.create(RealService.class);

        View view=inflater.inflate(R.layout.fragment_weather,container,false);


        postAsync(view);
        return view;

    }

    //网络请求
    public void postAsync(View view) {

        MainActivity activity=(MainActivity) getActivity();
        call = realService.get();
        call.enqueue(new retrofit2.Callback<WeatherResponse>() {
            //请求完成
            @Override
            public void onResponse(@Nullable Call<WeatherResponse> call, @Nullable Response<WeatherResponse> response) {
                try{
                    if(response.body()!=null){

                    }else {
                        String error =response.errorBody() != null ? response.errorBody().string():"kong";
                        Log.e("DEBUG",error);
                        Log.e("DEBUG","bbbb");
                        max_now.setText("最高气温度？");
                        min_now.setText("最低气温"+"度");
                        avg_now.setText("平均气温"+"度");
                        return;
                    }
                }catch (Exception e){
                    Log.e("DEBUG","ddd");
                    e.printStackTrace();
                }
            }

            //请求失败
            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG,"uu"+t.getMessage(),t);
                MainActivity activity=(MainActivity) getActivity();
                activity.selectFragment(0);
            }
        });
    }



}