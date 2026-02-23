package com.example.time.bean;


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
            private List<Skycon> skycon;

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
                  public String getDate(){
                      return date;
                  }
                  public void setDate(String date){
                      this.date = date;
                  }

                  public int getMax(){
                      return max;
                  }
                  public void setMax(int max){
                      this.max = max;
                  }

                  public int getMin(){
                      return min;
                  }
                  public void setMin(int min){
                      this.min = min;
                  }

                  public double getAvg(){
                      return avg;
                  }
                  public void setAvg(double avg){
                      this.avg=avg;
                  }
            }

            public List<Skycon> getSkycon(){
                return skycon;
            }
            public void setSkycon(List<Skycon> skycon){
                this.skycon=skycon;
            }

            public static class Skycon{
                String value;//主要气象
                public String getValue(){
                    return value;
                }
                public void setValue(String value){
                    this.value=value;
                }
            }
        }
    }



}
