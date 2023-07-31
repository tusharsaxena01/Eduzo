package com.bitAndroid.eduzo.fragments;

import static android.R.color.transparent;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bitAndroid.eduzo.classes.UserData;
import com.bitAndroid.eduzo.databinding.ErrorDialogBinding;
import com.bitAndroid.eduzo.databinding.FragmentNavigationBinding;
import com.bitAndroid.eduzo.recyclerview.NavigationAdapter;
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
        binding.pbLoading.setVisibility(View.VISIBLE);
        String uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        firebaseDatabase.getReference()
                        .child("Registered Users")
                        .child(uid)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserData data = snapshot.getValue(UserData.class);
                                binding.tvName.setText(String.format("Name: %s", Objects.requireNonNull(data).getName()));
                                binding.tvEmail.setText(String.format("Email: %s", Objects.requireNonNull(data).getEmail()));
                                binding.tvRole.setText(String.format("Role: %s", Objects.requireNonNull(data).getRole()));
                                setRecyclerView(data.getRole());
                                binding.pbLoading.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                showErrorDialog();
                            }
                        });

        return binding.getRoot();
    }

    public void setRecyclerView(String role) {
        // Todo: Recycler View
        // 1. Create Data
        ArrayList<NavigationRecyclerData> navigationRecyclerData = new ArrayList<>();

        // Todo: Add if else clause for student and teacher
//        Student
//                - Start Quiz
//                - History
//        Teacher
//                - Submit Quiz
//                - Results
//        Common
//                - About Us
//                - Settings
//                - Logout

        // Adding items based on roles
        switch (role){
            case "Student":
                NavigationRecyclerData startQuiz = new NavigationRecyclerData(
                        Uri.parse("https://firebasestorage.googleapis.com/v0/b/eduzo-766c6.appspot.com/o/Navigation%20Menu%2FImages%2Fchoose.png?alt=media&token=d7adc96e-203e-4a04-8e93-6ef1f9e2adae"),
                        "Start Quiz"
                );
                NavigationRecyclerData history = new NavigationRecyclerData(
                        Uri.parse("https://firebasestorage.googleapis.com/v0/b/eduzo-766c6.appspot.com/o/Navigation%20Menu%2FImages%2Frecord.png?alt=media&token=6ac3cbf0-f105-4a49-be58-2c0e49674d1f"),
                        "History"
                );
                navigationRecyclerData.add(startQuiz);
                navigationRecyclerData.add(history);
                break;
            case "Teacher":
                NavigationRecyclerData submitQuiz = new NavigationRecyclerData(
                        Uri.parse("https://firebasestorage.googleapis.com/v0/b/eduzo-766c6.appspot.com/o/Navigation%20Menu%2FImages%2Fupload.png?alt=media&token=859024e9-719d-4649-bca8-5bf03835a944"),
                        "Submit Quiz"
                );
                NavigationRecyclerData results = new NavigationRecyclerData(
                        Uri.parse("https://firebasestorage.googleapis.com/v0/b/eduzo-766c6.appspot.com/o/Navigation%20Menu%2FImages%2Fhistory.png?alt=media&token=2c95f678-cf28-4ef2-9c8a-ed45190f9d00"),
                        "Results"
                );
                navigationRecyclerData.add(submitQuiz);
                navigationRecyclerData.add(results);
                break;
            default:
                showErrorDialog();
                break;
        }
        // Adding common items
        NavigationRecyclerData aboutUs = new NavigationRecyclerData(
                Uri.parse("https://firebasestorage.googleapis.com/v0/b/eduzo-766c6.appspot.com/o/Navigation%20Menu%2FImages%2Fabout%20us.png?alt=media&token=ee7c5942-9825-487f-a103-33b3f4b8b196"),
                "About Us"
        );
        NavigationRecyclerData settings = new NavigationRecyclerData(
                Uri.parse("https://firebasestorage.googleapis.com/v0/b/eduzo-766c6.appspot.com/o/Navigation%20Menu%2FImages%2Fsettings.png?alt=media&token=0ee06acf-e85b-44d1-960f-84d48485e6d3"),
                "Settings"
        );
        NavigationRecyclerData logOut = new NavigationRecyclerData(
                Uri.parse("https://firebasestorage.googleapis.com/v0/b/eduzo-766c6.appspot.com/o/Navigation%20Menu%2FImages%2Flogout.png?alt=media&token=4ef61301-e0cc-4993-8b16-1e5bca3780b2"),
                "Logout"
        );
        navigationRecyclerData.add(aboutUs);
        navigationRecyclerData.add(settings);
        navigationRecyclerData.add(logOut);

        NavigationAdapter adapter = new NavigationAdapter(requireContext(), navigationRecyclerData);

        binding.rvNavigation.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.rvNavigation.setAdapter(adapter);

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
