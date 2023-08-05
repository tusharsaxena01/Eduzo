package com.bitAndroid.eduzo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bitAndroid.eduzo.classes.UserData;
import com.bitAndroid.eduzo.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        binding.btnRegister.setOnClickListener(v -> {
            String name = binding.etName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                if (user != null) {
                                    String uuid = user.getUid();
                                    String mobileNo = user.getPhoneNumber();
                                    String role = "Student"; // default role for new users
                                    UserData newUser = new UserData(uuid, name, mobileNo, email, role);
                                    saveUser(newUser);
                                    Intent intent = new Intent(RegisterActivity.this, NavigationActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.tvLogin.setOnClickListener(v -> {
            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });
    }

    private void saveUser(UserData data) {
        try {
            String uuid = firebaseAuth.getUid();

            binding.pbLoading.setVisibility(View.VISIBLE);

            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Registered Users")
                    .child(uuid)
                    .setValue(data)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "User Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        binding.pbLoading.setVisibility(View.GONE);
                    });
        } catch (NullPointerException e) {
            Toast.makeText(RegisterActivity.this, "User Registration Failed: NullPointerException", Toast.LENGTH_SHORT).show();
        }
    }
}
