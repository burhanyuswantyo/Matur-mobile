package com.project.matur.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetKategori {
    @SerializedName("status")
    String status;

    @SerializedName("data")
    List<Kategori> listDataKategori;

    @SerializedName("message")
    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Kategori> getListDataKategori() {
        return listDataKategori;
    }

    public void setListDataKategori(List<Kategori> listDataKategori) {
        this.listDataKategori = listDataKategori;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}