package com.example.medrem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.medrem.data.LoginDataSource;
import com.example.medrem.data.LoginRepository;
import com.example.medrem.databinding.ActivityMainBinding;
import com.example.medrem.ui.login.LoginActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    @SuppressLint("NonConstantResourceId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!LoginRepository.getInstance(LoginDataSource.getInstance()).isLoggedIn()) {
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivity(loginActivity);
        }


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new TodayFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.today:
                    replaceFragment(new TodayFragment());
                    break;
                case R.id.progress:
                    replaceFragment(new ProgressFragment());
                    break;
                case R.id.therapy:
                    replaceFragment(new TherapyFragment());
                    break;
            }
            return true;
        });
        if (getIntent().hasExtra("measurementId")) {
            MeasurementClicked();
        }
    }

    private void MeasurementClicked() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("measurements").document(getIntent().getStringExtra("measurementId"));
        documentReference.update("clicked", true);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}