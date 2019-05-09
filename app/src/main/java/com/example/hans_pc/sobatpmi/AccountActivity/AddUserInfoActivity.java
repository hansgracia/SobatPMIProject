package com.example.hans_pc.sobatpmi.AccountActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hans_pc.sobatpmi.MainActivity;
import com.example.hans_pc.sobatpmi.Model.DataProfilUser;
import com.example.hans_pc.sobatpmi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class AddUserInfoActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    private String urlImageProfile;

    private EditText input_displayName;
    private Button button_saveAdd;
    private ImageView button_imageAdd;
    private ProgressBar progressbar_AddUser;
    private ProgressDialog progressDialog;

    private FirebaseAuth dbAuth;
    private FirebaseUser dbUser;
    private FirebaseFirestore dbFirestore;

    private Uri uriImageProfile;

    private ArrayList<DataProfilUser> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_info);

        input_displayName = findViewById(R.id.inputDisplayNameAddUserInfo);
        button_saveAdd = findViewById(R.id.buttonSaveAddUserInfo);
        button_imageAdd = findViewById(R.id.buttonImageAddUserInfo);
        progressbar_AddUser = findViewById(R.id.progressbarAddUserInfo);

        progressDialog = new ProgressDialog(AddUserInfoActivity.this,
                R.style.AppTheme_Dark_Dialog);

        dbFirestore = FirebaseFirestore.getInstance();
        dbAuth = FirebaseAuth.getInstance();
        dbUser = dbAuth.getCurrentUser();

        button_imageAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayImageChooser();
                    }
                }
        );

        button_saveAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addUserInfo();
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImageProfile = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImageProfile);
                button_imageAdd.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }

    private void uploadImageProfile() {

        final StorageReference storageReference = FirebaseStorage.getInstance().
                getReference("profilepics/" + dbUser.getEmail() + ".jpg"
                );

        if (uriImageProfile != null) {

            progressbar_AddUser.setVisibility(View.VISIBLE);

            storageReference.putFile(uriImageProfile).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressbar_AddUser.setVisibility(View.GONE);
                            urlImageProfile = uriImageProfile.toString();
                        }
                    }
            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressbar_AddUser.setVisibility(View.GONE);
                    Toast.makeText(AddUserInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void addUserInfo() {

        progressDialog.setMessage("Loading data...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String display_name = input_displayName.getText().toString();

        addExtendUserInfo(display_name);
        uploadImageProfile();

        if (display_name.isEmpty()) {
            input_displayName.setError("Display Name Required");
            input_displayName.requestFocus();
            return;
        }
        if (dbUser != null && urlImageProfile != null) {
            try {
                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                        .setDisplayName(display_name)
                        .setPhotoUri(Uri.parse(urlImageProfile))
                        .build();

                dbUser.updateProfile(profileChangeRequest)
                        .addOnCompleteListener(
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        Toast.makeText(AddUserInfoActivity.this,
                                                "Profile Added", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddUserInfoActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }
                        ).addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(AddUserInfoActivity.this,
                                        e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
        }
    }

    private void addExtendUserInfo(String sDisplayName) {
        String sEmail = dbUser.getEmail();
        String sID = "User" + UUID.randomUUID().toString();

        Date date = Calendar.getInstance(TimeZone.getDefault()).getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY");

        String sDateJoin = dateFormat.format(date);

        final DataProfilUser current = new DataProfilUser(
                sID, sDisplayName, sEmail, sDateJoin);

        dbFirestore.collection("list_profiluser").document(sID)
                .set(current).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                    }
                }
        );
    }
}
