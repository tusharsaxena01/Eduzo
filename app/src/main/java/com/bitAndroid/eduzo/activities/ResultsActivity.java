package com.bitAndroid.eduzo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.databinding.ActivityResultsBinding;
import com.bitAndroid.eduzo.recyclerview.HistoryAdapter;
import com.bitAndroid.eduzo.recyclerview.ResultAdapter;
import com.bitAndroid.eduzo.recyclerview.ResultData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    ActivityResultsBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigationIntent = new Intent(ResultsActivity.this, NavigationActivity.class);
                startActivity(navigationIntent);
            }
        });

        setData();

    }

    private void setData() {
        binding.pbLoading.setVisibility(View.VISIBLE);
        // Get a reference to the "Results" node in the database
        DatabaseReference resultsRef = FirebaseDatabase.getInstance().getReference().child("Results");
        
        resultsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> users = snapshot.getChildren(); // each userid
                ArrayList<ResultData> resultData = new ArrayList<>();
                for (DataSnapshot user : users) {
                    Iterable<DataSnapshot> tests = user.getChildren();
                    // get user name
                    for (DataSnapshot test : tests) {
                        ResultData value = test.getValue(ResultData.class);
                        value.setTestName("dummy username");
                        resultData.add(value);
                    }
                }

                ResultAdapter adapter = new ResultAdapter(ResultsActivity.this, resultData);

                binding.rvResults.setLayoutManager(new LinearLayoutManager(ResultsActivity.this, LinearLayoutManager.VERTICAL, false));

                binding.rvResults.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                new WelcomeActivity().showErrorDialog(error.getMessage());
            }
        });
        binding.pbLoading.setVisibility(View.GONE);
        // Get a reference to the specific user's test node
//        DatabaseReference userTestRef = resultsRef.child(userId).child("testNo");

//        firebaseDatabase.getReference()
//                .child("Results")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Log.e("children", ""+snapshot.getChildrenCount());
//                        ArrayList<ResultData> resultData = new ArrayList<>();
//                        for (DataSnapshot child : snapshot.getChildren()) {
//                            ResultData value = child.getValue(ResultData.class);
//                            value.setTestName(child.getKey());
//                            resultData.add(value);
//                        }
//                        ResultAdapter adapter = new ResultAdapter(ResultsActivity.this, resultData);
//
//                        binding.rvResults.setLayoutManager(new LinearLayoutManager(ResultsActivity.this, LinearLayoutManager.VERTICAL, false));
//
//                        binding.rvResults.setAdapter(adapter);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        new WelcomeActivity().showErrorDialog(error.getMessage());
//                    }
//                });
    }
}