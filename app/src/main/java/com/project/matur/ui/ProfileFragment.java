package com.project.matur.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.project.matur.R;
import com.project.matur.adapter.LaporanAdapter;
import com.project.matur.api.ApiClient;
import com.project.matur.api.ApiInterface;
import com.project.matur.api.Session;
import com.project.matur.model.GetLaporan;
import com.project.matur.model.Laporan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    ApiInterface apiInterface;
    private RecyclerView.Adapter adapter;
    TextView tvTerkirim, tvDiterima, tvDiproses, tvSelesai;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        tvTerkirim = (TextView) root.findViewById(R.id.tvTerkirim);
        tvDiterima = (TextView) root.findViewById(R.id.tvDiterima);
        tvDiproses = (TextView) root.findViewById(R.id.tvDiproses);
        tvSelesai = (TextView) root.findViewById(R.id.tvSelesai);

        Session session = Session.init(getContext());
        ImageView foto = root.findViewById(R.id.ivFoto);
        Glide.with(this)
                .load(session.getString("foto"))
                .circleCrop()
                .apply(new RequestOptions().override(240, 240))
                .into(foto);
        TextView nama = root.findViewById(R.id.tvNama);
        nama.setText(session.getString("nama"));
        TextView nim = root.findViewById(R.id.tvNIM);
        nim.setText(session.getString("nim"));
        TextView prodi = root.findViewById(R.id.tvProdi);
        prodi.setText(session.getString("prodi"));

        return root;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiInterface = ApiClient.connect().create(ApiInterface.class);
        terkirim();
        diterima();
        diproses();
        selesai();
    }

    public void terkirim() {
        Session session = Session.init(getContext());
        String user_id = session.getString("id");
        Call<GetLaporan> laporanCall = apiInterface.getLaporan(user_id, "1");
        laporanCall.enqueue(new Callback<GetLaporan>() {
            @Override
            public void onResponse(Call<GetLaporan> call, Response<GetLaporan> response) {
                List<Laporan> laporanList = response.body().getListDataLaporan();
                tvTerkirim.setText(String.valueOf(laporanList.size()));
            }

            @Override
            public void onFailure(Call<GetLaporan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

    public void diterima() {
        Session session = Session.init(getContext());
        String user_id = session.getString("id");
        Call<GetLaporan> laporanCall = apiInterface.getLaporan(user_id, "2");
        laporanCall.enqueue(new Callback<GetLaporan>() {
            @Override
            public void onResponse(Call<GetLaporan> call, Response<GetLaporan> response) {
                List<Laporan> laporanList = response.body().getListDataLaporan();
                tvDiterima.setText(String.valueOf(laporanList.size()));
            }

            @Override
            public void onFailure(Call<GetLaporan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

    public void diproses() {
        Session session = Session.init(getContext());
        String user_id = session.getString("id");
        Call<GetLaporan> laporanCall = apiInterface.getLaporan(user_id, "3");
        laporanCall.enqueue(new Callback<GetLaporan>() {
            @Override
            public void onResponse(Call<GetLaporan> call, Response<GetLaporan> response) {

                List<Laporan> laporanList = response.body().getListDataLaporan();
                tvDiproses.setText(String.valueOf(laporanList.size()));
            }

            @Override
            public void onFailure(Call<GetLaporan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

    public void selesai() {
        Session session = Session.init(getContext());
        String user_id = session.getString("id");
        Call<GetLaporan> laporanCall = apiInterface.getLaporan(user_id, "4");
        laporanCall.enqueue(new Callback<GetLaporan>() {
            @Override
            public void onResponse(Call<GetLaporan> call, Response<GetLaporan> response) {
                List<Laporan> laporanList = response.body().getListDataLaporan();
                tvSelesai.setText(String.valueOf(laporanList.size()));
            }

            @Override
            public void onFailure(Call<GetLaporan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}