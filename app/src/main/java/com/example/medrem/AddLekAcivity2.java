package com.example.medrem;

import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddLekAcivity2 extends AppCompatActivity {
    private Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lek2);

        CalendarView calendarFrom = findViewById(R.id.calendarViewLekFrom);
        CalendarView calendarTo = findViewById(R.id.calendarViewLekTo);

        medicine = (Medicine) getIntent().getSerializableExtra("medicine");

        calendarFrom.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            calendarFrom.setDate(calendar.getTimeInMillis());
        });

        calendarTo.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            calendarTo.setDate(calendar.getTimeInMillis());
        });
    }
}
