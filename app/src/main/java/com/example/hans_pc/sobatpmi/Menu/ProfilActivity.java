package com.example.hans_pc.sobatpmi.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hans_pc.sobatpmi.Adapter.RiwayatProfilAdapter;
import com.example.hans_pc.sobatpmi.Model.DataRiwayatProfil;
import com.example.hans_pc.sobatpmi.R;
import com.example.hans_pc.sobatpmi.RiwayatProfilActivity;
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

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {

    private ImageView gambar_profil;
    private TextView display_nameProfil, jumlah_riwayatProfil, tanggal_bergabungProfil, email_profil;
    private EditText password_profil;
    private ProgressBar progressbar_profil;

    private Button button_editProfil;

    private FirebaseAuth dbAuth;
    private FirebaseFirestore dbFirestore;
    private FirebaseUser dbUser;

    private String sEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        settingsPreference();
        setContentView(R.layout.activity_profil);

        dbAuth = FirebaseAuth.getInstance();
        dbFirestore = FirebaseFirestore.getInstance();

        gambar_profil = findViewById(R.id.gambarProfil);
        display_nameProfil = findViewById(R.id.displayNameProfil);
        jumlah_riwayatProfil = findViewById(R.id.jumlahRiwayatProfil);
        tanggal_bergabungProfil = findViewById(R.id.tanggalBergabungProfil);
        email_profil = findViewById(R.id.emailProfil);
        button_editProfil = findViewById(R.id.buttonEditProfil);
        progressbar_profil = findViewById(R.id.progressbarProfil);

        dbUser = dbAuth.getCurrentUser();
        sEmail = dbUser.getEmail();

        loadProfilInformation();

        button_editProfil.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }
        );

        jumlah_riwayatProfil.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ProfilActivity.this, RiwayatProfilActivity.class));
                    }
                }
        );
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

    private void loadProfilInformation() {

        progressbar_profil.setVisibility(View.VISIBLE);

        if (dbUser != null) {

            if (dbUser.getDisplayName() != null) {
                display_nameProfil.setText(dbUser.getDisplayName());
            }

            if (sEmail != null) {
                email_profil.setText(dbUser.getEmail());
            }
        }

        loadJumlahRiwayatInformation();
        loadTanggalBergabung();

        final StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference("profilepics/" + dbUser.getEmail() + ".jpg");

        storageReference.getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        progressbar_profil.setVisibility(View.GONE);

                        Glide.with(ProfilActivity.this)
                                .load(uri.toString())
                                .fitCenter()
                                .into(gambar_profil);
                    }
                }
        );
    }

    private void loadTanggalBergabung() {
        dbFirestore.collection("list_profiluser").whereEqualTo("email_user", sEmail).get()
                .addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot doc : task.getResult()) {
                                    String sTanggalBergabung = doc.getString("date_join");
                                    tanggal_bergabungProfil.setText(sTanggalBergabung);
                                }
                            }
                        }
                );
    }

    private void loadJumlahRiwayatInformation() {
        if (!dbUser.getEmail().isEmpty()) {

            dbFirestore.collection("list_bantudonor").whereEqualTo("email", sEmail).get()
                    .addOnSuccessListener(
                            new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    int jumlahDocument = queryDocumentSnapshots.size();
                                    if (jumlahDocument <= 0) {
                                        jumlah_riwayatProfil.setText("0");
                                        jumlah_riwayatProfil.setTextColor(getResources().getColor(R.color.red));
                                    } else {
                                        jumlah_riwayatProfil.setText(String.valueOf(jumlahDocument));
                                    }
                                }
                            }
                    );
        }
    }
}
