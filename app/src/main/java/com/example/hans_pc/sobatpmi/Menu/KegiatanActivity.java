package com.example.hans_pc.sobatpmi.Menu;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hans_pc.sobatpmi.Adapter.DonorDarahAdapter;
import com.example.hans_pc.sobatpmi.Adapter.KegiatanAdapter;
import com.example.hans_pc.sobatpmi.Fragment.DatePickerFragment;
import com.example.hans_pc.sobatpmi.Model.DataDonorDarah;
import com.example.hans_pc.sobatpmi.Model.DataKegiatan;
import com.example.hans_pc.sobatpmi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class KegiatanActivity extends AppCompatActivity {

    private TextInputLayout input_namaKegiatan, input_tempatKegiatan, input_keteranganKegiatan;
    private Button button_tambahKegiatan, input_waktuKegiatan;
    private ProgressDialog progressDialog;

    private String sNamaKegiatan, sTempatKegiatan, sWaktuKegiatan, sKeteranganKegiatan;

    private FirebaseFirestore dbFirestore;
    private RecyclerView recyclerView;

    private ArrayList<DataKegiatan> data = new ArrayList<>();

    private KegiatanAdapter kegiatanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kegiatan);

        input_namaKegiatan = findViewById(R.id.inputNamaKegiatan);
        input_tempatKegiatan = findViewById(R.id.inputTempatKegiatan);
        input_waktuKegiatan = findViewById(R.id.inputWaktuKegiatan);
        input_keteranganKegiatan = findViewById(R.id.inputKeteranganKegiatan);
        button_tambahKegiatan = findViewById(R.id.buttonTambahKegiatan);

        progressDialog = new ProgressDialog(KegiatanActivity.this,
                R.style.AppTheme_Dark_Dialog);

        recyclerView = findViewById(R.id.recycleViewKegiatan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(KegiatanActivity.this));

        dbFirestore = FirebaseFirestore.getInstance();

        showData();

        input_waktuKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        button_tambahKegiatan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addKegiatan();
                    }
                }
        );
    }

    private void addKegiatan() {
        String id = UUID.randomUUID().toString();
        sNamaKegiatan = input_namaKegiatan.getEditText().getText().toString();
        sTempatKegiatan = input_tempatKegiatan.getEditText().getText().toString();
        sWaktuKegiatan = input_waktuKegiatan.getText().toString();
        sKeteranganKegiatan = input_keteranganKegiatan.getEditText().getText().toString();

        final DataKegiatan current = new DataKegiatan(
                id, sNamaKegiatan, sTempatKegiatan, sWaktuKegiatan, sKeteranganKegiatan
        );

        dbFirestore.collection("list_kegiatan").document(id).set(current)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                data.add(current);

                                kegiatanAdapter = new KegiatanAdapter(KegiatanActivity.this, data);
                                recyclerView.setAdapter(kegiatanAdapter);

                                Toast.makeText(KegiatanActivity.this,
                                        "Data Kegiatan Telah Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();

                            }
                        }
                );
    }

    private void showDatePickerDialog() {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void deleteData(String documentName) {

        dbFirestore.collection("list_kegiatan").document(documentName)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(KegiatanActivity.this,
                                    "Deleted", Toast.LENGTH_SHORT).show();
                            showData();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(KegiatanActivity.this,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showData() {
        progressDialog.setMessage("Loading data...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        dbFirestore.collection("list_kegiatan").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        data.clear();
                        for (DocumentSnapshot doc : task.getResult()) {
                            DataKegiatan dataKegiatan = new DataKegiatan(
                                    doc.getString("idKegiatan"),
                                    doc.getString("namaKegiatan"),
                                    doc.getString("tempatKegiatan"),
                                    doc.getString("waktuKegiatan"),
                                    doc.getString("keteranganKegiatan"));
                            data.add(dataKegiatan);
                        }

                        kegiatanAdapter = new KegiatanAdapter(KegiatanActivity.this, data);
                        recyclerView.setAdapter(kegiatanAdapter);

                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();

                        Toast.makeText(KegiatanActivity.this, e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        clear();
                    }
                });
    }

    private void clear() {
        input_namaKegiatan.getEditText().setText(null);
        input_waktuKegiatan.setText("PILIH WAKTU");
        input_tempatKegiatan.getEditText().setText(null);
        input_keteranganKegiatan.getEditText().setText(null);
    }
}
