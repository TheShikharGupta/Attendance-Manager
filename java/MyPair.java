package com.shikhar.attendancemanager;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by Shikhar on 10-03-2016.
 */
public class MyPair {
    private Calendar stime;
    private Calendar etime;
    private String subj;

    public MyPair(String s){
        stime = parser(s, 1);
        etime = parser(s, 10);
        subj = "";
        int i = 19;
        for(; i < s.length(); ++i){
            subj += s.charAt(i);
        }
    }

    public MyPair(String s, int j){
        stime = parser(s, 0);
        etime = parser(s, 12);
        subj = "";
        int i = 21;
        for(; i < s.length(); ++i){
            subj += s.charAt(i);
        }
    }

    public Calendar parser(String s, int i){
        Calendar t;
        String h = "", m = "";
        int hi, mi;
        h += s.charAt(i);
        h += s.charAt(i + 1);
        m += s.charAt(i + 3);
        m += s.charAt(i + 4);
        hi = Integer.parseInt(h);
        mi = Integer.parseInt(m);
        if(s.charAt(i + 6) == 'p' && hi != 12)hi += 12;
        t = Calendar.getInstance();
        t.setTimeInMillis(0);
        t.set(Calendar.HOUR_OF_DAY, hi);
        t.set(Calendar.MINUTE, mi);
        return t;
    }

    public String antiparse(){
        return "<" + antiparsehelp(stime) + "," + antiparsehelp(etime) + "," + subj + ">";
    }

    public String antiparse2(){
        return antiparsehelp(stime) + " to " + antiparsehelp(etime) + "\n" + subj;
    }

    public String antiparse3(){
        return antiparsehelp(stime) + " to " + antiparsehelp(etime);
    }

    public String antiparsehelp(Calendar t){
        String ampm, tor, temp;
        int h;
        h = t.get(Calendar.HOUR_OF_DAY);
        if(h > 11){
            ampm = " pm";
            if(h > 12)h -= 12;
        }
        else ampm = " am";
        temp = Integer.toString(h);
        if(temp.length() == 1)temp = "0" + temp;
        tor = temp + ":";
        temp = Integer.toString(t.get(Calendar.MINUTE));
        if(temp.length() == 1)temp = "0" + temp;
        tor += temp + ampm;
        return tor;
    }

    public boolean mequal(MyPair mp){
        if(stime.compareTo(mp.start()) == 0 && etime.compareTo(mp.endd()) == 0 && subj.equals(mp.subject()))return true;
        return false;
    }

    public Calendar start()   { return stime; }
    public Calendar endd() { return etime; }
    public String subject() { return subj; }
}
