package com.example.hans_pc.sobatpmi.Menu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hans_pc.sobatpmi.Adapter.DonorDarahAdapter;
import com.example.hans_pc.sobatpmi.Model.DataDonorDarah;
import com.example.hans_pc.sobatpmi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class DonorDarahActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    private EditText input_penerimaDonor, input_deskripsiDonor, input_jumlahDonor;
    private ImageView button_imageAddCusDialDonorDarah;
    private Spinner input_golonganDonorDarah;

    private AlertDialog.Builder dialog;
    private RecyclerView recyclerView;
    private DonorDarahAdapter donorDarahAdapter;
    private ArrayList<DataDonorDarah> data = new ArrayList<>();

    private FirebaseFirestore dbFirestore;
    private FirebaseUser dbUser;
    private FirebaseAuth dbAuth;

    private LayoutInflater inflater;
    private View dialogView;

    private ProgressDialog progressDialog;

    private Uri uriImageProfile;
    private String urlImageProfile;

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
        toolbar.setLogoDescription(getResources().getString(R.string.donor_darah));

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
        button_imageAddCusDialDonorDarah = dialogView.findViewById(R.id.buttonImageAddCusDialDonorDarah);

        button_imageAddCusDialDonorDarah.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        displayImageChooser();
                    }
                }
        );

        dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addDonorDarah();
            }
        });

        clear();

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && requestCode == RESULT_OK && data != null && data.getData() != null) {
            uriImageProfile = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImageProfile);
                button_imageAddCusDialDonorDarah.setImageBitmap(bitmap);

                uploadImageDonorDarah();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void displayImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PREVIEW IMAGE FOR DONOR DARAH"), CHOOSE_IMAGE);
    }

    private void uploadImageDonorDarah() {
        dbUser = dbAuth.getCurrentUser();
        final StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference("imagedonordarah/" + dbUser.getEmail() + ".jpg");

        if(uriImageProfile != null){
            storageReference.putFile(uriImageProfile).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            urlImageProfile = storageReference.getDownloadUrl().toString();
                            Toast.makeText(DonorDarahActivity.this, "Berhasil upload..",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
            ).addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DonorDarahActivity.this,
                                    e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
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
    public void deleteData(String documentName) {

        dbFirestore.collection("list_donordarah").document(documentName)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
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
