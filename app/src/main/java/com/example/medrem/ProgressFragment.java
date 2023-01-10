package com.example.medrem;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.medrem.data.LoginDataSource;
import com.example.medrem.data.LoginRepository;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class ProgressFragment extends Fragment {

    public ProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        TextView progressInitialTextView = view.findViewById(R.id.initialTextViewProgress);
        ListView progressMedicinesListView = view.findViewById(R.id.listViewProgressMedicines);
        ListView progressMeasurementsListView = view.findViewById(R.id.listViewProgressMeasurements);
        Button showMedicinesProgressButton = view.findViewById(R.id.showMedicinesProgressButton);
        Button showMeasurementsProgressButton = view.findViewById(R.id.showMeasurementsProgressButton);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Medicine> medicinesFromDb = new ArrayList<>();
        List<Measurement> measurementsFromDb = new ArrayList<>();
        db.collection("medicines")
                .orderBy("date")
                .orderBy("time")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Medicine medicine = document.toObject(Medicine.class);
                            if (medicine.getUsername() != null &&
                                    medicine.getUsername().equals(LoginRepository.getInstance(LoginDataSource.getInstance()).getUsername())) {
                                medicinesFromDb.add(medicine);
                            }
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
        db.collection("measurements")
                .orderBy("date")
                .orderBy("time")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Measurement measurement = document.toObject(Measurement.class);

                            if (measurement.getUsername() != null &&
                                    measurement.getUsername().equals(LoginRepository.getInstance(LoginDataSource.getInstance()).getUsername())) {
                                measurementsFromDb.add(measurement);
                            }
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });


        progressInitialTextView.setText("Wybierz listę klikając przycisk");

        showMedicinesProgressButton.setOnClickListener(v -> {
            progressInitialTextView.setVisibility(View.VISIBLE);
            progressMeasurementsListView.setVisibility(View.GONE);
            if(medicinesFromDb.size() == 0) {
                progressInitialTextView.setText("Brak wpisów dla leków");
            } else {
                progressInitialTextView.setVisibility(View.GONE);

                ArrayList<String> medicinesStringArray = new ArrayList<>();
                for (int i = 0; i < medicinesFromDb.size(); i++) {
                    medicinesStringArray.add("Nazwa leku: " + medicinesFromDb.get(i).getName() + ", " + '\n' +
                            "Dawka: " + medicinesFromDb.get(i).getDose() + ", " + '\n' +
                            "Typ dawkowania: " + medicinesFromDb.get(i).getDoseType() + ", " + '\n' +
                            "Data: " + medicinesFromDb.get(i).getDate() + ", " + '\n' +
                            "Godzina: " + medicinesFromDb.get(i).getTime());
                }
                ArrayAdapter<String> medicinesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, medicinesStringArray);
                progressMedicinesListView.setAdapter(medicinesAdapter);

                progressMedicinesListView.setVisibility(View.VISIBLE);

            }
            Log.d(TAG, "number of medicines: " + medicinesFromDb.size());
        });

        showMeasurementsProgressButton.setOnClickListener(v -> {
            progressInitialTextView.setVisibility(View.VISIBLE);
            progressMedicinesListView.setVisibility(View.GONE);
            if (measurementsFromDb.size() == 0) {
                progressInitialTextView.setText("Brak wpisów dla pomiarów");
            } else {
                progressInitialTextView.setVisibility(View.GONE);

                ArrayList<String> measurementsStringArray = new ArrayList<>();
                for (int i = 0; i < measurementsFromDb.size(); i++) {
                    measurementsStringArray.add("Nazwa pomiaru: " +measurementsFromDb.get(i).getName() + ", " + '\n' +
                            "Data: " + measurementsFromDb.get(i).getDate() + ", " + '\n' +
                            "Godzina: " + measurementsFromDb.get(i).getTime());
                }
                ArrayAdapter<String> measurementsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, measurementsStringArray);
                progressMedicinesListView.setAdapter(measurementsAdapter);

                progressMedicinesListView.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}