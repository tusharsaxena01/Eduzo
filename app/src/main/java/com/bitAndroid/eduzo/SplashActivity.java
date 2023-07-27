package com.bitAndroid.eduzo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp = getSharedPreferences("data", 0);
        Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);
        Intent welcomeActivity = new Intent(SplashActivity.this, WelcomeActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int first_visit = sp.getInt("first_visit", 0);
                if(first_visit == 0)
                    startActivity(mainActivity);
                else
                    startActivity(welcomeActivity);
            }
        }, 3000);
    }
}