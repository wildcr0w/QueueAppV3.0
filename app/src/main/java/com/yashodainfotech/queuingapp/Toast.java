package com.yashodainfotech.queuingapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Toast extends AppCompatActivity {
    public  static void toast(String message,Context context){
        View layout = LayoutInflater.from(context).inflate(R.layout.toast,
                null,false);
        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_tick);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);

        android.widget.Toast toast = new android.widget.Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static void toastnew(String message, Context context){
        View layout = LayoutInflater.from(context).inflate(R.layout.toast,
                null,false);

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_wrong);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);

        android.widget.Toast toast = new android.widget.Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}
