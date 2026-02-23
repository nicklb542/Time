package com.example.time.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicReference;

//数据库增删改查
public class Dao {

    private final DatebaseHelper mHelper;
    public Dao(Context context){
         mHelper=new DatebaseHelper(context);
    }
    //增
    public void insert(){

    }
    //删
    public void delete(){


    }
    //改
    public void update(){


    }
    //查
    public void query(){

    }
}
