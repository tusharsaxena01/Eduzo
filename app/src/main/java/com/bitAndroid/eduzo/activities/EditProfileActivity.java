package com.bitAndroid.eduzo.activities;

import static android.os.Build.VERSION_CODES.R;

import static android.R.color.transparent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bitAndroid.eduzo.classes.UserData;
import com.bitAndroid.eduzo.databinding.ActivityEditProfileBinding;
import com.bitAndroid.eduzo.databinding.ErrorDialogBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String currentUserRole;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.ivBack.setOnClickListener(v -> {
            Intent navigationIntent = new Intent(EditProfileActivity.this, NavigationActivity.class);
            startActivity(navigationIntent);
        });

        firebaseDatabase.getReference()
                .child("Registered Users")
                .child(firebaseAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binding.pbLoading.setVisibility(View.VISIBLE);
                        UserData userData = snapshot.getValue(UserData.class);
                        if (userData != null) {
                            currentUserRole = userData.getRole();
                            setTextForFields(userData.getName(), userData.getMobileNo(), userData.getEmail(), userData.getPassword());
                        }
                        binding.pbLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        showErrorDialog("Unable to fetch data");
                    }
                });

        binding.btnUpdate.setOnClickListener(v -> {
            binding.pbLoading.setVisibility(View.VISIBLE);
            UserData userData = new UserData();
            userData.setName(binding.etName.getText().toString());
            userData.setMobileNo(binding.etMobile.getText().toString());
            userData.setEmail(binding.etEmail.getText().toString());
            userData.setPassword(binding.etPassword.getText().toString());
            updateUserInfo(userData, firebaseAuth.getCurrentUser().getUid());
            binding.pbLoading.setVisibility(View.GONE);
        });
    }

    private void updateUserInfo(UserData userData, String uuid) {
        userData.setRole(currentUserRole);
        firebaseDatabase.getReference()
                .child("Registered Users")
                .child(uuid)
                .setValue(userData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditProfileActivity.this, "User Saved Successfully", Toast.LENGTH_SHORT).show();
                        Intent navigationActivityIntent = new Intent(EditProfileActivity.this, NavigationActivity.class);
                        startActivity(navigationActivityIntent);
                    } else {
                        Toast.makeText(EditProfileActivity.this, "User Not Saved " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    binding.pbLoading.setVisibility(View.GONE);
                });
    }

    private void setTextForFields(String name, String mobileNo, String email, String password) {
        binding.etName.setText(name);
        binding.etMobile.setText(mobileNo);
        binding.etEmail.setText(email);
        binding.etPassword.setText(password);
        binding.etEmail.setEnabled(false);
    }

    private void showErrorDialog(String errorMessage) {
        ErrorDialogBinding errorDialogBinding = ErrorDialogBinding.inflate(getLayoutInflater());
        errorDialogBinding.tvMessage.setText(errorMessage);
        errorDialogBinding.btnOkay.setOnClickListener(v -> dialog.dismiss());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(errorDialogBinding.getRoot());
        dialog = builder.create();
        dialog.show();
        errorDialogBinding.cardMain.setBackgroundResource(transparent);
        dialog.getWindow().setBackgroundDrawableResource(transparent);
    }
}
