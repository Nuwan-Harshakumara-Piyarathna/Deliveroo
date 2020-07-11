package com.nofirst.deliveroo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {


    //For an in-memory database that is not persisted to permanent storage, use null as the database file name.
    private static final String DATABASE_NAME = ":memory:";
    //otherwise if you want permenant storage use this
    //    private static final String DATABASE_NAME = "deliveroo";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DatabaseHelper";


    private static final String TABLE = "Cart";
    //create cart table


    public static final String DATABASE_DELIVEROO_CART_TABLE_CREATE = "create table " + TABLE + "( "
            + _ID + " integer primary key autoincrement,"
            + "foodId text,foodName text,price double,quantity int); ";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //this is called when the first time database is accessed.There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_DELIVEROO_CART_TABLE_CREATE);
    }

    //this is called if the database version number changed.It prevents previous users apps from breaking
    // when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS 'Cart'; ");
        Log.d(TAG, "DROP TABLE IF EXISTS 'Cart'; ");
        onCreate(sqLiteDatabase);
    }

}
