package com.bitAndroid.eduzo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bitAndroid.eduzo.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY_MS = 3000;
    private static final String DATA_PREFS_NAME = "data";
    private static final String FIRST_VISIT_KEY = "first_visit";

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp = getSharedPreferences(DATA_PREFS_NAME, MODE_PRIVATE);

        Intent mainActivityIntent = new Intent(SplashActivity.this, MainActivity.class);
        Intent welcomeActivityIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
        Intent navigationActivityIntent = new Intent(SplashActivity.this, NavigationActivity.class);

        new Handler().postDelayed(() -> {
            int firstVisit = sp.getInt(FIRST_VISIT_KEY, 0);

            if (firstVisit == 0) {
                startActivity(mainActivityIntent);
                Log.e("Activity", "main activity");
            } else {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(welcomeActivityIntent);
                    Log.e("Activity", "welcome activity");
                } else {
                    startActivity(navigationActivityIntent);
                }
            }

            finish();
        }, SPLASH_DELAY_MS);
    }
}
