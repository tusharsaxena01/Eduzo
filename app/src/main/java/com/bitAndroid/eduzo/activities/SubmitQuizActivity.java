package com.bitAndroid.eduzo.activities;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SubmitQuizActivity extends AppCompatActivity {

    ActivitySubmitQuizBinding binding;
    ArrayList<QuestionData> questions = new ArrayList<>();
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
                int noOfQues = Integer.parseInt(parent.getItemAtPosition(position).toString());
                ArrayList<QuestionData> questionData = new ArrayList<>();
                for (int i = 0; i < noOfQues; i++) {
                    binding.pbLoading.setVisibility(View.VISIBLE);
                    questionData.add(new QuestionData());
                    questions.add(new QuestionData());                    binding.pbLoading.setVisibility(View.VISIBLE);
                    binding.pbLoading.setVisibility(View.GONE);
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

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.pbLoading.setVisibility(View.VISIBLE);
                validateQuestions();
                binding.pbLoading.setVisibility(View.GONE);
            }
        });

    }

    private void validateQuestions() {
        //checking if all options and question is filled and not null
        for (QuestionData question : questions) {
            if(!checkIfEmpty(question)){
                new WelcomeActivity().showErrorDialog("Error, Empty field Found");
                return;
            }
        }

        for (int i = 0; i < questions.size(); i++) {
            saveQuestionToDatabase(questions.get(i), i);
        }
    }

    private void saveQuestionToDatabase(QuestionData data, int i) {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("Questions")
                .child(String.valueOf(i))
                .setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SubmitQuizActivity.this, "Question "+i+" Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SubmitQuizActivity.this, "Question "+i+" not submitted : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean checkIfEmpty(QuestionData question) {
        if(question.getQuestion().equals(""))
            return false;
        if(question.getOption1().equals(""))
            return false;
        if(question.getOption2().equals(""))
            return false;
        if(question.getOption3().equals(""))
            return false;
        if(question.getOption4().equals(""))
            return false;
        return !question.getAnswer().equals("");
    }


}