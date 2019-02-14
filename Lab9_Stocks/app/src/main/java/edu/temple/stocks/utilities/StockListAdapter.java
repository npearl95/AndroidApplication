package edu.temple.stocks.utilities;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import edu.temple.stocks.R;

public class StockListAdapter extends ArrayAdapter<Stock> implements ListAdapter {
    Utility utility;

    public static class ViewHolder {
        TextView symbolText;
        TextView currentPrice;

    }

    public StockListAdapter(Context context, ArrayList<Stock> stocks) {
        super(context, 0, stocks);
        utility = new Utility(getContext());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Stock stock = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.stock_list_item, parent, false);

            viewHolder.symbolText = (TextView) convertView.findViewById(R.id.stock_symbol_text);
            viewHolder.currentPrice = (TextView) convertView.findViewById(R.id.stock_price_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.symbolText.setText(stock.getStockSymbol());
        viewHolder.symbolText.setTextSize(25);
        viewHolder.symbolText.setTextColor(Color.parseColor("black"));
        convertView.setBackgroundColor(Color.parseColor("white"));

        //Compare the price and display
        String price="";
        String openPrice="";
        String json=stock.getStockJsonString();
        try{
            if(json!=null) {
                JSONObject jsonObject = new JSONObject(json);
                price = jsonObject.getString("LastPrice");
                openPrice = jsonObject.getString("Open");
            }
        }catch(JSONException e){
            price ="Invalid Info";
        }
        viewHolder.currentPrice.setText(price);
        double currentPriceNumber = Double.parseDouble(price);
        double openPriceNumber = Double.parseDouble(openPrice);

        if(currentPriceNumber>=openPriceNumber){
            viewHolder.currentPrice.setBackgroundColor(Color.GREEN);
        }else viewHolder.currentPrice.setBackgroundColor(Color.RED);
        return convertView;
    }
}
