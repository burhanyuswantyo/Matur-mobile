package com.project.matur.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.matur.MainActivity;
import com.project.matur.R;
import com.project.matur.api.ApiClient;
import com.project.matur.api.ApiInterface;
import com.project.matur.api.Session;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    EditText edtNim;
    EditText edtPassword;
    Button btnLogin;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtNim = findViewById(R.id.edt_nim);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        btnLogin.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        Retrofit retrofit = ApiClient.connect();
        ApiInterface service = retrofit.create(ApiInterface.class);
        final Call<ResponseBody> request = service.login(
                edtNim.getText().toString(),
                edtPassword.getText().toString()
        );
        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String json = response.body().string();
                    Log.e("Response", json);
                    JSONObject obj = new JSONObject(json);
                    Boolean status = obj.getBoolean("status");
                    if (status==true) {
                        Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        JSONObject user = obj.getJSONObject("data");
                        Session.init(LoginActivity.this)
                                .set("id", user.getString("id"))
                                .set("nim", user.getString("nim"))
                                .set("nama", user.getString("nama"))
                                .set("prodi", user.getString("prodi"))
                                .set("foto", user.getString("foto"));
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Password salah!", Toast.LENGTH_SHORT).show();
                        onFailure(null, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Kowe salah!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t != null) {
                    t.printStackTrace();
                    ;
                }
                Toast.makeText(LoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
