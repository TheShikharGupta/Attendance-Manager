package com.shikhar.attendancemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SetTT extends AppCompatActivity {

    public final static String PTDATA  = "com.shikhar.attendancemanager.PTDATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("settt", "onCreate Execution has begun for setTT");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String ftime = intent.getStringExtra(EditTT.TDATA);
        String stime = "";
        String etime = "";
        int i;
        for(i = 0; ftime.charAt(i) != ','; ++i){
            stime += ftime.charAt(i);
        }
        for(i += 1; ftime.charAt(i) != ','; ++i){
            etime += ftime.charAt(i);
        }
        String subj = "";
        for(i += 1; i < ftime.length(); ++i){
            subj += ftime.charAt(i);
        }
        TextView tv = (TextView)findViewById(R.id.tvstime);
        tv.setText(stime);
        tv = (TextView)findViewById(R.id.tvetime);
        tv.setText(etime);
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Select Subject");
        try{
            FileInputStream fis = openFileInput("subjects.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine()) != null)spinnerArray.add(new SubjPair(line).subject());
            bufferedReader.close();
            inputStreamReader.close();
            fis.close();
        }
        catch(Exception e){

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapter);
        if(subj.equals(""))sItems.setSelection(0);
        else sItems.setSelection(adapter.getPosition(subj));
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem mi = (MenuItem)menu.findItem(R.id.action_delete);
        mi.setVisible(true);
        return true;
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
            Spinner spin = (Spinner)findViewById(R.id.spinner);
            String subj = (String)spin.getSelectedItem();
            if(subj.equals("Select Subject")){
                Toast.makeText(SetTT.this, "Please enter the Subject name!", Toast.LENGTH_LONG).show();
                spin.performClick();
                return true;
            }
            TextView tv = (TextView)findViewById(R.id.tvstime);
            String s1 = tv.getText().toString();
            String tosend = tv.getText().toString() + " to ";
            tv = (TextView)findViewById(R.id.tvetime);
            String s2 = tv.getText().toString();
            if(s1.equals(s2)){
                Toast.makeText(SetTT.this, "The class can't be of 0 minutes!", Toast.LENGTH_LONG).show();
                return true;
            }
            MyPair mp = new MyPair("<" + s1 + "," + s2 + ",");
            if(mp.start().compareTo(mp.endd()) > 0){
                Toast.makeText(SetTT.this, "The class can't start after it ends...", Toast.LENGTH_LONG).show();
                return true;
            }
            tosend += tv.getText().toString() + "\n" + subj;
            Intent intent = new Intent();
            intent.putExtra("NEWDATA", tosend);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        if(id == R.id.action_delete){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            Toast.makeText(SetTT.this, "Period Deleted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            String tosend = "~";
                            intent.putExtra("NEWDATA", tosend);
                            setResult(RESULT_OK, intent);
                            finish();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(SetTT.this);
            builder.setMessage("Do you want to delete this period?").setNegativeButton("Yes", dialogClickListener)
                    .setPositiveButton("No", dialogClickListener).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void pickthetime(View view){
        Intent intent2 = new Intent(this, PickTime.class);
        TextView tv = null;
        int rcode = 1;
        switch(view.getId()){
            case R.id.btntvstime:
                tv = (TextView)findViewById(R.id.tvstime);
                rcode = 1;
                break;
            case R.id.btntvetime:
                tv = (TextView)findViewById(R.id.tvetime);
                rcode = 2;
                break;
        }
        String tosend = tv.getText().toString();
        intent2.putExtra(PTDATA, tosend);
        startActivityForResult(intent2, rcode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String ntime = data.getStringExtra("NEWTIME");
                TextView tv = (TextView)findViewById(R.id.tvstime);
                tv.setText(ntime);
            }
        }
        else{
            if(resultCode == RESULT_OK){
                String ntime = data.getStringExtra("NEWTIME");
                TextView tv = (TextView)findViewById(R.id.tvetime);
                tv.setText(ntime);
            }
        }
    }
}
