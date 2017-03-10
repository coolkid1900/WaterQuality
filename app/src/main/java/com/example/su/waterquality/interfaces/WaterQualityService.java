package com.example.su.waterquality.interfaces;

import com.example.su.waterquality.beans.HttpGetRes;
import com.example.su.waterquality.beans.HttpPostRes;
import com.example.su.waterquality.beans.WaterData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by su on 2017/1/12.
 */

public interface WaterQualityService {
    @GET("/data/findAll_data")
    Call<HttpGetRes> getWaterData();

    @GET("/data/findByName")
    Call<HttpGetRes> getWaterDataByName(String name);

    @GET("/data/findByTypeAndName")
    Call<HttpGetRes> getWaterDataByTypeAndName(Integer type, String name);


    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("/data/upload")
    Call<HttpPostRes> postWaterData(@Body WaterData waterData);
}
