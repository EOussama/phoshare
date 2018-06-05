package com.app.eoussama.phoshare;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onPause() {
        super.onPause();

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon);
        builder.setTitle(getResources().getString(R.string.main_logout));
        builder.setMessage(getResources().getString(R.string.main_logout_confirmation_message));
        builder.setPositiveButton(getResources().getString(R.string.main_logout_dialog_positive_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.main_logout_dialog_negative_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onResume();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
