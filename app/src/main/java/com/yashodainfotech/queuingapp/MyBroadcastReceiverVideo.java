package com.yashodainfotech.queuingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static com.yashodainfotech.queuingapp.Timeout.stopalarm;

public class MyBroadcastReceiverVideo  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            Intent intent1=new Intent(context,TableActivity.class);
            intent1.putExtra("table_id",Sharedpref.getmPref(context).gettimeOutId());
            intent1.putExtra("table_name",Sharedpref.getmPref(context).gettimeOutname());
            stopalarm();
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
