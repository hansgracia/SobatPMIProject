package com.example.hans_pc.sobatpmi.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hans_pc.sobatpmi.Adapter.LokasiPMIAdapter;
import com.example.hans_pc.sobatpmi.Detail.LokasiPMIDetail;
import com.example.hans_pc.sobatpmi.MainActivity;
import com.example.hans_pc.sobatpmi.Model.DataLokasiPMI;
import com.example.hans_pc.sobatpmi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LokasiPMIActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataLokasiPMI> data = new ArrayList<>();
    private LokasiPMIAdapter lokasiPMIAdapter;
    private FirebaseFirestore dbFirestore;
    Button button_backLokasiPMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingsPreference();
        setContentView(R.layout.activity_lokasi_pmi);

        button_backLokasiPMI = findViewById(R.id.buttonBackLokasiPMI);
        recyclerView = findViewById(R.id.recycleViewLokasiPMI);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(LokasiPMIActivity.this));

        button_backLokasiPMI.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(LokasiPMIActivity.this, MainActivity.class));
                                                        finish();
                                                    }
                                                }
        );

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

        dbFirestore.collection("lokasipmi_list").get()
                .addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                data.clear();
                                for (DocumentSnapshot doc : task.getResult()) {
                                    try {
                                        String location_name = doc.getString("location_name");
                                        String location_position = doc.getGeoPoint("location_position")
                                                .toString();
                                        double lat = doc.getGeoPoint("location_position").getLatitude();
                                        double lng = doc.getGeoPoint("location_position").getLongitude();

                                        System.out.println(lat);
                                        System.out.println(lng);

                                        final DataLokasiPMI current = new DataLokasiPMI(
                                                location_name,
                                                location_position);

                                        current.setNamaLokasiPMI(location_name);
                                        current.setLongitude(lng);
                                        current.setLatitude(lat);

                                        data.add(current);
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }

                                lokasiPMIAdapter = new LokasiPMIAdapter(LokasiPMIActivity.this, data);
                                recyclerView.setAdapter(lokasiPMIAdapter);
                            }
                        }
                ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LokasiPMIActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
