package com.bitAndroid.eduzo.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.databinding.ActivityQuizBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizActivity extends AppCompatActivity {
    ActivityQuizBinding binding;

    private DatabaseReference questionsRef;
    private TextView questionTextView, scoreTextView;
    private RadioGroup optionsRadioGroup;
    private Button submitButton;

    private int currentQuestion = 1;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        scoreTextView = findViewById(R.id.scoreTextView);

        questionsRef = FirebaseDatabase.getInstance().getReference().child("Quiz");

        // Load the first question
        loadQuestion(currentQuestion);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOptionId = optionsRadioGroup.getCheckedRadioButtonId();
                if (selectedOptionId != -1) {
                    binding.ivBack.setVisibility(View.INVISIBLE);
                    RadioButton selectedOption = findViewById(selectedOptionId);
                    String userAnswer = selectedOption.getText().toString();
                    checkAnswer(userAnswer);
                }
            }
        });
    }

    private void loadQuestion(final int questionNumber) {
        questionsRef.child(String.valueOf(questionNumber)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String question = dataSnapshot.child("ques").getValue(String.class);
                    String option1 = dataSnapshot.child("option1").getValue(String.class);
                    String option2 = dataSnapshot.child("option2").getValue(String.class);
                    String option3 = dataSnapshot.child("option3").getValue(String.class);
                    String option4 = dataSnapshot.child("option4").getValue(String.class);

                    questionTextView.setText(question);
                    ((RadioButton) optionsRadioGroup.getChildAt(0)).setText(option1);
                    ((RadioButton) optionsRadioGroup.getChildAt(1)).setText(option2);
                    ((RadioButton) optionsRadioGroup.getChildAt(2)).setText(option3);
                    ((RadioButton) optionsRadioGroup.getChildAt(3)).setText(option4);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error if needed
            }
        });
    }

    private void checkAnswer(final String userAnswer) {
        questionsRef.child(String.valueOf(currentQuestion)).child("answer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String correctAnswer = dataSnapshot.getValue(String.class);
                    if (userAnswer.equals(correctAnswer)) {
                        score++;
                    }
                    currentQuestion++;
                    if (currentQuestion <= 5) {
                        loadQuestion(currentQuestion);
                        optionsRadioGroup.clearCheck();
                    } else {
                        // All questions answered, show final score
                        scoreTextView.setText("Score: " + score + " out of 5");
                        questionTextView.setVisibility(View.GONE);
                        optionsRadioGroup.setVisibility(View.GONE);
                        submitButton.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error if needed
            }
        });
    }
}
