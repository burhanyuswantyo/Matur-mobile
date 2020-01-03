package com.project.matur.api;

import com.project.matur.model.GetLaporan;
import com.project.matur.model.PostPutDelLaporan;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("laporan/login")
    Call<ResponseBody> login(@Field("nim") String nim,
                             @Field("password") String password);

    @GET("laporan?status_id=4")
    Call<GetLaporan> getLaporan();

    @FormUrlEncoded
    @POST("laporan")
    Call<ResponseBody> postLaporan(@Field("deskripsi") String deskripsi,
                                        @Field("gambar") String gambar,
                                        @Field("date_created") String date_created,
                                        @Field("user_id") String user_id,
                                        @Field("kategori_id") String kategori_id,
                                        @Field("status_id") String status_id);
    @FormUrlEncoded
    @PUT("laporan")
    Call<PostPutDelLaporan> putLaporan(@Field("id") String id,
                                     @Field("nama") String nama,
                                     @Field("nomor") String nomor);
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "laporan", hasBody = true)
    Call<PostPutDelLaporan> deletLaporan(@Field("id") String id);
}
