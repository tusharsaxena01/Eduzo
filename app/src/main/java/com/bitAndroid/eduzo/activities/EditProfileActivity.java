package com.bitAndroid.eduzo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.classes.UserData;
import com.bitAndroid.eduzo.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    final String[] currentUserRole = new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigationIntent = new Intent(EditProfileActivity.this, NavigationActivity.class);
                startActivity(navigationIntent);
            }
        });

        firebaseDatabase.getReference()
                .child("Registered Users")
                .child(firebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binding.pbLoading.setVisibility(View.VISIBLE);
                        UserData value = snapshot.getValue(UserData.class);
                        String name = value.getName();
                        String mobileNo = value.getMobileNo();
                        String email = value.getEmail();
                        String password = value.getPassword();
                        currentUserRole[0] = value.getRole();
                        setTextForFields(name, mobileNo, email, password);
                        binding.pbLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        new WelcomeActivity().showErrorDialog("Unable to fetch data");
                    }
                });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.pbLoading.setVisibility(View.VISIBLE);
                UserData userData = new UserData();
                userData.setName(binding.etName.getText().toString());
                userData.setMobileNo(binding.etMobile.getText().toString());
                userData.setEmail(binding.etEmail.getText().toString());
                userData.setPassword(binding.etPassword.getText().toString());
                updateUserInfo(userData, firebaseAuth.getCurrentUser().getUid());
                binding.pbLoading.setVisibility(View.GONE);
            }
        });

    }

    private void updateUserInfo(UserData userData, String uuid) {
        userData.setRole(currentUserRole[0]);
        firebaseDatabase.getReference()
                .child("Registered Users")
                .child(uuid)
                .setValue(userData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EditProfileActivity.this, "User Saved Successfully", Toast.LENGTH_SHORT).show();
                            Intent navigationActivityIntent = new Intent(EditProfileActivity.this, NavigationActivity.class);
                            startActivity(navigationActivityIntent);
                        }else{
                            Toast.makeText(EditProfileActivity.this, "User Not Saved "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        binding.pbLoading.setVisibility(View.GONE);
                    }
                });
    }

    private void setTextForFields(String name, String mobileNo, String email, String password) {
        binding.etName.setText(name);
        binding.etMobile.setText(mobileNo);
        binding.etEmail.setText(email);
        binding.etPassword.setText(password);
        binding.etEmail.setEnabled(false);
    }
}