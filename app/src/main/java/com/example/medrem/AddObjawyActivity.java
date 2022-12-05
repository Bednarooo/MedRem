package com.example.medrem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddObjawyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_objawy);

        Spinner spinner = findViewById(R.id.spinnerObjawy);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typ_przypomnienia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int index = parent.getSelectedItemPosition();
        Intent intent;
        if (index == 1) {
            intent = new Intent(this, AddLekAcivity.class);
            startActivity(intent);
        } else if (index == 2) {
            intent = new Intent(this, AddBadanieActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}