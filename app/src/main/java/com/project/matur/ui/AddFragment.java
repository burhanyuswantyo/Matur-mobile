package com.project.matur.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.matur.R;
import com.project.matur.activity.AddActivity;
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

public class AddFragment extends Fragment {
    ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        apiInterface = ApiClient.connect().create(ApiInterface.class);
        refresh();
    }

    public void refresh(){
        Session session = Session.init(getContext());
        String user_id = session.getString("id");
        Call<GetLaporan> laporanCall = apiInterface.getLaporan(user_id,null);
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


