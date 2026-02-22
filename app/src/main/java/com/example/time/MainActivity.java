package com.example.time;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView textView;
    private WeatherFragment mWeatherFragment;
    private CalenderFragment mCanlenderFragment;
    private MemoFragment mMemoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        bottomNavigationView = findViewById(R.id.page1);

        selectFragment(8);
    }

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
        } else if (position == 2) {
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