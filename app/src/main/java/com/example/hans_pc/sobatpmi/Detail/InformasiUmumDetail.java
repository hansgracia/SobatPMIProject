package com.example.hans_pc.sobatpmi.Detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hans_pc.sobatpmi.R;

public class InformasiUmumDetail extends AppCompatActivity {

    TextView tanggal_detailInformasi, nama_detailInformasi, isi_detailInformasi;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_umum_detail);

        tanggal_detailInformasi = findViewById(R.id.tanggalDetailInformasi);
        nama_detailInformasi = findViewById(R.id.namaDetailInformasi);
        isi_detailInformasi = findViewById(R.id.isiDetailInformasi);

        i = getIntent();

        tanggal_detailInformasi.setText(i.getStringExtra("Tanggal Informasi Detail"));
        nama_detailInformasi.setText(i.getStringExtra("Nama Informasi Detail"));
        isi_detailInformasi.setText(i.getStringExtra("Isi Informasi Detail"));
    }
}
