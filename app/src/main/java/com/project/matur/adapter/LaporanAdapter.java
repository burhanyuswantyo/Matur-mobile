package com.project.matur.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.matur.R;
import com.project.matur.model.Laporan;

import java.util.List;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewHolder> {
    List<Laporan> laporan;

    public LaporanAdapter(List<Laporan> laporanList) {
        laporan = laporanList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvDeskripsi.setText("Deskripsi : " + laporan.get(position).getDeskripsi());
        holder.tvKategorti.setText("Kategori : " + laporan.get(position).getKategori());
        holder.tvStatus.setText("Status : " + laporan.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return laporan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDeskripsi, tvKategorti, tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDeskripsi = (TextView) itemView.findViewById(R.id.laporan_deskripsi);
            tvKategorti = (TextView) itemView.findViewById(R.id.laporan_kategori);
            tvStatus = (TextView) itemView.findViewById(R.id.laporan_status);
        }
    }
}

