package com.bitAndroid.eduzo.recyclerview;

import static android.R.color.transparent;
import android.content.Context;
import android.content.Intent;
import android.view.DragEvent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.activities.EditProfileActivity;
import com.bitAndroid.eduzo.activities.HistoryActivity;
import com.bitAndroid.eduzo.activities.QuizActivity;
import com.bitAndroid.eduzo.activities.ResultsActivity;
import com.bitAndroid.eduzo.activities.SubmitQuizActivity;
import com.bitAndroid.eduzo.activities.WelcomeActivity;
import com.bitAndroid.eduzo.databinding.BottomSheetLayoutBinding;
import com.bitAndroid.eduzo.databinding.ConfirmDialogBinding;
import com.bitAndroid.eduzo.databinding.NavigationRecyclerLayoutBinding;
import com.bitAndroid.eduzo.databinding.SettingsBottomSheetLayoutBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {
    Context context;
    ArrayList<NavigationRecyclerData> navigationRecyclerData;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetLayoutBinding bottomSheetLayoutBinding;

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
            case "Start Quiz":
                // Todo: Add working here
                Intent quizIntent = new Intent(context, QuizActivity.class);
                context.startActivity(quizIntent);
                break;
            case "Submit Quiz":
                // Todo: Add working here
                Intent submitQuizIntent = new Intent(context, SubmitQuizActivity.class);
                context.startActivity(submitQuizIntent);
                break;
            case "Results":
                // Todo: Add working here
                Intent resultIntent = new Intent(context, ResultsActivity.class);
                context.startActivity(resultIntent);
                break;
            case "History":
                // Todo: Add working here
                Intent historyIntent = new Intent(context, HistoryActivity.class);
                context.startActivity(historyIntent);
                break;
            case "About Us":
                // Todo: Add working here
                showBottomSheetDialog();
                break;
            case "Settings":
                // Todo: Add working here
                showSettingsBottomSheetDialog();
                break;

            case "Logout":
                // Todo: Move to a function
                showConfirmDialog();
                break;
            default:
                break;
        }
    }

    private void showSettingsBottomSheetDialog() {
        SettingsBottomSheetLayoutBinding settingsBottomSheetLayoutBinding = SettingsBottomSheetLayoutBinding.inflate(LayoutInflater.from(context));
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(settingsBottomSheetLayoutBinding.getRoot());

        settingsBottomSheetLayoutBinding.bsBar.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                v.canScrollVertically(0);
                v.canScrollVertically(1);
                return false;
            }
        });

        settingsBottomSheetLayoutBinding.updateProfileOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfileIntent = new Intent(context, EditProfileActivity.class);
                context.startActivity(editProfileIntent);
            }
        });

        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.setDismissWithAnimation(true);

//        bottomSheetDialog.getWindow().setBackgroundDrawableResource(R.drawable.card_top_rounded);
        bottomSheetDialog.getWindow().setBackgroundDrawableResource(transparent);
        bottomSheetDialog.show();
    }

    private void showBottomSheetDialog() {
        bottomSheetLayoutBinding = BottomSheetLayoutBinding.inflate(LayoutInflater.from(context));
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomSheetLayoutBinding.getRoot());

        bottomSheetLayoutBinding.bsBar.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                v.canScrollVertically(0);
                v.canScrollVertically(1);
                return false;
            }
        });

        bottomSheetLayoutBinding.tvTitle.setText("About Us");

        // Getting data from realtime database and set in recycler view
        setRecyclerView();

        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.setDismissWithAnimation(true);

//        bottomSheetDialog.getWindow().setBackgroundDrawableResource(R.drawable.card_top_rounded);
        bottomSheetDialog.getWindow().setBackgroundDrawableResource(transparent);
        bottomSheetDialog.show();
    }

    private void setRecyclerView() {
        ArrayList<AboutUsData> aboutUsData = new ArrayList<>();
        AboutUsData aboutUsData1 = new AboutUsData(
                R.drawable.creator,
                "Abhi Saxena",
                "Leader"
        );

        // Todo: Change dp url

        AboutUsData aboutUsData2 = new AboutUsData(
                R.drawable.creator,
                "Tanmay Singh",
                "Member"
        );
        AboutUsData aboutUsData3 = new AboutUsData(
                R.drawable.creator,
                "Shresth Prakash Yadav",
                "Member"
        );
        aboutUsData.add(aboutUsData1);
        aboutUsData.add(aboutUsData2);
        aboutUsData.add(aboutUsData3);

        AboutUsAdapter adapter = new AboutUsAdapter(bottomSheetDialog.getContext(), aboutUsData);

//        bottomSheetLayoutBinding = BottomSheetLayoutBinding.inflate(LayoutInflater.from(bottomSheetDialog.getContext()), null, false);


//        bottomSheetLayoutBinding.rvCreators.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        bottomSheetLayoutBinding.rvCreators.setLayoutManager(new LinearLayoutManager(
                bottomSheetDialog.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        ));
        bottomSheetLayoutBinding.rvCreators.setAdapter(adapter);

    }


    public void showConfirmDialog() {
        AlertDialog dialog;
        ConfirmDialogBinding confirmDialogBinding = ConfirmDialogBinding.inflate(LayoutInflater.from(context), null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(confirmDialogBinding.getRoot());
        dialog = builder.create();
        confirmDialogBinding.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirmDialogBinding.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent welcomeIntent = new Intent(context, WelcomeActivity.class);
                context.startActivity(welcomeIntent);
            }
        });

        dialog.show();
        confirmDialogBinding.cardMain.setBackgroundResource(transparent);
        dialog.getWindow().setBackgroundDrawableResource(transparent);
    }


    @Override
    public int getItemCount() {
        return navigationRecyclerData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        NavigationRecyclerLayoutBinding binding;
        public ViewHolder(@NonNull NavigationRecyclerLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
