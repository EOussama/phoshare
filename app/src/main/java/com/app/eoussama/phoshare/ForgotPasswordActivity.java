package com.app.eoussama.phoshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    public static final String PASSWORD_FORGOTTEN_NEXT_USERNAME_PARAM = "com.app.eoussama.phoshare.PASSWORD_FORGOTTEN_NEXT_USERNAME_PARAM";

    EditText etUsername;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etUsername = (EditText) this.findViewById(R.id.etUsername);
        btnNext = (Button) this.findViewById(R.id.btnNext);

        etUsername.setText(this.getIntent().getExtras().getString(LoginActivity.PASSWORD_FORGOTTEN_USERNAME_PARAM));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Connection conn = new Connection(ForgotPasswordActivity.this);

                    if(etUsername.getText().toString().trim().length() <= 0) throw new Exception(ForgotPasswordActivity.this.getResources().getString(R.string.login_username_empty_error));
                    if(!conn.isValidUsername(etUsername.getText().toString().trim())) throw new Exception(getResources().getString(R.string.login_invalid_username_error));

                    Intent forgotPasswordNextIntent = new Intent(ForgotPasswordActivity.this, ForgotPasswordNextActivity.class);
                    forgotPasswordNextIntent.putExtra(PASSWORD_FORGOTTEN_NEXT_USERNAME_PARAM, etUsername.getText().toString().trim());
                    ForgotPasswordActivity.this.startActivity(forgotPasswordNextIntent);
                } catch (Exception ex) {
                    Toast.makeText(ForgotPasswordActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
