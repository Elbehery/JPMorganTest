package JPMorgan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;


public class GBCETest {


    private Stock TEAStock;
    private Stock POPStock;
    private Stock ALEStock;
    private Stock GINStock;
    private Stock JOEStock;
    private List<Stock> stockList;

    private Trade tradeOne;
    private Trade tradeTwo;
    private Trade tradeThree;
    private Trade tradeFour;
    private Trade tradeFive;


    @Before
    public void setUp() {

        this.TEAStock = new Stock("TEA", StockType.COMMON, 0, 0.0, 100);
        this.POPStock = new Stock("POP", StockType.COMMON, 8, 0.0, 100);
        this.ALEStock = new Stock("ALE", StockType.COMMON, 23, 0.0, 60);
        this.GINStock = new Stock("GIN", StockType.PREFERRED, 8, 0.2, 100);
        this.JOEStock = new Stock("JOE", StockType.COMMON, 13, 0.0, 250);

        this.stockList = new ArrayList<Stock>(5);
        this.stockList.add(this.TEAStock);
        this.stockList.add(this.POPStock);
        this.stockList.add(this.ALEStock);
        this.stockList.add(this.GINStock);
        this.stockList.add(this.JOEStock);

        Date currentTime = new Date();

        this.tradeOne = new Trade(10, 50.0, TradeType.BUY);
        this.tradeOne.setDate(new Date(currentTime.getTime() - (50 * 60 * 1000)));

        this.tradeTwo = new Trade(20, 100.0, TradeType.SELL);
        this.tradeTwo.setDate(new Date(currentTime.getTime() - (40 * 60 * 1000)));

        this.tradeThree = new Trade(30, 220, TradeType.BUY);
        this.tradeThree.setDate(new Date(currentTime.getTime() - (30 * 60 * 1000)));

        this.tradeFour = new Trade(50, 300, TradeType.BUY);
        this.tradeFour.setDate(new Date(currentTime.getTime() - (20 * 60 * 1000)));

        this.tradeFive = new Trade(60, 10, TradeType.BUY);
        this.tradeFive.setDate(new Date(currentTime.getTime() - (10 * 60 * 1000)));


        TreeMap<Date, Trade> dummyMap = new TreeMap<Date, Trade>();

        dummyMap.put(tradeOne.getDate(), tradeOne);
        dummyMap.put(tradeTwo.getDate(), tradeTwo);
        dummyMap.put(tradeThree.getDate(), tradeThree);
        dummyMap.put(tradeFour.getDate(), tradeFour);
        dummyMap.put(tradeFive.getDate(), tradeFive);

        // replace the tradeMap of each stock with dummy Map
        this.TEAStock.setStockTradeMap(dummyMap);
        this.POPStock.setStockTradeMap(dummyMap);
        this.ALEStock.setStockTradeMap(dummyMap);
        this.GINStock.setStockTradeMap(dummyMap);
        this.JOEStock.setStockTradeMap(dummyMap);
    }

    @Test(expected = NullPointerException.class)
    public void testCalculateGBCEWithNull() {

        List<Stock> nullList = null;
        GBCE.calculateGBCE(nullList);
    }


    @Test
    public void testCalculateGBCE() {

        double actual = Math.pow(10 * 10 * 10 * 10 * 10, 1.0 / 5);
        double expected = GBCE.calculateGBCE(stockList);
        Assert.assertEquals(expected, actual, 0.0);
    }

}

