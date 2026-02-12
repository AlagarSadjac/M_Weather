package com.nature.m_weather;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("main")
    private Main main;
    @SerializedName("name")
    private String name;

    public Main getMain() { return main; }
    public String getName() { return name; }

    public static class Main {
        @SerializedName("temp")
        private double temp;

        public double getTemp() { return temp; }
    }
}