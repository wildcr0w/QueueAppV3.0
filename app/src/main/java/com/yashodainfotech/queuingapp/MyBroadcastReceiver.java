package com.yashodainfotech.queuingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.e("TAG", "onReceive: " );
        Ringtone.RingtoneManager(context);
        Intent intent1=new Intent(context,TableActivity.class);
        intent1.putExtra("broadcast","1");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
