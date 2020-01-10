package com.project.matur.api;

import com.project.matur.model.GetKategori;
import com.project.matur.model.GetLaporan;
import com.project.matur.model.GetResponse;
import com.project.matur.model.PostPutDelLaporan;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("laporan/login")
    Call<ResponseBody> login(@Field("nim") String nim,
                             @Field("password") String password);

    @GET("laporan")
    Call<GetLaporan> getLaporan(@Query("user_id") String user_id,
                                @Query("status_id") String status_id);

    @GET("laporan/kategori")
    Call<GetKategori> getKategori();

    @Multipart
    @POST("laporan")
    Call<GetResponse> uploadImage(@Part MultipartBody.Part file,
                                  @Part("deskripsi") RequestBody deskripsi,
                                  @Part("gambar") RequestBody gambar,
                                  @Part("date_created") RequestBody date_created,
                                  @Part("user_id") RequestBody user_id,
                                  @Part("kategori_id") RequestBody kategori_id,
                                  @Part("status_id") RequestBody status_id);

    @FormUrlEncoded
    @PUT("laporan")
    Call<PostPutDelLaporan> putLaporan(@Field("id") String id,
                                       @Field("nama") String nama,
                                       @Field("nomor") String nomor);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "laporan", hasBody = true)
    Call<PostPutDelLaporan> deletLaporan(@Field("id") String id);


}
