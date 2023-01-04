package com.example.medrem;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddingMeasurementActivity extends AppCompatActivity {

    private int notificationId = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_measurement);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        EditText measurementNameEditText = (EditText) findViewById(R.id.editTextMeasurementName);
        TimePicker measurementTimePicker = (TimePicker) findViewById(R.id.timePickerMeasurement);

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
                        measurement = new Measurement(measurementNameEditText.getText().toString(), sdf.format(Calendar.getInstance().getTime()), measurementTimePicker.getHour() + ":" + measurementTimePicker.getMinute());
                    } else {
                        measurement = new Measurement(measurementNameEditText.getText().toString(), sdf.format(Calendar.getInstance().getTime()), measurementTimePicker.getCurrentHour() + ":" + measurementTimePicker.getCurrentMinute());

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
                    Intent intent = new Intent(AddingMeasurementActivity.this, AlarmReceiver.class);
                    intent.putExtra("notificationId", notificationId);
                    intent.putExtra("name", measurement.getName());


                    PendingIntent alarmIntent = PendingIntent.getBroadcast(AddingMeasurementActivity.this, 0,
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
                        d = sdf.parse(measurement.getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long alarmStartTime = combine(d, t).getTimeInMillis();
                    alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                    measurementNameEditText.getText().clear();
                }
            }
        });
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}