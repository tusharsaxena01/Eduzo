package com.bitAndroid.eduzo.activities;

import static android.R.color.transparent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bitAndroid.eduzo.classes.UserData;
import com.bitAndroid.eduzo.databinding.ConfirmDialogBinding;
import com.bitAndroid.eduzo.fragments.NavigationFragment;
import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.databinding.ActivityNavigationBinding;
import com.bitAndroid.eduzo.recyclerview.NavigationAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class NavigationActivity extends AppCompatActivity {
    ActivityNavigationBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    NavigationFragment navigationFragment = new NavigationFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            binding = ActivityNavigationBinding.inflate(LayoutInflater.from(NavigationActivity.this));
        }catch(Exception e){
            Toast.makeText(this, "Firebase Server not Responding, Try Again Later", Toast.LENGTH_SHORT).show();
            finishAffinity();
//            Toast.makeText(this, "Exiting Application", Toast.LENGTH_SHORT).show();
            new WelcomeActivity().showErrorDialog("Internet Connection Error");
        }
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });

        String uuid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        Log.w("uid", uuid);

//        firebaseDatabase.getReference().child("Registered Users")
//                        .child(uuid)
//                                .addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        UserData data = snapshot.getValue(UserData.class);
//                                        // Todo: set recycler view adapter based on roles
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });


        // Setting fragment container to change
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragContainer, navigationFragment)
                .commit();

    }
    public void showConfirmDialog() {
        AlertDialog dialog;
        ConfirmDialogBinding confirmDialogBinding = ConfirmDialogBinding.inflate(LayoutInflater.from(NavigationActivity.this), null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(NavigationActivity.this);
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
                Intent welcomeIntent = new Intent(NavigationActivity.this, WelcomeActivity.class);
                startActivity(welcomeIntent);
            }
        });

        dialog.show();
        confirmDialogBinding.cardMain.setBackgroundResource(transparent);
        dialog.getWindow().setBackgroundDrawableResource(transparent);
    }


}