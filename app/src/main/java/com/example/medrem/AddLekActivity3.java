package com.example.medrem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AddLekActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lek3);

        Button goBackFromAddingLekButton = findViewById(R.id.anuluj3LekButton);
        Button addLekButton = findViewById(R.id.ustawLekButton);

        goBackFromAddingLekButton.setOnClickListener(
                v -> openMainActivity());

        addLekButton.setOnClickListener(
                v -> {
                    //todo dokonczyc
                }
        );
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}