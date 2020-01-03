package com.project.matur.api;

import com.project.matur.model.GetLaporan;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("laporan/login")
    Call<ResponseBody> login(@Field("nim") String nim,
                             @Field("password") String password);

    @GET("laporan?status_id=4")
    Call<GetLaporan> getLaporan();

}
