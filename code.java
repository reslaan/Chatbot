package com.alobeidi.speechtotext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 100;
    private Intent intent;
    TextView mTextTv;
    TextToSpeech textToSpeech;
    private Handler handler = new Handler();

    @Override
    protected void onDestroy() {
       if (textToSpeech !=null){
           textToSpeech.stop();
           textToSpeech.shutdown();

       }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextTv = findViewById(R.id.textTv);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
              if (status!=TextToSpeech.ERROR){
                  textToSpeech.setLanguage(Locale.ENGLISH);
              }
            }
        });

        speck();

    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            speck();

        }
    };

    private void speck(){

         intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
   //     intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 9000); // value to wait
      //  intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,10);


        try {

     startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }catch (Exception e){
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                    String toSpeech = "";
                    if (result.get(0).equalsIgnoreCase("what is your name"))
                        toSpeech = "My name is reslaan ";
                    else if (result.get(0).equalsIgnoreCase("how old are you"))
                        toSpeech = "22 years ";
                    else if (result.get(0).equalsIgnoreCase("how are you"))
                        toSpeech = "alhamdu lilaah ";
                    else if (result.get(0).equalsIgnoreCase("where are you work"))
                        toSpeech = "I work in a smart methods company   ";
                    else if (result.get(0).equalsIgnoreCase("who is your supervisor"))
                        toSpeech = " engineer asma";
                    else
                        toSpeech = " i do not have answer for this question ";


                    Toast.makeText(this, toSpeech, Toast.LENGTH_LONG).show();
                    textToSpeech.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null);
                    mTextTv.setText(toSpeech);
                    handler.postDelayed(runnable, 3*1000);

                }else{
                   handler.postDelayed(runnable, 3*1000);
                     }
           // }
       // }
    }
}
