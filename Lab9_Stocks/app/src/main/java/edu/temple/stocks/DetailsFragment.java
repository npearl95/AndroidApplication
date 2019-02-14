package edu.temple.stocks;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import edu.temple.stocks.utilities.Stock;
import edu.temple.stocks.utilities.Utility;

public class DetailsFragment extends Fragment {

    TextView companyNameTextView;
    TextView companyStockPrice;
    TextView companyOpenStockPrice;
    WebView StockGraph;
    Utility utility;
    private static final String TAG="Test";

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String param1, String param2) {

        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utility = new Utility(getActivity());

    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){

        if (container != null) {
            container.removeAllViews();
        }

        Bundle bundle = this.getArguments();
        int position = 0;
        String symbol = "";

        if(bundle != null) {
            position = bundle.getInt("position");
            symbol = bundle.getString("symbol");
        }

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        companyNameTextView = (TextView) view.findViewById(R.id.companyNameTextView);
        companyStockPrice = (TextView) view.findViewById(R.id.companyStockTextView);
        companyOpenStockPrice = (TextView) view.findViewById(R.id.companyOpenStockTextView);

        StockGraph =(WebView) view.findViewById(R.id.StockGraph);
        ArrayList<Stock> dataFromFile = utility.getStockData();
        if(bundle != null) {
            companyNameTextView.setText(utility.getJSONCompanyName(dataFromFile.get(position).getStockJsonString()));
            String last = utility.getJSONLastPrice(dataFromFile.get(position).getStockJsonString());
            String open = utility.getJSONOpenPrice(dataFromFile.get(position).getStockJsonString());
            companyStockPrice.setText(last);
            companyOpenStockPrice.setText(open);

            double lastPriceNumber=Double.parseDouble(last);
            double openPriceNumber = Double.parseDouble(open);

            //compare 2 prices
            if(lastPriceNumber>=openPriceNumber){
                companyStockPrice.setTextColor(Color.GREEN);
            }else companyStockPrice.setTextColor(Color.RED);
        }

        if(!dataFromFile.isEmpty())
            showStockChart(symbol);

        return view;
    }

    @Override
    public void onDetach () {

        super.onDetach();
    }
    public void showStockChart(String symbol){

        StockGraph.getSettings().setJavaScriptEnabled(true);
        StockGraph.loadUrl("https://macc.io/lab/cis3515/?symbol="+symbol+"&width=300&height=150");
    }
    public void setDualPaneView(int position, String symbol) {

        ArrayList<Stock> dataFromFile = utility.getStockData();
        companyNameTextView.setText(utility.getJSONCompanyName(dataFromFile.get(position).getStockJsonString()));
        showStockChart(symbol);
        String last = utility.getJSONLastPrice(dataFromFile.get(position).getStockJsonString());
        String open = utility.getJSONOpenPrice(dataFromFile.get(position).getStockJsonString());
        companyStockPrice.setText(last);
        companyOpenStockPrice.setText(open);

        double lastPriceNumber=Double.parseDouble(last);
        double openPriceNumber = Double.parseDouble(open);

        //compare 2 prices
        if(lastPriceNumber>=openPriceNumber){
            companyStockPrice.setTextColor(Color.GREEN);
        }else companyStockPrice.setTextColor(Color.RED);
    }


}
