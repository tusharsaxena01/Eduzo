package com.bitAndroid.eduzo.recyclerview;

import static android.R.color.transparent;
import android.content.Context;

import android.view.LayoutInflater;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitAndroid.eduzo.databinding.ResultRecyclerLayoutBinding;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    Context context;
    ArrayList<ResultData> object;

    public ResultAdapter(Context context, ArrayList<ResultData> objectData) {
        this.context = context;
        this.object = objectData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ResultRecyclerLayoutBinding binding = ResultRecyclerLayoutBinding.inflate(LayoutInflater.from(context), null, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultData item = object.get(position);
        // Data manipulation
        int i=0;
        holder.binding.tvName.setText("Test "+i++);
        holder.binding.tvScore.setText(item.getScore());
        holder.binding.tvDate.setText(item.getDate());

        // Todo: Add transparent background
        holder.binding.cvMain.setBackgroundResource(transparent);
    }

    @Override
    public int getItemCount() {
        return object.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ResultRecyclerLayoutBinding binding;
        public ViewHolder(@NonNull ResultRecyclerLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
