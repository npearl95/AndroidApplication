package edu.temple.stocks.utilities;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UIThreadGetStock extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... symbol) {
        URL stockQuoteUrl;
        BufferedReader reader = null;
        HttpURLConnection urlConnection = null;
        String response = null;
        try {
            stockQuoteUrl = new URL("http://dev.markitondemand.com/MODApis/Api/v2/Quote/json/?symbol=" + symbol[0]);
            Log.e("URL", String.valueOf(stockQuoteUrl));
            urlConnection = (HttpURLConnection) stockQuoteUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                response = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if(buffer.length() == 0){
                response = buffer.toString();
            }
            response = buffer.toString();
            String tmpResponse;
            tmpResponse = reader.readLine();
            while (tmpResponse != null) {
                response = response + tmpResponse;
                tmpResponse = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader != null){
                try {
                    reader.close();
                } catch (final IOException e){
                }
            }
        }
        if(response != null) {
            Log.e("Response", response);
        }else{
            Log.e("Response", "No Response");
        }
        return response;
    }
    @Override
    protected void onPostExecute(String jsonString){
        super.onPostExecute(jsonString);
    }
}
