package com.example.time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //初始化登录页面
    private EditText editTextpass,editTextcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnlog = findViewById(R.id.btnlog);
        Button btnregister = findViewById(R.id.btnregister);
        btnlog.setOnClickListener(this);
        btnregister.setOnClickListener(this);

        editTextpass = findViewById(R.id.editTextpass);
        editTextcount = findViewById(R.id.editTextcount);
    }


    @Override
    //实现登录与注册功能
    public void onClick(View v){
        int btn=v.getId();
        //常见的登录反馈
        if(btn == R.id.btnlog){
            String inputcount = editTextcount.getText().toString();
            String inputpass = editTextpass.getText().toString();
            if(inputcount.equals("admin")){
                 if(inputpass.equals("123")){
                     loginSuccess(inputcount,inputpass);
              }//登录成功
                else {
                     Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                 }
            }
            else{
                 Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
        //常见的注册反馈
        if(btn == R.id.btnregister){
            String inputcount = editTextcount.getText().toString();
            String inputpass = editTextpass.getText().toString();
            if(inputcount == "83484"){
                Toast.makeText(this, "该账号已存在", Toast.LENGTH_SHORT).show();
            }
            if(inputpass.isEmpty()){
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "注册成功，请重新输入密码登录", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void loginSuccess(String username,String password){

        LoginActivity.startActivity(this,username,password);
        Toast.makeText(this,"登陆成功",Toast.LENGTH_SHORT).show();
    }
}