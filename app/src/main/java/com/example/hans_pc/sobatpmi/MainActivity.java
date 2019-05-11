package com.example.hans_pc.sobatpmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hans_pc.sobatpmi.AccountActivity.LoginActivity;
import com.example.hans_pc.sobatpmi.Menu.DonorDarahActivity;
import com.example.hans_pc.sobatpmi.Menu.InformasiUmumActivity;
import com.example.hans_pc.sobatpmi.Menu.KegiatanActivity;
import com.example.hans_pc.sobatpmi.Menu.LokasiPMIActivity;
import com.example.hans_pc.sobatpmi.Menu.ProfilActivity;
import com.example.hans_pc.sobatpmi.Menu.SettingsActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    //inisiasi variable
    private FirebaseAuth firebaseAuth;
    private GoogleSignInAccount mGoogleSignInClient;
    CardView donorDarahMenu, logoutMenu, infoUmumMenu, lokasiPMIMenu, profilMenu, kegiatanMenu, settingsMenu;
    ImageView gambar_profilMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        settingsPrefence();

        setContentView(R.layout.activity_main);

        donorDarahMenu = findViewById(R.id.donorDarahMenu);
        lokasiPMIMenu = findViewById(R.id.lokasiMenu);
        infoUmumMenu = findViewById(R.id.infoUmumMenu);
        kegiatanMenu = findViewById(R.id.kegiatanMenu);
        settingsMenu = findViewById(R.id.settingsMenu);
        logoutMenu = findViewById(R.id.logoutMenu);
        profilMenu = findViewById(R.id.profilMenu);
        gambar_profilMain = findViewById(R.id.gambarProfilMain);

        firebaseAuth = FirebaseAuth.getInstance();

        loadImageProfil();

        //method button untuk menu donor darah
        donorDarahMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DonorDarahActivity.class));
            }
        });
        //method button untuk menu lokasi PMI
        lokasiPMIMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LokasiPMIActivity.class));
            }
        });

        kegiatanMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, KegiatanActivity.class));
            }
        });

        //method button untuk menu informasi umum
        infoUmumMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InformasiUmumActivity.class));
            }
        });

        settingsMenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                    }
                }
        );

        //method button untuk menu profil
        profilMenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, ProfilActivity.class));
                    }
                }
        );



        //method button untuk logout
        logoutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    private void settingsPrefence() {SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);

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

    //method untuk load image dari profil
    private void loadImageProfil() {

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference("profilepics/" +user.getEmail() +".jpg");

            storageReference.getDownloadUrl().addOnSuccessListener(
                    new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(MainActivity.this)
                                    .load(uri.toString())
                                    .fitCenter()
                                    .into(gambar_profilMain);
                        }
                    }
            );
        }
    }
}
