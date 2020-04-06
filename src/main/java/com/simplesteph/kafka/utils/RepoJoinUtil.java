package com.simplesteph.kafka.utils;

public class RepoJoinUtil {
    public static String Join(String s1,String s2){
        if(s1==null&&s2==null)
            return "";
        if(s1==null||s1.trim().isEmpty())
            return s2.trim();
        else if(s2==null||s2.trim().isEmpty())
            return s1.trim();
        else
            return s1.trim()+","+s2.trim();
    }

}
