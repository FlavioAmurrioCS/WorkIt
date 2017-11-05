package com.flavio.workit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Flavio on 11/4/2017.
 */

public class WorkOut {

    public static String COLUMN_DECLARATION = "id integer primary key, name varchar, weight varchar, reps varchar, sets varchar, notes varchar";
    public static String INSERT_COLUMN = "name, weight, reps, sets, notes";
    public static String TABLE_NAME = "workout";

    String name;
    int weight;
    int reps;
    int sets;
    String notes;

    public WorkOut(String name, int weight, int reps, int sets, String notes) {
        this.name = name;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
        this.notes = notes;
    }

    public WorkOut(String name, MyDBHelper mdb) {
        this.name = name;
        Cursor cursor = mdb.getItemList(TABLE_NAME, this.getWhereClause());
        cursor.moveToFirst();
        int weightColumn = cursor.getColumnIndex("weight");
        int repsColumn = cursor.getColumnIndex("reps");
        int setsColumn = cursor.getColumnIndex("sets");
        int notesColumn = cursor.getColumnIndex("notes");
        this.weight = cursor.getInt(weightColumn);
        this.reps = cursor.getInt(repsColumn);
        this.sets = cursor.getInt(setsColumn);
        this.notes = cursor.getString(notesColumn);
    }

    public String toString() {
        return name;
    }

    public String getWhereClause() {
        return "name='" + this.name + "'";
    }

    public String getInsertValue() {
        return "'" + this.name + "', " +
                "'" + this.weight + "', " +
                "'" + this.reps + "', " +
                "'" + this.sets + "', " +
                "'" + this.notes + "'";
    }

    public String getSetClause() {
        return "weight='" + this.weight + "', " +
                "reps='" + this.reps + "', " +
                "sets='" + this.sets + "', " +
                "notes='" + this.notes + "'";
    }

    public void insertToMyDB(MyDBHelper mdb) {
        mdb.insertToTable(TABLE_NAME, INSERT_COLUMN, getInsertValue());
    }

    public void updateMyDB(MyDBHelper mdb) {
        mdb.updateTable(TABLE_NAME, getSetClause(), getWhereClause());
    }

    public static ArrayList<String> getDBList(MyDBHelper mdb, String tableName){
        ArrayList<String> wList = new ArrayList<>();
        Cursor cursor = mdb.getAll(tableName);
        int nameID = cursor.getColumnIndex("name");
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            wList.add(cursor.getString(nameID));
        }
        return wList;
    }
}

/*

    public WorkOut(String name, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM workout WHERE name = " + name + ";", null);
        int nameColumn = cursor.getColumnIndex("name");
        int weightColumn = cursor.getColumnIndex("weight");
        int repsColumn = cursor.getColumnIndex("reps");
        int setsColumn = cursor.getColumnIndex("sets");
        int notesColumn = cursor.getColumnIndex("notes");
        cursor.moveToFirst();
        this.name = cursor.getString(nameColumn);
        this.weight = cursor.getInt(weightColumn);
        this.reps = cursor.getInt(repsColumn);
        this.sets = cursor.getInt(setsColumn);
        this.notes = cursor.getString(notesColumn);
    }

public void insertToDB(SQLiteDatabase db) {
        if (!dbContains(this.name))
            db.execSQL("INSERT INTO workout (name, weight, reps, sets, notes) VALUES ('" + name + "', '" + weight + "', '" + reps + "', '" + sets + "', '" + notes + "');");
    }

    public void updateDB(SQLiteDatabase db) {
        db.execSQL("UPDATE workout SET weight = '"
                + weight + "', reps = '"
                + reps + "', sets = '"
                + sets + "', notes = '"
                + notes + "' WHERE name = " + name + ";");
    }

    public boolean dbContains(String name) {
        Cursor cursor = MainActivity.wDataBase.rawQuery("SELECT * FROM workout WHERE name = " + name + ";", null);
        return cursor.getCount() > 0;
    }


 */
