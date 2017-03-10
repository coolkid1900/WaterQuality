package com.example.su.waterquality.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by su on 2017/1/11.
 */

public class WaterData {

    @SerializedName("type")
    private Integer type;

    @SerializedName("name")
    private String  name;

    @SerializedName("date")
    private String date;

    @SerializedName("temperature")
    private Double temperature;

    @SerializedName("turbidity")
    private Double turbidity;

    @SerializedName("ph")
    private Double ph;

    @SerializedName("nitrogen")
    private Double nitrogen;

    @SerializedName("phosphorus")
    private Double  phosphorus;


    public WaterData(Integer type, String name, String date, Double temperature, Double turbidity, Double ph, Double nitrogen, Double phosphorus) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.temperature = temperature;
        this.turbidity = turbidity;
        this.ph = ph;
        this.nitrogen = nitrogen;
        this.phosphorus = phosphorus;
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

    public Double getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(Double nitrogen) {
        this.nitrogen = nitrogen;
    }

    public Double getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(Double phosphorus) {
        this.phosphorus = phosphorus;
    }

    @Override
    public String toString() {
        return "WaterData{" +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", temperature=" + temperature +
                ", turbidity=" + turbidity +
                ", ph=" + ph +
                ", nitrogen=" + nitrogen +
                ", phosphorus=" + phosphorus +
                '}';
    }
}

