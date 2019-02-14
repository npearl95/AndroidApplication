package edu.temple.stocks;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.temple.stocks.utilities.Stock;
import edu.temple.stocks.utilities.StockListAdapter;
import edu.temple.stocks.utilities.Utility;


public class PortfolioFragment extends Fragment {

    ArrayList<Stock> stockDataList;
    ListView stockListView;
    StockListAdapter stockListAdapter;
    Utility utility;
    PortfolioInterface portfolioInterface;
    IntentFilter filter = new IntentFilter();


    public PortfolioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utility = new Utility(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);

        stockDataList = utility.getStockData();

        stockListView = (ListView) view.findViewById(R.id.stockListView);
        TextView emptyStockText = (TextView) view.findViewById(R.id.emptyStockText);
        stockListView.setEmptyView(emptyStockText);

        stockListAdapter = new StockListAdapter(getActivity(), stockDataList);
        stockListView.setAdapter(stockListAdapter);

        AdapterView.OnItemClickListener stockListListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                portfolioInterface.stockItemSelected(position, stockDataList.get(position).toString());
            }
        };

        stockListView.setOnItemClickListener(stockListListener);

        return view;
    }

    public void addStock(Stock stock) {
        utility.addStock(stock);
        stockListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof PortfolioInterface) {
            portfolioInterface = (PortfolioInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PortfolioInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        filter.addAction("getUpdatedData");
        getActivity().registerReceiver(JSONReceiver, filter);
    }

    @Override
    public void onPause() {
        try {
            getActivity().unregisterReceiver(JSONReceiver);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Receiver not registered")) {
                // Ignore this exception
            } else {
                // unexpected, re-throw
                throw e;
            }
        }
        super.onPause();
    }

    public interface PortfolioInterface {
        void addStockToList(Stock stock);
        void stockItemSelected(int position, String symbol);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    BroadcastReceiver JSONReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            stockDataList = utility.getStockData();
            stockListAdapter = new StockListAdapter(getActivity(), stockDataList);
            stockListView.setAdapter(stockListAdapter);
        }
    };
}
