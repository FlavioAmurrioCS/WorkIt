package com.flavio.workit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    boolean edit = true;
    TextView tittle;
    Button editBtn;
    EditText name, weight, sets, reps, notes;
    LinearLayout nameLayout, editLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        tittle = findViewById(R.id.tittle);
        nameLayout = findViewById(R.id.nameLayout);
        editLayout = findViewById(R.id.editLayout);

        name = findViewById(R.id.name);
        weight = findViewById(R.id.weight);
        sets = findViewById(R.id.sets);
        reps = findViewById(R.id.reps);
        notes = findViewById(R.id.notes);

        Bundle bundle = getIntent().getExtras();
        edit = bundle.getBoolean("Edit");
        editBtn = findViewById(R.id.editBtn);

        if (edit) {
            //Updating
            nameLayout.setVisibility(View.INVISIBLE);
            editLayout.removeView(nameLayout);
            editBtn.setText("Update These Values");

            String mName = bundle.getString("name");

            WorkOut wk = new WorkOut(mName, MainActivity.myDBHelper);

            tittle.setText(mName);
            weight.setText("" + wk.weight);
            sets.setText("" + wk.sets);
            reps.setText("" + wk.reps);
            notes.setText("" + wk.notes);
        } else {
            //Adding
            tittle.setText("Add a New Exercise");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public int eToInt(EditText e) {
        return Integer.parseInt(e.getText().toString());
    }

    public void editAddBtn(View view) {
        String mName = name.getText().toString();
        int mWeight = eToInt(weight);
        int mReps = eToInt(reps);
        int mSets = eToInt(sets);
        String mNotes = notes.getText().toString();
        WorkOut wk = new WorkOut(mName, mWeight, mReps, mSets, mNotes);
        if (edit) {
            wk.name = tittle.getText().toString();
            wk.updateMyDB(MainActivity.myDBHelper);
            //Update Values
        } else {
            if (MainActivity.myDBHelper.tableContains(WorkOut.TABLE_NAME, wk.getWhereClause())) {
                Toast.makeText(this, "Duplicate Name Error", Toast.LENGTH_SHORT).show();
                return;
            }
            if (wk.name.equals("")) {
                Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                return;
            }
            wk.insertToMyDB(MainActivity.myDBHelper);
        }
        setResult(RESULT_OK);
        finish();
    }
}
