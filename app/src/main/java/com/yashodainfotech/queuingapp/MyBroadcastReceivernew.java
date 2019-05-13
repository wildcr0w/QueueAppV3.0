package com.yashodainfotech.queuingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.yashodainfotech.queuingapp.Timeout.stopalarm;
import static com.yashodainfotech.queuingapp.Timeout.stopalarm15sec;

public class MyBroadcastReceivernew extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        try{
            Intent intent1=new Intent(context,TableActivity.class);
            intent1.putExtra("table_id",Sharedpref.getmPref(context).gettimeOutId());
            intent1.putExtra("table_name",Sharedpref.getmPref(context).gettimeOutname());
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            stopalarm15sec();
           // context.startActivity(intent1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
