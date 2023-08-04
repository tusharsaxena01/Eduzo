package com.bitAndroid.eduzo.activities;

import static android.R.color.transparent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.classes.UserData;
import com.bitAndroid.eduzo.databinding.ActivityWelcomeBinding;
import com.bitAndroid.eduzo.databinding.ErrorDialogBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class WelcomeActivity extends AppCompatActivity {
    private ActivityWelcomeBinding binding;
    private SharedPreferences sp;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    private static final int RC_SIGN_IN = 1234;
    private static final String DATA_PREFS_NAME = "data";
    private static final String FIRST_VISIT_KEY = "first_visit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sp = getSharedPreferences(DATA_PREFS_NAME, MODE_PRIVATE);
        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        binding.ivBack.setOnClickListener(v -> {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        });

        binding.btnGoogle.setOnClickListener(v -> {
            Intent googleSignInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(googleSignInIntent, RC_SIGN_IN);
        });

        binding.btnEmail.setOnClickListener(v -> {
            Intent registerIntent = new Intent(WelcomeActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        binding.tvLogin.setOnClickListener(v -> {
            Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    String uuid = user.getUid();
                                    String name = user.getDisplayName();
                                    String email = user.getEmail();
                                    String mobileNo = user.getPhoneNumber();
                                    String role = "Student"; // default role for google users
                                    UserData googleUser = new UserData(uuid, name, mobileNo, email, role);
                                    saveUser(googleUser);
                                    Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                showErrorDialog(task1.getException().getMessage());
                            }
                        });
            } catch (ApiException e) {
                showErrorDialog();
            }
        }
    }

    public void showErrorDialog() {
        showErrorDialog("An error occurred during authentication.");
    }

    public void showErrorDialog(String message) {
        try {
            ErrorDialogBinding errorDialogBinding = ErrorDialogBinding.inflate(getLayoutInflater());
            errorDialogBinding.tvMessage.setText(message);

            AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(errorDialogBinding.getRoot());
            dialog = builder.create();
            errorDialogBinding.btnOkay.setOnClickListener(v -> dialog.dismiss());
            dialog.show();
            errorDialogBinding.cardMain.setBackgroundResource(transparent);
            dialog.getWindow().setBackgroundDrawableResource(transparent);
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Firebase Connection Error", Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), "Exiting Application", Toast.LENGTH_SHORT).show();
            finishAffinity();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(this, NavigationActivity.class));
        }
    }

    private void saveUser(UserData data) {
        try {
            String uuid = firebaseAuth.getUid();
            String role = "Student";

            binding.pbLoading.setVisibility(View.VISIBLE);

            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Registered Users")
                    .child(uuid)
                    .setValue(data)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(WelcomeActivity.this, "User Saved Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(WelcomeActivity.this, "User Not Saved " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            showErrorDialog();
                        }
                        binding.pbLoading.setVisibility(View.GONE);
                    });
        } catch (NullPointerException e) {
            showErrorDialog();
        }
    }
}
