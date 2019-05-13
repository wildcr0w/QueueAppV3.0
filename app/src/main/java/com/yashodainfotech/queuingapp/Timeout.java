package com.yashodainfotech.queuingapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class Timeout {
    static AlarmManager alarmManager;
    static PendingIntent pendingIntent;
    static AlarmManager alarmManager1;
    static PendingIntent pendingIntent1;
    public static void runAdsvideo(Context context){
        try{
            Log.e("TAG", "runAds: " );
            Calendar cal = Calendar.getInstance();cal.add(Calendar.SECOND, 15);
            Intent intent = new Intent(context, MyBroadcastReceiverVideo.class);
            pendingIntent = PendingIntent.getBroadcast(
                    context.getApplicationContext(), 234324243, intent, 0);
            alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }catch (Exception e){
            e.printStackTrace();
            try{
                Log.e("TAG", "onStopalaram: ");
                alarmManager.cancel(pendingIntent);
            }catch (Exception e1){
                e.printStackTrace();
            }
        }

    }

    public static void stopalarm(){
        try{
            alarmManager.cancel(pendingIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void runAdsvideo15sec(Context context){
        try{
            Log.e("TAG", "runAds: " );
            Calendar cal = Calendar.getInstance();cal.add(Calendar.SECOND, 15);
            Intent intent = new Intent(context, MyBroadcastReceivernew.class);
            pendingIntent1 = PendingIntent.getBroadcast(
                    context.getApplicationContext(), 234324243, intent, 0);
            alarmManager1 = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager1.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent1);
        }catch (Exception e){
            e.printStackTrace();
            try{
                Log.e("TAG", "onStopalaram: ");
                alarmManager1.cancel(pendingIntent1);
            }catch (Exception e1){
                e.printStackTrace();
            }
        }

    }

    public static void stopalarm15sec(){
        try{
            alarmManager1.cancel(pendingIntent1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
