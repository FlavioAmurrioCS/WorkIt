package com.flavio.workit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView eListView;
    ArrayAdapter<String> mAdapter;
    ArrayList<String> strList;
    public static MyDBHelper myDBHelper = null;
//
//    public static SQLiteDatabase wDataBase = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDBHelper = new MyDBHelper(this, "WorkItDB");
        myDBHelper.openOrCreate(WorkOut.TABLE_NAME, WorkOut.COLUMN_DECLARATION);
        if (myDBHelper.isEmpty(WorkOut.TABLE_NAME)) {
            WorkOut wk = new WorkOut("Curl", 20, 20, 4, "Right Angle");
            wk.insertToMyDB(myDBHelper);
        }
//        createDB();

        strList = WorkOut.getDBList(myDBHelper, WorkOut.TABLE_NAME);
        eListView = findViewById(R.id.eListView);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strList);
        eListView.setAdapter(mAdapter);
//        new WorkOut("Curl", 20, 20, 4, "Form").insertToDB(wDataBase);
        updateListView();

        eListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Edit View
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("Edit", true);
                intent.putExtra("name", strList.get(i));
                startActivityForResult(intent, 1);
            }
        });

        eListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });
    }

    public void addBtn(View view) {

        Intent addIntent = new Intent(this, EditActivity.class);
        addIntent.putExtra("Edit", false);
        startActivityForResult(addIntent, 1);
//        startActivity(addIntent);
//        Toast.makeText(this, "Msg", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateListView();
        //Do Something
    }

    public void updateListView() {
        strList.clear();
        strList.addAll(WorkOut.getDBList(myDBHelper, WorkOut.TABLE_NAME));
        mAdapter.notifyDataSetChanged();
    }
}

/*

//    @Override
//    protected void onResume() {
//        super.onResume();
//        updateListView(wDataBase);
//    }

    public void createDB() {
        wDataBase = this.openOrCreateDatabase("MyWorkout", MODE_PRIVATE, null);
        wDataBase.execSQL("CREATE TABLE IF NOT EXISTS workout " +
                "(id integer primary key, name varchar, weight integer, reps integer, sets integer, notes varchar);");
        File db = getApplicationContext().getDatabasePath("MyWorkout.db");
        if (!db.exists()) {
            Toast.makeText(this, "Database Created", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Database Missing", Toast.LENGTH_SHORT).show();
        }
    }


 */
