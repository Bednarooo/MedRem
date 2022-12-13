package com.example.medrem;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddingMeasurementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_measurement);
        EditText measurementNameEditText = (EditText) findViewById(R.id.editTextMeasurementName);
        EditText measurementValueEditText = (EditText) findViewById(R.id.editTextValue);
        Button saveMeasurementButton = (Button) findViewById(R.id.saveMeasurementButton);
        Button goBackFromAddingMeasurementButton = (Button) findViewById(R.id.goBackFromAddingMeasurementButton);
        goBackFromAddingMeasurementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new TodayFragment());
            }
        });
        saveMeasurementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (measurementNameEditText.getText().toString().equals("") || measurementValueEditText.getText().toString().equals("")) {
                    Toast.makeText(AddingMeasurementActivity.this, "Należy podać nazwę pomiaru oraz wartość", Toast.LENGTH_LONG).show();
                } else {
                    Measurement measurement = new Measurement(measurementNameEditText.getText().toString(), measurementValueEditText.getText().toString());
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, Object> mapMeasurement = new HashMap<>();
                    mapMeasurement.put("name", measurement.getName());
                    mapMeasurement.put("value", measurement.getValue());
                    db.collection("measurements")
                            .add(mapMeasurement)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AddingMeasurementActivity.this, "Pomyślnie dodano pomiar", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddingMeasurementActivity.this, "Błąd podczas dodawania pomiaru", Toast.LENGTH_SHORT).show();
                                }
                            });
                    measurementNameEditText.getText().clear();
                    measurementValueEditText.getText().clear();
                }
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}