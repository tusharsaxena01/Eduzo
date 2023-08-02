package com.bitAndroid.eduzo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.databinding.ActivitySubmitQuizBinding;
import com.bitAndroid.eduzo.recyclerview.QuestionAdapter;
import com.bitAndroid.eduzo.recyclerview.QuestionData;

import java.util.ArrayList;

public class SubmitQuizActivity extends AppCompatActivity {

    ActivitySubmitQuizBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubmitQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigationIntent = new Intent(SubmitQuizActivity.this, NavigationActivity.class);
                startActivity(navigationIntent);
                Toast.makeText(SubmitQuizActivity.this, "Back Pressed", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.pbLoading.setVisibility(View.VISIBLE);
                int noOfQues = Integer.parseInt(parent.getItemAtPosition(position).toString());
                ArrayList<QuestionData> questionData = new ArrayList<>();
                for (int i = 0; i < noOfQues; i++) {
                    questionData.add(new QuestionData());
                }
                QuestionAdapter questionAdapter = new QuestionAdapter(SubmitQuizActivity.this, questionData);
                binding.rvResults.setLayoutManager(new LinearLayoutManager(SubmitQuizActivity.this, LinearLayoutManager.VERTICAL, false));
                binding.rvResults.setAdapter(questionAdapter);
                binding.rvResults.setVisibility(View.VISIBLE);
                binding.pbLoading.setVisibility(View.GONE);
                Log.e("Adapter", ""+noOfQues);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.rvResults.setVisibility(View.INVISIBLE);
            }
        });

    }
}