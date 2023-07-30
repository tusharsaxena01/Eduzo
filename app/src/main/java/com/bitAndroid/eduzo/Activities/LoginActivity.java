package com.bitAndroid.eduzo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.databinding.ActivityLoginBinding;
import com.bitAndroid.eduzo.databinding.LayoutLoginBinding;
import com.bitAndroid.eduzo.databinding.LayoutRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomeIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(welcomeIntent);
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
                                    }else{
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    binding.pbLoading.setVisibility(View.GONE);
                                }
                            });
                }
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

    private boolean validate(String email, String password) {
        if(email != ""){
            return password != "" && password.length() > 8;
        }
        return false;
    }


}