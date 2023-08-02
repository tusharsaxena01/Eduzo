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
import com.bitAndroid.eduzo.databinding.AboutUsRecycleLayoutBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.ViewHolder> {
    Context context;
    ArrayList<AboutUsData> object;

    public AboutUsAdapter(Context context, ArrayList<AboutUsData> objectData) {
        this.context = context;
        this.object = objectData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AboutUsRecycleLayoutBinding binding = AboutUsRecycleLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AboutUsData item = object.get(position);
        holder.binding.tvName.setText(item.getName());
        holder.binding.tvRole.setText(item.getRole());
//        Glide.with(context)
//                .load(item.getImgUrl())
//                .into(holder.binding.ivDp);

        // Todo: Add transparent background
        holder.binding.cvMain.setBackgroundResource(transparent);
    }

    private void showBottomSheetDialog() {
        BottomSheetLayoutBinding bottomSheetLayoutBinding = BottomSheetLayoutBinding.inflate(LayoutInflater.from(context));
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomSheetLayoutBinding.getRoot());

        bottomSheetLayoutBinding.bsBar.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                v.canScrollVertically(0);
                v.canScrollVertically(1);
                return false;
            }
        });

        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.setDismissWithAnimation(true);

//        bottomSheetDialog.getWindow().setBackgroundDrawableResource(R.drawable.card_top_rounded);
        bottomSheetDialog.getWindow().setBackgroundDrawableResource(transparent);
        bottomSheetDialog.show();
    }

    @Override
    public int getItemCount() {
        return object.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AboutUsRecycleLayoutBinding binding;
        public ViewHolder(@NonNull AboutUsRecycleLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
