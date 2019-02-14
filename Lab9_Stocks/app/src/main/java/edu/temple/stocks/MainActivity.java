package edu.temple.stocks;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import edu.temple.stocks.utilities.UIThreadGetStock;
import edu.temple.stocks.utilities.Stock;
import edu.temple.stocks.utilities.StockUpdate;
import edu.temple.stocks.utilities.Utility;

public class MainActivity extends AppCompatActivity implements AddNewStockDialog.AddNewStockInterface,
        PortfolioFragment.PortfolioInterface {
    Toolbar myToolbar;
    PortfolioFragment portfolioFragment;
    DetailsFragment detailsFragment;
    boolean twoPanes;
    String newStockJson = null;
    Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent stockServiceIntent = new Intent(this, StockUpdate.class);
        startService(stockServiceIntent);

        utility = new Utility(this);
        twoPanes = (findViewById(R.id.stocks_details_fragment) != null);

        portfolioFragment = new PortfolioFragment();
        detailsFragment = new DetailsFragment();

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.showOverflowMenu();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.stocks_nav_fragment, portfolioFragment);
        fragmentTransaction.commit();

        if(twoPanes) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.stocks_details_fragment, detailsFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentManager fragmentManager = getFragmentManager();

        switch (item.getItemId()) {
            case R.id.action_new:

                AddNewStockDialog newStockDialog = new AddNewStockDialog();
                newStockDialog.show(fragmentManager, "AddNewStockDialog");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment) {

        EditText stockEditText = (EditText) dialogFragment.getDialog().findViewById(R.id.stock_editText);
        String userInputStock = stockEditText.getText().toString();
        getNewStockJson(userInputStock.toUpperCase());
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {
    }

    @Override
    public void addStockToList(Stock stock) {
        portfolioFragment.addStock(stock);
    }

    @Override
    public void stockItemSelected(int position, String symbol) {

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("symbol", symbol);

        if(!twoPanes) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.stocks_nav_fragment, detailsFragment)
                    .addToBackStack(null).commit();
            detailsFragment.setArguments(bundle);

            getFragmentManager().executePendingTransactions();
        }
        detailsDualPane(position, symbol);
    }

    public void getNewStockJson(final String stockSymbol) {

        new UIThreadGetStock() {

            @Override
            protected void onPostExecute(String jsonString) {

                Utility utility = new Utility(getApplicationContext());
                newStockJson = jsonString;

                if(utility.isValidSymbol(jsonString) == true) {
                    Stock stock = new Stock(stockSymbol, newStockJson);
                    addStockToList(stock);
                }else{
                    Toast.makeText(getApplicationContext(), getResources().
                            getString(R.string.invalid_symbol), Toast.LENGTH_SHORT).show();
                }
                newStockJson = null;
            }
        }.execute(stockSymbol);
    }

    public void detailsDualPane(int position, String symbol) {
        detailsFragment.setDualPaneView(position, symbol);
    }
}

