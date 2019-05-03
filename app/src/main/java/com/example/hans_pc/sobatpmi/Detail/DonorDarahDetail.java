package com.example.hans_pc.sobatpmi.Detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hans_pc.sobatpmi.Menu.DonorDarahActivity;
import com.example.hans_pc.sobatpmi.Model.DataBantuDonorDarah;
import com.example.hans_pc.sobatpmi.Model.DataDonorDarah;
import com.example.hans_pc.sobatpmi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DonorDarahDetail extends AppCompatActivity {

    private ImageView gambar_detailDonorDarah;
    private TextView penerima_detailDonorDarah, desc_detailDonorDarah, gol_detailDonorDarah, jumlah_detailDonorDarah;
    private Button button_tambahDonorDarahDetail;

    private ArrayList<DataBantuDonorDarah> data = new ArrayList<>();

    private Intent i;

    private FirebaseFirestore dbFirestore;
    private FirebaseAuth dbAuth;

    private String idDonor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_darah_detail);

        gambar_detailDonorDarah = findViewById(R.id.gambarDetailDonorDarah);
        penerima_detailDonorDarah = findViewById(R.id.penerimaDetailDonorDarah);
        desc_detailDonorDarah = findViewById(R.id.descDetailDonorDarah);
        gol_detailDonorDarah = findViewById(R.id.golDetailDonorDarah);
        jumlah_detailDonorDarah = findViewById(R.id.jumlahDetailDonorDarah);
        button_tambahDonorDarahDetail = findViewById(R.id.buttonTambahDonorDarahDetail);

        dbFirestore = FirebaseFirestore.getInstance();

        dbAuth = FirebaseAuth.getInstance();

        i = getIntent();

        penerima_detailDonorDarah.setText(i.getStringExtra("Penerima Donor"));
        desc_detailDonorDarah.setText(i.getStringExtra("Deskripsi Donor"));
        gol_detailDonorDarah.setText(i.getStringExtra("Golongan Darah"));
        jumlah_detailDonorDarah.setText(i.getStringExtra("Jumlah Donor"));
        idDonor = i.getStringExtra("ID Donor");

        button_tambahDonorDarahDetail.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DonorDarahDetail.this);
                        alertDialog.setMessage("Bantu donor darah");
                        alertDialog.setPositiveButton("Lakukan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tambahDonorDarahDetail();
                            }
                        })
                                .setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        alertDialog.show();
                    }
                }
        );
    }

    private void tambahDonorDarahDetail() {
        Date date = Calendar.getInstance(TimeZone.getDefault()).getTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MMMMM-YYYY hh:mm:ss");
        String sDate = dateFormat.format(date);

        FirebaseUser firebaseUser = dbAuth.getCurrentUser();

        String sEmail = firebaseUser.getEmail();

        int jumlah_donor = Integer.parseInt(jumlah_detailDonorDarah.getText().toString());
        final int jumlah_donorCurrent = jumlah_donor - 1;

        final DataBantuDonorDarah current = new DataBantuDonorDarah(
                sDate, sEmail
        );

        dbFirestore.collection("list_bantudonor").document().set(current)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                data.add(current);
                                Toast.makeText(DonorDarahDetail.this, "Selamat Anda telah membantu 1 nyawa", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

        dbFirestore.collection("list_donordarah").document(idDonor)
                .update("jumlahDonor", jumlah_donorCurrent)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                if (jumlah_donorCurrent > 0) {
                                    Toast.makeText(DonorDarahDetail.this, "Berhasil Update Jumlah Donor", Toast.LENGTH_SHORT).show();
                                    jumlah_detailDonorDarah.setText(jumlah_donorCurrent);
                                } else {
                                    deleteDataDonor();
                                }
                            }
                        }
                )
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        }
                );

        current.setDate(sDate);
        current.setEmail(sEmail);
    }

    private void deleteDataDonor() {
        dbFirestore.collection("list_donordarah").document(idDonor)
                .delete().addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DonorDarahDetail.this, "Data donor dihapus...",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DonorDarahDetail.this,
                                    DonorDarahActivity.class));
                        }
                    }
                }
        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Toast.makeText(DonorDarahDetail.this, e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
