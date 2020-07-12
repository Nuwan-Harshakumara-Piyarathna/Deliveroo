package com.nofirst.deliveroo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private static final String TAG = "DeliverooDataSource";
    private SQLiteDatabase sqLiteDatabase;
    private SQLiteOpenHelper sqLiteOpenHelper;
    private Context context;
    private String DataBaseOpenedMessage = "Database opened.";
    private String DataBaseClosedMessage = "Database closed.";

    private String orderDetailsFoodIdKey ="foodId";
    private String orderDetailsFoodNameKey ="foodName";
    private String orderDetailsPriceKey ="price";
    private String orderDetailsQuantityKey ="quantity";

    private String OrderDetailsTable = "Cart";

    public DataSource(Context context) {
        this.context = context;
        sqLiteOpenHelper = new DatabaseHelper(context);
    }

    public void open() {
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        Log.d(TAG, DataBaseOpenedMessage);
    }

    public void close() {
        sqLiteOpenHelper.close();
        Log.d(TAG, DataBaseClosedMessage);
    }

    //--------------------------InsertQuery-----------------------------
    public void insertDataOrderDetails(String food_id, String food_name, double food_price, int quantity) {
        ContentValues newValues = new ContentValues();
        newValues.put(orderDetailsFoodIdKey, food_id);
        newValues.put(orderDetailsFoodNameKey, food_name);
        newValues.put(orderDetailsPriceKey, food_price);
        newValues.put(orderDetailsQuantityKey, quantity);
        sqLiteDatabase.insertOrThrow(OrderDetailsTable, null, newValues);
    }

    //------------------------RetrieveQuery----------------------------
    public String[] getDataOrderDetails(String index) {
        Cursor cursor = sqLiteDatabase.query(OrderDetailsTable, null, " _id=?",
                new String[]{index}, null, null, null);
        cursor.moveToFirst();
        String[] arr = new String[4];
        String food_id = cursor.getString(cursor.getColumnIndex(orderDetailsFoodIdKey));
        String food_name = cursor.getString(cursor.getColumnIndex(orderDetailsFoodNameKey));
        Double food_price = cursor.getDouble(cursor.getColumnIndex(orderDetailsPriceKey));
        int quantity = cursor.getInt(cursor.getColumnIndex(orderDetailsQuantityKey));

        arr[0] = food_id;
        arr[1] = food_name;
        arr[2] = String.valueOf(food_price);
        arr[3] = String.valueOf(quantity);

        cursor.close();
        return arr;
    }

    public List<String[]> getAllDataOrderDetails() {
        String selectQuery = "select * from "+OrderDetailsTable;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
        List<String[]> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String[] arr = new String[4];
                String food_id = cursor.getString(cursor.getColumnIndex(orderDetailsFoodIdKey));
                String food_name = cursor.getString(cursor.getColumnIndex(orderDetailsFoodNameKey));
                Double food_price = cursor.getDouble(cursor.getColumnIndex(orderDetailsPriceKey));
                int quantity = cursor.getInt(cursor.getColumnIndex(orderDetailsQuantityKey));

                arr[0] = food_id;
                arr[1] = food_name;
                arr[2] = String.valueOf(food_price);
                arr[3] = String.valueOf(quantity);

                list.add(arr);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return list;
    }

    //----------------------UpdateQuery-----------------------------

    public void updateFoodIdOrderDetails(String index, String foodId) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(orderDetailsFoodIdKey, foodId);
        String where = "_id = ?";
        sqLiteDatabase.update(OrderDetailsTable, updatedValues, where, new String[]{index});
    }

    public void updateFoodNameOrderDetails(String index, String foodName) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(orderDetailsFoodNameKey, foodName);
        String where = "_id = ?";
        sqLiteDatabase.update(OrderDetailsTable, updatedValues, where, new String[]{index});
    }

    public void updatePriceOrderDetails(String index, double price) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(orderDetailsPriceKey, price);
        String where = "_id = ?";
        sqLiteDatabase.update(OrderDetailsTable, updatedValues, where, new String[]{index});
    }

    public void updateQuantityOrderDetails(String index, double quantity) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(orderDetailsQuantityKey, quantity);
        String where = "_id = ?";
        sqLiteDatabase.update(OrderDetailsTable, updatedValues, where, new String[]{index});
    }

    //-------------------------DeleteQuery--------------------------
    public void deleteTableCart() {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS 'Cart'; ");
        sqLiteDatabase.execSQL(DatabaseHelper.DATABASE_DELIVEROO_CART_TABLE_CREATE);
    }




}
