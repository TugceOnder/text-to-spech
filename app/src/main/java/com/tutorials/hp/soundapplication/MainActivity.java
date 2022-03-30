package com.tutorials.hp.soundapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import static java.util.Locale.UK;

public class MainActivity extends AppCompatActivity {

    EditText Text;
    Button btnText,speakbutton;
    TextToSpeech textToSpeech;
    TextView textView;

    boolean txt2speech_available = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Text = findViewById(R.id.Text);
        btnText = findViewById(R.id.btnText);

        textView = findViewById(R.id.textView);
        speakbutton=findViewById(R.id.speakbutton);

        // create an object textToSpeech and adding features into it
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    if (textToSpeech.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE) {
                        textToSpeech.setLanguage(Locale.US);
                    }
                }
            }
        });

        // Adding OnClickListener
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                textToSpeech.speak(Text.getText().toString(), TextToSpeech.LANG_COUNTRY_AVAILABLE, null, null);
                // textToSpeech.speak(Text.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        speakbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    //... put other settings in the Intent
                    startActivityForResult(intent, 100);
                }
               catch(ActivityNotFoundException e) {
                   Intent browserIntent = new Intent(Intent.ACTION_VIEW,   Uri.parse("https://www.google.com.tr/"));
                   startActivity(browserIntent);
                }
            }
        });

    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            //... do your stuf with results
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}