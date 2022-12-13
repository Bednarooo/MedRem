package com.example.medrem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddingMedicineActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Lek medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_medicine);

        medicine = new Lek();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        EditText medicineNameEditText = (EditText) findViewById(R.id.editTextMedicineName);
        EditText medicineDoseEditText = (EditText) findViewById(R.id.editTextMedicineDose);
        Spinner medicineDoseTypeSpinner = (Spinner) findViewById(R.id.spinnerMedicineDoseType);
        TimePicker medicineTimePicker = (TimePicker) findViewById(R.id.timePickerMedicine);

        CalendarView medicineCalendarView = (CalendarView) findViewById(R.id.calendarMedicine);
        medicineCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                medicineCalendarView.setDate(calendar.getTimeInMillis());
            }
        });

        Button goBackFromMedicineButton = (Button) findViewById(R.id.goBackFromAddingMedicineButton);
        Button saveMedicineButton = (Button) findViewById(R.id.saveMedicineButton);

        ArrayAdapter<CharSequence> spinnerDoseTypeAdapter = ArrayAdapter.createFromResource(this, R.array.typ_dawki, android.R.layout.simple_spinner_item);
        spinnerDoseTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medicineDoseTypeSpinner.setAdapter(spinnerDoseTypeAdapter);
        medicineDoseTypeSpinner.setOnItemSelectedListener(this);

        goBackFromMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new TodayFragment());
            }
        });
        saveMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (medicineNameEditText.getText().toString().equals("") || medicineDoseEditText.getText().toString().equals("") || medicineDoseTypeSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(AddingMedicineActivity.this, "Należy podać nazwę leku, dawkę oraz typ dawkowania", Toast.LENGTH_LONG).show();
                } else {
                    medicine.setNazwa(medicineNameEditText.getText().toString());
                    medicine.setDawka(medicineDoseEditText.getText().toString());
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, Object> mapMedicine = new HashMap<>();
                    mapMedicine.put("name", medicine.getNazwa());
                    mapMedicine.put("dose", medicine.getDawka());
                    mapMedicine.put("doseType", medicine.getTypDawkowania());
                    mapMedicine.put("date", sdf.format(medicineCalendarView.getDate()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mapMedicine.put("time", medicineTimePicker.getHour() + ":" + medicineTimePicker.getMinute());
                    } else {
                        mapMedicine.put("time", medicineTimePicker.getCurrentHour() + ":" + medicineTimePicker.getCurrentMinute());
                    }
                    db.collection("medicines")
                            .add(mapMedicine)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AddingMedicineActivity.this, "Pomyślnie dodano pomiar", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddingMedicineActivity.this, "Błąd podczas dodawania pomiaru", Toast.LENGTH_SHORT).show();
                                }
                            });
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getSelectedItemPosition()) {
            case 1:
                medicine.setTypDawkowania(TypDawkowania.Gramy);
                break;
            case 2:
                medicine.setTypDawkowania(TypDawkowania.Czopki);
                break;
            case 3:
                medicine.setTypDawkowania(TypDawkowania.Kapsulki);
                break;
            case 4:
                medicine.setTypDawkowania(TypDawkowania.Krople);
                break;
            case 5:
                medicine.setTypDawkowania(TypDawkowania.Plastry);
                break;
            case 6:
                medicine.setTypDawkowania(TypDawkowania.Saszetki);
                break;
            case 7:
                medicine.setTypDawkowania(TypDawkowania.Mililitry);
                break;
            case 8:
                medicine.setTypDawkowania(TypDawkowania.Łyzeczki);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}