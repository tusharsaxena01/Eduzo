package com.bitAndroid.eduzo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bitAndroid.eduzo.Fragments.NavigationFragment;
import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.databinding.ActivityNavigationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class NavigationActivity extends AppCompatActivity {
    ActivityNavigationBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    NavigationFragment navigationFragment = new NavigationFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());

        String uuid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        Log.w("uid", uuid);


        // Setting fragment container to change
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragContainer, navigationFragment)
                .commit();

    }

}