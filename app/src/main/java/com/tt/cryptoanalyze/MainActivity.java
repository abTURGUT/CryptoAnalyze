package com.tt.cryptoanalyze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.api.BinanceApiException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText coinNameTXT, buyPriceTXT, coinCountTXT;
    Button addCoinBTN;
    ListView coinLV;

    DBHelper db;
    CoinAdapter coinAdapter;
    ArrayList<Coin> coinList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coinNameTXT = findViewById(R.id.coinNameTXT);
        buyPriceTXT = findViewById(R.id.buyPriceTXT);
        coinCountTXT = findViewById(R.id.coinCountTXT);
        addCoinBTN = findViewById(R.id.addCoinBTN);
        coinLV = findViewById(R.id.coinLV);

        db = new DBHelper(getApplicationContext());

        addCoinBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coinName = coinNameTXT.getText().toString().toUpperCase();
                String buyPrice = buyPriceTXT.getText().toString();
                String coinCount = coinCountTXT.getText().toString();

                AddCoin(-1, coinName, buyPrice, coinCount);

            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        RefreshLV();

    }

    public void AddCoin(int id, String coinName, String buyPrice, String coinCount){
        db.AddCoin(new Coin(id, coinName,buyPrice,coinCount));
        RefreshLV();
    }

    public void GetMyCoins(){
        coinList = db.GetAllCoin();
    }

    public void UpdateLV(){
        GetMyCoins();
        coinAdapter.notifyDataSetChanged();
    }

    public void RefreshLV(){
        GetMyCoins();

        coinAdapter = new CoinAdapter(this,R.layout.listview_coin, coinList, db);
        coinLV.setAdapter(coinAdapter);
    }
}

