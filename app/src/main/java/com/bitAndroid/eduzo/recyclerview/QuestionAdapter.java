package com.bitAndroid.eduzo.recyclerview;

import static android.R.color.transparent;
import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitAndroid.eduzo.databinding.BottomSheetLayoutBinding;
import com.bitAndroid.eduzo.databinding.QuestionRecyclerLayoutBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
//        QuestionData item = object.get(position);

        // layout changes here

//        holder.binding.cvMain.setBackgroundResource(transparent);
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
