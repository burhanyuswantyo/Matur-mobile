package com.project.matur.model;

import com.google.gson.annotations.SerializedName;

public class Laporan {
    @SerializedName("gambar")
    private String gambar;

    @SerializedName("deskripsi")
    private String deskripsi;

    @SerializedName("kategori")
    private String kategori;

    @SerializedName("status")
    private String status;

    public Laporan(){}

    public Laporan(String gambar, String deskripsi, String kategori, String status) {
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
        this.status = status;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
