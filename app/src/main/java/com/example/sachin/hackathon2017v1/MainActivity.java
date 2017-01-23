package com.example.sachin.hackathon2017v1;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.app.Activity;
import android.database.Cursor;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {

    PieChart pieChart;
    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    PieDataSet pieDataSet;
    PieData pieData;

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    Calendar c = Calendar.getInstance();
    String date = df.format(c.getTime());

    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDB();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button newEntry = (Button) findViewById(R.id.dataEntry);
        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity( new Intent(MainActivity.this,dataEntry.class));
                dataEntryActivity(v);
            }

        });

        TextView budgetText = (TextView) findViewById(R.id.budget);
        budgetText.setText("Budget\n" + getTodayTotal(Integer.parseInt(date)));

        pieChart = (PieChart) findViewById(R.id.chart1);
        entries = new ArrayList<>();
        PieEntryLabels = new ArrayList<String>();

        AddValuesToPIEENTRY(Integer.parseInt(date));
        AddValuesToPieEntryLabels();

        pieDataSet = new PieDataSet(entries, "");
        pieData = new PieData(PieEntryLabels, pieDataSet);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieChart.setData(pieData);
        pieChart.animateY(3000);
    }

    public void dataEntryActivity(View view) {
        Intent intent = new Intent(this, DataEntry.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(this, InputBudget.class));
        } else if (id == R.id.nav_gallery) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void AddValuesToPIEENTRY(int date) {

        try {
            Cursor cursor = myDb.getRow(date);

            int food = cursor.getInt(DBAdapter.COL_FOOD);
            int gas = cursor.getInt(DBAdapter.COL_GAS);
            int groceries = cursor.getInt(DBAdapter.COL_GROCEREIES);
            int pItems = cursor.getInt(DBAdapter.COL_PITEMS);
            int other = cursor.getInt(DBAdapter.COL_NE);

            entries.add(new BarEntry(food, 0));
            entries.add(new BarEntry(gas, 1));
            entries.add(new BarEntry(groceries, 2));
            entries.add(new BarEntry(pItems, 3));
            entries.add(new BarEntry(other, 4));
            entries.add(new BarEntry(0,5));
        }
        catch (CursorIndexOutOfBoundsException e){
            entries.add(new BarEntry(0, 0));
            entries.add(new BarEntry(0, 1));
            entries.add(new BarEntry(0, 2));
            entries.add(new BarEntry(0, 3));
            entries.add(new BarEntry(0, 4));
            entries.add(new BarEntry(0,5));
        }
    }

    public void AddValuesToPieEntryLabels() {

        PieEntryLabels.add("Food");
        PieEntryLabels.add("Gas");
        PieEntryLabels.add("Groceries");
        PieEntryLabels.add("Personal Items");
        PieEntryLabels.add("Other");
        PieEntryLabels.add("");

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


    public int[] getDailyData(int date)
    {
        int data[];

        try {

            Cursor cursor = myDb.getRow(date);


            int food = cursor.getInt(DBAdapter.COL_FOOD);
            int gas = cursor.getInt(DBAdapter.COL_GAS);
            int groceries = cursor.getInt(DBAdapter.COL_GROCEREIES);
            int pItems = cursor.getInt(DBAdapter.COL_PITEMS);
            int nonE = cursor.getInt(DBAdapter.COL_NE);
            int other = cursor.getInt(DBAdapter.COL_OTHER);

            return (data = new int[]{food, gas, groceries, pItems, nonE, other});
        }
        catch (CursorIndexOutOfBoundsException e){
            addRecord(date,0,0,0,0,0,0);
            return (data = new int[]{0,0,0,0,0,0});
        }

    }

    private String getTodayTotal(int date)
    {
        int[] dailyArray = getDailyData(date);
        int total = 0;
        for (int i = 0; i < (dailyArray.length - 2); i++)
        {
            total += dailyArray[i];
        }
        return "" + (dailyArray[dailyArray.length-1] - total);
    }

}
