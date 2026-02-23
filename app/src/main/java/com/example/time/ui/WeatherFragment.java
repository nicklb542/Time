package com.example.time.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.time.R;
import com.example.time.api.RealService;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherFragment extends Fragment {


    private static final String TAG = "MainActivity";
    private RealService realService;


    private TextView max_now;
    private TextView min_now;
    private TextView avg_now;
    private Retrofit retrofit;
    private retrofit2.Call<WeatherResponse> call;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("DEBUG","dd");
        retrofit = new Retrofit.Builder().baseUrl("https://api.caiyunapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        realService= retrofit.create(RealService.class);

        View view=inflater.inflate(R.layout.fragment_weather,container,false);
        max_now = view.findViewById(R.id.max_now);
        min_now = view.findViewById(R.id.min_now);
        avg_now = view.findViewById(R.id.avg_now);

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