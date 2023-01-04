package com.example.medrem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;


public class AddLekAcivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private Medicine medicine;

    private CalendarView calendarViewLek;
    private TimePicker timePickerLek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lek);

        medicine = new Medicine();

        Spinner spinner = findViewById(R.id.spinnerLek);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typ_przypomnienia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinnerDawka = findViewById(R.id.spinnerLekDawka);
        ArrayAdapter<CharSequence> adapterDawka = ArrayAdapter.createFromResource(this, R.array.typ_dawki, android.R.layout.simple_spinner_item);
        adapterDawka.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDawka.setAdapter(adapterDawka);
        spinnerDawka.setOnItemSelectedListener(this);

        findViewById(R.id.ustawLekButton).setOnClickListener(this);
        findViewById(R.id.anulujLekButton).setOnClickListener(this);
        calendarViewLek = findViewById(R.id.calendarViewLek);
        timePickerLek = findViewById(R.id.timePickerLek);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int index = parent.getSelectedItemPosition();
        switch (parent.getId()) {
            case R.id.spinnerLek:
                Intent intent;
                if (index == 2) {
                    intent = new Intent(this, AddBadanieActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.spinnerLekDawka:
                if (index == 1)
                    medicine.setDoseType(DoseType.Gramy);
                else if (index == 2)
                    medicine.setDoseType(DoseType.Czopki);
                else if (index == 3)
                    medicine.setDoseType(DoseType.Kapsulki);
                else if (index == 4)
                    medicine.setDoseType(DoseType.Krople);
                else if (index == 5)
                    medicine.setDoseType(DoseType.Plastry);
                else if (index == 6)
                    medicine.setDoseType(DoseType.Saszetki);
                else if (index == 7)
                    medicine.setDoseType(DoseType.Mililitry);
                else if (index == 8)
                    medicine.setDoseType(DoseType.Łyzeczki);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        EditText editTextNazwa = findViewById(R.id.textNazwaLeku);
        EditText editTextDawka = findViewById(R.id.textDawka);
        TimePicker timePickerLek = findViewById(R.id.timePickerLek);
        CalendarView calendarView = findViewById(R.id.calendarViewLek);

    }
}