package com.example.medrem;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.medrem.data.LoginDataSource;
import com.example.medrem.data.LoginRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddingMedicineActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Medicine medicine;
    private int notificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_medicine);

        medicine = new Medicine();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        EditText medicineNameEditText = findViewById(R.id.editTextMedicineName);
        EditText medicineDoseEditText = findViewById(R.id.editTextMedicineDose);
        Spinner medicineDoseTypeSpinner = findViewById(R.id.spinnerMedicineDoseType);
        TimePicker medicineTimePicker = findViewById(R.id.timePickerMedicine);

        Button goBackFromMedicineButton = findViewById(R.id.goBackFromAddingMedicineButton);
        Button saveMedicineButton = findViewById(R.id.saveMedicineButton);

        ArrayAdapter<CharSequence> spinnerDoseTypeAdapter = ArrayAdapter.createFromResource(this, R.array.typ_dawki, android.R.layout.simple_spinner_item);
        spinnerDoseTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medicineDoseTypeSpinner.setAdapter(spinnerDoseTypeAdapter);
        medicineDoseTypeSpinner.setOnItemSelectedListener(this);

        goBackFromMedicineButton.setOnClickListener(v -> openMainActivity());
        saveMedicineButton.setOnClickListener(v -> {
            if (medicineNameEditText.getText().toString().equals("") || medicineDoseEditText.getText().toString().equals("") || medicineDoseTypeSpinner.getSelectedItemPosition() == 0) {
                Toast.makeText(AddingMedicineActivity.this, "Należy podać nazwę leku, dawkę oraz typ dawkowania", Toast.LENGTH_LONG).show();
            } else {
                medicine.setName(medicineNameEditText.getText().toString());
                medicine.setDose(medicineDoseEditText.getText().toString());
                medicine.setDate(sdf.format(Calendar.getInstance().getTime()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    medicine.setTime(medicineTimePicker.getHour() + ":" + medicineTimePicker.getMinute());
                } else {
                    medicine.setTime(medicineTimePicker.getCurrentHour() + ":" + medicineTimePicker.getCurrentMinute());
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> mapMedicine = new HashMap<>();
                mapMedicine.put("username", LoginRepository.getInstance(LoginDataSource.getInstance()).getUsername());
                mapMedicine.put("name", medicine.getName());
                mapMedicine.put("dose", medicine.getDose());
                mapMedicine.put("doseType", medicine.getDoseType());
                mapMedicine.put("date", medicine.getDate());
                mapMedicine.put("time", medicine.getTime());
                db.collection("medicines")
                        .add(mapMedicine)
                        .addOnSuccessListener(documentReference -> Toast.makeText(AddingMedicineActivity.this, "Pomyślnie dodano lek", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(AddingMedicineActivity.this, "Błąd podczas dodawania leku", Toast.LENGTH_SHORT).show());
                Intent intent = new Intent(AddingMedicineActivity.this, AlarmReceiver.class);
                intent.putExtra("notificationId", notificationId);
                intent.putExtra("name", medicine.getName());
                intent.putExtra("dose", medicine.getDose() + " " + medicine.getDoseType().toString());

                PendingIntent alarmIntent = PendingIntent.getBroadcast(AddingMedicineActivity.this, 0,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

                Date t = null;
                try {
                    t = new SimpleDateFormat("hh:mm").parse(medicine.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date d = null;
                try {
                    d = sdf.parse(medicine.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long alarmStartTime = combine(d, t).getTimeInMillis();
                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                openMainActivity();
            }
        });
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
                medicine.setDoseType(DoseType.Gramy);
                break;
            case 2:
                medicine.setDoseType(DoseType.Czopki);
                break;
            case 3:
                medicine.setDoseType(DoseType.Kapsulki);
                break;
            case 4:
                medicine.setDoseType(DoseType.Krople);
                break;
            case 5:
                medicine.setDoseType(DoseType.Plastry);
                break;
            case 6:
                medicine.setDoseType(DoseType.Saszetki);
                break;
            case 7:
                medicine.setDoseType(DoseType.Mililitry);
                break;
            case 8:
                medicine.setDoseType(DoseType.Łyzeczki);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}