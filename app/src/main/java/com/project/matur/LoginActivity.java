package com.project.matur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.matur.api.ApiClient;
import com.project.matur.api.ApiInterface;
import com.project.matur.api.RetrofitClient;
import com.project.matur.api.Session;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnLongClickListener {

    EditText edtNim;
    EditText edtPassword;
    Button btnLogin;
    ApiInterface apiInterface;
    ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtNim = (EditText) findViewById(R.id.edt_nim);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        apiInterface = ApiClient.connect().create(ApiInterface.class);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient client = new RetrofitClient("http://192.168.1.152/matur/api/laporan/login/");
                client.getServies().login(edtNim.getText().toString(), edtPassword.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String json = response.body().string();
                            Log.e("Response", json);
                            JSONObject obj = new JSONObject(json);
                            boolean status = obj.getBoolean("status");
                            if (status == true) {
                                Toast.makeText(LoginActivity.this,"Login Berhasil", Toast.LENGTH_SHORT).show();
                                JSONObject user = obj.getJSONObject("data");
                                Session.init(LoginActivity.this)
                                        .set("id", user.getString("id"))
                                        .set("nim", user.getString("nim"))
                                        .set("password", user.getString("password"))
                                        .set("nama", user.getString("nama"))
                                        .set("prodi", user.getString("prodi"))
                                        .set("foto", user.getString("foto"));
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                onFailure(null, null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onLongClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

//    @Override
//    public void onClick(View view) {
//        Retrofit retrofit = ApiClient.connect();
//        ApiInterface service = retrofit.create(ApiInterface.class);
//        final Call<ResponseBody> request = service.login(
//                edtNim.getText().toString(),
//                edtPassword.getText().toString()
//        );
//        request.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String json = response.body().string();
//                    Log.e("Response", json);
//                    JSONObject obj = new JSONObject(json);
//                    Boolean status = obj.getBoolean("status");
//                    if (status==true) {
//                        Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
//                        JSONObject user = obj.getJSONObject("data");
//                        Session.init(LoginActivity.this)
//                                .set("id", user.getString("id"))
//                                .set("nim", user.getString("nim"))
//                                .set("nama", user.getString("nama"))
//                                .set("prodi", user.getString("prodi"))
//                                .set("foto", user.getString("foto"));
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Password salah!", Toast.LENGTH_SHORT).show();
//                        onFailure(null, null);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(LoginActivity.this, "Kowe salah!", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                if (t != null) {
//                    t.printStackTrace();
//                    ;
//                }
//                Toast.makeText(LoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}
