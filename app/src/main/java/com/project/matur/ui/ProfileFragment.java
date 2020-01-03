package com.project.matur.ui;

import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.project.matur.R;
import com.project.matur.api.Session;

public class ProfileFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        Session session = Session.init(getContext());
        ImageView foto = root.findViewById(R.id.ivFoto);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(this)
                .load(session.getString("foto"))
                .apply(requestOptions)
                .into(foto);
        TextView nama = root.findViewById(R.id.tvNama);
        nama.setText(session.getString("nama"));
        TextView nim = root.findViewById(R.id.tvNIM);
        nim.setText(session.getString("nim"));
        TextView prodi = root.findViewById(R.id.tvProdi);
        prodi.setText(session.getString("prodi"));

        return root;
    }
}