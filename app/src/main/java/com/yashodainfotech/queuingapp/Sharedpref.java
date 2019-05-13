package com.yashodainfotech.queuingapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpref {
    private static Sharedpref mPref;
    private Context context;
    private static final String Check_login = "login";
    private static final String Check_time = "time";
    private static final String Check_name = "name";
    private static final String Check_activity = "activity";
    private static final String Check_q = "Check_q";
    private static final String Check_table= "table";
    private static final String Check_frontback= "frontback";
    private static final String Check_tableid= "tableid";
    private static final String Check_dashboard= "dashboard";
    private static final String Check_memberid= "memberid";
    private static final String Check_tableids= "tableids";
    private static final String Check_tablenames= "tablenames";
    private static final String Sharedprefname = "Sharedprefname";
    private static final String Check_cameraid= "cameraid";
    private static final String Check_timeoutid= "timeoutid";
    private static final String Check_timeoutidname= "idname";
    private static final String Check_cameraback= "Check_cameraback";
    private static final String Check_camerafront= "camerafront";
    private static final String Check_gracetime= "Check_gracetime";
    private static final String Check_counter= "Check_counter";




    private Sharedpref(Context context) {
        this.context = context;
    }

    public static synchronized Sharedpref getmPref(Context mContext) {
        if (mPref == null) {
            mPref = new Sharedpref(mContext);
        }
        return mPref;
    }
    public boolean checkLogin(String login) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_login, login);
        mEditor.apply();
        return true;
    }

    public String getloginstatus() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_login, null);
    }

    public boolean checktime(String time ,String tablename2) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_time, time);
        mEditor.putString(Check_name, tablename2);
        mEditor.apply();
        return true;
    }

    public String gettime() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_time, null);
    }
    public String getname() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_name, null);
    }

    public boolean checkActivity(String activity) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_activity, activity);
        mEditor.apply();
        return true;
    }

    public String getActivity() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_activity, null);
    }

    public boolean checkfirstqueue(String queue) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_q, queue);
        mEditor.apply();
        return true;
    }

    public String getfirstqueue() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_q, null);
    }

    public boolean isfromtable(String table) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_table, table);
        mEditor.apply();
        return true;
    }

    public String getisfromtable() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_table, null);
    }

    public boolean iscamerabackfront(String frontback) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_frontback, frontback);
        mEditor.apply();
        return true;
    }

    public String getiscamerabackfront() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_frontback, null);
    }

    public boolean issavetableid(String tableid) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_tableid, tableid);
        mEditor.apply();
        return true;
    }

    public String gettableid() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_tableid, null);
    }

    public boolean isdashboard(String dashboard) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_dashboard, dashboard);
        mEditor.apply();
        return true;
    }

    public String getisdashboard() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_dashboard, null);
    }

    public boolean isusertable(String memberid,String tableid,String tablename) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_memberid, memberid);
        mEditor.putString(Check_tableids, tableid);
        mEditor.putString(Check_tablenames, tablename);
        mEditor.apply();
        return true;
    }

    public String getmemberids() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_memberid, null);
    }

    public String gettableids() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_tableids, null);
    }

    public String gettablenames() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_tablenames, null);
    }


    public boolean iscameraid(String tableid) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_cameraid, tableid);
        mEditor.apply();
        return true;
    }

    public String getcameraid() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_cameraid, null);
    }

    public boolean timeOutId(String tableid,String tablename) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_timeoutid, tableid);
        mEditor.putString(Check_timeoutidname, tablename);
        mEditor.apply();
        return true;
    }

    public String gettimeOutId() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_timeoutid, null);
    }
    public String gettimeOutname() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_timeoutidname, null);
    }

    public boolean cameraBack(String back) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_cameraback, back);
        mEditor.apply();
        return true;
    }

    public String getcameraBack() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_cameraback, null);
    }

    public boolean cameraFront(String front) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_camerafront, front);
        mEditor.apply();
        return true;
    }

    public String getcameraFront() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_camerafront, null);
    }



    public boolean gracetime(String gracetime) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_gracetime, gracetime);
        mEditor.apply();
        return true;
    }

    public String getgracetime() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_gracetime, null);
    }
    public boolean counterattack(String counter) {
        android.content.SharedPreferences mShared = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor mEditor = mShared.edit();
        mEditor.putString(Check_counter, counter);
        mEditor.apply();
        return true;
    }

    public String getcounterattack() {
        android.content.SharedPreferences getFirebaseToken = context.getSharedPreferences(Sharedprefname, Context.MODE_PRIVATE);
        return getFirebaseToken.getString(Check_counter, null);
    }
}
