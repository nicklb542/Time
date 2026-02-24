package com.example.time.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.time.R;
import com.example.time.api.RealService;
import com.example.time.bean.RealResponce;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.caiyunapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        RealService realService = retrofit.create(RealService.class);

        postAsync(realService);

    }

    //网络请求
    public void postAsync(RealService realService) {

        Call<RealResponce> call= realService.get();
        call.enqueue(new Callback<RealResponce>() {
            //请求完成
            @Override
            public void onResponse(@Nullable Call<RealResponce> call, @Nullable Response<RealResponce> response) {
                try{
                    if(response.body()!=null){

                    }else {
                        String error =response.errorBody() != null ? response.errorBody().string():"kong";
                        Log.e("DEBUG",error);
                        Log.e("DEBUG","bbbb");
                        return;
                    }
                }catch (Exception e){
                    Log.e("DEBUG","ddd");
                    e.printStackTrace();
                }
            }

            //请求失败
            @Override
            public void onFailure(Call<RealResponce> call, Throwable t) {
            }
        });
    }



}