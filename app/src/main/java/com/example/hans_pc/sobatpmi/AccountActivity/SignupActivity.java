package com.example.hans_pc.sobatpmi.AccountActivity;

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

    EditText textUsername,textEmail, textPassword, textConfPassword;
    Button signup_button, signin_button;
    private FirebaseAuth mAuth;
    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textEmail = (EditText)findViewById(R.id.email_text);
        textPassword = (EditText)findViewById(R.id.password_text);
        textConfPassword = (EditText)findViewById(R.id.confpassword_text);
        signup_button = (Button)findViewById(R.id.buttonSignUp);
        signin_button = (Button)findViewById(R.id.buttonSignIn);

        mAuth = FirebaseAuth.getInstance();

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                final String password = textPassword.getText().toString().trim();
                final String confpassword = textConfPassword.getText().toString().trim();

                if(textEmail.toString().isEmpty() || textPassword.toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "" +
                            "Dont let email or password is empty !", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    if(password != confpassword ){
                        Toast.makeText(getApplicationContext(), "" +
                                "Make your password same each other !", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                                SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Log.d(TAG, "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            if(password.length() < 6){
                                                textPassword.setError(getString(R.string.input_minimum_password));
                                            }
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(SignupActivity.this, "Authentication failed."
                                                    +task.getException(), Toast.LENGTH_SHORT  ).show();
                                        }
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
