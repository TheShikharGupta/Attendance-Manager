package com.shikhar.attendancemanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MngSubj extends AppCompatActivity {

    private ArrayList al = new ArrayList();
    private ArrayList<SubjPair> sal = new ArrayList<SubjPair>();
    private View.OnClickListener clickbtn = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            Button btn = (Button)view;
            alertbam(btn.getText().toString());
        }
    };

    private String blockCharacterSet = "|";

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng_subj);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertbam("");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("or", "onResume called");
        theStarter();
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("op", "onPause called");
        theEnder();
    }

    public void alertbam(String s){
        final String bb = s;
        AlertDialog.Builder alert = new AlertDialog.Builder(MngSubj.this);
        final EditText edittext = new EditText(MngSubj.this);
        edittext.setText(s);
        edittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        edittext.setFilters(new InputFilter[]{filter});
        edittext.setSingleLine(true);
        alert.setMessage("Enter subject name: ");
        alert.setView(edittext);
        alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.setNegativeButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (al.contains(bb)) {
                            try {
                                FileInputStream fis = openFileInput("timetable.txt");
                                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                MyPair mp;
                                String line, buffer;
                                int i, j;
                                for (i = 0; i < 7; ++i) {
                                    line = bufferedReader.readLine();
                                    Log.d("mngsubj", line);
                                    buffer = "";
                                    for (j = 1; line.charAt(j) != '|'; ++j) {
                                        if (line.charAt(j) == '>') {
                                            mp = new MyPair(buffer);
                                            buffer = "";
                                            Log.d("mngsubj", mp.subject());
                                            if (mp.subject().equals(bb)) {
                                                try {
                                                    bufferedReader.close();
                                                    inputStreamReader.close();
                                                    fis.close();
                                                }
                                                catch (Exception e){

                                                }
                                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        switch (which) {
                                                            case DialogInterface.BUTTON_POSITIVE:
                                                                Toast.makeText(MngSubj.this, "Subject has not been deleted.", Toast.LENGTH_LONG).show();
                                                                break;

                                                            case DialogInterface.BUTTON_NEGATIVE:
                                                                Toast.makeText(MngSubj.this, "Subject has been deleted along with its periods.", Toast.LENGTH_LONG).show();
                                                                removePeriods(bb);
                                                                al.remove(bb);
                                                                theEnder();
                                                                theStarter();
                                                                break;
                                                        }
                                                    }
                                                };

                                                AlertDialog.Builder builder = new AlertDialog.Builder(MngSubj.this);
                                                builder.setMessage("This subject has some periods in the time table. Would you like to delete its periods in the time table?").setNegativeButton("Yes", dialogClickListener)
                                                        .setPositiveButton("No", dialogClickListener).show();
                                                return;
                                            }
                                        } else buffer += line.charAt(j);
                                    }
                                }
                                bufferedReader.close();
                                inputStreamReader.close();
                                fis.close();
                            } catch (Exception e) {

                            }
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Toast.makeText(MngSubj.this, "Subject Deleted", Toast.LENGTH_LONG).show();
                                            al.remove(bb);
                                            theEnder();
                                            theStarter();
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(MngSubj.this);
                            builder.setMessage("Do you want to delete this subject?").setNegativeButton("Yes", dialogClickListener)
                                    .setPositiveButton("No", dialogClickListener).show();
                        }
                    }
                });


        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String sname = edittext.getText().toString();
                if (sname.equals("")) {
                    Toast.makeText(MngSubj.this, "The subject has to have a name, right?", Toast.LENGTH_LONG).show();
                } else if (al.contains(sname)) {
                    Toast.makeText(MngSubj.this, "You have already added a subject with the same name!", Toast.LENGTH_LONG).show();
                } else {
                    al.add(sname);
                    sal.add(new SubjPair(sname + "|0|0|\n"));
                    theEnder();
                    theStarter();
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    public void theStarter(){
        TextView tv = (TextView)findViewById(R.id.tvnosubj);
        FileInputStream fis = null;
        boolean flag = true;
        try{
            fis = openFileInput("subjects.txt");
        }
        catch(FileNotFoundException e){
            flag = false;
        }
        if(flag){
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            Button btn;
            LinearLayout llay = (LinearLayout)findViewById(R.id.llaymngsubj);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            boolean mflag = true;
            SubjPair sp;
            try {
                while((line = bufferedReader.readLine()) != null){
                    mflag = false;
                    tv.setVisibility(View.GONE);
                    sp = new SubjPair(line);
                    btn = new Button(MngSubj.this);
                    btn.setText(sp.subject());
                    btn.setAllCaps(false);
                    btn.setLayoutParams(lp);
                    btn.setOnClickListener(clickbtn);
                    llay.addView(btn);
                    al.add(sp.subject());
                    sal.add(sp);
                }
                bufferedReader.close();
                inputStreamReader.close();
                fis.close();
            }
            catch(Exception e){

            }
            if(mflag)tv.setVisibility(View.VISIBLE);
        }
        else tv.setVisibility(View.VISIBLE);
    }

    public void theEnder(){
        try{
            FileOutputStream fos = openFileOutput("subjects.txt", Context.MODE_PRIVATE);
            int i, l = sal.size();
            for(i = 0; i < l; ++i){
                if(al.contains(sal.get(i).subject()))fos.write(sal.get(i).antiparse().getBytes());
            }
            fos.close();
            sal.clear();
            al.clear();
            TextView tv = (TextView)findViewById(R.id.tvnosubj);
            LinearLayout llay = (LinearLayout)findViewById(R.id.llaymngsubj);
            llay.removeAllViews();
            llay.addView(tv);
        }
        catch (Exception e){

        }
    }

    public void removePeriods(String bb){
        try{
            FileInputStream fis = openFileInput("timetable.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String[] towrite = new String[7];
            String line, buffer, nline;
            MyPair mp;
            int i, j;
            for(i = 0; i < 7; ++i){
                line = bufferedReader.readLine();
                buffer = "";
                nline = "1";
                for(j = 1; line.charAt(j) != '|'; ++j){
                    if(line.charAt(j) == '>'){
                        mp = new MyPair(buffer);
                        if(!mp.subject().equals(bb))nline += buffer + ">";
                        buffer = "";
                    }
                    else buffer += line.charAt(j);
                }
                nline += "|\n";
                towrite[i] = nline;
            }
            bufferedReader.close();
            inputStreamReader.close();
            fis.close();
            FileOutputStream fos = openFileOutput("timetable.txt", Context.MODE_PRIVATE);
            for(i = 0; i < 7; ++i)fos.write(towrite[i].getBytes());
            fos.close();
        }
        catch(Exception e){

        }
    }

}
