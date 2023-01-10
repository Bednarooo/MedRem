package com.example.medrem;

import androidx.appcompat.app.AppCompatActivity;

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

public class AddBadanieActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Measurement measurement;

    private TimePicker timePickerBadanie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_badanie);

        measurement = new Measurement();

        Spinner spinner = findViewById(R.id.spinnerBadanie);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typ_przypomnienia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Button goBackFromAddingMeasurementButton = findViewById(R.id.anulujBadanieButton);
        Button goToSelectDateActivityButton = findViewById(R.id.dalejBadanieButton);

        goBackFromAddingMeasurementButton.setOnClickListener(
                v -> openMainActivity());

        goToSelectDateActivityButton.setOnClickListener(
                v -> {
                    EditText editText = findViewById(R.id.textBadanie);
                    timePickerBadanie = findViewById(R.id.timePickerBadanie);
                    if (editText.getText().toString().equals("")) {
                        Toast.makeText(this, "Należy podać nazwe badania", Toast.LENGTH_SHORT).show();
                    } else {
                        measurement.setName(editText.getText().toString());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            measurement.setTime(timePickerBadanie.getHour() + ":" + timePickerBadanie.getMinute());
                        } else {
                            measurement.setTime(timePickerBadanie.getCurrentHour() + ":" + timePickerBadanie.getCurrentMinute());
                        }
                        goToSelectDateActivity(measurement);
                    }

                }
        );
    }

    private void goToSelectDateActivity(Measurement measurement) {
        Intent intent = new Intent(this, AddBadanieActivity2.class);
        intent.putExtra("measurement", measurement);
        startActivity(intent);
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int index = parent.getSelectedItemPosition();
        Intent intent;
        if (index == 1) {
            intent = new Intent(this, AddLekActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}