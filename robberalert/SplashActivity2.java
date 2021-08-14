package com.example.robberalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashActivity2 extends AppCompatActivity {

    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPasswordActivity();
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFingerprintAuthentication();
            }
        });
    }

    public void openPasswordActivity() {
        Intent intent = new Intent(this, PasswordActivity.class);
        startActivity(intent);
        finish();
    }

    public void openFingerprintAuthentication() {
        Intent intent = new Intent(this, FingerprintAuthentication.class);
        startActivity(intent);
        finish();
    }
}