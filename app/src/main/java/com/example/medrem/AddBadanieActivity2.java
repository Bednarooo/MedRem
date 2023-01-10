package com.example.medrem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddBadanieActivity2 extends AppCompatActivity {
    private Measurement measurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_badanie2);

        CalendarView calendarFrom = findViewById(R.id.calendarViewBadanieFrom);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        measurement = (Measurement) getIntent().getSerializableExtra("measurement");

        calendarFrom.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            calendarFrom.setDate(calendar.getTimeInMillis());
        });

        Button goBackFromAddingMeasurementButton = findViewById(R.id.anuluj2BadanieButton);
        Button goToSelectDateActivityButton = findViewById(R.id.dalej2BadanieButton);

        goBackFromAddingMeasurementButton.setOnClickListener(
                v -> openMainActivity());

        goToSelectDateActivityButton.setOnClickListener(
                v -> {
                    measurement.setDate(sdf.format(calendarFrom.getDate()));
                    goToDateSelectionActivity(measurement);
                }
        );
    }

    private void goToDateSelectionActivity(Measurement measurement) {
        Intent intent = new Intent(this, AddBadanieActivity3.class);
        intent.putExtra("measurement", measurement);
        startActivity(intent);
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}