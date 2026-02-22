package com.example.time.bean;


import android.health.connect.datatypes.BodyTemperatureRecord;
import android.health.connect.datatypes.units.Temperature;

import javax.xml.transform.Result;
import java.util.List;

public class WeatherResponse {
    private Result result;
    public Result getResult(){
        return result;
    }
    public void setResult(Result result){
        this.result = result;
    }
    public static class Result{
        private Daily daily;

        public Daily getDaily() {
            return daily;
        }

        public void setDaily(Daily daily){
            this.daily = daily;
        }
        public static class Daily{
            private List<Temperature> temperature;

            public List<Temperature> getTemperature(){
                return temperature;
            }

            public void seTemperature(List<Temperature> temperature){
                this.temperature = temperature;
            }

            public static class Temperature{
                  String date;
                  int max;
                  int min;
                  double avg;
            }
        }
    }



}
