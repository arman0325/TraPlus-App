package com.arman.traplus;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class activityChat extends AppCompatActivity implements View.OnClickListener {
    protected static final int Result_SPEECH =1;
    private boolean isUserJP = true;
    private TextToSpeech mTTS;
    // assume the first input is by waiter

    String transTxt;
    EditText jaEditTxt, enEditTxt;
    Button jaSendBtn, enSendBtn, jaVoiceBtn, enVoiceBtn, voiceJP;
    TextView jaTxtCustomer, jaTxtWaiter, enTxtCustomer, enTxtWaiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_activity_chat);
        getSupportActionBar().hide();

        jaTxtCustomer = (TextView) findViewById(R.id.jaTxtFromCustomer);
        jaTxtCustomer.setText("...");
        jaTxtWaiter = (TextView) findViewById(R.id.jaTxtFromWaiter);
        jaTxtWaiter.setText("...");
        enTxtCustomer = (TextView) findViewById(R.id.enTxtFromCustomer);
        enTxtCustomer.setText("...");
        enTxtWaiter = (TextView) findViewById(R.id.enTxtFromWaiter);
        enTxtWaiter.setText("...");

        jaEditTxt = (EditText) findViewById(R.id.waiterEditTxt);
        enEditTxt = (EditText) findViewById(R.id.customerEditTxt);

        jaVoiceBtn = (Button) findViewById(R.id.waiterMicBtn);
        jaVoiceBtn.setOnClickListener(this);
        enVoiceBtn = (Button) findViewById(R.id.customerMicBtn);
        enVoiceBtn.setOnClickListener(this);

        jaSendBtn = (Button) findViewById(R.id.waiterSendBtn);
        jaSendBtn.setOnClickListener(this);
        enSendBtn = (Button) findViewById(R.id.customerSendBtn);
        enSendBtn.setOnClickListener(this);

        voiceJP = (Button)findViewById(R.id.voiceJP);
        voiceJP.setOnClickListener(this);


        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.JAPAN);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.waiterMicBtn: // speech to text
                isUserJP = true;
                Intent i1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, (RecognizerIntent.LANGUAGE_MODEL_FREE_FORM));
                i1.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 50000000);
                i1.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ja");
                try {
                    startActivityForResult(i1, Result_SPEECH);
                    jaEditTxt.setText("");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"Your device does't support Speech to Text",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;

            case R.id.customerMicBtn: // speech to text
                isUserJP = false;
                Intent i2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, (RecognizerIntent.LANGUAGE_MODEL_FREE_FORM));
                i2.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 50000000);
                i2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-hk");
                try {
                    startActivityForResult(i2, Result_SPEECH);
                    enEditTxt.setText("");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"Your device does't support Speech to Text",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;

            case R.id.waiterSendBtn:
                jaTxtWaiter.setText(jaEditTxt.getText());
                translate transJa = new translate();
                transTxt = transJa.translateMethod(jaEditTxt.getText().toString(), "ja", "zh-TW");
                enTxtWaiter.setText(transTxt);
                enTxtCustomer.setText("...");
                jaEditTxt.setText("");
                break;

            case R.id.customerSendBtn:
                enTxtCustomer.setText(enEditTxt.getText());
                translate transEn = new translate();
                transTxt = transEn.translateMethod(enEditTxt.getText().toString(), "zh-Tw", "ja");
                jaTxtCustomer.setText(transTxt);
                jaTxtWaiter.setText("...");
                enEditTxt.setText("");
                break;

            case R.id.voiceJP:
                String playText = jaTxtCustomer.getText().toString();
                mTTS.speak(playText, TextToSpeech.QUEUE_FLUSH, null);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case Result_SPEECH:
                if(resultCode==RESULT_OK && data!= null){
                    ArrayList<String> text= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String voiceText = text.get(0);
                    if(isUserJP) {
                        jaEditTxt.setText(voiceText);
                    } else {
                        enEditTxt.setText(voiceText);
                    }
                }
                break;

        }
    }
}
