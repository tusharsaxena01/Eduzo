package com.bitAndroid.eduzo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bitAndroid.eduzo.activities.HistoryActivity;
import com.bitAndroid.eduzo.activities.WelcomeActivity;
import com.bitAndroid.eduzo.classes.UserData;
import com.bitAndroid.eduzo.databinding.FragmentResultLayoutBinding;
import com.bitAndroid.eduzo.recyclerview.HistoryAdapter;
import com.bitAndroid.eduzo.recyclerview.HistoryData;
import com.bitAndroid.eduzo.recyclerview.ResultAdapter;
import com.bitAndroid.eduzo.recyclerview.ResultData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

        String uid = firebaseAuth.getCurrentUser().getUid();
        final String[] name = new String[1];
        firebaseDatabase.getReference()
                .child("Registered Users")
                .child(uid)
                .child("name")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                name[0] = (String) snapshot.getValue();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                new WelcomeActivity().showErrorDialog(error.getMessage());
                            }
                        });

        firebaseDatabase.getReference()
                .child("Results")
                .child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ArrayList<ResultData> resultData = new ArrayList<>();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            ResultData value = child.getValue(ResultData.class);
                            value.setTestName(name[0]);
                            resultData.add(value);
                        }

                        ResultAdapter adapter = new ResultAdapter(requireContext(), resultData);

                        binding.rvResults.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        binding.rvResults.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        new WelcomeActivity().showErrorDialog(error.getMessage());
                    }
                });
    }


}
