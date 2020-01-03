package com.project.matur.model;

import com.google.gson.annotations.SerializedName;

public class PostPutDelLaporan {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    Laporan laporan;
    @SerializedName("message")
    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Laporan getLaporan() {
        return laporan;
    }

    public void setLaporan(Laporan laporan) {
        this.laporan = laporan;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
