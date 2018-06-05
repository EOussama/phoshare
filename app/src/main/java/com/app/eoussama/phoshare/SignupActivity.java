package com.app.eoussama.phoshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.CheckBox;

public class SignupActivity extends AppCompatActivity {

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
        sSecurityQuestions = (Spinner) this.findViewById(R.id.etPasswordConfirmation);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
