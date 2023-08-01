package com.bitAndroid.eduzo.activities;

import static android.R.color.transparent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bitAndroid.eduzo.databinding.ActivityForgotPasswordBinding;
import com.bitAndroid.eduzo.databinding.EmailSentDialogBinding;
import com.bitAndroid.eduzo.databinding.ErrorDialogBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmail.getText().toString().trim();
                if(validate(email)){

                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        // Todo: create function
                                        showResetEmailSentDialog();
                                        Toast.makeText(ForgotPasswordActivity.this, "Reset Link Sent to Email", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        showErrorDialog();
                                        binding.etEmail.setError("Email Not Registered");
                                        Toast.makeText(ForgotPasswordActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });


        // Navigation
        Intent loginIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loginIntent);
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loginIntent);
            }
        });

    }

    public void showErrorDialog() {
        ErrorDialogBinding errorDialogBinding = ErrorDialogBinding.inflate(getLayoutInflater());
        errorDialogBinding.btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(errorDialogBinding.getRoot());
        dialog = builder.create();
        dialog.show();
        errorDialogBinding.cardMain.setBackgroundResource(transparent);
        dialog.getWindow().setBackgroundDrawableResource(transparent);
    }

    private void showResetEmailSentDialog() {
        EmailSentDialogBinding emailSentDialogBinding = EmailSentDialogBinding.inflate(getLayoutInflater());
//        emailSentDialogBinding.tvMessage.setText("Password Reset Email Sent!");
        emailSentDialogBinding.btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(emailSentDialogBinding.getRoot());
        dialog = builder.create();
        dialog.show();
        emailSentDialogBinding.cardMain.setBackgroundResource(transparent);
        dialog.getWindow().setBackgroundDrawableResource(transparent);
    }

    private boolean validate(String email) {
        if(email.equals("")){
            binding.etEmail.setError("Invalid Email");
            return false;
        }
        return true;
    }


}