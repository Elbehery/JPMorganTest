package JPMorgan;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;


public class StockTest {

    private Stock TEAStock;
    private Stock POPStock;
    private Stock ALEStock;
    private Stock GINStock;
    private Stock JOEStock;

    private Trade tradeOne;
    private Trade tradeTwo;
    private Trade tradeThree;

    private Stock nullStock = null;

    @Before
    public void setUp() {

        this.TEAStock = new Stock("TEA", StockType.COMMON, 0, 0.0, 100);
        this.POPStock = new Stock("POP", StockType.COMMON, 8, 0.0, 100);
        this.ALEStock = new Stock("ALE", StockType.COMMON, 23, 0.0, 60);
        this.GINStock = new Stock("GIN", StockType.PREFERRED, 8, 0.2, 100);
        this.JOEStock = new Stock("JOE", StockType.COMMON, 13, 0.0, 250);

        Date currentTime = new Date();

        // set the time for this trade for 30 minutes ago.
        this.tradeOne = new Trade(10, 50.0, TradeType.BUY);
        this.tradeOne.setDate(new Date(currentTime.getTime() - (30 * 60 * 1000)));

        // set the time for this trade for 10 minutes ago.
        this.tradeTwo = new Trade(20, 100.0, TradeType.SELL);
        this.tradeTwo.setDate(new Date(currentTime.getTime() - (10 * 60 * 1000)));

        // set the time for this trade for 5 minutes ago.
        this.tradeThree = new Trade(30, 220, TradeType.BUY);
        this.tradeThree.setDate(new Date(currentTime.getTime() - (5 * 60 * 1000)));

        //add the trades to JOEStock
        this.JOEStock.getStockTradeMap().put(tradeOne.getDate(), tradeOne);
        this.JOEStock.getStockTradeMap().put(tradeTwo.getDate(), tradeTwo);
        this.JOEStock.getStockTradeMap().put(tradeThree.getDate(), tradeThree);
    }

    @Test(expected = NullPointerException.class)
    public void testCalculateDividendYieldWithNull() {
        nullStock.calculateDividendYield(1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateDividendYieldWithIllegalPrice() {
        this.TEAStock.calculateDividendYield(0.0);
        this.GINStock.calculateDividendYield(-1);
    }

    @Test
    public void testCalculateDividendYield() {

        double actual = this.TEAStock.calculateDividendYield(100.0);
        double expected = this.TEAStock.getLastDividend() / 100.0;
        Assert.assertEquals(expected, actual, 0.0);

        actual = this.GINStock.calculateDividendYield(99.0);
        expected = (this.GINStock.getFixedDividend() * this.GINStock.getParValue()) / 99.0;
        Assert.assertEquals(expected, actual, 0.0);
    }


    @Test(expected = NullPointerException.class)
    public void testCalculatePERatioWithNull() {
        nullStock.calculatePERatio(1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculatePERatioWithIllegalPrice() {
        this.GINStock.calculatePERatio(0.0);
        this.TEAStock.calculatePERatio(-1);
    }

    @Test
    public void testCalculatePERatio() {

        double actual = this.TEAStock.calculatePERatio(100.0);
        double expected = 100.0 / this.TEAStock.getLastDividend();
        Assert.assertEquals(expected, actual, 0.0);

        actual = this.GINStock.calculatePERatio(99.0);
        expected = 99.0 / this.GINStock.getLastDividend();
        Assert.assertEquals(expected, actual, 0.0);
    }


    @Test(expected = NullPointerException.class)
    public void testVolumeWeightedStockPriceWithNull() {
        nullStock.volumeWeightedStockPrice();
    }

    @Test
    public void testVolumeWeightedStockPrice() {

        double volumeWeightedStockPrice = 0;
        int quantities = 0;
        // add trade two values
        volumeWeightedStockPrice = volumeWeightedStockPrice + (tradeTwo.getQuantity() * tradeTwo.getPrice());
        quantities += tradeTwo.getQuantity();
        // add trade three values
        volumeWeightedStockPrice = volumeWeightedStockPrice + (tradeThree.getQuantity() * tradeThree.getPrice());
        quantities += tradeThree.getQuantity();

        double actual = volumeWeightedStockPrice / quantities;
        double expected = this.JOEStock.volumeWeightedStockPrice();

        Assert.assertEquals(expected, actual, 0.0);

    }


}
