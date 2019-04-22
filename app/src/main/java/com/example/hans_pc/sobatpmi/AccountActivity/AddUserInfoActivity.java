package com.example.hans_pc.sobatpmi.AccountActivity;

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
import com.example.hans_pc.sobatpmi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddUserInfoActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    private String urlImageProfile;

    private EditText input_displayName;
    private Button button_saveAdd;
    private ImageView button_imageAdd;
    private ProgressBar progressbar_AddUser;

    private FirebaseAuth firebaseAuth;

    private FirebaseUser firebaseUser;

    private Uri uriImageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_info);

        input_displayName = findViewById(R.id.inputDisplayNameAddUserInfo);
        button_saveAdd = findViewById(R.id.buttonSaveAddUserInfo);
        button_imageAdd = findViewById(R.id.buttonImageAddUserInfo);
        progressbar_AddUser = findViewById(R.id.progressbarAddUserInfo);

        firebaseAuth = FirebaseAuth.getInstance();

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

    private void addUserInfo() {
        String display_name = input_displayName.getText().toString();
        if (display_name.isEmpty()) {
            input_displayName.setError("Display Name Required");
            input_displayName.requestFocus();
            return;
        }

        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null && urlImageProfile != null) {
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(display_name)
                    .setPhotoUri(Uri.parse(urlImageProfile))
                    .build();

            firebaseUser.updateProfile(profileChangeRequest)
                    .addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
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
                            Toast.makeText(AddUserInfoActivity.this,
                                    e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImageProfile = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImageProfile);
                button_imageAdd.setImageBitmap(bitmap);

                uploadImageProfile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageProfile() {

        firebaseUser = firebaseAuth.getCurrentUser();

        final StorageReference storageReference = FirebaseStorage.getInstance().
                getReference("profilepics/" + firebaseUser.getEmail() + ".jpg"
                );

        if (uriImageProfile != null) {

            progressbar_AddUser.setVisibility(View.VISIBLE);

            storageReference.putFile(uriImageProfile).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            urlImageProfile = storageReference.getDownloadUrl().toString();
                            progressbar_AddUser.setVisibility(View.GONE);
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

    private void displayImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }
}
