package com.example.time.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatebaseHelper extends SQLiteOpenHelper {

    //context 上下文 name 数据库名 version 版本号 factory:null 默认
    public DatebaseHelper(@Nullable Context context) {
        super(context, "笔记存储", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //第一次创建时回调
        String sql="create table date(title TEXT,content TEXT, create_time INTEGER)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {

    }
}
