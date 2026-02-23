package com.example.time;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.time.api.HttpService;
import com.example.time.bean.WeatherResponse;
import com.example.time.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class WeatherFragment extends Fragment {


    private static final String TAG = "MainActivity";
    private HttpService httpService;


    private TextView max_now;
    private TextView min_now;
    private TextView avg_now;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_weather,container,false);
        max_now = view.findViewById(R.id.max_now);
        min_now = view.findViewById(R.id.min_now);
        avg_now = view.findViewById(R.id.avg_now);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }


    //网络请求
    public void postAsync(View view) {
        retrofit2.Call<WeatherResponse> call= httpService.get("ture",1);
        call.enqueue(new retrofit2.Callback<WeatherResponse>() {
            //请求完成
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                try{
                    if(response.body()!=null){
                            WeatherResponse.Result result = response.body().getResult();
                            WeatherResponse.Result.Daily daily =response.body().getResult().getDaily();
                            List<WeatherResponse.Result.Daily.Temperature> tempeList = response.body().getResult().getDaily().getTemperature();
                            WeatherResponse.Result.Daily.Temperature temperature= tempeList.get(0);
                            int max = temperature.getMax();
                            int min = temperature.getMin();
                            double avg = temperature.getAvg();
                            max_now.setText("最高气温"+max+"度");
                            min_now.setText("最低气温"+min+"度");
                            avg_now.setText("平均气温"+avg+"度");

                    }else {
                        Log.e(TAG, "qqq"+response.code());
                    }
                }catch (Exception e){
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