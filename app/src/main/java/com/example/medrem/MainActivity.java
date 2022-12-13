package com.example.medrem;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.medrem.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SpeechService speechService;
    private Switch ttsSwitch;

    @Override
    @SuppressLint("NonConstantResourceId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new TodayFragment());
        ttsSwitch = (Switch) findViewById(R.id.ttsSwitch);
        speechService = new SpeechService(this);
        ttsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                speechService.enableSpeaking();
            } else {
                speechService.disableSpeaking();
            }
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.today:
                    replaceFragment(new TodayFragment());
                    speak("Today");
                    break;
                case R.id.progress:
                    replaceFragment(new ProgressFragment());
                    speak("Progress");
                    break;
                case R.id.therapy:
                    replaceFragment(new TherapyFragment());
                    speak("Therapy");
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void speak(String text) {
        speechService.speak(text);
    }
}