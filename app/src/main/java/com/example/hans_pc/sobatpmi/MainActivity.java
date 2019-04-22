package com.example.hans_pc.sobatpmi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.hans_pc.sobatpmi.AccountActivity.LoginActivity;
import com.example.hans_pc.sobatpmi.Menu.DonorDarahActivity;
import com.example.hans_pc.sobatpmi.Menu.InformasiUmumActivity;
import com.example.hans_pc.sobatpmi.Menu.LokasiPMIActivity;
import com.example.hans_pc.sobatpmi.Menu.ProfilActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private GoogleSignInAccount mGoogleSignInClient;
    CardView donorDarahMenu, logoutMenu, infoUmumMenu, lokasiPMIMenu, profilMenu, kegiatanMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        donorDarahMenu = findViewById(R.id.donorDarahMenu);
        lokasiPMIMenu = findViewById(R.id.lokasiMenu);
        infoUmumMenu = findViewById(R.id.infoUmumMenu);
        logoutMenu = findViewById(R.id.logoutMenu);
        profilMenu = findViewById(R.id.profilMenu);

        firebaseAuth = FirebaseAuth.getInstance();

        donorDarahMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DonorDarahActivity.class));
            }
        });

        lokasiPMIMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LokasiPMIActivity.class));
            }
        });

        infoUmumMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InformasiUmumActivity.class));
            }
        });

        profilMenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, ProfilActivity.class));
                    }
                }
        );

        logoutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
}
