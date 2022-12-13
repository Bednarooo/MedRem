package com.example.medrem;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ProgressFragment extends Fragment {
    private SpeechService speechService;

    public ProgressFragment() {
        // Required empty public constructor
        speechService = SpeechService.getInstance();
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
        TextView progressInitialTextView = (TextView) view.findViewById(R.id.initialTextViewProgress);
        ListView progressMedicinesListView = (ListView) view.findViewById(R.id.listViewProgressMedicines);
        ListView progressMeasurementsListView = (ListView) view.findViewById(R.id.listViewProgressMeasurements);
        Button showMedicinesProgressButton = (Button) view.findViewById(R.id.showMedicinesProgressButton);
        Button showMeasurementsProgressButton = (Button) view.findViewById(R.id.showMeasurementsProgressButton);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Medicine> medicinesFromDb = new ArrayList<>();
        List<Measurement> measurementsFromDb = new ArrayList<>();
        db.collection("medicines")
                .orderBy("date")
                .orderBy("time")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                medicinesFromDb.add(document.toObject(Medicine.class));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        db.collection("measurements")
                .orderBy("date")
                .orderBy("time")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                measurementsFromDb.add(document.toObject(Measurement.class));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        progressInitialTextView.setText("Wybierz listę klikając przycisk");

        showMedicinesProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressInitialTextView.setVisibility(View.VISIBLE);
                progressMeasurementsListView.setVisibility(View.GONE);
                if(medicinesFromDb.size() == 0) {
                    progressInitialTextView.setText("Brak wpisów dla leków");
                } else {
                    progressInitialTextView.setVisibility(View.GONE);

                    ArrayList<String> medicinesStringArray = new ArrayList<>();
                    for (int i = 0; i < medicinesFromDb.size(); i++) {
                        medicinesStringArray.add("Nazwa leku: " + medicinesFromDb.get(i).getName() + ", " +
                                "Dawka: " + medicinesFromDb.get(i).getDose() + ", " +
                                "Typ dawkowania: " + medicinesFromDb.get(i).getDoseType() + ", " +
                                "Data: " + medicinesFromDb.get(i).getDate() + ", " +
                                "Godzina: " + medicinesFromDb.get(i).getTime());
                    }
                    ArrayAdapter<String> medicinesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, medicinesStringArray);
                    progressMedicinesListView.setAdapter(medicinesAdapter);

                    progressMedicinesListView.setVisibility(View.VISIBLE);

                    speechService.speak("You have " + medicinesStringArray.size() + " medicines in total");
                }
                Log.d(TAG, "number of medicines: " + medicinesFromDb.size());
            }
        });

        showMeasurementsProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressInitialTextView.setVisibility(View.VISIBLE);
                progressMedicinesListView.setVisibility(View.GONE);
                if (measurementsFromDb.size() == 0) {
                    progressInitialTextView.setText("Brak wpisów dla pomiarów");
                } else {
                    progressInitialTextView.setVisibility(View.GONE);

                    ArrayList<String> measurementsStringArray = new ArrayList<>();
                    for (int i = 0; i < measurementsFromDb.size(); i++) {
                        measurementsStringArray.add("Nazwa pomiaru: " +measurementsFromDb.get(i).getName() + ", " +
                                "Wartość: " + measurementsFromDb.get(i).getValue() + ", " +
                                "Data: " + measurementsFromDb.get(i).getDate() + ", " +
                                "Godzina: " + measurementsFromDb.get(i).getTime());
                    }
                    ArrayAdapter<String> measurementsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, measurementsStringArray);
                    progressMedicinesListView.setAdapter(measurementsAdapter);

                    speechService.speak("You have " + measurementsFromDb.size() + " ongoing measurements");

                    progressMedicinesListView.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }
}