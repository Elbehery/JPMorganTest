package JPMorgan;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Stock {

    private String stockSymbol;
    private StockType stockType;
    private int lastDividend;
    private double fixedDividend;
    private int parValue;
    private Map<Date, Trade> stockTradeMap;

    public Stock(String stockSymbol, StockType type, int lastDividend, double fixedDividend, int parValue) {

        this.stockSymbol = stockSymbol;
        this.stockType = type;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
        this.stockTradeMap = new TreeMap<Date, Trade>();
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public StockType getStockType() {
        return stockType;
    }

    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }

    public int getLastDividend() {
        return lastDividend;
    }

    public void setLastDividend(int lastDividend) {
        this.lastDividend = lastDividend;
    }

    public double getFixedDividend() {
        return fixedDividend;
    }

    public void setFixedDividend(double fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    public int getParValue() {
        return parValue;
    }

    public void setParValue(int parValue) {
        this.parValue = parValue;
    }

    public Map<Date, Trade> getStockTradeMap() {
        return stockTradeMap;
    }

    public void setStockTradeMap(Map<Date, Trade> stockTradeMap) {
        this.stockTradeMap = stockTradeMap;
    }

    public double getStockMostRecentPrice() {

        Trade lastTrade = ((TreeMap<Date, Trade>) this.stockTradeMap).lastEntry().getValue();

        return lastTrade != null ? lastTrade.getPrice() : 0;
    }


    public double calculateDividendYield(double price) {
        if (price <= 0)
            throw new IllegalArgumentException(" Invalid Input Price");

        if (this == null)
            throw new NullPointerException();

        double dividendYield = Double.NEGATIVE_INFINITY;

        if (this.getStockType().equals(StockType.COMMON))
            dividendYield = this.getLastDividend() / price;
        else if (this.getStockType().equals(StockType.PREFERRED))
            dividendYield = (this.getFixedDividend() * this.getParValue()) / price;

        return dividendYield;
    }


    public double calculatePERatio(double price) {

        if (price <= 0)
            throw new IllegalArgumentException(" Invalid Input");

        if (this == null)
            throw new NullPointerException();

        return price / getLastDividend();
    }

    public double volumeWeightedStockPrice() {

        if (this == null)
            throw new NullPointerException();

        // get the last 15 minutes
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -15);
        Date searchTime = calendar.getTime();

        // search for trades within the last 15 minutes
        Map<Date, Trade> lastFifteenMinutesTradesMap = ((TreeMap<Date, Trade>) this.stockTradeMap).tailMap(searchTime);

        double volumeWeightedStockPrice = 0;
        int quantities = 0;

        for (Trade trade : lastFifteenMinutesTradesMap.values()) {
            volumeWeightedStockPrice = volumeWeightedStockPrice + (trade.getQuantity() * trade.getPrice());
            quantities += trade.getQuantity();
        }

        return volumeWeightedStockPrice / quantities;

    }
}
