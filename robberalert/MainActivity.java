package com.example.robberalert;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    //Vibrator vibrator;
    PowerManager.WakeLock wakeLock;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;
    /* private Accelerometer accelerometer;
    private Gyroscope gyroscope; */

    private Button passwordButton;

    private Button fingerprintButton;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        passwordButton = (Button) findViewById(R.id.passwordButton);
        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEnterPasswordActivity();
            }
        });
        fingerprintButton = findViewById(R.id.fingerprintButton);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(MainActivity.this, "Authentication Error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                MainActivity.this.finish();
                System.exit(0);
                Toast.makeText(MainActivity.this, "Thank You for using me. I will always protect you from robbers.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Biometric Authentication").setSubtitle("Login using Fingerprint Authentication")
                .setNegativeButtonText("User App Password").build();

        fingerprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                biometricPrompt.authenticate(promptInfo);
            }
        });



        /* accelerometer = new Accelerometer(this);
        gyroscope = new Gyroscope(this);

        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {

                if (tx > 1.0f)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
                else if (tx < -1.0f)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }
                else if (ty > 1.0f)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
                else if (ty < -1.0f)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }
                else if (tz > 1.0f)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
                else if (tz < -1.0f)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }
            }
        });

        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void orientation(float rx, float ry, float rz) {
                if (rz > 1.0f)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
                else if (rz < -1.0f)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
            }
        }); */

        if (proximitySensor == null) {
            Toast.makeText(this, "Proximity sensor is not available!", Toast.LENGTH_LONG).show();
            finish();
        }
        proximitySensorListener = new SensorEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("InvalidWakeLockTag")
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                    PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    wakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "");
                    wakeLock.acquire();
                    mediaPlayer.stop();
                   // vibrator.cancel();
                }
                else {
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.police);
                    //mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    final long[] pattern = {2000, 1000};
                    vibrator.vibrate(pattern, 0);
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(proximitySensorListener, proximitySensor, 2*1000*1000);
    }

  /*  @Override
    protected void onResume() {
        super.onResume();

        accelerometer.register();
        gyroscope.register();
    } */

    @Override
    protected void onPause() {
        super.onPause();

      //  accelerometer.unregister();
      //  gyroscope.unregister();
        sensorManager.unregisterListener(proximitySensorListener);
    }

    public void openEnterPasswordActivity() {
        Intent intent = new Intent(this, EnterPasswordActivity.class);
        startActivity(intent);
        finish();
    }
}