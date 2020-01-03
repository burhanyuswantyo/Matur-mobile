package com.project.matur.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.matur.MainActivity;
import com.project.matur.R;
import com.project.matur.api.ApiClient;
import com.project.matur.api.ApiInterface;
import com.project.matur.api.RetrofitClient;
import com.project.matur.api.Session;
import com.project.matur.model.GetKategori;
import com.project.matur.model.Kategori;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddFragment extends Fragment {
    @BindView(R.id.edtDeskripsi)
    EditText edtDeskripsi;
    @BindView(R.id.ivGambar)
    ImageView ivGambar;
    @BindView(R.id.spKategori)
    Spinner spKategori;

    Button btnSimpan;
    Retrofit retrofit;
    ApiInterface apiInterface;
    private long time = System.currentTimeMillis() / 1000;

    Context context;
    String idk;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        ButterKnife.bind(this, root);
        context = getContext();
        retrofit = ApiClient.connect();

        refresh();

        Session session = Session.init(context);
        String user_id = session.getString("id");

        edtDeskripsi = (EditText) root.findViewById(R.id.edtDeskripsi);
        spKategori = (Spinner) root.findViewById(R.id.spKategori);
        apiInterface = ApiClient.connect().create(ApiInterface.class);
        btnSimpan = (Button) root.findViewById(R.id.btnKirim);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> postKontakCall = apiInterface.postLaporan(
                        edtDeskripsi.getText().toString(),
                        "https://static.republika.co.id/uploads/images/inpicture_slide/stmik-amikom-_130502180015-427.jpg",
                        Long.toString(time),
                        user_id,
                        Long.toString(spKategori.getSelectedItemId()+1),
                        "1");
                postKontakCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getContext(), "Laporan berhasil dikirim.", Toast.LENGTH_LONG).show();
                        edtDeskripsi.setText("");
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

    public void refresh() {
        RetrofitClient client = new RetrofitClient("http://matur.mikelas.online/api/laporan/kategori/");
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