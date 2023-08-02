package com.bitAndroid.eduzo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.databinding.ActivityQuizBinding;
import com.bitAndroid.eduzo.fragments.ResultFragment;

public class QuizActivity extends AppCompatActivity {

    ActivityQuizBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigationIntent = new Intent(QuizActivity.this, NavigationActivity.class);
                startActivity(navigationIntent);
            }
        });


    }
}