package edu.temple.stocks.utilities;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class StockUpdate extends IntentService {

    public static final int ONE_MINUTE = 60000;
    Utility utility;

    public StockUpdate() {
        super("StockUpdate");
        utility = new Utility(this);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final ArrayList<String> symbols = utility.getStockSymbols();
                ArrayList<Stock> newStockData = new ArrayList<>();
                for(int i = 0; i < symbols.size(); i++) {
                    Stock stock = new Stock(symbols.get(i), getStockQuote(symbols.get(i)));
                    newStockData.add(stock);
                }
                utility.saveStocks(newStockData);
            }
        }, 0, ONE_MINUTE);
    }

    public String getStockQuote(final String symbol) {

        URL stockQuoteUrl;
        BufferedReader reader = null;
        HttpURLConnection urlConnection = null;
        String response = null;
        try {
            stockQuoteUrl = new URL("http://dev.markitondemand.com/MODApis/Api/v2/Quote/json/?symbol=" + symbol);
            Log.e("URL", String.valueOf(stockQuoteUrl));
            urlConnection = (HttpURLConnection) stockQuoteUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null){
                response = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0){
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
}

