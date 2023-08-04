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
        setData();

    }


    private void setData() {
        firebaseDatabase.getReference()
                .child("Results").child(firebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        testCount[0] = snapshot.getChildrenCount();
                        Log.e("children", ""+snapshot.getChildrenCount());
                        ArrayList<HistoryData> historyData = new ArrayList<>();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            HistoryData value = child.getValue(HistoryData.class);
                            value.setTestName(child.getKey());
                            historyData.add(value);
                        }
                        HistoryAdapter adapter = new HistoryAdapter(HistoryActivity.this, historyData);

                        binding.rvResults.setLayoutManager(new LinearLayoutManager(HistoryActivity.this, LinearLayoutManager.VERTICAL, false));

                        binding.rvResults.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        new WelcomeActivity().showErrorDialog(error.getMessage());
                    }
                });
    }

}