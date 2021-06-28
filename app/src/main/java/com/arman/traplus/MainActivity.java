package com.arman.traplus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arman.traplus.Table_manner.TableMannerClassfication;
import com.arman.traplus.menu.EditMenuActivity;
import com.arman.traplus.menu.offlineListActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // the path of capture
    private String currentPhotoPath;

    // exchange rate
    private String urlExRate = "https://free.currconv.com/api/v7/convert?q=JPY_HKD&compact=ultra&apiKey=b25d1b14e15796970496";
    private JsonFX objRate;
    private double fxRate = 0;
    private int showRatio = 1000;
    private boolean liveFXMode = true;
    TextView rateTV;
    TextView scaleTV;

    // weather
    private String urlLocation = "http://api.ipstack.com/check?access_key=d02460f51758de00c306d5e212cd8ce0";
    private JsonLocation objLocation;
    private String urlToken = "https://pfa.foreca.com/authorize/token?user=ouhk&password=AxaKEFMbjeRU";//&expire_hours=3";
    private JsonWeatherToken objToken;
    private String weatherToken = "?token=";
    private String urlWeather = "https://pfa.foreca.com/api/v1/current/";
    private JsonWeatherNow objWeather;
    private String weatherTime, weatherSymbol;
    TextView describeTV, temperatureTV, rainTV;
    ImageView weatherIV, weatherBgIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        rateTV = (TextView) findViewById (R.id.rateValue);
        scaleTV = (TextView) findViewById (R.id.jpyScale);
        rainTV = (TextView) findViewById (R.id.rainValue);
        describeTV = (TextView) findViewById (R.id.mDescribe);
        temperatureTV = (TextView) findViewById (R.id.tempValue);
        weatherIV = (ImageView) findViewById (R.id.weatherCardImg);
        weatherBgIV = (ImageView) findViewById (R.id.weatherCardBg);

        started();

    }

    public void started(){
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        }

        // load live exchange rate and weather info at app started
        if (isNetworkConnected()) { // Check Internet connection before retrieve data from APIs
            // Currency Exchange Rate API Json
            objRate = new JsonFX(urlExRate);
            objRate.fetchJSON();
            while(objRate.parsingComplete);
            fxRate = Double.parseDouble(objRate.getHKD());
            scaleTV.setText("¥1000 ");
            rateTV.setText(Double.toString(Math.round(fxRate * Double.valueOf(showRatio) * 100.0) / 100.0) + " ");

            // Location access, retrieve longitude and latitude of the device
            objLocation = new JsonLocation(urlLocation);
            objLocation.fetchJSON();
            while(objLocation.parsingComplete);
            if (objLocation.getLongitude().length() < 5) {
                urlWeather += objLocation.getLongitude() + "," + objLocation.getLatitude();
            }
            else {
                urlWeather += objLocation.getLongitude().substring(0,6) + "," + objLocation.getLatitude().substring(0,6);
            }

            // Retrieve accessing token of the Weather API Json
            objToken = new JsonWeatherToken(urlToken);
            objToken.fetchJSON();
            while(objToken.parsingComplete);
            weatherToken += objToken.getToken();
            urlWeather += weatherToken;

            // Retrieve weather information from the Weather API Json
            objWeather = new JsonWeatherNow(urlWeather);
            objWeather.fetchJSON();
            while(objWeather.parsingComplete);

            // Put the values in the layout and picture settings
            describeTV.setText(objLocation.getCity() + " " + getString(R.string.divider5) + " " + objWeather.getDescription() + " " + getString(R.string.divider4));
            temperatureTV.setText(objWeather.getTemperature() + "°C");
            rainTV.setText(objWeather.getRainProb() + "% ");
            weatherTime = objWeather.getTime();
            weatherSymbol = objWeather.getSymbol();
            String weatherStr = "@drawable/" + weatherSymbol;
            int weatherInt = getResources().getIdentifier(weatherStr, null, getPackageName());
            Drawable res = getResources().getDrawable(weatherInt);
            weatherIV.setImageDrawable(res);
            if (weatherSymbol.substring(1,2).equals("4")) {
                weatherBgIV.setImageResource(R.drawable.background_cloudy); // Rainy Day
            }
            else if (weatherTime.substring(0,1).equals("0")) {
                if (Integer.parseInt(weatherTime.substring(1,2)) >= 0 && Integer.parseInt(weatherTime.substring(1,2)) <= 5 ) {
                    weatherBgIV.setImageResource(R.drawable.background_night); // Time 0000-0500
                }
                else { weatherBgIV.setImageResource(R.drawable.background_eve_morning); } // Time 0600-0900
            }
            else if (weatherTime.substring(0,1).equals("1")) {
                if (Integer.parseInt(weatherTime.substring(1,2)) >= 0 && Integer.parseInt(weatherTime.substring(1,2)) <= 6 ) {
                    weatherBgIV.setImageResource(R.drawable.background_afternoon); // Time 1000-1600
                }
                else { weatherBgIV.setImageResource(R.drawable.background_eve_morning); } // Time 1700-1900
            }
            else if (weatherTime.substring(0,1).equals("2")) {
                weatherBgIV.setImageResource(R.drawable.background_night); // Time 2000-2400
            }
        }
    }

    public void doCapture(View view) {
        //open the camera page to capture
        if (isNetworkConnected()) {
            String fileName = "photo";
            File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                // the path of photo to saving
                File imageFile = File.createTempFile(fileName,".jpg", storageDirectory);
                currentPhotoPath = imageFile.getAbsolutePath();
                Uri imageUri = FileProvider.getUriForFile(MainActivity.this,
                        "com.arman.traplus.fileprovider", imageFile);

                startCropActivity();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "Not network turned on\nPlease turn on the network", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Intent i = new Intent(MainActivity.this, EditMenuActivity.class);
                i.putExtra("uri", resultUri.toString());
                startActivity(i);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        if (requestCode == 21) {
            if (resultCode == RESULT_OK) {
                showRatio = Integer.valueOf(data.getStringExtra("rRatio"));
                liveFXMode = Boolean.valueOf(data.getStringExtra("rMode"));
                fxRate = Double.valueOf(data.getStringExtra("rRate"));
                rateTV.setText(Double.toString(Math.round(fxRate * Double.valueOf(showRatio) * 100.0) / 100.0) + " ");
                scaleTV.setText("¥" + Integer.toString(showRatio) + " ");
            } else {
                finish();
            }
        }

        if (requestCode == 22) {
            if (resultCode == RESULT_OK) {
                temperatureTV.setText(data.getStringExtra("rTemp") + "°C");
                rainTV.setText(data.getStringExtra("rRain") + "% ");
            } else {
                finish();
            }
        }

    }

    private void startCropActivity(){
        // start the camera activity and call cropper activity after capture
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    public void voicePage(View view) {
        //go to the voice chat page
        Intent intent = new Intent(MainActivity.this, activityChat.class);
        startActivity(intent);
    }

    public void tablemanner(View view) {
        //go to the front page oftable manner
        Intent intent = new Intent(MainActivity.this, TableMannerClassfication.class);
        startActivity(intent);
    }

    public void ToOfflineMenu(View view) {
        Intent i = new Intent(MainActivity.this, offlineListActivity.class);
        startActivity(i);
    }

    public void weatherPage(View view) {
        Intent i = new Intent(MainActivity.this, activityWeather.class);
        i.putExtra("apiToken", weatherToken);
        startActivityForResult(i, 22);
    }

    public void fxPage(View view) {
        Intent i = new Intent(MainActivity.this, activityFX.class);
        i.putExtra("rate", Double.toString(fxRate));
        i.putExtra("ratio", Integer.toString(showRatio));
        i.putExtra("live", Boolean.toString(liveFXMode));
        startActivityForResult(i, 21);
    }

    // check if the devices has Internet connection
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}