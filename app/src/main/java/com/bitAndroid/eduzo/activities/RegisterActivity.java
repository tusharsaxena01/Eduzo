package com.bitAndroid.eduzo.activities;

import static android.R.color.transparent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bitAndroid.eduzo.classes.UserData;
import com.bitAndroid.eduzo.databinding.ActivityRegisterBinding;
import com.bitAndroid.eduzo.databinding.ErrorDialogBinding;
import com.bitAndroid.eduzo.databinding.SuccessDialogBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        //Navigation

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomeIntent = new Intent(RegisterActivity.this, WelcomeActivity.class);
                Log.e("welcomeIntent", "init");
                startActivity(welcomeIntent);
                Log.e("welcomeIntent", "done");
            }
        });
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });


        // Register layout functionality
        binding.btnRegister.setEnabled(false);

        binding.cbTerms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.btnRegister.setEnabled(binding.cbTerms.isChecked());
                }
            });

            binding.btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if(validate()) {
                            // Todo: add register working
                            String text = "Name: " + binding.etName.getText().toString()
                                    + ", Email: " + binding.etEmail.getText().toString()
                                    + ", Mobile No: " + binding.etMobile.getText().toString()
                                    + ", Password: " + binding.etPassword.getText().toString();
                            Toast.makeText(RegisterActivity.this, text, Toast.LENGTH_SHORT).show();
                            Log.e("check", text);
                            binding.pbLoading.setVisibility(View.VISIBLE);
                            String name = binding.etName.getText().toString().trim();
                            String mobileNo = binding.etMobile.getText().toString().trim();
                            String email = binding.etEmail.getText().toString().trim();
                            String password = binding.etPassword.getText().toString();
                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            saveUser(name, mobileNo, email, password);
                                            // Todo: Create function
                                            showSuccessDialog("Registration Successful");

                                            Intent navigationIntent = new Intent(RegisterActivity.this, NavigationActivity.class);
                                            startActivity(navigationIntent);
                                        }
                                    });
                            }else{
                            showErrorDialog();
                            binding.pbLoading.setVisibility(View.GONE);
                            }
                }
            });


    }

    private void showErrorDialog() {
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

    private void showSuccessDialog(String message) {

        SuccessDialogBinding successDialogBinding = SuccessDialogBinding.inflate(getLayoutInflater());
        successDialogBinding.btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(successDialogBinding.getRoot());
        dialog = builder.create();
        dialog.show();
        successDialogBinding.cardMain.setBackgroundResource(transparent);
        dialog.getWindow().setBackgroundDrawableResource(transparent);

    }

    public void saveUser(String name, String mobileNo, String email, String password) {

        String uuid = firebaseAuth.getUid();
        String role = "Student";
        UserData data = new UserData(uuid,name, mobileNo, email, password, role);

        binding.pbLoading.setVisibility(View.VISIBLE);

        firebaseDatabase.getReference()
                .child("Registered Users")
                .child(uuid)
                .setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Saved Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, "User Not Saved "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        binding.pbLoading.setVisibility(View.GONE);
                    }
                });

    }

    private boolean validate() {
            binding.pbLoading.setVisibility(View.VISIBLE);
            if(binding.etName.getText().toString().equals("")){
                binding.etName.setError("Invalid Name");
                getFocusOn(binding.etName);
                return false;
            } else if (emailValidator(binding.etEmail.getText().toString())) {
                binding.etEmail.setError("Invalid Email");
                getFocusOn(binding.etEmail);
                return false;
            } else if (binding.etMobile.getText().toString().length() < 10) {
                binding.etMobile.setError("Invalid Mobile Number");
                getFocusOn(binding.etMobile);
                return false;
            } else if (binding.etPassword.getText().toString().length() < 8) {
                binding.etPassword.setError("Password must be of least length 8");
                getFocusOn(binding.etPassword);
                return false;
            }else{
                return true;
            }
    }

    private boolean emailValidator(String emailToText) {
        return emailToText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailToText).matches();
    }

    private void getFocusOn(EditText field) {
        field.setFocusable(true);
        field.setFocusableInTouchMode(true);
        field.requestFocus();
    }
}