package com.bitAndroid.eduzo.activities;

import static android.R.color.transparent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    ActivityWelcomeBinding binding;
    SharedPreferences sp;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sp = getSharedPreferences("data", 0);
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getStartedIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(getStartedIntent);
            }
        });

        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo: add login with google

                // authentication code
                Intent googleSignInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(googleSignInIntent, 1234);

                //after authentication

                sp.edit()
                        .putInt("first_visit", 1)
                        .apply();

            }
        });

        binding.btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo: add login with email
//                Intent loginActivityIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                Intent registerIntent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo: add intent to move to login page
                Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    // for google authentication
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Todo: create next Activity in place of 'LoginActivity' in below code (call 1) -- done
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    assert user != null;
                                    String uuid = user.getUid();
                                    String name = user.getDisplayName();
                                    String email = user.getEmail();
                                    String mobileNo = user.getPhoneNumber();
                                    // default role for google users
                                    String role = "Student";
                                    UserData googleUser = new UserData(uuid, name, mobileNo, email, role);
                                    saveUser(googleUser);
                                    Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(WelcomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (ApiException e) {
//                throw new RuntimeException(e);
                showErrorDialog();
            }
        }

    }

    public void showErrorDialog() {
        ErrorDialogBinding errorDialogBinding = ErrorDialogBinding.inflate(getLayoutInflater());
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(errorDialogBinding.getRoot());
        dialog = builder.create();
        errorDialogBinding.btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        errorDialogBinding.cardMain.setBackgroundResource(transparent);
        dialog.getWindow().setBackgroundDrawableResource(transparent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            // Todo: create next Activity in place of 'LoginActivity' in below code (call 2) -- done

            Intent intent = new Intent(this, NavigationActivity.class);
            startActivity(intent);
        }
    }

    private void saveUser(UserData data) {

        try{
            String uuid = firebaseAuth.getUid();
            String role = "Student";

            binding.pbLoading.setVisibility(View.VISIBLE);

            assert uuid != null;
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Registered Users")
                    .child(uuid)
                    .setValue(data)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(WelcomeActivity.this, "User Saved Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(WelcomeActivity.this, "User Not Saved " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                showErrorDialog();
                            }
                            binding.pbLoading.setVisibility(View.GONE);
                        }
                    });
        } catch (NullPointerException e) {
            showErrorDialog();
        }
    }
}