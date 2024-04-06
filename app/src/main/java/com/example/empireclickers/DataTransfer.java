package com.example.empireclickers;

import android.os.AsyncTask;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataTransfer extends AsyncTask<Object, Void, String> {
    private static final String SUPABASE_URL = "https://epwuxumpmqaprnpdvpkh.supabase.co";
    private static final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVwd3V4dW1wbXFhcHJucGR2cGtoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTI0MTA2NzAsImV4cCI6MjAyNzk4NjY3MH0.bn5r1yTIeCutKWMeVpl06RCYYDggqqcSW4KEfQCNkEA";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    String tablename;

    public DataTransfer(String tablename) {
        this.tablename = tablename;
    }

    @Override
    protected String doInBackground(Object... data) {
        try {
            String jsonData = gson.toJson(data[0]);
            RequestBody body = RequestBody.create(jsonData, JSON);
            Request request = new Request.Builder()
                    .url(SUPABASE_URL + "/rest/v1/" + tablename)
                    .post(body)
                    .addHeader("apikey", SUPABASE_KEY)
                    .addHeader("Authorization", "Bearer " + SUPABASE_KEY)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Prefer", "return=representation")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Handle the result of the network operation here
    }
}

