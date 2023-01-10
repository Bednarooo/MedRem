package com.example.medrem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddLekActivity3 extends AppCompatActivity {
    private Medicine medicine;
    private ArrayList<Date> datesInRange = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lek3);
        
        CalendarView calendarTo = findViewById(R.id.calendarViewLekTo);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        medicine = (Medicine) getIntent().getSerializableExtra("medicine");

        Button goBackFromAddingLekButton = findViewById(R.id.anuluj3LekButton);
        Button addLekButton = findViewById(R.id.ustawLekButton);

        calendarTo.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            calendarTo.setDate(calendar.getTimeInMillis());
        });

        goBackFromAddingLekButton.setOnClickListener(
                v -> openMainActivity());

        Calendar cal1 = Calendar.getInstance();
        try {
            cal1.setTime(Objects.requireNonNull(sdf.parse(medicine.getDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        addLekButton.setOnClickListener(
                v -> {
                    Calendar cal2 = Calendar.getInstance();
                    try {
                        cal2.setTime(Objects.requireNonNull(sdf.parse(sdf.format(calendarTo.getDate()))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    while(!cal1.after(cal2)) {
                        datesInRange.add(cal1.getTime());
                        cal1.add(Calendar.DATE, 1);
                    }

                    for (Date date : datesInRange) {
                        Map<String, Object> mapMedicine = new HashMap<>();
                        mapMedicine.put("name", medicine.getName());
                        mapMedicine.put("dose", medicine.getDose());
                        mapMedicine.put("doseType", medicine.getDoseType());
                        mapMedicine.put("date", sdf.format(date));
                        mapMedicine.put("time", medicine.getTime());
                        db.collection("medicines")
                                .add(mapMedicine)
                                .addOnSuccessListener(documentReference -> Toast.makeText(this, "Pomyslnie dodano lek", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(this, "Blad podczas dodawania leku", Toast.LENGTH_SHORT));
                    }
                }
        );
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}