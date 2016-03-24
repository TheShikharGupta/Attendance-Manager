package com.shikhar.attendancemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class EditTT extends AppCompatActivity {

    public final static String TDATA = "com.shikhar.attendancemanager.TDATA";
    private ArrayList<MyPair> talmon = new ArrayList<MyPair>();
    private ArrayList<MyPair> taltue = new ArrayList<MyPair>();
    private ArrayList<MyPair> talwed = new ArrayList<MyPair>();
    private ArrayList<MyPair> talthu = new ArrayList<MyPair>();
    private ArrayList<MyPair> talfri = new ArrayList<MyPair>();
    private ArrayList<MyPair> talsat = new ArrayList<MyPair>();
    private ArrayList<MyPair> talsun = new ArrayList<MyPair>();
    private ArrayList almon = new ArrayList();
    private ArrayList altue = new ArrayList();
    private ArrayList alwed = new ArrayList();
    private ArrayList althu = new ArrayList();
    private ArrayList alfri = new ArrayList();
    private ArrayList alsat = new ArrayList();
    private ArrayList alsun = new ArrayList();
    private ArrayList mahbuttons = new ArrayList();
    private int batman = 0;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private View.OnClickListener clicktt = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            set_tt(view);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPause(){
        super.onPause();
        theEnder();
    }

    public void theEnder(){
        try{
            FileOutputStream fos = openFileOutput("timetable.txt", Context.MODE_PRIVATE);
            String sw = saveTree(talmon, 1);
            fos.write(sw.getBytes());
            sw = saveTree(taltue, 2);
            fos.write(sw.getBytes());
            sw = saveTree(talwed, 3);
            fos.write(sw.getBytes());
            sw = saveTree(talthu, 4);
            fos.write(sw.getBytes());
            sw = saveTree(talfri, 5);
            fos.write(sw.getBytes());
            sw = saveTree(talsat, 6);
            fos.write(sw.getBytes());
            sw = saveTree(talsun, 7);
            fos.write(sw.getBytes());
            fos.close();
        }catch(Exception e){

        }
        talmon.clear();
        taltue.clear();
        talwed.clear();
        talthu.clear();
        talfri.clear();
        talsat.clear();
        talsun.clear();
        mahbuttons.clear();
    }

    @Override
    protected void onResume(){
        super.onResume();
        theStarter();
    }

    public void theStarter(){
        try {
            FileInputStream fis = null;
            String[] sw = new String[7];
            boolean flag = true;
            int i;
            try {
                fis = openFileInput("timetable.txt");
            }catch (FileNotFoundException fe) {
                flag = false;
                sw[0] = "1|\n";
                sw[1] = "2|\n";
                sw[2] = "3|\n";
                sw[3] = "4|\n";
                sw[4] = "5|\n";
                sw[5] = "6|\n";
                sw[6] = "7|\n";
                FileOutputStream fos = openFileOutput("timetable.txt", Context.MODE_PRIVATE);
                for (i = 0; i < 7; ++i) {
                    fos.write(sw[i].getBytes());
                }
                fos.close();
            }
            if (flag) {
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                i = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    sw[i++] = line;
                }
                inputStreamReader.close();
                fis.close();
            }
            mahbuttons.add(R.id.monplus);
            mahbuttons.add(R.id.tueplus);
            mahbuttons.add(R.id.wedplus);
            mahbuttons.add(R.id.thuplus);
            mahbuttons.add(R.id.friplus);
            mahbuttons.add(R.id.satplus);
            mahbuttons.add(R.id.sunplus);
            filltree(sw[0], talmon);
            filltree(sw[1], taltue);
            filltree(sw[2], talwed);
            filltree(sw[3], talthu);
            filltree(sw[4], talfri);
            filltree(sw[5], talsat);
            filltree(sw[6], talsun);
            fillrow(talmon, almon, R.id.tblrmon, R.id.monplus, R.id.tvmon);
            fillrow(taltue, altue, R.id.tblrtue, R.id.tueplus, R.id.tvtue);
            fillrow(talwed, alwed, R.id.tblrwed, R.id.wedplus, R.id.tvwed);
            fillrow(talthu, althu, R.id.tblrthu, R.id.thuplus, R.id.tvthu);
            fillrow(talfri, alfri, R.id.tblrfri, R.id.friplus, R.id.tvfri);
            fillrow(talsat, alsat, R.id.tblrsat, R.id.satplus, R.id.tvsat);
            fillrow(talsun, alsun, R.id.tblrsun, R.id.sunplus, R.id.tvsun);
        }
        catch(Exception e){

        }
    }

    public String saveTree(ArrayList<MyPair> al, int startint){
        String tor = Integer.toString(startint);
        int i, l = al.size();
                for(i = 0; i < l; ++i){
            tor += al.get(i).antiparse();
        }
        tor += "|\n";
        return tor;
    }

    public static int mgenerateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public void set_tt(View view){
        Button btn = (Button)view;
        int rcode = 0, mid = btn.getId();
        String btntext = btn.getText().toString();
        String ftime;
        if(btntext.equals("+")){
            Calendar cal = Calendar.getInstance();
            int hod = cal.get(Calendar.HOUR_OF_DAY);
            String stime = Integer.toString(cal.get(Calendar.HOUR));
            if(hod == 12)stime = "12";
            if(stime.length() == 1){
                stime = "0" + stime;
            }
            stime += ":";
            String mtime = Integer.toString(cal.get(Calendar.MINUTE));
            if(mtime.length() == 1){
                mtime = "0" + mtime;
            }
            stime += mtime;
            if(hod > 11)stime += " pm";
            else stime += " am";
            ftime = stime + "," + stime + ",";
        }
        else{
            int i;
            String thet = btn.getText().toString();
            ftime = "";
            for(i = 0; thet.charAt(i) != ' '; ++i){
                ftime += thet.charAt(i);
            }
            ftime += ' ';
            for(i += 1; thet.charAt(i) != ' '; ++i){
                ftime += thet.charAt(i);
            }
            i += 4;
            ftime += ",";
            for(; thet.charAt(i) != '\n'; ++i){
                ftime += thet.charAt(i);
            }
            ftime += ",";
            for(i += 1; i < thet.length(); ++i){
                ftime += thet.charAt(i);
            }
        }
        if(almon.contains(mid))rcode = 0;
        else if(altue.contains(mid))rcode = 1;
        else if(alwed.contains(mid))rcode = 2;
        else if(althu.contains(mid))rcode = 3;
        else if(alfri.contains(mid))rcode = 4;
        else if(alsat.contains(mid))rcode = 5;
        else if(alsun.contains(mid))rcode = 6;
        Intent intent = new Intent(this, SetTT.class);
        intent.putExtra(TDATA, ftime);
        batman = mid;
        startActivityForResult(intent, rcode);
    }

    public void filltree(String s, ArrayList<MyPair> al){
        int i;
        String buffer = "";
        for (i = 1; s.charAt(i) != '|'; ++i) {
            if(s.charAt(i) == '>'){
                al.add(new MyPair(buffer));
                buffer = "";
            }
            else{
                buffer += s.charAt(i);
            }
        }
    }

    public void fillrow(ArrayList<MyPair> tal, ArrayList al, int rowid, int btnid, int tvid){
        TableRow tr = (TableRow)findViewById(rowid);
        Button btnplus = (Button)findViewById(btnid);
        TextView tv = (TextView)findViewById(tvid);
        tr.removeAllViews();
        al.clear();
        tr.addView(tv);
        Button btnadd;
        int mid, i = 2, j = 0, l = tal.size();
        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        for(; j < l; ++j){
            btnadd = new Button(EditTT.this);
            lp.column = i;
            btnadd.setAllCaps(false);
            btnadd.setText(tal.get(j).antiparse2());
            btnadd.setOnClickListener(clicktt);
            float scale = getResources().getDisplayMetrics().density;
            int p1 = (int)(10 * scale + 0.5f);
            btnadd.setPadding(p1, p1, p1, p1);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mid = mgenerateViewId();
            }
            else{
                mid = View.generateViewId();
            }
            al.add(mid);
            btnadd.setId(mid);
            ++i;
            tr.addView(btnadd);
        }
        lp.column = i;
        btnplus.setLayoutParams(lp);
        al.add(btnid);
        tr.addView(btnplus);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null)return;
        super.onActivityResult(requestCode, resultCode, data);
        int mid = batman;
        boolean mflag = false;
        int theindex = 0;
        Button btn = (Button) findViewById(mid);
        String txt = btn.getText().toString();
        String txt2 = data.getStringExtra("NEWDATA");
        if(txt.equals("+") && txt2.equals("~"))return;
        theStarter();
        if(!mahbuttons.contains(mid))mflag = true;
        MyPair mp, mp2 = null;
        if(txt2.equals("~") || mflag){
            mp2 = new MyPair(txt, 0);
            switch (requestCode){
                case 0:
                    theindex = alindex(talmon, mp2);
                    talmon.remove(theindex);
                    break;
                case 1:
                    theindex = alindex(taltue, mp2);
                    taltue.remove(theindex);
                    break;
                case 2:
                    theindex = alindex(talwed, mp2);
                    talwed.remove(theindex);
                    break;
                case 3:
                    theindex = alindex(talthu, mp2);
                    talthu.remove(theindex);
                    break;
                case 4:
                    theindex = alindex(talfri, mp2);
                    talfri.remove(theindex);
                    break;
                case 5:
                    theindex = alindex(talsat, mp2);
                    talsat.remove(theindex);
                    break;
                case 6:
                    theindex = alindex(talsun, mp2);
                    talsun.remove(theindex);
                    break;
            }
        }
        if(!txt2.equals("~")){
            mp = new MyPair(txt2, 0);
            switch(requestCode){
                case 0:
                    batfunc(mp, talmon, mflag, theindex, mp2);
                    break;
                case 1:
                    batfunc(mp, taltue, mflag, theindex, mp2);
                    break;
                case 2:
                    batfunc(mp, talwed, mflag, theindex, mp2);
                    break;
                case 3:
                    batfunc(mp, talthu, mflag, theindex, mp2);
                    break;
                case 4:
                    batfunc(mp, talfri, mflag, theindex, mp2);
                    break;
                case 5:
                    batfunc(mp, talsat, mflag, theindex, mp2);
                    break;
                case 6:
                    batfunc(mp, talsun, mflag, theindex, mp2);
                    break;
            }
        }
        theEnder();
    }

    public void batfunc(MyPair mp, ArrayList<MyPair> tal, boolean mflag, int theindex, MyPair mp2){
        int i, l = tal.size();
        if(l == 0){
            tal.add(mp);
            return;
        }
        if(mp.endd().compareTo(tal.get(0).start()) <= 0){
            tal.add(0, mp);
            return;
        }
        for(i = 1; i < l; ++i){
            if(mp.endd().compareTo(tal.get(i).start()) <= 0 && mp.start().compareTo(tal.get(i - 1).endd()) >= 0){
                tal.add(i, mp);
                return;
            }
        }
        if(mp.start().compareTo(tal.get(l - 1).endd()) >= 0){
            tal.add(mp);
            return;
        }
        Toast.makeText(EditTT.this, "You already have a class from " + mp.antiparse3(), Toast.LENGTH_LONG).show();
        if(mflag)tal.add(theindex, mp2);
    }

    public int alindex(ArrayList<MyPair> al, MyPair mp){
        int i = 0, l = al.size();
        for(; i < l; ++i){
            if(al.get(i).mequal(mp)){
                return i;
            }
        }
        return -1;
    }
}
