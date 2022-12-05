package com.example.medrem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;


public class AddLekAcivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Lek lek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lek);

        lek = new Lek();

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
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getAdapter().getItem(0) == "Wybierz Typ") {
            int index = parent.getSelectedItemPosition();
            Intent intent;
            if (index == 2) {
                intent = new Intent(this, AddBadanieActivity.class);
                startActivity(intent);
            } else if (index == 3) {
                intent = new Intent(this, AddObjawyActivity.class);
                startActivity(intent);
            }
        } else {
            int index = parent.getSelectedItemPosition();

            if (index == 1)
                lek.setTypDawkowania(TypDawkowania.Gramy);
            else if (index == 2)
                lek.setTypDawkowania(TypDawkowania.Czopki);
            else if (index == 3)
                lek.setTypDawkowania(TypDawkowania.Kapsulki);
            else if (index == 4)
                lek.setTypDawkowania(TypDawkowania.Krople);
            else if (index == 5)
                lek.setTypDawkowania(TypDawkowania.Plastry);
            else if (index == 6)
                lek.setTypDawkowania(TypDawkowania.Saszetki);
            else if (index == 7)
                lek.setTypDawkowania(TypDawkowania.Mililitry);
            else if (index == 8)
                lek.setTypDawkowania(TypDawkowania.Łyzeczki);
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