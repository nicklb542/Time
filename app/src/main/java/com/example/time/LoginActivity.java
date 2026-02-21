package com.example.time;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView(){
        textView=findViewById(R.id.tv_home_word);
    }
    public static void startActivity(Context context, String username, String password){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("密码",password);
        context.startActivity(intent);
    }
}
