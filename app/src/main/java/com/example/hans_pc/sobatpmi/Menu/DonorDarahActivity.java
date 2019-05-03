package com.example.hans_pc.sobatpmi.Menu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hans_pc.sobatpmi.Adapter.DonorDarahAdapter;
import com.example.hans_pc.sobatpmi.Model.DataDonorDarah;
import com.example.hans_pc.sobatpmi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class DonorDarahActivity extends AppCompatActivity {

    private EditText input_penerimaDonor, input_deskripsiDonor, input_jumlahDonor;
    private ImageButton button_imagePenerimaDonor;
    private Spinner input_golonganDonorDarah;

    private AlertDialog.Builder dialog;
    private RecyclerView recyclerView;
    private DonorDarahAdapter donorDarahAdapter;
    private ArrayList<DataDonorDarah> data = new ArrayList<>();

    FirebaseFirestore dbFirestore;

    LayoutInflater inflater;
    View dialogView;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_darah);

        progressDialog = new ProgressDialog(DonorDarahActivity.this,
                R.style.AppTheme_Dark_Dialog);

        recyclerView = findViewById(R.id.recycleViewDonorDarah);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DonorDarahActivity.this));

        dbFirestore = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        showData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogDonorDarah();
            }
        });
    }
    //method untuk costumer dialog donor darah
    private void CustomDialogDonorDarah() {

        dialog = new AlertDialog.Builder(DonorDarahActivity.this);

        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.cdialog_donor_darah, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Input Donor Darah");

//        button_imagePenerimaDonor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        input_penerimaDonor = dialogView.findViewById(R.id.inputPenerimaCusDialDonorDarah);
        input_deskripsiDonor = dialogView.findViewById(R.id.inputDescCusDialDonorDarah);
        input_jumlahDonor = dialogView.findViewById(R.id.inputJumlahCusDialDonorDarah);
        input_golonganDonorDarah = dialogView.findViewById(R.id.spinnerGolCusDialDonorDarah);

        dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addDonorDarah();
            }
        });

        clear();

        dialog.show();
    }
    //method untuk menambah donor darah
    private void addDonorDarah() {

        String penerima_donor = input_penerimaDonor.getText().toString().trim();
        String deskripsi_donor = input_deskripsiDonor.getText().toString();
        int jumlah_donor = Integer.parseInt(input_jumlahDonor.getText().toString());
        String golongan_darah = input_golonganDonorDarah.getSelectedItem().toString();
        String id = UUID.randomUUID().toString();

        final DataDonorDarah current = new DataDonorDarah(id, penerima_donor, deskripsi_donor, golongan_darah,
                jumlah_donor);

        dbFirestore.collection("list_donordarah").document(id).set(current)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        data.add(current);

                        donorDarahAdapter = new DonorDarahAdapter(DonorDarahActivity.this, data);
                        recyclerView.setAdapter(donorDarahAdapter);

                        Toast.makeText(getApplicationContext(), "Kebutuhan Donor Darah Berhasil Ditambahkan",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Kebutuhan Donor Darah Gagal Ditambahkan",
                        Toast.LENGTH_SHORT).show();
            }
        });

        current.setId(id);
        current.setPenerimaDonor(penerima_donor);
        current.setDeskripsiDonor(deskripsi_donor);
        current.setJumlahDonor(jumlah_donor);
        current.setGolDarahDonor(golongan_darah);
    }
    // method untuk melihat data donor darah
    private void showData() {

        progressDialog.setMessage("Loading data...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        dbFirestore.collection("list_donordarah").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        data.clear();
                        for (DocumentSnapshot doc : task.getResult()) {
                            DataDonorDarah dataDonorDarah = new DataDonorDarah(
                                    doc.getString("id"),
                                    doc.getString("penerimaDonor"),
                                    doc.getString("deskripsiDonor"),
                                    doc.getString("golDarahDonor"),
                                    doc.getDouble("jumlahDonor").intValue());
                            data.add(dataDonorDarah);
                        }

                        donorDarahAdapter = new DonorDarahAdapter(DonorDarahActivity.this, data);
                        recyclerView.setAdapter(donorDarahAdapter);

                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();

                        Toast.makeText(DonorDarahActivity.this, e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        clear();
                    }
                });
    }
    //method untuk delete data donor darah
    public void deleteData(String documentName){

        dbFirestore.collection("list_donordarah").document(documentName)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(DonorDarahActivity.this,
                                    "Deleted", Toast.LENGTH_SHORT).show();
                            showData();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DonorDarahActivity.this,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //method untuk clear data
    private void clear() {
        input_penerimaDonor.setText(null);
        input_deskripsiDonor.setText(null);
        input_jumlahDonor.setText(null);
        input_golonganDonorDarah.setSelection(0);
    }

}
