package com.example.android.creditmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Credit.db";
    public static final String TABLE_NAME_1 = "User";
    public static final String TABLE_NAME_2 = "Transfers";
    public static final String USER_COL_1 = "NAME";
    public static final String USER_COL_2 = "EMAIL";
    public static final String USER_COL_3 = "CURRENTCREDIT";
    public static final String TRANSFERS_COL_1 = "SENDER";
    public static final String TRANSFERS_COL_2 = "RECEIVER";
    public static final String TRANSFERS_COL_3 = "CREDITS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_1 + "(NAME TEXT , EMAIL TEXT , CURRENTCREDIT INTEGER)");
        db.execSQL("create table " + TABLE_NAME_2 + "(SENDER TEXT , RECEIVER TEXT , CREDITS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    public void insertData(String name, String email, int currentCredit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_1, name);
        contentValues.put(USER_COL_2, email);
        contentValues.put(USER_COL_3, currentCredit);
        db.insert(TABLE_NAME_1, null, contentValues);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_1, null);
        return res;
    }

    public void insertTransferData(String sender, String receiver, int credits) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANSFERS_COL_1, sender);
        contentValues.put(TRANSFERS_COL_2, receiver);
        contentValues.put(TRANSFERS_COL_3, credits);
        db.insert(TABLE_NAME_2, null, contentValues);
    }

    public void updateData(String name, int credits) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_3, credits);
        db.update(TABLE_NAME_1, contentValues, "name = ?", new String[]{name});
    }

    public boolean isEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_NAME_1;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int iCount = cursor.getInt(0);
        if (iCount > 0)
            return false;
        else
            return true;
    }
}
