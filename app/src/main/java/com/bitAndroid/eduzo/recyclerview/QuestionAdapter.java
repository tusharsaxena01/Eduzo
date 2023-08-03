package com.bitAndroid.eduzo.recyclerview;

import static android.R.color.transparent;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.LayoutInflater;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitAndroid.eduzo.databinding.QuestionRecyclerLayoutBinding;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    Context context;
    ArrayList<QuestionData> object;

    public QuestionAdapter(Context context, ArrayList<QuestionData> objectData) {
        this.context = context;
        this.object = objectData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuestionRecyclerLayoutBinding binding = QuestionRecyclerLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionData item = object.get(position);

        // layout changes here

        holder.binding.etAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setErrorOnEmpty(holder, item);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing when text is changed
            }

            @Override
            public void afterTextChanged(Editable s) {
                setQuestions(holder, object.get(holder.getAdapterPosition()));
            }
        });

//        holder.binding.cvMain.setBackgroundResource(transparent);
    }

    private void setQuestions(@NonNull ViewHolder holder, QuestionData item) {
        String ques = holder.binding.etQuestion.getText().toString();
        String option1 = holder.binding.etOption1.getText().toString();
        String option2 = holder.binding.etOption2.getText().toString();
        String option3 = holder.binding.etOption3.getText().toString();
        String option4 = holder.binding.etOption4.getText().toString();
        String answer = holder.binding.etAnswer.getText().toString();
        item.setQuestion(ques);
        item.setOption1(option1);
        item.setOption2(option2);
        item.setOption3(option3);
        item.setOption4(option4);
        item.setAnswer(answer);
    }


    private void setErrorOnEmpty(@NonNull ViewHolder holder, QuestionData item) {
        if(item.getQuestion().isEmpty()){
            holder.binding.etQuestion.setError("Empty");
        }if(item.getOption1().isEmpty()){
            holder.binding.etQuestion.setError("Empty");
        }if(item.getOption2().isEmpty()){
            holder.binding.etQuestion.setError("Empty");
        }if(item.getOption3().isEmpty()){
            holder.binding.etQuestion.setError("Empty");
        }if(item.getOption4().isEmpty()){
            holder.binding.etQuestion.setError("Empty");
        }
    }


    @Override
    public int getItemCount() {
        return object.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        QuestionRecyclerLayoutBinding binding;
        public ViewHolder(@NonNull QuestionRecyclerLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
