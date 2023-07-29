package com.bitAndroid.eduzo.Activities;

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
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;
    LayoutRegisterBinding registerBinding;
    LayoutLoginBinding loginBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent prevIntent = getIntent();
        firebaseAuth = FirebaseAuth.getInstance();

        // changing card
        ViewStub stub = binding.icLayout.getViewStub();

        // Todo: add login and register functionality here
        if(prevIntent.getStringExtra("login_register").equals("register")){
            // register working here
            binding.tvHeaderText.setText("Register to Continue");
            assert stub != null;
            stub.setLayoutResource(R.layout.layout_register);
            stub.inflate();
            registerBinding = LayoutRegisterBinding.inflate(LayoutInflater.from(this));


        } else if (prevIntent.getStringExtra("login_register").equals("login")) {
            // login working here
            binding.tvHeaderText.setText("Welcome Back");
            stub.setLayoutResource(R.layout.layout_login);
            stub.inflate();
            loginBinding = LayoutLoginBinding.inflate(LayoutInflater.from(this));

        }


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomeIntent = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(welcomeIntent);
            }
        });

        // Register layout functionality
        if(registerBinding != null){
            registerBinding.cbTerms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (registerBinding.cbTerms.isChecked()) {
                        registerBinding.btnRegister.setEnabled(true);
                    } else {
                        registerBinding.btnRegister.setEnabled(false);
                    }
                }
            });

            registerBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate(registerBinding, prevIntent.getStringExtra("login_register"))) {
                        // Todo: add register working
                        String text = "Name" + registerBinding.etName.getText().toString()
                                + ", Email: " + registerBinding.etEmail.getText().toString()
                                + ", Mobile No: " + registerBinding.etMobile.getText().toString()
                                + ", Password: " + registerBinding.etPassword.getText().toString();
                        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
                        Log.e("check", text);
                    }
                }
            });
        }


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

    // register validation
    private boolean validate(LayoutRegisterBinding registerBinding, String login_register) {

        if(registerBinding.etName.getText().toString().equals("")){
            registerBinding.etName.setError("Invalid Name");
            return false;
        } else if (registerBinding.etEmail.getText().toString().equals("")) {
            registerBinding.etEmail.setError("Invalid Email");
            return false;
        } else if (registerBinding.etMobile.getText().toString().length() < 10) {
            registerBinding.etMobile.setError("Invalid Mobile Number");
            return false;
        } else if (registerBinding.etPassword.getText().toString().length() < 8) {
            registerBinding.etPassword.setError("Password must be of least length 8");
            return false;
        }else{
            return true;
        }

    }


}