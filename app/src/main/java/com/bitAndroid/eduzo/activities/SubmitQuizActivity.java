package com.bitAndroid.eduzo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.recyclerview.QuestionModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitQuizActivity extends AppCompatActivity {

    private DatabaseReference quizRef;
    private Button submitButton;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_quiz);

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitQuizActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });
        quizRef = FirebaseDatabase.getInstance().getReference().child("Quiz");

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQuiz();
            }
        });
    }

    private void submitQuiz() {
        for (int i = 1; i <= 5; i++) {
            String question = ((EditText) findViewById(getResources().getIdentifier("question" + i + "EditText", "id", getPackageName()))).getText().toString().trim();
            String option1 = ((EditText) findViewById(getResources().getIdentifier("option1_" + i + "EditText", "id", getPackageName()))).getText().toString().trim();
            String option2 = ((EditText) findViewById(getResources().getIdentifier("option2_" + i + "EditText", "id", getPackageName()))).getText().toString().trim();
            String option3 = ((EditText) findViewById(getResources().getIdentifier("option3_" + i + "EditText", "id", getPackageName()))).getText().toString().trim();
            String option4 = ((EditText) findViewById(getResources().getIdentifier("option4_" + i + "EditText", "id", getPackageName()))).getText().toString().trim();
            String answer = ((EditText) findViewById(getResources().getIdentifier("answer_" + i + "EditText", "id", getPackageName()))).getText().toString().trim();

            String questionId = quizRef.push().getKey();
            if (questionId != null) {
                QuestionModel questionModel = new QuestionModel(question, option1, option2, option3, option4, answer);
                quizRef.child(questionId).setValue(questionModel);
            }
        }

        // Clear input fields after submission
        clearFields();
    }

    private void clearFields() {
        for (int i = 1; i <= 5; i++) {
            ((EditText) findViewById(getResources().getIdentifier("question" + i + "EditText", "id", getPackageName()))).setText("");
            ((EditText) findViewById(getResources().getIdentifier("option1_" + i + "EditText", "id", getPackageName()))).setText("");
            ((EditText) findViewById(getResources().getIdentifier("option2_" + i + "EditText", "id", getPackageName()))).setText("");
            ((EditText) findViewById(getResources().getIdentifier("option3_" + i + "EditText", "id", getPackageName()))).setText("");
            ((EditText) findViewById(getResources().getIdentifier("option4_" + i + "EditText", "id", getPackageName()))).setText("");
            ((EditText) findViewById(getResources().getIdentifier("answer_" + i + "EditText", "id", getPackageName()))).setText("");
        }
    }
}
