package com.example.empireclickers;

import androidx.annotation.Nullable;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigInteger;


public class DatabaseConfig extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "empclickers.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedContract.FeedEntry.TABLE_NAME + " (" +
                    FeedContract.FeedEntry._ID + " TEXT PRIMARY KEY," +
                    FeedContract.FeedEntry.COLUMN_NAME_TITLE + " STRING)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedContract.FeedEntry.TABLE_NAME;

    public DatabaseConfig(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    void updateCredit(String userId, BigInteger credit) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedContract.FeedEntry._ID, userId);
        values.put(FeedContract.FeedEntry.COLUMN_NAME_TITLE, credit.toString());
        // Insert the new row, returning the primary key value of the new row
        long result = db.insertWithOnConflict(FeedContract.FeedEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1) {
           System.out.println("Failed to Insert Credit");
        }
    }

    Cursor getCredit(String userId) {
        String query = "SELECT " + FeedContract.FeedEntry.COLUMN_NAME_TITLE + " FROM " +
                FeedContract.FeedEntry.TABLE_NAME + " WHERE " + FeedContract.FeedEntry._ID + " = '"
                + userId + "'";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
