package com.app.eoussama.phoshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;


public class ForgotPasswordNextActivity extends AppCompatActivity {


    Button btnRestore;
    TextView tvSecurityQuestion;
    EditText etSecurityAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_next);

        btnRestore = (Button) this.findViewById(R.id.btnRestore);
        etSecurityAnswer = (EditText) this.findViewById(R.id.etSecurityAnswer);
        tvSecurityQuestion = (TextView) this.findViewById(R.id.tvSecurityQuestion);

        /*
        TODO - Set the security question here
        tvSecurityQuestion.setText(getIntent().getExtras().getString());
        */

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(etSecurityAnswer.getText().toString().trim().length() <= 0) throw new Exception(getResources().getString(R.string.signup_security_answer_empty_error));
                    //if() throw new Exception(); TODO - Check if the answer is correct

                    // TODO - Display the password in an AlertDialog
                } catch(Exception ex) {
                    Toast.makeText(ForgotPasswordNextActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
