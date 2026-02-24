package com.example.time.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.time.bean.Note;

import java.util.ArrayList;
import java.util.List;

public class DatebaseHelper extends SQLiteOpenHelper {

    //context 上下文 name 数据库名 version 版本号 factory:null 默认
    public DatebaseHelper(@Nullable Context context) {
        super(context, "笔记", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //第一次创建时回调
        String sql = "create table date(title TEXT,content TEXT, create_time INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("笔记", null, null, null, null, null, "create_time DESC");
        if (cursor != null) {
            //遍历列表
            while (cursor.moveToNext()) {
                //读取数据
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                long create_time = cursor.getLong(cursor.getColumnIndexOrThrow("create_time"));
                //创建对象
                Note note = new Note();
                note.setTitle(title);
                note.setContent(content);
                note.setCreate_time(create_time);
                noteList.add(note);
            }
            cursor.close();
        }
        db.close();
        return noteList;
    }
}
