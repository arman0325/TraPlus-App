package com.arman.traplus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import androidx.annotation.Nullable;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Locale;

public class voiceChat extends AppCompatActivity implements View.OnClickListener {

    protected static  final int Result_SPEECH =1;

    private boolean clickVoice = true; //true = HK, false = jp
    private boolean clickPlayVoice = true; //true = HK, false = jp

    EditText editTextJP;
    EditText editTextZH;
    TextView tvZH1, tvZH2, tvJP1, tvJP2;

    private TextToSpeech mTTS,mTTSZH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_chat);

        editTextJP = (EditText) findViewById(R.id.editTextJP);
        editTextZH = (EditText) findViewById(R.id.editTextZH);

        tvZH1 = (TextView)findViewById(R.id.textZh1);// after translate
        tvZH2 = (TextView)findViewById(R.id.textZh2);
        tvJP1 = (TextView)findViewById(R.id.textJp1);// after translate
        tvJP2 = (TextView)findViewById(R.id.textJp2);

        Button VoiceBtnJP = (Button) findViewById(R.id.voiceBtnJP);
        VoiceBtnJP.setOnClickListener(this);

        Button VoiceJP = (Button) findViewById(R.id.voiceJP);
        VoiceJP.setOnClickListener(this);

        Button VoiceBtnZH = (Button) findViewById(R.id.voiceBtnZH);
        VoiceBtnZH.setOnClickListener(this);

        Button VoiceZH = (Button) findViewById(R.id.voiceZH);
        VoiceZH.setOnClickListener(this);

        editTextJP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String jp = editTextJP.getText().toString();
                    translate trans = new translate();
                    String transZH = trans.translateMethod(jp, "ja", "zh-TW");
                    tvJP1.setText(transZH);
                }
            }
        });

        editTextZH.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String zh = editTextZH.getText().toString();
                    translate trans = new translate();
                    String transJP = trans.translateMethod(zh, "zh-TW", "ja");
                    tvZH1.setText(transJP);
                }
            }
        });

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

        mTTSZH = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTSZH.setLanguage(new Locale("yue", "HK"));

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
            case R.id.voiceBtnJP:// speech to text
                clickVoice = false;
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, (RecognizerIntent.LANGUAGE_MODEL_FREE_FORM));
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 50000000);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ja");
                try {
                    startActivityForResult(intent, Result_SPEECH);
                    editTextJP.setText("");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"Your device does't support Speech to Text",Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
                break;

            case R.id.voiceJP: // text to speech
                clickPlayVoice = false;
                String playText = tvZH1.getText().toString();
                mTTS.speak(playText, TextToSpeech.QUEUE_FLUSH, null);
                break;

            case R.id.voiceBtnZH:// speech to text
                clickVoice = true;
                Intent intent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent2.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 50000000);
                intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, (RecognizerIntent.LANGUAGE_MODEL_FREE_FORM));
                intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-hk");
                try {
                    startActivityForResult(intent2, Result_SPEECH);
                    editTextZH.setText("");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"Your device does't support Speech to Text",Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }

                break;
            case R.id.voiceZH: // text to speech
                clickPlayVoice = true;
                String playText2 = tvJP1.getText().toString();
                mTTSZH.speak(playText2, TextToSpeech.QUEUE_FLUSH, null);

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
                    if (clickVoice == true) {
                        editTextZH.setText(voiceText);
                        translate trans = new translate();
                        String transJP = trans.translateMethod(voiceText, "zh-TW", "ja");
                        tvZH1.setText(transJP);
                    } else {
                        editTextJP.setText(voiceText);
                        translate trans = new translate();
                        String transZH = trans.translateMethod(voiceText, "ja", "zh-TW");
                        tvJP1.setText(transZH);
                    }
                }
                break;

        }
    }

}