package com.arman.traplus;

import android.annotation.SuppressLint;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonWeather3H {
    private String[] symbol = new String[3];
    private String[] temperature = new String[3];
    private String[] rainProb = new String[3];
    private String[] time = new String[3];
    private String urlString = null;
    public volatile boolean parsingComplete = true;

    public JsonWeather3H(String url) {
        this.urlString = url;
    }
    public String[] getSymbol() { return symbol; }
    public String[] getTime() {
        return time;
    }
    public String[] getTemperature() {
        return temperature;
    }
    public String[] getRainProb() {
        return rainProb;
    }

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in) {
        try {
            JSONObject root = new JSONObject(in);
            JSONArray arrayObj = root.getJSONArray("forecast");
            for (int i = 0; i < 3; i++) {
                JSONObject childObj = arrayObj.getJSONObject(i);
                symbol[i] = childObj.getString("symbol");
                time[i] = childObj.getString("time").substring(11,16);
                temperature[i] = childObj.getString("temperature");
                rainProb[i] = childObj.getString("precipProb");
            }
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
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
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
