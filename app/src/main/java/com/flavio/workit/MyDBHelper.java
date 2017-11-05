package com.flavio.workit;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Flavio on 11/4/2017.
 */

public class MyDBHelper {
    SQLiteDatabase db;

    public MyDBHelper(Context context, String dbName) {
        db = context.openOrCreateDatabase("MyDB", context.MODE_PRIVATE, null);
    }

    public void openOrCreate(String tableName, String columnDeclaration) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnDeclaration + ");");
    }

    public boolean tableContains(String tableName, String whereClause) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + whereClause + ";", null);
        return cursor.getCount() > 0;
    }

    public void insertToTable(String tableName, String insertColumn, String insertValues) {
        db.execSQL("INSERT INTO " + tableName + " (" + insertColumn + ") VALUES (" + insertValues + ");");
    }

    public void updateTable(String tableName, String setClause, String whereClause) {
        db.execSQL("UPDATE " + tableName + " SET " + setClause + " WHERE " + whereClause + ";");
    }

    public Cursor getItemList(String tableName, String whereClause) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + whereClause + ";", null);
        return cursor;
    }

    public Cursor getAll(String tableName) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + ";", null);
        return cursor;
    }

    public boolean isEmpty(String tableName) {
        return getAll(tableName).getCount() == 0;
    }
}
