package edu.temple.stocks.utilities;

public class Stock {

    String stockSymbol;
    String stockJsonString;


    public Stock(String stockSymbol, String stockJsonString) {
        this.stockSymbol = stockSymbol;
        this.stockJsonString = stockJsonString;
    }

    public String getStockSymbol() {

        return stockSymbol;
    }

    public String getStockJsonString() {

        return stockJsonString;
    }

    public void setStockSymbol(String stockSymbol) {

        this.stockSymbol = stockSymbol;
    }

    @Override
    public String toString() {

        return stockSymbol;
    }
}
