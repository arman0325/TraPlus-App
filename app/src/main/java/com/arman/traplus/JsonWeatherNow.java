package com.arman.traplus;

import android.annotation.SuppressLint;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonWeatherNow {
    private String time;
    private String symbol;
    private String description;
    private String temperature;// = "temperature";
    private String rainProb;// = "rainProb";
    private String urlString = null;
    public volatile boolean parsingComplete = true;

    public JsonWeatherNow(String url) {
        this.urlString = url;
    }
    public String getTime() {
        return time;
    }
    public String getSymbol() { return symbol; }
    public String getDescription() {
        return description;
    }
    public String getTemperature() {
        return temperature;
    }
    public String getRainProb() {
        return rainProb;
    }

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in) {
        try {
            JSONObject root = new JSONObject(in);
            JSONObject childObj = root.getJSONObject("current");
            time = childObj.getString("time").substring(11,16);
            symbol = childObj.getString("symbol");
            description = childObj.getString("symbolPhrase");
            temperature = childObj.getString("temperature");
            rainProb = childObj.getString("precipProb");
            parsingComplete = false;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void fetchJSON(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    String data = convertStreamToString(stream);

                    readAndParseJSON(data);
                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
