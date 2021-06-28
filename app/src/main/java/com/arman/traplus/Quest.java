package com.arman.traplus;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Quest extends AppCompatActivity {
    private CheckBox img,trans,other;
    private TextInputLayout editTextMsg;
    private EditText editText;
    private String jaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        jaText = getIntent().getStringExtra("jaText");;
        img = findViewById(R.id.cbImage);
        trans = findViewById(R.id.cbTrans);
        other = findViewById(R.id.cbOther);
        editTextMsg = findViewById(R.id.textInputLayout);
        editText = findViewById(R.id.editTextMsg);

    }

    public void sendMsg(View view) {
        String TAG = "send message to server";
        String msg ="";
        String urlPath = "http://armanser.asuscomm.com:8080/quest/android&"+jaText+"&";

        if (trans.isChecked()){
            msg += trans.getText().toString();
        }
        if (img.isChecked()){
            if (!msg.equals("")){
                msg += " + ";
            }
            msg += img.getText().toString();
        }
        if (other.isChecked()){
            if (!msg.equals("")){
                msg += " + ";
            }
            msg += "Other problem: " + editTextMsg.getEditText().getText().toString();
        }
        Log.d(TAG, "sendMsg: ");
        String url = urlPath + msg;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i("TAG", "onResponse: OK");
                finish();

            }
        });

    }

    public void onCheckBoxClicked(View view) {
        if (other.isChecked()){
            editText.setEnabled(true);
            editText.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            editText.setEnabled(false);
            editText.setBackgroundColor(Color.parseColor("#718489"));
        }
    }
}