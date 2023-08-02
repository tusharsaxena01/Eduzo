package com.bitAndroid.eduzo.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bitAndroid.eduzo.activities.ForgotPasswordActivity;
import com.bitAndroid.eduzo.activities.LoginActivity;
import com.bitAndroid.eduzo.activities.RegisterActivity;
import com.bitAndroid.eduzo.activities.WelcomeActivity;
import com.bitAndroid.eduzo.databinding.FragmentResultLayoutBinding;
import com.bitAndroid.eduzo.recyclerview.ResultAdapter;
import com.bitAndroid.eduzo.recyclerview.NavigationRecyclerData;
import com.bitAndroid.eduzo.recyclerview.ResultData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultFragment extends Fragment {
// Todo: complete it
    FragmentResultLayoutBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentResultLayoutBinding.inflate(inflater, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // Todo: Manipulate layout
        setRecyclerView();

        return binding.getRoot();
    }

    public void setRecyclerView() {
        // Todo: Recycler View
        // 1. Create Data

        long testCount = getEntriesFromDatabase();

        ArrayList<ResultData> resultData = getTestResults(testCount);


        ResultAdapter adapter = new ResultAdapter(requireContext(), resultData);

        binding.rvResults.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvResults.setAdapter(adapter);

    }

    private ArrayList<ResultData> getTestResults(long testCount) {
        ArrayList<ResultData> resultData = new ArrayList<>();
        String uid = firebaseAuth.getCurrentUser().getUid();
        for(int i=1;i<=testCount;i++){
            firebaseDatabase.getReference()
                    .child("Results")
                    .child(String.valueOf(i))
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ResultData item = snapshot.getValue(ResultData.class);
                            resultData.add(item);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            new WelcomeActivity().showErrorDialog(error.getMessage());
                        }
                    });
        }
        return resultData;
    }

    private long getEntriesFromDatabase() {
        final long[] testCount = new long[1];
        String uid = firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase.getReference()
                .child("Results")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        testCount[0] = snapshot.getChildrenCount();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        new WelcomeActivity().showErrorDialog(error.getMessage());
                    }
                });
        return testCount[0];
    }

}
