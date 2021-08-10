package com.tt.cryptoanalyze;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SectionIndexer;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.Collections;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "coinsDB.db";

    private static final String TABLE_NAME = "Coins";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_BUY_PRICE = "buy_price";
    private static final String COLUMN_COIN_COUNT = "coin_count";

    private static final String SQL_CREATE_TABLE_COIN =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT ," +
                    COLUMN_BUY_PRICE + " TEXT ," +
                    COLUMN_COIN_COUNT + " TEXT)"
            ;


    private static final String SQL_DELETE_TABLE_SECTION =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_COIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_SECTION);
        onCreate(db);
    }

    public long AddCoin(Coin coin){

        SQLiteDatabase db = this.getWritableDatabase();

        try{
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_NAME, coin.getCoinName());
            cv.put(COLUMN_BUY_PRICE, coin.getBuyPrice());
            cv.put(COLUMN_COIN_COUNT, coin.getCoinCount());
            return db.insert(TABLE_NAME,null,cv);
        }
        catch (Exception ex){
            return -1;
        }
        finally {
            db.close();
        }
    }

    public boolean DeleteCoin(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            db.execSQL("Delete from " + TABLE_NAME + " where id = " + id);
            return true;
        }
        catch (Exception ex){
            return false;
        }
        finally {
            db.close();
        }
    }

    public ArrayList<Coin> GetAllCoin(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try{
            if(db==null){
                throw new Exception("Database is not found");
            }

            String query = "Select * From " + TABLE_NAME ;
            cursor = db.rawQuery(query,null);

            ArrayList<Coin> coins = new ArrayList<>();
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String coinName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String buyPrice = cursor.getString(cursor.getColumnIndex(COLUMN_BUY_PRICE));
                String coinCount = cursor.getString(cursor.getColumnIndex(COLUMN_COIN_COUNT));

                coins.add(new Coin(id,coinName,buyPrice,coinCount));
            }
            Collections.reverse(coins);
            return coins;
        }
        catch (Exception ex){
            return null;
        }
        finally {
            db.close();
        }
    }
}
