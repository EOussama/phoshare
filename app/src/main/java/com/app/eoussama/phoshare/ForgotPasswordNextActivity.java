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

        btnRestore = (Button) this.findViewById(R.id.btnRestoreRecover);
        etSecurityAnswer = (EditText) this.findViewById(R.id.etSecurityAnswerRecover);
        tvSecurityQuestion = (TextView) this.findViewById(R.id.tvSecurityQuestionRecover);

        Connection conn = new Connection(ForgotPasswordNextActivity.this);
        final String username = getIntent().getExtras().getString(ForgotPasswordActivity.PASSWORD_FORGOTTEN_NEXT_USERNAME_PARAM);
        final String securityQuestion = conn.getUserSecurityQuestion(username);
        final String securityAnswer = conn.getUserSecurityAnswer(username);
        tvSecurityQuestion.setText(securityQuestion);

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(etSecurityAnswer.getText().toString().trim().length() <= 0) throw new Exception(getResources().getString(R.string.signup_security_answer_empty_error));
                    if(!etSecurityAnswer.getText().toString().trim().equals(securityAnswer)) throw new Exception(getResources().getString(R.string.forgot_password_security_question_error));

                    // TODO - Open password recovery Intent
                } catch(Exception ex) {
                    Toast.makeText(ForgotPasswordNextActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
