package com.example.su.waterquality.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by su on 2017/1/12.
 */

public class HttpGetRes {

    @SerializedName("status")
    private boolean status;

    @SerializedName("list")
    private List<WaterData> mWaterDataList;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<WaterData> getWaterDataList() {
        return mWaterDataList;
    }

    public void setWaterDataList(List<WaterData> waterDataList) {
        mWaterDataList = waterDataList;
    }
}
