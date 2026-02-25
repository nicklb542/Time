package com.example.time.bean;

/*
 * description:Gson解析对应JSON数据结构（3天的气温数据）
 * auther:龙斌
 * email:2275201369@qq.com
 * date:2025-02-24
 * */
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyResponse {
    private DailyResponse.Result result;
    public DailyResponse.Result getResult(){
        return result;
    }
    public static class Result {
        private DailyResponse.Result.Daily daily;
        public DailyResponse.Result.Daily getDaily(){
            return daily;
        }
        public static class Daily{
            @SerializedName("temperature")
            private List<TemperatureItem> temperatureList;
            public List<TemperatureItem> getTemperatureList(){
                return temperatureList;
            }

            public static class TemperatureItem{
                private String date;
                private double max,min,avg;
                public String getDate(){
                    return date;
                }
                public double getMax(){
                    return max;
                }
                public double getMin(){
                    return min;
                }
                public double getAvg(){
                    return avg;
                }
            }
        }
    }
}
