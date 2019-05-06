package com.example.hans_pc.sobatpmi.Menu;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.hans_pc.sobatpmi.Adapter.KegiatanPMIAdapter;
import com.example.hans_pc.sobatpmi.Model.DataKegiatanPMI;
import com.example.hans_pc.sobatpmi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class KegiatanPMIActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore dbFirestore;
    private ArrayList<DataKegiatanUmum> data = new ArrayList<>();
    private KegiatanUmumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_Kegiatan_PMI);

        recyclerView = findViewById(R.id.recycleViewKegiatanPMI);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(KegiatanPMIActivity.this));

        dbFirestore = FirebaseFirestore.getInstance();

        showData();
    }

    private void showData() {

        dbFirestore.collection("list_kegiatanumum").get()
                .addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                data.clear();
                                for (DocumentSnapshot doc : task.getResult()){
                                    String nama_kegiatan = doc.getString("name_information");
                                    String isi_kegiatan = doc.getString("content_information");
                                    String tanggal_kegiatan = doc.getDate("datecreated_information")
                                            .toString();

                                    final DataInformasiUmum current = new DataInformasiUmum(
                                            nama_informasi, isi_informasi, tanggal_informasi
                                    );

                                    current.setNamakegiatan(nama_kegiatan);
                                    current.setIsikegiatan(isi_kegiatan);
                                    current.setTanggalkegiatan(tanggal_kegiatan);

                                    data.add(current);
                                }
                                adapter = new KegiatanPMIAdapter(KegiatanPMIctivity.this, data);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(KegiatanPMIActivity.this, e.getMessage()
                                , Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
