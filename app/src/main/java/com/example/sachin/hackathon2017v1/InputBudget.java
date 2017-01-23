package com.example.sachin.hackathon2017v1;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class InputBudget extends AppCompatActivity {

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

    Calendar c = Calendar.getInstance();
    String date = df.format(c.getTime());

    DBAdapter myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_budget);
        openDB();
        getAmount();
    }


    public void getAmount() {

        final EditText budgetInput = (EditText) findViewById(R.id.budgetInput);
        budgetInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                boolean handled = false;
                if(i == EditorInfo.IME_ACTION_DONE){

                    if (!doesItExist(Integer.parseInt(date))){
                        addRecord(Integer.parseInt(date),0,0,0,0,0,Integer.parseInt(budgetInput.getText().toString()));
                    }
                    else {
                        updateRecord(Integer.parseInt(date),0,0,0,0,0,Integer.parseInt(budgetInput.getText().toString()));
                    }
                    Log.i("",displayRecordSet(myDb.getAllRows()));

                    // int  date1 = cursor.getInt(DBAdapter.COL_DATE) + date;
                    //int budget = cursor.getInt(DBAdapter.COL_OTHER);

                    //Toast.makeText(InputBudget.this,budget,Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(InputBudget.this, MainActivity.class));

                }
                return handled;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }

    public void addRecord(int date, int food, int gas, int grocereies, int pItem, int nonE, int other) {

        myDb.insertRow(date, food, gas, grocereies, pItem, nonE, other);

        // Cursor cursor = myDb.getRow(date);
        // displayRecordSet(cursor);
    }


    private String getMonth(Cursor cursor) {
        String month = cursor.getString(DBAdapter.COL_DATE);
        String returnValue = null;
        String[] parts = month.split("-");

        returnValue = parts[0] + parts[1];
        return returnValue;
    }

    private String getYear(Cursor cursor) {
        String month = cursor.getString(DBAdapter.COL_DATE);
        String returnValue = null;
        String[] parts = month.split("-");

        returnValue = parts[4] + parts[5];
        return returnValue;
    }


    // KEY_DATE, KEY_FOOD, KEY_GAS, KEY_GROCERIES, KEY_PITEMS, KEY_NE, KEY_OTHER
    // Display an entire recordset to the screen.
    private String displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                String date = cursor.getString(DBAdapter.COL_DATE);
                int food = cursor.getInt(DBAdapter.COL_FOOD);
                int gas = cursor.getInt(DBAdapter.COL_GAS);
                int groceries = cursor.getInt(DBAdapter.COL_GROCEREIES);
                int pItems = cursor.getInt(DBAdapter.COL_PITEMS);
                int nonE = cursor.getInt(DBAdapter.COL_NE);
                int other = cursor.getInt(DBAdapter.COL_OTHER);
                Log.i(myDb.TAG, "Entered");

                // Append data to the message:
                message += "Date= " + date
                        + ", food= " + food
                        + ", gas= " + gas
                        + ", groceries= " + groceries
                        + ", personal Items= " + pItems
                        + ", Non Essentials= " + nonE
                        + ", other= " + other

                        + "\n";
            } while (cursor.moveToNext());

        }

        // Close the cursor to avoid a resource leak.
        cursor.close();
        return message;
    }

    //Enter date you want to update and the value you want to update
//Enter 0 if you dont want to update that column
    public void updateRecord(int date, int food, int gas, int grocereies, int pItem, int nonE, int other) {
        Cursor cursor = myDb.getRow(date);

        // int  date1 = cursor.getInt(DBAdapter.COL_DATE) + date;
        int food1 = cursor.getInt(DBAdapter.COL_FOOD) + food;
        int gas1 = cursor.getInt(DBAdapter.COL_GAS) + gas;
        int grocereies1 = cursor.getInt(DBAdapter.COL_GROCEREIES) + grocereies;
        int pItem1 = cursor.getInt(DBAdapter.COL_PITEMS) + pItem;
        int nonE1 = cursor.getInt(DBAdapter.COL_NE) + nonE;
        int other1 = cursor.getInt(DBAdapter.COL_OTHER) + other;

        if (myDb.updateRow(date, food1, gas1, grocereies1, pItem1, nonE1, other1)) {
            Log.i("Trying to Update", "True");
        } else Log.i("Trying to Update", "False");


    }

    public boolean doesItExist(int date) {
        try {
            Log.i("doesItExist: ", "Checking DB");
            Cursor cursor = myDb.getAllRows();
            if (cursor.moveToFirst()) {
                Log.i("no ", "dontexist");
                return true;
            } else {
                return false;
            }
        } catch (CursorIndexOutOfBoundsException e) {
            addRecord(date, 0, 0, 0, 0, 0, 0);
            return true;
        }
    }
}
