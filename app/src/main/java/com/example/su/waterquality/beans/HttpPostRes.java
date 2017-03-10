package com.example.su.waterquality.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by su on 2017/1/12.
 */

public class HttpPostRes {

    @SerializedName("status")
    private byte status;

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
