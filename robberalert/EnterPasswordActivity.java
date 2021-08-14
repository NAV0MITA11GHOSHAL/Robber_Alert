package com.example.robberalert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPasswordActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    EditText editText;
    Button button;

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password", "");

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();

                if (text.equals(password)) {
                    /*mediaPlayer.stop();
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    vibrator.cancel();*/
                    EnterPasswordActivity.this.finish();
                    System.exit(0);
                    Toast.makeText(EnterPasswordActivity.this, "Thank You for using me. I will always protect you from robbers.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EnterPasswordActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}