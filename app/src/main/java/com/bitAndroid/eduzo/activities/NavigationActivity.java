package com.bitAndroid.eduzo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bitAndroid.eduzo.classes.UserData;
import com.bitAndroid.eduzo.fragments.NavigationFragment;
import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.databinding.ActivityNavigationBinding;
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
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());

        String uuid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        Log.w("uid", uuid);

        firebaseDatabase.getReference().child("Registered Users")
                        .child(uuid)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        UserData data = snapshot.getValue(UserData.class);
                                        // Todo: set recycler view adapter based on roles

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


        // Setting fragment container to change
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragContainer, navigationFragment)
                .commit();

    }

}