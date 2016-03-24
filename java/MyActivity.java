package com.shikhar.attendancemanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MyActivity extends AppCompatActivity {

    private ArrayList<SubjPair> al = new ArrayList<SubjPair>();
    private View mview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        float scale = this.getResources().getDisplayMetrics().density;
        int pixels = (int) (2 * scale + 0.5f);
        mview = new View(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, pixels);
        mview.setLayoutParams(lp);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        mview.setBackgroundColor(this.getResources().getColor(R.color.blackk, null));
        else mview.setBackgroundColor(this.getResources().getColor(R.color.blackk));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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

    public void editTT(View view) {
        Intent intent = new Intent(this, EditTT.class);
        startActivity(intent);
    }

    public void editsubj(View view){
        Intent intent = new Intent(this, MngSubj.class);
        startActivity(intent);
    }
}
