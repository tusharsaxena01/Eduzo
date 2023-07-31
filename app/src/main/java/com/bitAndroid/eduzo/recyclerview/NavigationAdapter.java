package com.bitAndroid.eduzo.recyclerview;

import static android.R.color.transparent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitAndroid.eduzo.activities.WelcomeActivity;
import com.bitAndroid.eduzo.databinding.NavigationRecyclerLayoutBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

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
        // Todo: Add More as per need
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkItemName(item);
            }
        });


    }

    private void checkItemName(NavigationRecyclerData item) {
        switch(item.itemText){
            case "Logout":
                Toast.makeText(context, item.itemText+" Clicked", Toast.LENGTH_SHORT).show();
                // Todo: Move to a function
                FirebaseAuth.getInstance().signOut();
                Intent welcomeIntent = new Intent(context, WelcomeActivity.class);
                context.startActivity(welcomeIntent);
                break;
            default:
                break;
        }
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
