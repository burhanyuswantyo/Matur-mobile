package com.project.matur.model;

import com.google.gson.annotations.SerializedName;

public class Kategori {
    @SerializedName("id")
    private String id;

    @SerializedName("kategori")
    private String kategori;


    public Kategori(){}

    public Kategori(String id, String kategori) {
        this.id = id;
        this.kategori = kategori;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
