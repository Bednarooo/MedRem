package com.example.medrem;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class SpeechService {
    private TextToSpeech tts;
    private boolean speakingEnabled;
    private static SpeechService instance;

    public SpeechService(Context context) {
        tts = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result=tts.setLanguage(Locale.US);
                if(result==TextToSpeech.LANG_MISSING_DATA ||
                        result==TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("error", "This Language is not supported");
                }
            }
            else {
                Log.e("error", "Initialization Failed!");
            }
        });
        instance = this;
    }

    public static SpeechService getInstance() {
        return instance;
    }

    public void speak(String text) {
        if (speakingEnabled && !tts.isSpeaking()) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    public void enableSpeaking() {
        speakingEnabled = true;
    }

    public void disableSpeaking() {
        speakingEnabled = false;
    }
}
