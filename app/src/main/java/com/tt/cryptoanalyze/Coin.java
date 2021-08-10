package com.tt.cryptoanalyze;

public class Coin {

    int id;
    String coinName;
    String buyPrice;
    String coinCount;
    String pnlRatio;
    String pnlPrice;


    public Coin(int id, String coinName, String buyPrice, String coinCount) {
        this.id = id;
        this.coinName = coinName;
        this.buyPrice = buyPrice;
        this.coinCount = coinCount;
    }

    public Coin(int id, String coinName, String buyPrice, String coinCount, String pnlRatio, String pnlPrice) {
        this.id = id;
        this.coinName = coinName;
        this.buyPrice = buyPrice;
        this.coinCount = coinCount;
        this.pnlRatio = pnlRatio;
        this.pnlPrice = pnlPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(String coinCount) {
        this.coinCount = coinCount;
    }

    public String getPnlRatio() {
        return pnlRatio;
    }

    public void setPnlRatio(String pnlRatio) {
        this.pnlRatio = pnlRatio;
    }

    public String getPnlPrice() {
        return pnlPrice;
    }

    public void setPnlPrice(String pnlPrice) {
        this.pnlPrice = pnlPrice;
    }
}
