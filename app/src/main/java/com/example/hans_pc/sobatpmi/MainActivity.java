package com.example.hans_pc.sobatpmi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.hans_pc.sobatpmi.AccountActivity.LoginActivity;
import com.example.hans_pc.sobatpmi.Menu.DonorDarahActivity;
import com.example.hans_pc.sobatpmi.Menu.InformasiUmumActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private GoogleSignInAccount mGoogleSignInClient;
    CardView donorDarahMenu, logoutMenu, infoUmumMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        donorDarahMenu = findViewById(R.id.donorDarahMenu);
        infoUmumMenu = findViewById(R.id.infoUmumMenu);
        logoutMenu = findViewById(R.id.logoutMenu);

        firebaseAuth = FirebaseAuth.getInstance();

        donorDarahMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DonorDarahActivity.class));
                finish();
            }
        });

        infoUmumMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InformasiUmumActivity.class));
                finish();
            }
        });

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
