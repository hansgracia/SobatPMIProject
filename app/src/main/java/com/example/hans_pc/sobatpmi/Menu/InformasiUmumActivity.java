package com.example.hans_pc.sobatpmi.Menu;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.hans_pc.sobatpmi.Adapter.InformasiUmumAdapter;
import com.example.hans_pc.sobatpmi.Model.DataInformasiUmum;
import com.example.hans_pc.sobatpmi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class InformasiUmumActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore dbFirestore;
    private ArrayList<DataInformasiUmum> data = new ArrayList<>();
    private InformasiUmumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingsPreference();
        setContentView(R.layout.activity_informasi_umum);

        recyclerView = findViewById(R.id.recycleViewInformasiUmum);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(InformasiUmumActivity.this));

        dbFirestore = FirebaseFirestore.getInstance();

        showData();
    }

    private void settingsPreference() {
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);

        boolean theme = preferences.getBoolean("set_dark_theme", false);
        boolean font = preferences.getBoolean("set_font_large", false);

        if (theme && font) {
            setTheme(R.style.AppTheme_Dark_FontLarge);
        } else if (theme) {
            setTheme(R.style.AppTheme_Dark_FontNormal);
        } else if (font) {
            setTheme(R.style.AppTheme_FontLarge);
        }
    }

    private void showData() {

        dbFirestore.collection("list_informasiumum").get()
                .addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                data.clear();
                                for (DocumentSnapshot doc : task.getResult()){
                                    String nama_informasi = doc.getString("name_information");
                                    String isi_informasi = doc.getString("content_information");
                                    String tanggal_informasi = doc.getDate("datecreated_information")
                                            .toString();

                                    final DataInformasiUmum current = new DataInformasiUmum(
                                            nama_informasi, isi_informasi, tanggal_informasi
                                    );

                                    current.setNamaInformasi(nama_informasi);
                                    current.setIsiInformasi(isi_informasi);
                                    current.setTanggalInformasi(tanggal_informasi);

                                    data.add(current);
                                }
                                adapter = new InformasiUmumAdapter(InformasiUmumActivity.this, data);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                ).addOnFailureListener(
                        new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InformasiUmumActivity.this, e.getMessage()
                                , Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
