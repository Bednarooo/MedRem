package com.example.medrem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddLekActivity2 extends AppCompatActivity {
    private Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lek2);

        CalendarView calendarFrom = findViewById(R.id.calendarViewLekFrom);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        medicine = (Medicine) getIntent().getSerializableExtra("medicine");

        calendarFrom.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            calendarFrom.setDate(calendar.getTimeInMillis());
        });


        Button goBackFromAddingLekButton = findViewById(R.id.anuluj2LekButton);
        Button goToSelectDateActivity = findViewById(R.id.dalej2LekButton);

        goBackFromAddingLekButton.setOnClickListener(
                v -> openMainActivity());

        goToSelectDateActivity.setOnClickListener(
                v -> {
                    medicine.setDate(sdf.format(calendarFrom.getDate()));
                    goToDateSelectionActivity(medicine);
                }
        );
    }

    private void goToDateSelectionActivity(Medicine medicine) {
        Intent intent = new Intent(this, AddLekActivity3.class);
        intent.putExtra("medicine", medicine);
        startActivity(intent);
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
