package com.example.hans_pc.sobatpmi.AccountActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    EditText textUsername, textPassword;
    Button buttonLogin, buttonSignUp, buttonLoginFacebook, buttonLoginGmail;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUsername = (EditText)findViewById(R.id.inputUsername);
        textPassword = (EditText)findViewById(R.id.inputPassword);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonSignUp = (Button)findViewById(R.id.buttonSignUp);
        buttonLoginFacebook = (Button)findViewById(R.id.buttonLoginFacebook);
        buttonLoginGmail = (Button)findViewById(R.id.buttonLoginGmail);

        firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser() != null){
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textUsername.getText().toString();
                final String password = textPassword.getText().toString();

                if(username.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Mohon Inputkan Email atau Username Anda",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Mohon Inputkan Password Anda",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener
                        (LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            if(password.length() < 6){
                                textPassword.setError(getString(R.string.input_minimum_password));
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Password Anda Salah",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
}
