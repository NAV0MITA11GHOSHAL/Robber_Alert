package com.example.robberalert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class FingerprintAuthentication extends AppCompatActivity {

    private TextView authStatusTv;
    private Button authBtn;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_authentication);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        authStatusTv = findViewById(R.id.authStatusTv);
        authBtn = findViewById(R.id.authBtn);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(FingerprintAuthentication.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                authStatusTv.setText("Authentication Error: " + errString);
                Toast.makeText(FingerprintAuthentication.this, "Authentication Error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                authStatusTv.setText("Authentication Successful!");
                Toast.makeText(FingerprintAuthentication.this, "Authentication Successful!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                authStatusTv.setText("Authentication Failed");
                Toast.makeText(FingerprintAuthentication.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Biometric Authentication").setSubtitle("Login using Fingerprint Authentication")
                .setNegativeButtonText("User App Password").build();

        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}