package com.bitAndroid.eduzo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bitAndroid.eduzo.Classes.UserData;
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

    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // Navigation
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomeIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(welcomeIntent);
            }
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        //Login functionality

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();
                if(validate(email, password)){
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    binding.pbLoading.setVisibility(View.VISIBLE);
                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        @NonNull
                                        String role = getRole(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                                        getSharedPreferences("data", 0).edit()
                                                .putString("role", role)
                                                .putInt("first_visit", 1)
                                                .apply();
                                        Intent navigationIntent = new Intent(LoginActivity.this, NavigationActivity.class);
                                        startActivity(navigationIntent);
                                    }else{
                                        Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    binding.pbLoading.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });

        // Forgot Password
        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPasswordIntent);
            }
        });

//
//        String phoneNo = firebaseAuth.getCurrentUser().getPhoneNumber();
//        String email = firebaseAuth.getCurrentUser().getEmail();
//        binding.tvSignInName.setText("Signed in as "+
//                firebaseAuth.getCurrentUser().getDisplayName()+
//                "\n Phone No:"+phoneNo+
//                "\n Email: "+email);
//        // Todo: remove this code, this is temporary
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//        StrictMode.setThreadPolicy(policy);
//
//        // get display picture
//
////        Uri dp = firebaseAuth.getCurrentUser().getPhotoUrl();
//
//
//        URL url = null;
//        try {
//            url = new URL(firebaseAuth.getCurrentUser().getPhotoUrl().toString());
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        Bitmap bmp = null;
//        try {
//            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        binding.ivDp.setImageBitmap(bmp);
//
////        binding.ivDp.setImageResource(url);
//
//        //logout button
//        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut();
//                Intent intent= new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    private String getRole(String uuid) {
        final String[] role = new String[1];
        firebaseDatabase.getReference()
                .child("Registered Users")
                .child(uuid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserData data = snapshot.getValue(UserData.class);
                        if(data != null){
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
        if(email.equals(""))
            return false;
        return !password.equals("") && password.length() >= 8;
    }


}