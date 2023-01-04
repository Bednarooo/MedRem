package com.example.medrem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddingMeasurementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_measurement);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        EditText measurementNameEditText = (EditText) findViewById(R.id.editTextMeasurementName);
        TimePicker measurementTimePicker = (TimePicker) findViewById(R.id.timePickerMeasurement);

        CalendarView measurementCalendarView = (CalendarView) findViewById(R.id.calendarMeasurement);
        measurementCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                measurementCalendarView.setDate(calendar.getTimeInMillis());
            }
        });

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
                if (measurementNameEditText.getText().toString().equals("")) {
                    Toast.makeText(AddingMeasurementActivity.this, "Należy podać nazwę pomiaru oraz wartość", Toast.LENGTH_LONG).show();
                } else {
                    Measurement measurement = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        measurement = new Measurement(measurementNameEditText.getText().toString(), sdf.format(measurementCalendarView.getDate()), measurementTimePicker.getHour() + ":" + measurementTimePicker.getMinute());
                    } else {
                        measurement = new Measurement(measurementNameEditText.getText().toString(), sdf.format(measurementCalendarView.getDate()), measurementTimePicker.getCurrentHour() + ":" + measurementTimePicker.getCurrentMinute());

                    }
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, Object> mapMeasurement = new HashMap<>();
                    mapMeasurement.put("name", measurement.getName());
                    mapMeasurement.put("date", measurement.getDate());
                    mapMeasurement.put("time", measurement.getTime());

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