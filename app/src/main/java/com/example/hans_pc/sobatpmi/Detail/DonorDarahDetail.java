package com.example.hans_pc.sobatpmi.Detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hans_pc.sobatpmi.Model.DataDonorDarah;
import com.example.hans_pc.sobatpmi.R;

public class DonorDarahDetail extends AppCompatActivity {

    ImageView gambar_detailDonorDarah;
    TextView penerima_detailDonorDarah, desc_detailDonorDarah, gol_detailDonorDarah, jumlah_detailDonorDarah;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_darah_detail);

        gambar_detailDonorDarah = findViewById(R.id.gambarDetailDonorDarah);
        penerima_detailDonorDarah = findViewById(R.id.penerimaDetailDonorDarah);
        desc_detailDonorDarah = findViewById(R.id.descDetailDonorDarah);
        gol_detailDonorDarah = findViewById(R.id.golDetailDonorDarah);
        jumlah_detailDonorDarah = findViewById(R.id.jumlahDetailDonorDarah);

        i = getIntent();

        penerima_detailDonorDarah.setText(i.getStringExtra("Penerima Donor"));
        desc_detailDonorDarah.setText(i.getStringExtra("Deskripsi Donor"));
        gol_detailDonorDarah.setText(i.getStringExtra("Golongan Darah"));
        jumlah_detailDonorDarah.setText(i.getStringExtra("Jumlah Donor"));
    }
}
