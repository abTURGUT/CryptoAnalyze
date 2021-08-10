package com.tt.cryptoanalyze;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.webcerebrium.binance.api.BinanceApiException;

import java.io.IOException;
import java.util.List;

public class CoinAdapter extends ArrayAdapter {

    List<Coin> coinList;
    Context context;
    int resourceID;
    final CoinAdapter adapterClass = this;
    DBHelper db;

    public CoinAdapter(@NonNull Context context, int resource, @NonNull List<Coin> list, DBHelper db) {
        super(context, resource, list);

        this.coinList = list;
        this.context = context;
        this.resourceID = resource;
        this.db = db;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(resourceID,null,false);

        final boolean[] stopProcess = {false};

        TextView lvCoinNameTXT = (TextView) view.findViewById(R.id.lvCoinNameTXT);
        TextView lvBuyPriceTXT = (TextView) view.findViewById(R.id.lvBuyPriceTXT);
        TextView lvCurrentPriceTXT = (TextView) view.findViewById(R.id.lvCurrentPriceTXT);
        TextView lvCoinCountTXT = (TextView) view.findViewById(R.id.lvCoinCountTXT);
        TextView lvNplTXT = (TextView) view.findViewById(R.id.lvNplTXT);
        TextView lvNplPriceTXT = (TextView) view.findViewById(R.id.lvNplPriceTXT);
        Button lvDeleteBTN = (Button) view.findViewById(R.id.lvDeleteBTN);

        final Coin coin = coinList.get(position);

        lvDeleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.DeleteCoin(coin.getId());
                ((MainActivity)context).RefreshLV();
                adapterClass.notifyDataSetChanged();
            }
        });

        lvCoinNameTXT.setText("Coin Adı : " + coin.coinName);
        lvBuyPriceTXT.setText("Alış Fiyatı : " + coin.buyPrice);
        lvCoinCountTXT.setText("Adedi : " + coin.coinCount);

        SetAsyncVariables(coin, lvCurrentPriceTXT, lvNplTXT, lvNplPriceTXT);

        return view;
    }

    public void  SetAsyncVariables(Coin coin, TextView lvCurrentPriceTXT, TextView lvNplTXT, TextView lvNplPriceTXT){
        Runnable r = () -> {
            Looper.prepare();

                double myPrice = Double.parseDouble(coin.buyPrice);
                Double coinCount = Double.parseDouble(coin.coinCount);

                while(true){

                    try {
                        double currentPrice = Double.parseDouble(DeleteZeros(BinanceHelper.GetPrice(coin.coinName)));
                        double nplRatio = ((currentPrice - myPrice) / currentPrice) * 100;
                        double nplPrice = coinCount * (currentPrice - myPrice);

                        if(nplRatio < 0){lvNplTXT.setTextColor(Color.RED); lvNplPriceTXT.setTextColor(Color.RED);}
                        else{lvNplTXT.setTextColor(Color.GREEN); lvNplPriceTXT.setTextColor(Color.GREEN);}

                        lvCurrentPriceTXT.setText("Anlık Fiyat : " + currentPrice);
                        lvNplTXT.setText(String.valueOf(nplRatio));
                        lvNplPriceTXT.setText(String.valueOf(nplPrice));

                    } catch (BinanceApiException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }



        };
        Thread t = new Thread(r);

        t.start();
    }

    public String DeleteZeros(String number){
        String tempNumber = number;

        for(int i = tempNumber.length() -1 ; i >= 0; i--){
            if(number.charAt(i) == '.'){break;}
            else{
                if(number.charAt(i) != 0 ){number = number.substring(0, i); break;}
            }
        }

        return number;
    }
}
