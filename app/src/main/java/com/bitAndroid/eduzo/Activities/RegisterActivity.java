package com.bitAndroid.eduzo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.databinding.ActivityRegisterBinding;
import com.bitAndroid.eduzo.databinding.LayoutRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();


        // Register layout functionality
        binding.btnRegister.setEnabled(false);

        binding.cbTerms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (binding.cbTerms.isChecked()) {
                        binding.btnRegister.setEnabled(true);
                    } else {
                        binding.btnRegister.setEnabled(false);
                    }
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
                            binding.pbLoading.setVisibility(View.GONE);
                            String email = binding.etEmail.getText().toString();
                            String password = binding.etPassword.getText().toString();
                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            binding.pbLoading.setVisibility(View.VISIBLE);
                                            // Todo: Create function
//                                            showSuccessDialog("Registration");
                                        }
                                    });
                        }else{
                            binding.btnRegister.setError("Error");
                            binding.pbLoading.setVisibility(View.GONE);
                        }
                }
            });


    }

    private boolean validate() {
            binding.pbLoading.setVisibility(View.VISIBLE);
            if(binding.etName.getText().toString().equals("")){
                binding.etName.setError("Invalid Name");
                return false;
            } else if (binding.etEmail.getText().toString().equals("")) {
                binding.etEmail.setError("Invalid Email");
                return false;
            } else if (binding.etMobile.getText().toString().length() < 10) {
                binding.etMobile.setError("Invalid Mobile Number");
                return false;
            } else if (binding.etPassword.getText().toString().length() < 8) {
                binding.etPassword.setError("Password must be of least length 8");
                return false;
            }else{
                return true;
            }
    }
}