package com.example.sachin.hackathon2017v1;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.Integer.parseInt;

public class DataEntry extends AppCompatActivity implements OnItemSelectedListener{

    private final int tempDate = 0;

    DBAdapter myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String TAG = "DataEntry";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        openDB();

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        Calendar c = Calendar.getInstance();
        String date = df.format(c.getTime());

        //Log.i("",displayRecordSet(myDb.getAllRows()));
       // addRecord(-1, 0, 0, 0, 0, 0, 0);


        if (!doesItExist(Integer.parseInt(date))){
            addRecord(Integer.parseInt(date),0,0,0,0,0,0);
        }





        //Log.i("",displayRecordSet(myDb.getAllRows()));



        getAmount();

        Spinner typeSelection = (Spinner) findViewById(R.id.typeSelection);
        typeSelection.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Food");
        categories.add("Gas");
        categories.add("Groceries");
        categories.add("Personal Items");
        categories.add("Other");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        typeSelection.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public String getTypeSelection(){
        Spinner typeSelection = (Spinner)findViewById(R.id.typeSelection);
       return typeSelection.getSelectedItem().toString();
    }

    public void getAmount() {

        final EditText amountInput = (EditText) findViewById(R.id.inputfield);
        amountInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent event) {
                boolean handled = false;
                if(i == EditorInfo.IME_ACTION_DONE){
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

                    Calendar c = Calendar.getInstance();
                    String date = df.format(c.getTime());

                    String type = getTypeSelection();
                    String tempAmount = amountInput.getText().toString();
                    int amount = Integer.parseInt(tempAmount);



                    if(!doesItExist(Integer.parseInt(date)) ) {
                        Log.i("Adding","        Adding...            ");
                        /*
                        if (type.equals("Food")) {
                            addRecord(parseInt(date), amount, 0, 0, 0, 0, 0);
                        } else if (type.equals("Gas")) {
                            addRecord(parseInt(date), 0, amount, 0, 0, 0, 0);
                        } else if (type.equals("Groceries")) {
                            addRecord(parseInt(date), 0, 0, amount, 0, 0, 0);
                        } else if (type.equals("Personal Item")) {
                            addRecord(parseInt(date), 0, 0, 0, amount, 0, 0);
                        } else if (type.equals("NonEssential")) {
                            addRecord(parseInt(date), 0, 0, 0, 0, amount, 0);
                        } else if (type.equals("Other")) {
                            addRecord(parseInt(date), 0, 0, 0, 0, 0, amount);
                        } */
                    }
                    if(doesItExist(Integer.parseInt(date))) {
                        Log.i("Update","Updating...");


                        Cursor cursor = myDb.getRow(Integer.parseInt(date));

                        if (type.equals("Food")) {
                            updateRecord(parseInt(date), amount, 0, 0, 0, 0, 0);
                        } else if (type.equals("Gas")) {
                            updateRecord(parseInt(date), 0, amount, 0, 0, 0, 0);
                        } else if (type.equals("Groceries")) {
                            updateRecord(parseInt(date), 0, 0, amount, 0, 0, 0);
                        } else if (type.equals("Personal Items")) {
                            updateRecord(parseInt(date), 0, 0, 0, amount, 0, 0);
                        } else if (type.equals("Other")) {
                            updateRecord(parseInt(date), 0, 0, 0, 0, amount, 0);
                        }

                    }
                    Log.i("",displayRecordSet(myDb.getAllRows()));
                    Log.i("","End of DB");

                    Toast.makeText(DataEntry.this,tempAmount,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DataEntry.this, MainActivity.class));

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

    public void addRecord(int date, int food, int gas, int grocereies, int pItem, int other, int budget) {

        myDb.insertRow(date, food, gas, grocereies, pItem, other, budget);

       // Cursor cursor = myDb.getRow(date);
       // displayRecordSet(cursor);
    }


    private String getMonth(Cursor cursor)
    {
        String month = cursor.getString(DBAdapter.COL_DATE);
        String returnValue = null;
        String[] parts = month.split("-");

        returnValue = parts[0] + parts[1];
        return returnValue;
    }

    private String getYear(Cursor cursor)
    {
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
                int budget = cursor.getInt(DBAdapter.COL_OTHER);
                Log.i(myDb.TAG,"Entered");

                // Append data to the message:
                message += "Date= " + date
                        +", food= " + food
                        +", gas= " + gas
                        +", groceries= " + groceries
                        +", personal Items= " + pItems
                        +", Other= " + nonE
                        +", Budget= " + budget

                        +"\n";
            } while(cursor.moveToNext());

        }

        // Close the cursor to avoid a resource leak.
        cursor.close();
        return message;
    }
    //Enter date you want to update and the value you want to update
//Enter 0 if you dont want to update that column
    public void updateRecord(int date, int food, int gas, int grocereies, int pItem, int other, int budget)
    {
        Cursor cursor = myDb.getRow(date);

       // int  date1 = cursor.getInt(DBAdapter.COL_DATE) + date;
        int food1 = cursor.getInt(DBAdapter.COL_FOOD) + food;
        int gas1 = cursor.getInt(DBAdapter.COL_GAS) + gas;
        int grocereies1 = cursor.getInt(DBAdapter.COL_GROCEREIES) + grocereies;
        int pItem1 = cursor.getInt(DBAdapter.COL_PITEMS) + pItem;
        int other1 = cursor.getInt(DBAdapter.COL_NE) + other;
        int budget1 = cursor.getInt(DBAdapter.COL_OTHER) + budget;

        if (myDb.updateRow(date, food1,gas1,grocereies1,pItem1,other1,budget1)){
            Log.i("Trying to Update","True");
        }
        else Log.i("Trying to Update","False");



    }
    public boolean doesItExist(int date)
    {
        try {
            Log.i("doesItExist: ", "Checking DB");
            Cursor cursor = myDb.getAllRows();
            if ( cursor.moveToFirst()) {
                Log.i("no ", "dontexist");
                return true;
            } else {
                return false;
            }
        }
        catch (CursorIndexOutOfBoundsException e){
            addRecord(date,0,0,0,0,0,0);
            return true;
        }
    }
}
