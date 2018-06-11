package com.app.eoussama.phoshare;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    public static final String PASSWORD_FORGOTTEN_USERNAME_PARAM = "com.app.eoussama.phoshare.PASSWORD_FORGOTTEN_USERNAME_PARAM";

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
                    Connection conn = new Connection(LoginActivity.this);

                    if(etUsername.getText().toString().trim().length() <= 0) throw new Exception(getResources().getString(R.string.login_username_empty_error));
                    if(etPassword.getText().toString().trim().length() <= 0) throw new Exception(getResources().getString(R.string.login_password_empty_error));
                    if(etPassword.getText().toString().trim().length() < 5) throw new Exception(getResources().getString(R.string.login_password_short_error));
                    if(!conn.isValidUsername(etUsername.getText().toString().trim())) throw new Exception(getResources().getString(R.string.login_invalid_username_error));
                    if(!conn.isPasswordCorrect(etPassword.getText().toString().trim(), etUsername.getText().toString().trim())) throw new Exception(getResources().getString(R.string.login_invalid_password_error));

                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_login_success_message), Toast.LENGTH_SHORT).show();

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
                LoginActivity.this.startActivity(signupIntent);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                forgotPasswordIntent.putExtra(PASSWORD_FORGOTTEN_USERNAME_PARAM, etUsername.getText().toString().trim());
                LoginActivity.this.startActivityForResult(forgotPasswordIntent, 2);
            }
        });
    }
}
