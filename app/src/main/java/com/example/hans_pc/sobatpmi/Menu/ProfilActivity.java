package com.example.hans_pc.sobatpmi.Menu;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hans_pc.sobatpmi.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
public class ProfilActivity extends AppCompatActivity {

    private ImageView gambar_profil;
    private TextView display_nameProfil, jumlah_riwayatProfil, tanggal_bergabungProfil, email_profil;
    private EditText password_profil;
    private ProgressBar progressbar_profil;

    private Button button_editProfil;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        firebaseAuth = FirebaseAuth.getInstance();

        gambar_profil = findViewById(R.id.gambarProfil);
        display_nameProfil = findViewById(R.id.displayNameProfil);
        jumlah_riwayatProfil = findViewById(R.id.jumlahRiwayatProfil);
        tanggal_bergabungProfil = findViewById(R.id.tanggalBergabungProfil);
        email_profil = findViewById(R.id.emailProfil);
        password_profil = findViewById(R.id.passwordProfil);
        button_editProfil = findViewById(R.id.buttonEditProfil);
        progressbar_profil = findViewById(R.id.progressbarProfil);

        loadProfilInformation();

        button_editProfil.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

    }

    private void loadProfilInformation() {

        progressbar_profil.setVisibility(View.VISIBLE);

        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {

            if (firebaseUser.getDisplayName() != null) {
                display_nameProfil.setText(firebaseUser.getDisplayName());
            }

            if (firebaseUser.getEmail() != null) {
                email_profil.setText(firebaseUser.getEmail());
            }
        }

        final StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference("profilepics/" + firebaseUser.getEmail() + ".jpg");

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
}
