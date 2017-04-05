package com.example.su.waterquality.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by su on 2017/1/11.
 */

public class WaterData {

    @SerializedName("type")
    private Integer type;

    @SerializedName("name")
    private String  name;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("latitude")
    private Double  latitude;

    @SerializedName("date")
    private String date;

    @SerializedName("temperature")
    private Double temperature;

    @SerializedName("turbidity")
    private Double turbidity;

    @SerializedName("ph")
    private Double ph;


    public WaterData(Integer type, String name, Double longitude, Double latitude,String date, Double temperature, Double turbidity, Double ph) {
        this.type = type;
        this.name = name;
        this.longitude=longitude;
        this.latitude=latitude;
        this.date = date;
        this.temperature = temperature;
        this.turbidity = turbidity;
        this.ph = ph;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(Double turbidity) {
        this.turbidity = turbidity;
    }

    public Double getPh() {
        return ph;
    }

    public void setPh(Double ph) {
        this.ph = ph;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "WaterData{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", date='" + date + '\'' +
                ", temperature=" + temperature +
                ", turbidity=" + turbidity +
                ", ph=" + ph +
                '}';
    }
}

