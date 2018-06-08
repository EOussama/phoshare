package com.app.eoussama.phoshare;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private final int SIGNUP_TIMEOUT = 2500;
    Button btnSignup, btnClear;
    EditText etUsername, etPassword, etPasswordConfirmation, etSecurityAnswer;
    Spinner sSecurityQuestions;
    CheckBox cbTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSignup = (Button) this.findViewById(R.id.btnSignup);
        btnClear = (Button) this.findViewById(R.id.btnClear);

        etUsername = (EditText) this.findViewById(R.id.etUsername);
        etPassword  = (EditText) this.findViewById(R.id.etPassword);
        etPasswordConfirmation  = (EditText) this.findViewById(R.id.etPasswordConfirmation);
        etSecurityAnswer = (EditText) this.findViewById(R.id.etSecurityAnswer);

        cbTerms = (CheckBox) this.findViewById(R.id.cbTerms);
        sSecurityQuestions = (Spinner) this.findViewById(R.id.sSecurityQuestions);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Connection conn = new Connection(SignupActivity.this);

                    if(etUsername.getText().toString().trim().length() <= 0) throw new Exception(getResources().getString(R.string.login_username_empty_error));
                    if(etPassword.getText().toString().trim().length() <= 0) throw new Exception(getResources().getString(R.string.login_password_empty_error));
                    if(etPassword.getText().toString().trim().length() < 5) throw new Exception(getResources().getString(R.string.login_password_short_error));
                    if(etPasswordConfirmation.getText().toString().trim().length() <= 0) throw new Exception(getResources().getString(R.string.signup_password_confirmation_empty_error));
                    if(!etPassword.getText().toString().trim().equals(etPasswordConfirmation.getText().toString().trim())) throw new Exception(getResources().getString(R.string.signup_wrong_passwords_error));
                    if(etSecurityAnswer.getText().toString().trim().length() <= 0) throw new Exception(getResources().getString(R.string.signup_security_answer_empty_error));
                    if(!cbTerms.isChecked()) throw new Exception(getResources().getString(R.string.signup_terms_agreement_error));
                    if(conn.isValidUsername(etUsername.getText().toString().trim())) throw new Exception(getResources().getString(R.string.signup_valid_user_error));

                    // TODO - Register username in the database
                    Toast.makeText(SignupActivity.this, getResources().getString(R.string.signup_registeration_success_message), Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() { SignupActivity.this.finish(); }
                    }, SIGNUP_TIMEOUT);
                } catch(Exception ex) {
                    Toast.makeText(SignupActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUsername.setText("");
                etPassword.setText("");
                etPasswordConfirmation.setText("");
                etSecurityAnswer.setText("");
                sSecurityQuestions.setSelection(0, true);
                cbTerms.setChecked(false);
                etUsername.requestFocus();
            }
        });
    }
}
