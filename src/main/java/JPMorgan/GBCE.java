package JPMorgan;

import java.util.List;


public class GBCE {

    public static double calculateGBCE(List<Stock> stocks) {

        if (stocks == null)
            throw new NullPointerException();

        double geometricMean = 1;

        for (Stock stock : stocks) {
            geometricMean = geometricMean * stock.getStockMostRecentPrice();
        }

        return Math.pow(geometricMean, 1.0 / stocks.size());
    }
}
