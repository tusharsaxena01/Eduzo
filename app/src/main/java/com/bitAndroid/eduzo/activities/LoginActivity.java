package com.bitAndroid.eduzo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bitAndroid.eduzo.classes.UserData;
import com.bitAndroid.eduzo.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // Navigation
        binding.ivBack.setOnClickListener(v -> {
            Intent welcomeIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(welcomeIntent);
        });

        binding.tvRegister.setOnClickListener(v -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        //Login functionality
        binding.btnLogin.setOnClickListener(v -> {
            binding.pbLoading.setVisibility(View.VISIBLE);
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString();

            if (validate(email, password)) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            binding.pbLoading.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                String role = getRole(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                                getSharedPreferences("data", 0).edit()
                                        .putString("role", role)
                                        .putInt("first_visit", 1)
                                        .apply();
                                Intent navigationIntent = new Intent(LoginActivity.this, NavigationActivity.class);
                                startActivity(navigationIntent);
                            } else {
                                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                binding.pbLoading.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });

        // Forgot Password
        binding.tvForgotPassword.setOnClickListener(v -> {
            Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(forgotPasswordIntent);
        });
    }

    private String getRole(String uuid) {
        final String[] role = new String[1];
        firebaseDatabase.getReference()
                .child("Registered Users")
                .child(uuid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserData data = snapshot.getValue(UserData.class);
                        if (data != null) {
                            role[0] = data.getRole();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return role[0];
    }

    private boolean validate(String email, String password) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length() >= 8;
    }
}
