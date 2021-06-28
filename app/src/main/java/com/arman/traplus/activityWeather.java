package com.arman.traplus;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class activityWeather extends AppCompatActivity {

    private String urlCurrent = "https://pfa.foreca.com/api/v1/current/";//140.00,35.60";
    private String urlThreeHr = "https://pfa.foreca.com/api/v1/forecast/hourly/";//140.00,35.60";
    private String urlLocation = "http://api.ipstack.com/check?access_key=d02460f51758de00c306d5e212cd8ce0";
    private String temperature, rain = "--- ";
    private JsonWeatherNow objWeather;
    private JsonWeather3H objWeather3H;
    private JsonLocation objLocation;
    private int img0int, img1int, img2int, img3int;
    private String img0String, img1String, img2String, img3String = "@drawable/";
    private String city, latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_activity_weather);
        getSupportActionBar().hide();
        Intent i = getIntent();

        TextView cityTV = (TextView) findViewById (R.id.wCity);
        TextView describeTV = (TextView) findViewById (R.id.wDescription);
        TextView nowTempTV = (TextView) findViewById (R.id.wNowTemp);
        TextView nowRainTV = (TextView) findViewById (R.id.wNowRain);
        TextView time1hTV = (TextView) findViewById (R.id.wOneHrTime);
        TextView temp1hTV = (TextView) findViewById (R.id.wOneHrTemp);
        TextView rain1hTV = (TextView) findViewById (R.id.wOneHrWet);
        TextView time2hTV = (TextView) findViewById (R.id.wTwoHrTime);
        TextView temp2hTV = (TextView) findViewById (R.id.wTwoHrTemp);
        TextView rain2hTV = (TextView) findViewById (R.id.wTwoHrWet);
        TextView time3hTV = (TextView) findViewById (R.id.wThreeHrTime);
        TextView temp3hTV = (TextView) findViewById (R.id.wThreeHrTemp);
        TextView rain3hTV = (TextView) findViewById (R.id.wThreeHrWet);
        ImageView img0h = (ImageView) findViewById (R.id.wNowImg);
        ImageView img1h = (ImageView) findViewById (R.id.wOneHrImg);
        ImageView img2h = (ImageView) findViewById (R.id.wTwoHrImg);
        ImageView img3h = (ImageView) findViewById (R.id.wThreeHrImg);
        ImageView bg1h = (ImageView) findViewById (R.id.wOneHrBg);
        ImageView bg2h = (ImageView) findViewById (R.id.wTwoHrBg);
        ImageView bg3h = (ImageView) findViewById (R.id.wThreeHrBg);

        if (isNetworkConnected()) {
            objLocation = new JsonLocation(urlLocation);
            objLocation.fetchJSON();
            while(objLocation.parsingComplete);
            if (objLocation.getLongitude().length() < 5) {
                latitude += objLocation.getLatitude();
                longitude += objLocation.getLongitude();
            }
            else {
                latitude += objLocation.getLatitude().substring(0,6);
                longitude += objLocation.getLongitude().substring(0,6);
            }
            city = objLocation.getCity();
            cityTV.setText(city);

            // Inject coordinates to weather URL request
            urlCurrent += longitude + "," + latitude + i.getStringExtra("apiToken");
            urlThreeHr += longitude + "," + latitude + i.getStringExtra("apiToken");

            // Retrieve latest weather information from the Weather API Json
            objWeather = new JsonWeatherNow(urlCurrent);
            objWeather.fetchJSON();
            while(objWeather.parsingComplete);

            img0String = "@drawable/" + objWeather.getSymbol();
            img0int = getResources().getIdentifier(img0String, null, getPackageName());
            Drawable res0 = getResources().getDrawable(img0int);
            img0h.setImageDrawable(res0);

            String str = objWeather.getDescription();
            int strLength = str.length();
            describeTV.setText(str.substring(0, 1).toUpperCase() + str.substring(1, strLength));
            temperature = objWeather.getTemperature();
            rain = objWeather.getRainProb();
            nowTempTV.setText(temperature + "°C");
            nowRainTV.setText(rain + "%");

            // Retrieve three hours forecast weather information from the Weather API Json
            objWeather3H = new JsonWeather3H(urlThreeHr);
            objWeather3H.fetchJSON();
            while(objWeather3H.parsingComplete);
            String[] arraySymbol = objWeather3H.getSymbol();
            String[] arrayTime = objWeather3H.getTime();
            String[] arrayTemp = objWeather3H.getTemperature();
            String[] arrayRain = objWeather3H.getRainProb();

            // Put values in forecast of first hour
            img1String = "@drawable/" + arraySymbol[0];
            img1int = getResources().getIdentifier(img1String, null, getPackageName());
            Drawable res1 = getResources().getDrawable(img1int);
            img1h.setImageDrawable(res1);
            time1hTV.setText(arrayTime[0]);
            temp1hTV.setText(arrayTemp[0] + "°C");
            rain1hTV.setText(arrayRain[0] + "%");
            if (arraySymbol[0].substring(1,2).equals("4")) {
                bg1h.setImageResource(R.drawable.background_cloudy); // Rainy Day
            }
            else if (arrayTime[0].substring(0,1).equals("0")) {
                if (Integer.parseInt(arrayTime[0].substring(1,2)) >= 0 && Integer.parseInt(arrayTime[0].substring(1,2)) <= 5 ) {
                    bg1h.setImageResource(R.drawable.background_night); // Time 0000-0500
                }
                else { bg1h.setImageResource(R.drawable.background_eve_morning); } // Time 0600-0900
            }
            else if (arrayTime[0].substring(0,1).equals("1")) {
                if (Integer.parseInt(arrayTime[0].substring(1,2)) >= 0 && Integer.parseInt(arrayTime[0].substring(1,2)) <= 6 ) {
                    bg1h.setImageResource(R.drawable.background_afternoon); // Time 1000-1600
                }
                else { bg1h.setImageResource(R.drawable.background_eve_morning); } // Time 1700-1900
            }
            else if (arrayTime[0].substring(0,1).equals("2")) {
                if (Integer.parseInt(arrayTime[0].substring(1,2)) >= 0 && Integer.parseInt(arrayTime[0].substring(1,2)) <= 4 ) {
                    bg1h.setImageResource(R.drawable.background_night); // Time 2000-2400
                }
            }

            // Put values in forecast of second hour
            img2String = "@drawable/" + arraySymbol[1];
            img2int = getResources().getIdentifier(img2String, null, getPackageName());
            Drawable res2 = getResources().getDrawable(img2int);
            img2h.setImageDrawable(res2);
            time2hTV.setText(arrayTime[1]);
            temp2hTV.setText(arrayTemp[1] + "°C");
            rain2hTV.setText(arrayRain[1] + "%");
            if (arraySymbol[1].substring(1,2).equals("4")) {
                bg2h.setImageResource(R.drawable.background_cloudy); // Rainy Day
            }
            else if (arrayTime[1].substring(0,1).equals("0")) {
                if (Integer.parseInt(arrayTime[1].substring(1,2)) >= 0 && Integer.parseInt(arrayTime[0].substring(1,2)) <= 5 ) {
                    bg2h.setImageResource(R.drawable.background_night); // Time 0000-0500
                }
                else { bg2h.setImageResource(R.drawable.background_eve_morning); } // Time 0600-0900
            }
            else if (arrayTime[1].substring(0,1).equals("1")) {
                if (Integer.parseInt(arrayTime[1].substring(1,2)) >= 0 && Integer.parseInt(arrayTime[0].substring(1,2)) <= 6 ) {
                    bg2h.setImageResource(R.drawable.background_afternoon); // Time 1000-1600
                }
                else { bg2h.setImageResource(R.drawable.background_eve_morning); } // Time 1700-1900
            }
            else if (arrayTime[1].substring(0,1).equals("2")) {
                if (Integer.parseInt(arrayTime[1].substring(1,2)) >= 0 && Integer.parseInt(arrayTime[0].substring(1,2)) <= 4 ) {
                    bg2h.setImageResource(R.drawable.background_night); // Time 2000-2400
                }
            }

            // Put values in forecast of third hour
            img3String = "@drawable/" + arraySymbol[2];
            img3int = getResources().getIdentifier(img3String, null, getPackageName());
            Drawable res3 = getResources().getDrawable(img3int);
            img3h.setImageDrawable(res3);
            time3hTV.setText(arrayTime[2]);
            temp3hTV.setText(arrayTemp[2] + "°C");
            rain3hTV.setText(arrayRain[2] + "%");
            if (arraySymbol[2].substring(1,2).equals("4")) {
                bg3h.setImageResource(R.drawable.background_cloudy); // Rainy Day
            }
            else if (arrayTime[2].substring(0,1).equals("0")) {
                if (Integer.parseInt(arrayTime[2].substring(1,2)) >= 0 && Integer.parseInt(arrayTime[0].substring(1,2)) <= 5 ) {
                    bg3h.setImageResource(R.drawable.background_night); // Time 0000-0500
                }
                else { bg3h.setImageResource(R.drawable.background_eve_morning); } // Time 0600-0900
            }
            else if (arrayTime[2].substring(0,1).equals("1")) {
                if (Integer.parseInt(arrayTime[2].substring(1,2)) >= 0 && Integer.parseInt(arrayTime[0].substring(1,2)) <= 6 ) {
                    bg3h.setImageResource(R.drawable.background_afternoon); // Time 1000-1600
                }
                else { bg3h.setImageResource(R.drawable.background_eve_morning); } // Time 1700-1900
            }
            else if (arrayTime[2].substring(0,1).equals("2")) {
                bg3h.setImageResource(R.drawable.background_night); // Time 2000-2400
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent r = new Intent();
        r.putExtra("rTemp", temperature);
        r.putExtra("rRain", rain);
        setResult(RESULT_OK, r);
        super.onBackPressed();
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}