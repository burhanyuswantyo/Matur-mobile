package com.project.matur.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLaporan {
    @SerializedName("status")
    String status;

    @SerializedName("data")
    List<Laporan> listDataLaporan;

    @SerializedName("message")
    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Laporan> getListDataLaporan() {
        return listDataLaporan;
    }

    public void setListDataLaporan(List<Laporan> listDataLaporan) {
        this.listDataLaporan = listDataLaporan;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
