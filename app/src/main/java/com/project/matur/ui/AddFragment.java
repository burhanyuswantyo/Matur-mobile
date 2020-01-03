package com.project.matur.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.matur.MainActivity;
import com.project.matur.R;
import com.project.matur.adapter.LaporanAdapter;
import com.project.matur.api.ApiClient;
import com.project.matur.api.ApiInterface;
import com.project.matur.model.GetLaporan;
import com.project.matur.model.Laporan;
import com.project.matur.model.PostPutDelLaporan;

import java.sql.Time;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFragment extends Fragment {
    EditText edtDeskripsi;
    ImageView ivGambar;
    Spinner spKategori;
    Button btnSimpan;
    ApiInterface apiInterface;
    ApiClient apiClient;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    public static MainActivity mainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        edtDeskripsi = (EditText) root.findViewById(R.id.edtDeskripsi);
        apiInterface = ApiClient.connect().create(ApiInterface.class);
        btnSimpan = (Button) root.findViewById(R.id.btnKirim);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> postKontakCall = apiInterface.postLaporan(
                        edtDeskripsi.getText().toString(),
                        "yuswan.jpg",
                        getTime,
                        "3",
                        "2",
                        "1");
                postKontakCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getContext(), "Laporan berhasil dikirim.", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void refresh(){
        Call<GetLaporan> laporanCall = apiInterface.getLaporan();
        laporanCall.enqueue(new Callback<GetLaporan>() {
            @Override
            public void onResponse(Call<GetLaporan> call, Response<GetLaporan> response) {
                List<Laporan> laporanList = response.body().getListDataLaporan();
                Log.d("Retrofit Get", "Jumlah data laporan : " + String.valueOf(laporanList.size()));
                adapter = new LaporanAdapter(laporanList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetLaporan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}