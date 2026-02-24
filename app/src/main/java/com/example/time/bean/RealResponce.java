package com.example.time.bean;

public class RealResponce {
    private Result result;
    public Result getResult(){
        return result;
    }
    public static class Result{
        private Realtime realtime;

        public Realtime getRealtime() {
            return realtime;
        }
        public static class Realtime{
            private double temperature,visibility,apparent_tempreature;
            private String skycon;
            private AirQuality air_quality;

            public double getTemperature() {
                return temperature;
            }

            public double getVisibility() {
                return visibility;
            }

            public double getApparent_tempreature(){
                return apparent_tempreature;
            }

            public String getSkycon(){
                return skycon;
            }

            public AirQuality getAir_quality() {
                return air_quality;
            }

            public class AirQuality{
                private int pm25,pm10;
                private Aqi aqi;
                private Description description;

                public int getPm25() {
                    return pm25;
                }

                public int getPm10() {
                    return pm10;
                }

                public Aqi getAqi() {
                    return aqi;
                }

                public Description getDescription(){
                    return description;
                }
                public class Aqi{
                    private int chn;

                    public int getChn() {
                        return chn;
                    }
                }

                public class Description{
                    private String chn;
                    public String getChn(){
                        return chn;
                    }
                }
            }
        }
    }

}
