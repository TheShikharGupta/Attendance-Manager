package com.shikhar.attendancemanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;

public class PickTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String timetoset = intent.getStringExtra(SetTT.PTDATA);
        String h = "", m = "";
        int i, hi, mi;
        for(i = 0; timetoset.charAt(i) != ':'; ++i){
            h += timetoset.charAt(i);
        }
        hi = Integer.parseInt(h);
        for(i += 1; timetoset.charAt(i) != ' '; ++i){
            m += timetoset.charAt(i);
        }
        mi = Integer.parseInt(m);
        if(timetoset.charAt(i + 1) == 'p')hi += 12;
        TimePicker tp = (TimePicker)findViewById(R.id.tptp);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            tp.setHour(hi);
            tp.setMinute(mi);
        }
        else{
            tp.setCurrentHour(hi);
            tp.setCurrentMinute(mi);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tickab, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            TimePicker tp = (TimePicker)findViewById(R.id.tptp);
            int h, m;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                h = tp.getHour();
                m = tp.getMinute();
            }
            else{
                h = tp.getCurrentHour();
                m = tp.getCurrentMinute();
            }
            String ntime, ampm;
            if(h > 11){
                ampm = " pm";
                if(h > 12)h -= 12;
            }
            else ampm = " am";
            ntime = Integer.toString(h);
            if(ntime.length() == 1){
                ntime = "0" + ntime;
            }
            ntime += ":";
            String ntime2 = Integer.toString(m);
            if(ntime2.length() == 1){
                ntime2 = "0" + ntime2;
            }
            ntime2 += ampm;
            ntime += ntime2;
            Intent intent2 = new Intent();
            intent2.putExtra("NEWTIME", ntime);
            setResult(RESULT_OK, intent2);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
