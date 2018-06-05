package com.app.eoussama.phoshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnSignup;
    EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignup = (Button) this.findViewById(R.id.btnSignup);
        btnLogin = (Button) this.findViewById(R.id.btnLogin);

        etUsername = (EditText) this.findViewById(R.id.etUsername);
        etPassword = (EditText) this.findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(etUsername.getText().toString().trim().length() <= 0) throw new Exception("Input a username first");
                    if(etPassword.getText().toString().trim().length() <= 0) throw new Exception("Input a password first");

                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(mainIntent);
                } catch(Exception ex) {
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
                LoginActivity.this.startActivity(signupIntent);
            }
        });
    }
}
