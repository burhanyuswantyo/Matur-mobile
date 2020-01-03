package com.project.matur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.project.matur.R;
import com.project.matur.api.Session;
import com.project.matur.model.Laporan;
import com.project.matur.ui.DashboardFragment;

import java.util.List;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewHolder> {
    List<Laporan> laporan;
    private Context context;

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
        Session session = Session.init(holder.ivGambar.getContext());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(holder.ivGambar.getContext())
                .load(laporan.get(position).getGambar())
                .apply(requestOptions)
                .into(holder.ivGambar);
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
        public ImageView ivGambar;

        public ViewHolder(View itemView) {
            super(itemView);
            ivGambar = (ImageView) itemView.findViewById(R.id.laporan_gambar);
            tvDeskripsi = (TextView) itemView.findViewById(R.id.laporan_deskripsi);
            tvKategorti = (TextView) itemView.findViewById(R.id.laporan_kategori);
            tvStatus = (TextView) itemView.findViewById(R.id.laporan_status);
        }
    }
}

