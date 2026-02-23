package com.example.time.ui;
import com.example.time.R;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.time.db.DatebaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView textView;
    private WeatherFragment mWeatherFragment;
    private MemoFragment mMemoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建数据库
        DatebaseHelper helper=new DatebaseHelper(this);
        helper.getWritableDatabase();

        //初始化底部导航栏
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //初始进入页面：天气
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
        } else {
            if (mMemoFragment == null) {
                mMemoFragment = new MemoFragment();
                fragmentTransaction.add(R.id.content, mMemoFragment);
            } else {
                fragmentTransaction.show(mMemoFragment);
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
    }


}