package com.example.time;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView textView;
    private WeatherFragment mHomeFragment;
    private CalenderFragment mSettingFragment;
    private MemoFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        bottomNavigationView = findViewById(R.id.page1);

        selectFragment(8);
    }

    public void selectFragment(int position){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        if(position == 0){
            if(mHomeFragment==null){
                mHomeFragment = new WeatherFragment();
                fragmentTransaction.add(R.id.content,mHomeFragment);
            }
            else{
                fragmentTransaction.show(mHomeFragment);
            }
        } else if (position == 1) {
            if(mListFragment==null){
                mListFragment = new MemoFragment();
                fragmentTransaction.add(R.id.content,mListFragment);
            }
            else{
                fragmentTransaction.show(mListFragment);
            }
        } else if (position == 2) {
            if(mSettingFragment==null){
                mSettingFragment = new CalenderFragment();
                fragmentTransaction.add(R.id.content,mSettingFragment);
            }
            else{
                fragmentTransaction.show(mSettingFragment);
            }
        }


        //提交
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction){
        if(mHomeFragment != null){
            fragmentTransaction.hide(mHomeFragment);
        }

        if(mListFragment != null){
            fragmentTransaction.hide(mListFragment);
        }

        if(mSettingFragment != null){
            fragmentTransaction.hide(mSettingFragment);
        }
    }
}
