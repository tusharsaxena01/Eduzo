package com.bitAndroid.eduzo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bitAndroid.eduzo.R;
import com.bitAndroid.eduzo.databinding.ActivityHistoryBinding;
import com.bitAndroid.eduzo.recyclerview.HistoryAdapter;
import com.bitAndroid.eduzo.recyclerview.HistoryData;
import com.bitAndroid.eduzo.recyclerview.NavigationAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ActivityHistoryBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigationIntent = new Intent(HistoryActivity.this, NavigationActivity.class);
                startActivity(navigationIntent);
            }
        });

        long totalTestCount = getTotalTestCount();
        ArrayList<HistoryData> historyData = getResults(totalTestCount);
        // -- sample data
        historyData.add(new HistoryData("hardcoded test","28-02-23", "2","2"));
        // -- end
        HistoryAdapter adapter = new HistoryAdapter(this, historyData);

        binding.rvResults.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        binding.rvResults.setAdapter(adapter);

    }


    private long getTotalTestCount() {
        final long[] testCount = new long[1];
        firebaseDatabase.getReference()
                .child("Results")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        testCount[0] = snapshot.getChildrenCount();
                        Log.e("children", ""+snapshot.getChildrenCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        new WelcomeActivity().showErrorDialog(error.getMessage());
                    }
                });
        return testCount[0];
    }
    private ArrayList<HistoryData> getResults(long totalTestCount) {
        ArrayList<HistoryData> historyData = new ArrayList<>();
        for (int i = 1; i <= totalTestCount; i++) {
            try{
                firebaseDatabase.getReference()
                        .child("Results")
                        .child(String.valueOf(i))
                        .child(firebaseAuth.getCurrentUser().getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                HistoryData item = snapshot.getValue(HistoryData.class);
                                historyData.add(item);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                throw new RuntimeException();
                            }
                        });
            }catch(RuntimeException e){
                continue;
            }
        }

        return historyData;
    }

}