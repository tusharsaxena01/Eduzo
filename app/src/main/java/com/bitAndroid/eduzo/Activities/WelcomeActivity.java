package com.bitAndroid.eduzo.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.databinding.ActivityWelcomeBinding;
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
                Intent loginActivityIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                loginActivityIntent.putExtra("login_register","register");
                startActivity(loginActivityIntent);
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo: add intent to move to login page
                Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                loginIntent.putExtra("login_register","login");
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
                                    // Todo: create next Activity in place of 'LoginActivity' in below code (call 1)
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(WelcomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            // Todo: create next Activity in place of 'LoginActivity' in below code (call 2)
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}