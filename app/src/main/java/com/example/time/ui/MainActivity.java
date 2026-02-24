package com.example.time.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.time.R;
import com.example.time.api.WeatherService;
import com.example.time.bean.DailyResponse;
import com.example.time.bean.RealResponse;
import com.google.gson.Gson;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvCity;
    private TextView tvSkycon;
    private TextView tvApparentTemperature;
    private TextView tvTemperature;
    private TextView tvPm25,tvPm10;
    private TextView tvAqi,tvDescription;
    private TextView tvTime1,tvTime2,tvTime3;
    private double lat;//纬度
    private double lon;//经度
    private RealResponse realResponse;
    private DailyResponse dailyResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvSkycon=(TextView) findViewById(R.id.tv_skycon);
        tvCity=(TextView) findViewById(R.id.title_city);
        tvApparentTemperature=(TextView) findViewById(R.id.tv_apparenttemperature);
        tvTemperature=(TextView) findViewById(R.id.tv_temperature);
        tvPm25=(TextView) findViewById(R.id.tv_pm25);
        tvPm10=(TextView) findViewById(R.id.tv_pm10);
        tvAqi=(TextView) findViewById(R.id.tv_aqi);
        tvDescription=(TextView) findViewById(R.id.tv_description);
        tvTime1=(TextView) findViewById(R.id.tv_time1);
        tvTime2=(TextView) findViewById(R.id.tv_time2);
        tvTime3=(TextView) findViewById(R.id.tv_time3);


        //创建并获取location对象
        LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1001);
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                1,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        locationUpdates(location);
                    }
                }
        );
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationUpdates(location);

        Log.e("DEBUG","获取");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.caiyunapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        WeatherService realService = retrofit.create(WeatherService.class);
        postAsyncReal(realService);

        WeatherService dailyService = retrofit.create(WeatherService.class);
        postAsyncDaily(dailyService);
        Log.e("DEBUG","联网结束");

        locationUpdates(location);
        Log.e("DEBUG","显示?");

    }

    public void locationUpdates(Location location){
        Log.e("DEBUG","way");
        if(location!=null){
            Log.e("DEBUG","djdjjfglk");
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("您的位置是：\n");
            stringBuilder.append("经度");
            lon = location.getLongitude();
            stringBuilder.append(lon);
            stringBuilder.append("\n纬度");
            lat = location.getLatitude();
            stringBuilder.append(lat);
            tvCity.setText(stringBuilder.toString());
        }else{

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@Nullable String[] permissions,@Nullable int[] grantResults){
        if(requestCode==1001){
            if(grantResults.length==0||grantResults[0]!= PackageManager.PERMISSION_GRANTED){
                tvCity.setText("请授予权限");
            }
        }
    }
    //网络请求
    public void postAsyncReal(WeatherService realService) {

        Call<RealResponse> call = realService.getRealResponse(lon,lat);
        call.enqueue(new Callback<RealResponse>() {
            //请求完成
            @Override
            public void onResponse(@Nullable Call<RealResponse> call, @Nullable Response<RealResponse> response) {
                try{
                    if(response.body()!=null){
                        Gson gson = new Gson();
                        String jison = gson.toJson(response.body());
                        Log.e("DEBUGT","g"+jison);
                        RealResponse.Result result =response.body().getResult();
                        RealResponse.Result.Realtime realtime = result.getRealTime();
                        RealResponse.Result.Realtime.AirQuality airQuality = realtime.getAir_quality();

                        String skycon = switchSkycon(realtime.getSkycon());
                        tvSkycon.setText("天气："+skycon);

                        String ApparentTemperature = String.format("%.1f度",realtime.getApparent_temperature());
                        tvApparentTemperature.setText("体感温度："+ApparentTemperature);

                        String temperature = String.format("%.1f",realtime.getTemperature());
                        tvTemperature.setText("温度："+temperature);

                        String pm25 = String.format("%.1f",airQuality.getPm25());
                        tvPm25.setText("pm2.5：" + pm25);

                        String pm10 = String.format("%.1f",airQuality.getPm10());
                        tvPm10.setText("pm1.0："+pm10);

                        String aqi = String.format("%.f",airQuality.getAqi());
                        tvAqi.setText("空气指数："+aqi);

                        String description = airQuality.toString();
                        tvDescription.setText("空气质量："+description);
                    }else {

                        if(response.errorBody()!=null){
                        Log.e("DEBUG","kong"+response.errorBody().string());}

                    }
                }catch (Exception e){
                    Log.e("DEBUG","jjjjj");
                    e.printStackTrace();
                }
            }

            //请求失败
            @Override
            public void onFailure(Call<RealResponse> call, Throwable t) {
                    Log.e("DEBUG","连接失败");
            }
        });
    }

    public void postAsyncDaily(WeatherService dailyService){
        Call<DailyResponse> call = dailyService.getDailyResponse(lon,lat);
        call.enqueue(new Callback<DailyResponse>() {
            @Override
            public void onResponse(@Nullable Call<DailyResponse> call, @Nullable Response<DailyResponse> response) {
                if(response.body()!=null){
                    DailyResponse.Result result =response.body().getResult();
                    DailyResponse.Result.Daily daily = result.getDaily();
                    List<DailyResponse.Result.Daily.TemperatureItem> temperature = daily.getTemperatureList();
                    for (int i=0;i<3;i++){
                        DailyResponse.Result.Daily.TemperatureItem temp = temperature.get(i);
                        String date = temp.getDate();
                        String max =String.format("%.1f",temp.getMax());
                        String min =String.format("%.1f",temp.getMin());
                        String avg =String.format("%.1f",temp.getAvg());
                        switch (i){
                            case 0:tvTime1.setText("日期："+date+"最高气温："+max+"最低气温："+min+"平均气温："+avg);
                                break;
                            case 1:tvTime2.setText("日期："+date+"最高气温："+max+"最低气温："+min+"平均气温："+avg);
                                break;
                            case 2:tvTime3.setText("日期："+date+"最高气温："+max+"最低气温："+min+"平均气温："+avg);
                                break;
                        }
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<DailyResponse> call, Throwable t) {

            }
        });
    }

    //
    public String switchSkycon(String skycon){
        switch (skycon){
            case "CLEAR_DAY":
                return "晴天";
            case "CLEAR_NIGHT":
                return "晴夜";
            case "PARTLY_CLOUDY_DAY":
                return "多云";
            case "PARTLY_CLOUDY_NIGHT":
                return "多云夜";
            case "CLOUDY":
                return "阴天";
            case "LIGHT_HAZE":
                return "轻度雾霾";
            case "MODERATE_HAZE":
                return "中度雾霾";
            case "HEAVY_HAZE":
                return "重度雾霾";
            case "LIGHT_RAIN":
                return "小雨";
            case "MODERATE_RAIN":
                return "中雨";
            case "HEAVY_RAIN":
                return "大雨";
            case "STORM_RAIN":
                return "暴雨";
            case "FOG":
                return "雾";
            case "LIGHT_SNOW":
                return "小雪";
            case "MODERATE_SNOW":
                return "中雪";
            case "HEAVY_SNOW":
                return "大雪";
            case "STORM_SNOW":
                return "暴雪";
            case "DUST":
                return "浮尘";
            case "SAND":
                return "沙尘";
            case "WIND":
                return "大风";
            default:
                return "未知天气";
        }
    }
}