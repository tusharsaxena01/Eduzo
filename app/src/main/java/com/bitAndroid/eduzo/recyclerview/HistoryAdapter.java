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
import com.bitAndroid.eduzo.databinding.HistoryRecyclerLayoutBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    Context context;
    ArrayList<HistoryData> object;

    public HistoryAdapter(Context context, ArrayList<HistoryData> objectData) {
        this.context = context;
        this.object = objectData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HistoryRecyclerLayoutBinding binding = HistoryRecyclerLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryData item = object.get(position);

        holder.binding.tvTestName.setText("Test "+item.getTestName());
        holder.binding.tvDate.setText(item.getDate());
        holder.binding.tvScore.setText(""+item.getScore());

        holder.binding.cvMain.setBackgroundResource(transparent);
    }


    @Override
    public int getItemCount() {
        return object.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        HistoryRecyclerLayoutBinding binding;
        public ViewHolder(@NonNull HistoryRecyclerLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
