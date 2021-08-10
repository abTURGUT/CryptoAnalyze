package com.tt.cryptoanalyze;

import android.util.Log;

import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.api.BinanceApiException;

public class BinanceHelper {
    static BinanceApi api = new BinanceApi();


    public static String GetPrice(String coinName) throws BinanceApiException {
        coinName = coinName.toUpperCase();
        String price = api.pricesMap().get(coinName).toString();
        return price;
    }
}
