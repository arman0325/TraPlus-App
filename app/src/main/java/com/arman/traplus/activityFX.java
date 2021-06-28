package com.arman.traplus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class activityFX extends AppCompatActivity{

    private String urlExRate = "https://free.currconv.com/api/v7/convert?q=JPY_HKD&compact=ultra&apiKey=b25d1b14e15796970496";
    private JsonFX objRate;
    private double fxRate;
    private int showRatio = 1000;
    private boolean liveMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_activity_currency);
        getSupportActionBar().hide();

        Intent i = getIntent();
        fxRate = Double.valueOf(i.getStringExtra("rate"));
        showRatio = Integer.valueOf(i.getStringExtra("ratio"));
        liveMode = Boolean.valueOf(i.getStringExtra("live"));

        CardView card2 = (CardView) findViewById (R.id.cCard2);
        TextView modeTxt1 = (TextView) findViewById (R.id.cModeTxt1);
        TextView modeTxt2 = (TextView) findViewById (R.id.cModeTxt2);
        TextView scale500 = (TextView) findViewById (R.id.sfh);
        TextView scale1000 = (TextView) findViewById (R.id.st);
        TextView scale7000 = (TextView) findViewById (R.id.sst);
        TextView rateHkd = (TextView) findViewById (R.id.cRateHkd);
        TextView rateJpy = (TextView) findViewById (R.id.cRateJpy);
        EditText editTxt = (EditText) findViewById (R.id.cEditTxt);
        TextView outputTxt = (TextView) findViewById (R.id.cOutTxt);

        rateHkd.setText(" " + Double.toString(Math.round(fxRate * Double.valueOf(showRatio) * 100.0) / 100.0) + " ");
        rateJpy.setText("¥ " + Integer.toString(showRatio) + " ");

        if (showRatio == 500) {
            scale500.setTextColor(Color.parseColor("#000000"));
            scale1000.setTextColor(Color.parseColor("#FFFFFF"));
            scale7000.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else if (showRatio == 1000) {
            scale500.setTextColor(Color.parseColor("#FFFFFF"));
            scale1000.setTextColor(Color.parseColor("#000000"));
            scale7000.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else if (showRatio == 7000) {
            scale500.setTextColor(Color.parseColor("#FFFFFF"));
            scale1000.setTextColor(Color.parseColor("#FFFFFF"));
            scale7000.setTextColor(Color.parseColor("#000000"));
        }

        if(liveMode) {
            modeTxt2.setText(getString(R.string.cMode2));
            if (isConnectedToNetwork()) {
                modeTxt1.setText(getString(R.string.cModeTitle1));
            }
        }
        else {
            modeTxt1.setText(getString(R.string.cModeTitle2));
            modeTxt2.setText(getString(R.string.cMode1));
        }

        scale500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatio = 500;
                rateHkd.setText(" " + Double.toString(Math.round(fxRate * Double.valueOf(showRatio) * 100.0) / 100.0) + " ");
                rateJpy.setText("¥ " + Integer.toString(showRatio) + " ");
                scale500.setTextColor(Color.parseColor("#000000"));
                scale1000.setTextColor(Color.parseColor("#FFFFFF"));
                scale7000.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        scale1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatio = 1000;
                rateHkd.setText(" " + Double.toString(Math.round(fxRate * Double.valueOf(showRatio) * 100.0) / 100.0) + " ");
                rateJpy.setText("¥ " + Integer.toString(showRatio) + " ");
                scale500.setTextColor(Color.parseColor("#FFFFFF"));
                scale1000.setTextColor(Color.parseColor("#000000"));
                scale7000.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        scale7000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatio = 7000;
                rateHkd.setText(" " + Double.toString(Math.round(fxRate * Double.valueOf(showRatio) * 100.0) / 100.0) + " ");
                rateJpy.setText("¥ " + Integer.toString(showRatio) + " ");
                scale500.setTextColor(Color.parseColor("#FFFFFF"));
                scale1000.setTextColor(Color.parseColor("#FFFFFF"));
                scale7000.setTextColor(Color.parseColor("#000000"));
            }
        });

        editTxt.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                //TODO Auto-generated method stub
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //TODO Auto-generated method stub
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO Auto-generated method stub
                if(!s.toString().trim().equalsIgnoreCase("")) {
                    outputTxt.setText(Double.toString(Math.round(fxRate * Double.valueOf(editTxt.getText().toString()) * 100.0) / 100.0));
                }
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (liveMode) {
                    LayoutInflater factory = LayoutInflater.from(activityFX.this);
                    final View textEntryView = factory.inflate(R.layout.currency_dialog, null);
                    final EditText inputHKD = (EditText) textEntryView.findViewById(R.id.editHKD);
                    final EditText inputJPY = (EditText) textEntryView.findViewById(R.id.editJPY);
                    final AlertDialog.Builder currencyBuilder = new AlertDialog.Builder(activityFX.this);
                    currencyBuilder.setTitle(getString(R.string.diaTxt1));
                    currencyBuilder.setView(textEntryView);
                    currencyBuilder.setPositiveButton(getString(R.string.diaTxt4), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fxRate = Double.parseDouble(inputHKD.getText().toString()) / Double.parseDouble(inputJPY.getText().toString());
                            rateHkd.setText(" " + Double.toString(Math.round(fxRate * Double.valueOf(showRatio) * 100.0) / 100.0) + " ");
                            modeTxt1.setText(getString(R.string.cModeTitle2));
                            modeTxt2.setText(getString(R.string.cMode1));
                            liveMode = false;
                        }
                    });
                    currencyBuilder.setNegativeButton(getString(R.string.diaTxt5), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    currencyBuilder.show();
                }
                else {
                    if (isConnectedToNetwork()) {
                        objRate = new JsonFX(urlExRate);
                        objRate.fetchJSON();
                        while(objRate.parsingComplete);
                        fxRate = Double.parseDouble(objRate.getHKD());
                        rateHkd.setText(" " + Double.toString(Math.round(fxRate * Double.valueOf(showRatio) * 100.0) / 100.0) + " ");
                    }
                    else {
                        rateHkd.setText(" - - - - ");
                    }
                    modeTxt1.setText(getString(R.string.cModeTitle1));
                    modeTxt2.setText(getString(R.string.cMode2));
                    liveMode = true;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent r = new Intent();
        r.putExtra("rRatio", Integer.toString(showRatio));
        r.putExtra("rMode", Boolean.toString(liveMode));
        r.putExtra("rRate", Double.toString(fxRate));
        setResult(RESULT_OK, r);
        super.onBackPressed();
    }

    public boolean isConnectedToNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
