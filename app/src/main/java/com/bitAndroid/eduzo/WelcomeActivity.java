package com.bitAndroid.eduzo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bitAndroid.eduzo.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}