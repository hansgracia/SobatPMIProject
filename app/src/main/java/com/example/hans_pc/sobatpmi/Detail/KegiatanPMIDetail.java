package com.example.hans_pc.sobatpmi.Detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hans_pc.sobatpmi.R;

public class KegiatanPMIDetail extends AppCompatActivity {

    TextView tanggal_detailKegiatan, nama_detailKegiatan, isi_detailKegiatan;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_Kegiatan_umum_detail);

        tanggal_detailKegiatan = findViewById(R.id.tanggalDetailKegiatan);
        nama_detailKegiatan = findViewById(R.id.namaDetailKegiatan);
        isi_detailKegiatan = findViewById(R.id.isiDetailKegiatan);

        i = getIntent();

        tanggal_detailKegiatan.setText(i.getStringExtra("Tanggal Kegiatan Detail"));
        nama_detailKegiatan.setText(i.getStringExtra("Nama Kegiatan Detail"));
        isi_detailKegiatan.setText(i.getStringExtra("Isi Kegiatan Detail"));
    }
}

