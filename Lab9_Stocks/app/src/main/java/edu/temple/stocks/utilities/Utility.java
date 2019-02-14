package edu.temple.stocks.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Utility {
    Context context;
    Gson gson = new Gson();
    public Utility(Context c){
        this.context = c;
    }
    //Save information to file by SharedPreference
    public void saveStocks(ArrayList<Stock> stocks) {
        SharedPreferences prefs = context.getSharedPreferences("Tag", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("size", String.valueOf(stocks.size()));
        for(int i = 0; i < stocks.size(); i++){
            String stockJson = gson.toJson(stocks.get(i));
            editor.putString("stock" + String.valueOf(i), stockJson);
        }
        editor.commit();
        prefs.getString("size", "0");
        prefs.getString("stock0", "null");
        Log.e("Saved String", String.valueOf(prefs.getString("stock0", "null")));
        sendUpdateBroadcast();
    }
    public ArrayList<Stock> getStockData() {
        ArrayList<Stock> symbolList = new ArrayList<>();
        SharedPreferences prefs = context.getSharedPreferences("Tag", Context.MODE_PRIVATE);
        String size = prefs.getString("size", "0");
        for(int i = 0; i < Integer.parseInt(size); i++){
            String stockJson = prefs.getString("stock" + String.valueOf(i), "null");
            Stock stock = gson.fromJson(stockJson, Stock.class);
            symbolList.add(stock);
        }
        return symbolList;
    }
    public ArrayList<String> getStockSymbols() {
        ArrayList<Stock> stockData = getStockData();
        ArrayList<String> stockSymbols = new ArrayList<>();
        for(int i = 0; i < stockData.size(); i++){
            stockSymbols.add(stockData.get(i).getStockSymbol());
        }
        return stockSymbols;
    }
    public ArrayList<String> getStockJSON() {
        ArrayList<Stock> stockData = getStockData();
        ArrayList<String> stockJSON = new ArrayList<>();
        for(int i = 0; i < stockData.size(); i++){
            stockJSON.add(stockData.get(i).getStockJsonString());
        }
        return stockJSON;
    }

    public void addStock(Stock stock) {
        ArrayList<Stock> stockData = getStockData();
        stockData.add(stock);
        SharedPreferences prefs = context.getSharedPreferences("Tag", Context.MODE_PRIVATE);
        saveStocks(stockData);

    }

    public void deleteStock(String symbol) {
        ArrayList<Stock> stockData = getStockData();
        ArrayList<Stock> newStockData = new ArrayList<>();
        for(int i = 0; i < stockData.size(); i++){
            if(!stockData.get(i).getStockSymbol().equals(symbol)){
                newStockData.add(stockData.get(i));
            }
        }
        saveStocks(newStockData);
    }

    public String getJSONLastPrice(String json){
        String high = "";

        try {
            if(json != null) {
                JSONObject jsonObject = new JSONObject(json);
                high = jsonObject.getString("LastPrice");
            }
        }catch (JSONException e){
            high = "Invalid symbol";
        }

        return high;
    }

    //get open price
    public String getJSONOpenPrice(String json){
        String open = "";
        try{
            if(json!=null) {
                JSONObject jsonObject = new JSONObject(json);
                open = jsonObject.getString("Open");
            }
        }catch(JSONException e){
                open ="Invalid Open Price";
        }
        return open;
    }
    public String getJSONCompanyName(String json) {

        String name = "";

        try{
            if(json != null) {
                JSONObject jsonObject = new JSONObject(json);
                name = jsonObject.getString("Name");
            }
        } catch (JSONException e){
            name = "Invalid name";
        }

        return name;
    }

    public String getJSONPriceChange(String json) {

        String priceChange = "";

        try{
            if(json != null) {
                JSONObject jsonObject = new JSONObject(json);
                priceChange = jsonObject.getString("Change");
            }
        } catch (JSONException e){
            priceChange = "Invalid Price Change";
        }

        return priceChange;
    }
    public boolean isValidSymbol(String json) {

        boolean isValid = false;

        try{
            JSONObject jsonObject = new JSONObject(json);
            Log.e("Status", jsonObject.getString("Status"));
            if(jsonObject.getString("Status").equals("SUCCESS")){
                isValid = true;
            }
        }catch (JSONException e){
        }

        return isValid;
    }

    public void sendUpdateBroadcast(){
        Intent broadcast = new Intent();
        broadcast.setAction("getUpdatedData");
        broadcast.putExtra("update", "update");
        context.sendBroadcast(broadcast);
    }
}
