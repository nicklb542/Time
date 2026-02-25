package com.example.time.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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

/**
 * description: 页面初始化，以及联网请求，获取位置
 * auther:姓名
 * email:2275201369@qq.com
 * date:2025-02-23
 * */
public class MainActivity extends AppCompatActivity {

    private TextView tvCity;
    private TextView tvSkycon;
    private TextView tvApparentTemperature;
    private TextView tvTemperature;
    private TextView tvPm25,tvPm10;
    private TextView tvAqi,tvDescription;
    private TextView tvTime1,tvTime2,tvTime3;
    private Button btFresh;
    private Button btThDay;
    private double lat;//纬度
    private double lon;//经度
    private RealResponse realResponse;
    private DailyResponse dailyResponse;
    private Retrofit retrofit;
    private Location location;


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

        btFresh=(Button) findViewById(R.id.bt_freshen);
        btThDay=(Button) findViewById(R.id.bt_thday);


        //创建并获取location对象
        LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);

        //检查有无定位权限
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1001);
            return;
        }

        //注册位置监听器
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                1,
                //回调监听器
                new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        locationUpdates(location);
                    }
                }
        );

        //缓存位置
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //更新位置
        locationUpdates(location);

        //创建Retrofit实例
        retrofit = new Retrofit.Builder().baseUrl("https://api.caiyunapp.com/").addConverterFactory(GsonConverterFactory.create()).build();

        WeatherService realService = retrofit.create(WeatherService.class);
        postAsyncReal(realService);

        fresh();
        thDay();


    }

    /*
     * description:获取并显示位置
     * auther:龙斌
     * email:2275201369@qq.com
     * date:2025-02-23
     * */
    public void locationUpdates(Location location){
        if(location!=null){
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

            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("您的位置是：\n");
            stringBuilder.append("经度");
            stringBuilder.append("\n纬度");
            tvCity.setText(stringBuilder.toString());
        }
    }

    //获取位置权限
    @Override
    public void onRequestPermissionsResult(int requestCode,@Nullable String[] permissions,@Nullable int[] grantResults){
        if(requestCode==1001){
            if(grantResults.length==0||grantResults[0]!= PackageManager.PERMISSION_GRANTED){
                tvCity.setText("请授予权限");
            }
        }
    }

    /*
    * Description:发送网络请求获取数据，url:https://api.caiyunapp.com/v2.6/pYLHWBhXj6bkqXUh/{lon},{lat}/realtime
    * auther:龙斌
    * email:2275201369@qq.com
    * date:2025-02-23
    * */
    public void postAsyncReal(WeatherService realService) {

        Call<RealResponse> call1 = realService.getRealResponse(lon,lat);
        call1.enqueue(new Callback<RealResponse>() {

            //请求完成
            @Override
            public void onResponse(@Nullable Call<RealResponse> call, @Nullable Response<RealResponse> response) {
                try{
                    if(response.body()!=null){
                        Gson gson = new Gson();
                        String jison = gson.toJson(response.body());
                        Log.e("DEBUGT","g"+jison);
                        RealResponse.Result result =response.body().getResult();
                        RealResponse.Result.Realtime realtime = result.getRealtime();
                        RealResponse.Result.Realtime.AirQuality air_quality = realtime.getAir_quality();

                        String skycon = switchSkycon(realtime.getSkycon());
                        tvSkycon.setText("天气："+skycon);

                        String ApparentTemperature = String.format("%.1f度",realtime.getApparent_temperature());
                        tvApparentTemperature.setText("体感温度："+ApparentTemperature+"°C");

                        String temperature = String.format("%.1f",realtime.getTemperature());
                        tvTemperature.setText("温度："+temperature+"°C");

                        String pm25 = String.valueOf(air_quality.getPm25());
                        tvPm25.setText("pm2.5："+pm25);

                        String pm10 = String.valueOf(air_quality.getPm10());
                        tvPm10.setText("pm1.0："+pm10);

                        RealResponse.Result.Realtime.AirQuality.Aqi aqi = air_quality.getAqi();
                        String aqi0 = String.valueOf(aqi.getChn());
                        tvAqi.setText("空气指数："+aqi0);

                        RealResponse.Result.Realtime.AirQuality.Description description = air_quality.getDescription();
                        String description0 = description.getChn();
                        tvDescription.setText("空气质量："+description0);
                    }else {
                        tvSkycon.setText("天气：");
                        tvApparentTemperature.setText("体感温度：");
                        tvTemperature.setText("温度：");
                        tvPm25.setText("pm2.5：");
                        tvPm10.setText("pm1.0：");
                        tvAqi.setText("空气指数：");
                        tvDescription.setText("空气质量：");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            //请求失败
            @Override
            public void onFailure(Call<RealResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this,"连接失败，请点击刷新按钮重试",Toast.LENGTH_SHORT).show();

            }
        });
    }

    /*
     * Description:发送网络请求获取数据（未来3天气温），url:https://api.caiyunapp.com/v2.6/pYLHWBhXj6bkqXUh/{lon},{lat}/dailysteps=3
     * auther:龙斌
     * email:3375201369@qq.com
     * date:2025-02-24
     * */
    public void postAsyncDaily(WeatherService dailyService){
        Call<DailyResponse> call = dailyService.getDailyResponse(lon,lat);
        call.enqueue(new Callback<DailyResponse>() {
            @Override
            public void onResponse(@Nullable Call<DailyResponse> call, @Nullable Response<DailyResponse> response) {
                try{if(response.body()!=null){
                    DailyResponse.Result result =response.body().getResult();
                    DailyResponse.Result.Daily daily = result.getDaily();
                    List<DailyResponse.Result.Daily.TemperatureItem> temperature = daily.getTemperatureList();
                    for (int i=0;i<3;i++){
                        DailyResponse.Result.Daily.TemperatureItem temp = temperature.get(i);
                        String date = temp.getDate();
                        String max =String.format("%.1f",temp.getMax());
                        String min =String.format("%.1f",temp.getMin());
                        String avg =String.format("%.1f",temp.getAvg());
                        date = date.substring(0,10);
                        switch (i){
                            case 0:tvTime1.setText("日期："+date+"\n最高气温："+max+"°C 最低气温："+min+"°C 平均气温："+avg+"°C\n");
                                break;
                            case 1:tvTime2.setText("日期："+date+"\n最高气温："+max+"°C 最低气温："+min+"°C 平均气温："+avg+"°C\n");
                                break;
                            case 2:tvTime3.setText("日期："+date+"\n最高气温："+max+"°C 最低气温："+min+"°C 平均气温："+avg+"°C\n");
                                break;
                        }
                    }
                }else{
                    for (int i=0;i<3;i++){
                        switch (i){
                            case 0:tvTime1.setText("日期：null\n"+" 最高气温：null"+" 最低气温：null"+" 平均气温：null");
                                break;
                            case 1:tvTime2.setText("日期：null\n"+" 最高气温：null"+" 最低气温：null"+" 平均气温：null");
                                break;
                            case 2:tvTime3.setText("日期：null\n"+" 最高气温：null"+" 最低气温：null"+" 平均气温：null");
                                break;
                        }
                    }

                }}catch(Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DailyResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"连接失败，请点击刷新按钮重试",Toast.LENGTH_SHORT).show();
            }
        });
    }


    //转化skycon
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

    public void fresh(){
        btFresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationUpdates(location);
                WeatherService realService = retrofit.create(WeatherService.class);
                postAsyncReal(realService);
            }
        });
    }
    public void thDay(){
        btThDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherService dailyService = retrofit.create(WeatherService.class);
                postAsyncDaily(dailyService);
            }
        });
    }
}