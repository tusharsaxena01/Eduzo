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

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Log.e("Current User", FirebaseAuth.getInstance().getCurrentUser().getEmail());

        sp = getSharedPreferences("data", 0);
        Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);
        Intent welcomeActivity = new Intent(SplashActivity.this, WelcomeActivity.class);
        Intent navigationActivity = new Intent(SplashActivity.this, NavigationActivity.class);
        FirebaseAuth.getInstance().signOut();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int first_visit = sp.getInt("first_visit", 0);
                if(first_visit == 0) {
                    startActivity(mainActivity);
                    Log.e("Activity", "main activity");
                }else{
                    Log.e("Activity", "welcome activity");
                    startActivity(welcomeActivity);
                }
                finish();
            }
        }, 3000);

    }
}