package com.bitAndroid.eduzo.fragments;

import static android.R.color.transparent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bitAndroid.eduzo.classes.UserData;
import com.bitAndroid.eduzo.databinding.ErrorDialogBinding;
import com.bitAndroid.eduzo.databinding.FragmentNavigationBinding;
import com.bitAndroid.eduzo.recyclerview.NavigationRecyclerData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class NavigationFragment extends Fragment {
    FragmentNavigationBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNavigationBinding.inflate(inflater, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        // Layout Manipulation
        String uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        firebaseDatabase.getReference()
                        .child("Registered Users")
                                .child(uid)
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                UserData data = snapshot.getValue(UserData.class);
                                                binding.tvName.setText("Name: "+data.getName());
                                                binding.tvEmail.setText("Email: "+data.getEmail());
                                                binding.tvRole.setText("Role: "+data.getRole());
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                showErrorDialog();
                                            }
                                        });

        // Todo: Recycler View
        // 1. Create Data
        ArrayList<NavigationRecyclerData> navigationRecyclerData= new ArrayList<>();




        return binding.getRoot();
    }

    private void showErrorDialog() {
        AlertDialog dialog;
        ErrorDialogBinding errorDialogBinding = ErrorDialogBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
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
}
