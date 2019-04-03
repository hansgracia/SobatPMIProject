package com.example.hans_pc.sobatpmi.AccountActivity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hans_pc.sobatpmi.MainActivity;
import com.example.hans_pc.sobatpmi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    EditText textUsername, textEmail, textPassword, textConfPassword;
    Button signup_button, signin_button;
    private FirebaseAuth mAuth;
    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textEmail = findViewById(R.id.email_text);
        textPassword = findViewById(R.id.password_text);
        textConfPassword = findViewById(R.id.confpassword_text);
        signup_button = findViewById(R.id.buttonSignup);
        signin_button = findViewById(R.id.buttonSignIn);

        mAuth = FirebaseAuth.getInstance();

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                final String password = textPassword.getText().toString().trim();
                String confpassword = textConfPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "" +
                            "Dont let email or password is empty !", Toast.LENGTH_SHORT).show();
                } else {
                    if (!password.equals(confpassword)) {
                        textPassword.setError("Your password not matching!");
                        textConfPassword.setError("Your password not matching!");
                    } else {
                        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                                R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Registering..");
                        progressDialog.show();
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                                SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            if (password.length() < 6) {
                                                textPassword.setError(getString(R.string.input_minimum_password));
                                            }
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(SignupActivity.this, "Authentication failed."
                                                    + task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        progressDialog.dismiss();
                                                    }
                                                }, 500);
                                    }
                                }
                        );
                    }
                }
            }
        });

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
