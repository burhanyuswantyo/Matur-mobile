package com.project.matur.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.matur.R;
import com.project.matur.api.ApiClient;
import com.project.matur.api.ApiInterface;
import com.project.matur.api.RetrofitClient;
import com.project.matur.api.Session;
import com.project.matur.model.GetKategori;
import com.project.matur.model.GetResponse;
import com.project.matur.model.Kategori;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddActivity extends AppCompatActivity {
    EditText edtDeskripsi;
    ImageView ivGambar;
    Spinner spKategori;
    Button btnKirim, btnUpload;
    ProgressDialog progressDialog;
Bitmap bitmap;


    Retrofit retrofit;
    ApiInterface apiInterface;
    Context context = AddActivity.this;

    private long time = System.currentTimeMillis() / 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnUpload = (Button) findViewById(R.id.btnUpload);
        ivGambar = (ImageView) findViewById(R.id.ivGambar);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        edtDeskripsi = (EditText) findViewById(R.id.edtDeskripsi);
        spKategori = (Spinner) findViewById(R.id.spKategori);
        btnKirim = (Button) findViewById(R.id.btnKirim);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        ButterKnife.bind(AddActivity.this);
        context = AddActivity.this;
        retrofit = ApiClient.connect();

        Session session = Session.init(context);
        String user_id = session.getString("id");

        apiInterface = ApiClient.connect().create(ApiInterface.class);


        kategori();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(user_id,String.valueOf(spKategori.getSelectedItemId()+1));
            }
        });

        ivGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    ivGambar.setImageBitmap(bitmap);
                    ivGambar.setVisibility(View.VISIBLE);
                    btnUpload.setVisibility(View.GONE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadImage(String user_id, String kategori_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiClient.URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    Toast.makeText(getApplicationContext(), Response, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String gambar = imagetoString(bitmap);
                params.put("gambar", gambar);
                params.put("deskripsi", edtDeskripsi.getText().toString());
                params.put("date_created", String.valueOf(time));
                params.put("user_id", user_id);
                params.put("kategori_id", kategori_id);
                params.put("status_id", "1");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddActivity.this);
        requestQueue.add(stringRequest);
    }

    private String imagetoString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageType = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageType, Base64.DEFAULT);
    }

    public void kategori() {
        RetrofitClient client = new RetrofitClient("http://192.168.1.152/matur/api/laporan/kategori/");
        client.getServies().getKategori().enqueue(new Callback<GetKategori>() {
            @Override
            public void onResponse(Call<GetKategori> call, Response<GetKategori> response) {
                List<Kategori> kategoriList = response.body().getListDataKategori();
                Log.d("Retrofit Get", "Jumlah data laporan : " + String.valueOf(kategoriList.size()));
                List<String> listSpinner = new ArrayList<String>();
                for (int i = 0; i < kategoriList.size(); i++) {
                    listSpinner.add(kategoriList.get(i).getKategori().toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spKategori.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetKategori> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}
