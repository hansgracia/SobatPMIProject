package com.example.hans_pc.sobatpmi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hans_pc.sobatpmi.Adapter.RiwayatProfilAdapter;
import com.example.hans_pc.sobatpmi.Menu.ProfilActivity;
import com.example.hans_pc.sobatpmi.Model.DataRiwayatProfil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RiwayatProfilActivity extends AppCompatActivity {

    private FirebaseFirestore dbFirestore;
    private FirebaseAuth dbAuth;
    private FirebaseUser dbUser;

    private RiwayatProfilAdapter riwayatProfilAdapter;
    private ArrayList<DataRiwayatProfil> data = new ArrayList<>();

    private ListView riwayatListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_profil);



        dbFirestore = FirebaseFirestore.getInstance();
        dbAuth = FirebaseAuth.getInstance();
        dbUser = dbAuth.getCurrentUser();

        String sCurrentUser = dbUser.getEmail();

        dbFirestore.collection("list_bantudonor").whereEqualTo("emailPemberiDonor",
                sCurrentUser).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.getResult().isEmpty()){
                            startActivity(new Intent(RiwayatProfilActivity.this, ProfilActivity.class));
                        }

                        else{

                            for(DocumentSnapshot doc : task.getResult()){
                                DataRiwayatProfil dataRiwayatProfil = new DataRiwayatProfil(
                                        doc.getString("date"),
                                        doc.getString("namaPenerimaDonor")
                                );
                                data.add(dataRiwayatProfil);
                            }
                            riwayatProfilAdapter = new RiwayatProfilAdapter(RiwayatProfilActivity.this, data);
                            riwayatListView = findViewById(R.id.riwayatProfilList);
                            riwayatListView.setAdapter(riwayatProfilAdapter);
                        }


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
