package JPMorgan;

import java.util.Date;


public class Trade {

    private int quantity;
    private double price;
    private TradeType tradeType;
    private Date date;

    public Trade(int quantity, double price, TradeType tradeType) {
        this.quantity = quantity;
        this.price = price;
        this.tradeType = tradeType;
        this.date = new Date();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
