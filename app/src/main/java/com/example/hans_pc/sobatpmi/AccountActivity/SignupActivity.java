package com.example.hans_pc.sobatpmi.AccountActivity;

<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
=======
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

>>>>>>> 8a7b42aeda8740c4c0e483228ae4b28ad20b1085
import com.example.hans_pc.sobatpmi.R;

public class SignupActivity extends AppCompatActivity {

<<<<<<< HEAD
=======
    EditText textName, textUsername,textEmail, textPhone, textPassword;
    Button signup_button;
>>>>>>> 8a7b42aeda8740c4c0e483228ae4b28ad20b1085
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
<<<<<<< HEAD
=======

        textName = (EditText)findViewById(R.id.name_text);
        textUsername = (EditText)findViewById(R.id.username_text);
        textEmail = (EditText)findViewById(R.id.password_text);
        textPassword = (EditText)findViewById(R.id.password_text);
        textPhone = (EditText)findViewById(R.id.phone_text);
        signup_button = (Button)findViewById(R.id.btnSignup);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

>>>>>>> 8a7b42aeda8740c4c0e483228ae4b28ad20b1085
    }
}
