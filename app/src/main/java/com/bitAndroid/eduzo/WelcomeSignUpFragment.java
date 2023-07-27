package com.bitAndroid.eduzo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bitAndroid.eduzo.databinding.WelcomeSignUpFragmentLayoutBinding;

public class WelcomeSignUpFragment extends Fragment {
    WelcomeSignUpFragmentLayoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WelcomeSignUpFragmentLayoutBinding.inflate(inflater, container, false);

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = binding.etFullName.getText().toString();
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();
                if(validate(fullName, email, password)){
                    Toast.makeText(getContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();
    }

    private boolean validate(String fullName, String email, String password) {
        if(fullName.equals("") || fullName.equals(" ")){
            binding.etFullName.setError("Invalid Name, Name Should not be Empty");
        }else{
            // TODO: Set Email Validation Here

            if(password.equals("") || password.length()<8){
                Log.e("Check","something");
            }
        }
        return true;
    }

}
