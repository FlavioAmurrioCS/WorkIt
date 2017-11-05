package com.flavio.workit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView eListView;
    ArrayAdapter<String> mAdapter;
    ArrayList<String> strList;
    public static MyDBHelper myDBHelper = null;
    AlertDialog actions;
    String selectedName = "";

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

        strList = WorkOut.getDBList(myDBHelper, WorkOut.TABLE_NAME);
        eListView = findViewById(R.id.eListView);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strList);
        eListView.setAdapter(mAdapter);
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
                selectedName = strList.get(i);
                actions.show();
                return true;
            }
        });
        DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Delete
                        WorkOut wk = new WorkOut(selectedName, myDBHelper);
                        myDBHelper.db.delete(WorkOut.TABLE_NAME, wk.getWhereClause(), null);
                        updateListView();
                        break;
                    default:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this item?");
        String[] options = {"Delete"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();
    }

    public void addBtn(View view) {

        Intent addIntent = new Intent(this, EditActivity.class);
        addIntent.putExtra("Edit", false);
        startActivityForResult(addIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateListView();
    }

    public void updateListView() {
        strList.clear();
        strList.addAll(WorkOut.getDBList(myDBHelper, WorkOut.TABLE_NAME));
        mAdapter.notifyDataSetChanged();
    }
}