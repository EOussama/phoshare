package com.app.eoussama.phoshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnSignup;
    TextView tvForgotPassword;
    EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignup = (Button) this.findViewById(R.id.btnSignup);
        btnLogin = (Button) this.findViewById(R.id.btnLogin);

        etUsername = (EditText) this.findViewById(R.id.etUsername);
        etPassword = (EditText) this.findViewById(R.id.etPassword);

        tvForgotPassword = (TextView) this.findViewById(R.id.tvForgotPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(etUsername.getText().toString().trim().length() <= 0) throw new Exception(getResources().getString(R.string.login_username_empty_error));
                    if(etPassword.getText().toString().trim().length() <= 0) throw new Exception(getResources().getString(R.string.login_password_empty_error));
                    if(etPassword.getText().toString().trim().length() < 5) throw new Exception(getResources().getString(R.string.login_password_short_error));

                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(mainIntent);
                    LoginActivity.this.finish();
                } catch(Exception ex) {
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
                LoginActivity.this.startActivityForResult(signupIntent, 1);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                LoginActivity.this.startActivityForResult(forgotPasswordIntent, 2);
            }
        });
    }
}
