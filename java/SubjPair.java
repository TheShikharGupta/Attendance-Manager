package com.shikhar.attendancemanager;

/**
 * Created by Shikhar on 11-03-2016.
 */
public class SubjPair {
    private String subjname;
    private int nol;
    private int atten;

    public SubjPair(String s){
        int i = 0;
        subjname = "";
        for(; s.charAt(i) != '|'; ++i)subjname += s.charAt(i);
        String t = "";
        for(i += 1; s.charAt(i) != '|'; ++i)t += s.charAt(i);
        nol = Integer.parseInt(t);
        t = "";
        for(i += 1; s.charAt(i) != '|'; ++i)t += s.charAt(i);
        atten = Integer.parseInt(t);
    }

    public String antiparse(){
        return subjname + "|" + Integer.toString(nol) + "|" + Integer.toString(atten) + "|\n";
    }

    public String subject(){ return subjname; }
    public int totlec(){ return nol; }
    public int attenlec(){ return atten; }
}
