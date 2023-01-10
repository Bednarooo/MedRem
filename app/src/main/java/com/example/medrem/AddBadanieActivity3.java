package com.example.medrem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.medrem.data.LoginDataSource;
import com.example.medrem.data.LoginRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AddBadanieActivity3 extends AppCompatActivity {
    private Measurement measurement;
    private int notificationId = 2;
    private ArrayList<Date> datesInRange = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_badanie3);

        CalendarView calendarTo = findViewById(R.id.calendarViewBadanieTo);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        measurement = (Measurement) getIntent().getSerializableExtra("measurement");

        Button goBackFromAddingMeasurementButton = findViewById(R.id.anuluj3BadanieButton);
        Button addMeasurementButton = findViewById(R.id.ustawBadanieButton);

        calendarTo.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            calendarTo.setDate(calendar.getTimeInMillis());
        });

        goBackFromAddingMeasurementButton.setOnClickListener(
                v -> openMainActivity());

        Calendar cal1 = Calendar.getInstance();
        try {
            cal1.setTime(Objects.requireNonNull(sdf.parse(measurement.getDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        addMeasurementButton.setOnClickListener(
                v -> {
                    Calendar cal2 = Calendar.getInstance();
                    try {
                        cal2.setTime(Objects.requireNonNull(sdf.parse(sdf.format(calendarTo.getDate()))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    while (!cal1.after(cal2)) {
                        datesInRange.add(cal1.getTime());
                        cal1.add(Calendar.DATE, 1);
                    }

                    for (Date date : datesInRange) {
                        Map<String, Object> mapMeasurement = new HashMap<>();
                        mapMeasurement.put("username", LoginRepository.getInstance(LoginDataSource.getInstance()).getUsername());
                        mapMeasurement.put("name", measurement.getName());
                        mapMeasurement.put("date", sdf.format(date));
                        mapMeasurement.put("time", measurement.getTime());
                        mapMeasurement.put("clicked", measurement.isClicked());

                        measurement.setMeasurementId(UUID.randomUUID().toString());

                        db.collection("measurements").document(measurement.getMeasurementId())
                                .set(measurement)
                                .addOnSuccessListener(documentReference -> Toast.makeText(this, "Pomyślnie dodano pomiar", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(this, "Błąd podczas dodawania pomiaru", Toast.LENGTH_SHORT).show());
                        Intent intent = new Intent(this, AlarmReceiver.class);
                        intent.putExtra("notificationId", notificationId);
                        intent.putExtra("measurementId", measurement.getMeasurementId());
                        intent.putExtra("name", measurement.getName());

                        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0,
                                intent, PendingIntent.FLAG_CANCEL_CURRENT);

                        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

                        Date t = null;
                        try {
                            t = new SimpleDateFormat("hh:mm").parse(measurement.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date d = null;
                        try {
                            d = sdf.parse(sdf.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long alarmStartTime = combine(d, t).getTimeInMillis();
                        alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                    }
                    openMainActivity();
                }
        );
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private static Calendar combine(Date date, Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        return cal;
    }
}