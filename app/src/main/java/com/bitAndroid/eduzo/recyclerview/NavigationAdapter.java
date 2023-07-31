package com.bitAndroid.eduzo.recyclerview;

import static android.R.color.transparent;
import android.content.Context;
import android.view.LayoutInflater;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitAndroid.eduzo.databinding.NavigationRecyclerLayoutBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {
    Context context;
    ArrayList<NavigationRecyclerData> navigationRecyclerData;

    public NavigationAdapter(Context context, ArrayList<NavigationRecyclerData> navigationRecyclerData) {
        this.context = context;
        this.navigationRecyclerData = navigationRecyclerData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NavigationRecyclerLayoutBinding binding = NavigationRecyclerLayoutBinding.inflate(LayoutInflater.from(context), null, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NavigationRecyclerData item = navigationRecyclerData.get(position);
        holder.binding.tvItemText.setText(item.itemText);
        Glide.with(context)
                .load(item.imgUrl)
                .into(holder.binding.ivItemImage);

        // Todo: Add transparent background
        holder.binding.cvMain.setBackgroundResource(transparent);
        // Todo: Add on click functions

    }

    @Override
    public int getItemCount() {
        return navigationRecyclerData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivItemImage;
        TextView tvItemText;
        NavigationRecyclerLayoutBinding binding;
        public ViewHolder(@NonNull NavigationRecyclerLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
