package com.example.time;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button btnlog = findViewById(R.id.btnlog);
        Button btnregister = findViewById(R.id.btnregister);
        btnlog.setOnClickListener(this);
        btnregister.setOnClickListener(this);
    }

    @Override
    //实现登录与注册功能
    public void onClick(View v){

    }
}