package com.example.time.bean;

import java.util.List;

public class Note {
    private String title;
    private String content;
    private long create_time;

    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public long getCreate_time(){
        return create_time;
    }

    public void setTitle(String title){
        this.title=title;
    }
    public void setContent(String content){
        this.content=content;
    }
    public void setCreate_time(long create_time){
        this.create_time=create_time;
    }
}
