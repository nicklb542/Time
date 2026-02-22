package com.example.time;
import com.example.time.api.HttpService;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView textView;
    private WeatherFragment mWeatherFragment;
    private CalenderFragment mCanlenderFragment;
    private MemoFragment mMemoFragment;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder().baseUrl("https://api.caiyunapp.com/v2.6/pYLHWBhXj6bkqXUh/101.6656,39.2072/weather").addConverterFactory(GsonConverterFactory.create()).build();

        HttpService httpService = retrofit.create(HttpService.class);

        //初始化底部导航栏
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        selectFragment(0);

        //检测按钮点击
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.weather){
                    selectFragment(0);
                } else if (menuItem.getItemId() == R.id.memo) {
                    selectFragment(1);
                } else{
                    selectFragment(2);
                }
            }
        });
    }

    //页面跳转
    public void selectFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        if (position == 0) {
            if (mWeatherFragment == null) {
                mWeatherFragment = new WeatherFragment();
                fragmentTransaction.add(R.id.content, mWeatherFragment);
            } else {
                fragmentTransaction.show(mWeatherFragment);
            }
        } else if (position == 1) {
            if (mMemoFragment == null) {
                mMemoFragment = new MemoFragment();
                fragmentTransaction.add(R.id.content, mMemoFragment);
            } else {
                fragmentTransaction.show(mMemoFragment);
            }
        } else {
            if (mCanlenderFragment == null) {
                mCanlenderFragment = new CalenderFragment();
                fragmentTransaction.add(R.id.content, mCanlenderFragment);
            } else {
                fragmentTransaction.show(mCanlenderFragment);
            }
        }

        //提交
        fragmentTransaction.commit();
    }

    //隐藏其他页面
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mWeatherFragment != null) {
            fragmentTransaction.hide(mWeatherFragment);
        }

        if (mMemoFragment != null) {
            fragmentTransaction.hide(mMemoFragment);
        }

        if (mCanlenderFragment != null) {
            fragmentTransaction.hide(mCanlenderFragment);
        }
    }
}