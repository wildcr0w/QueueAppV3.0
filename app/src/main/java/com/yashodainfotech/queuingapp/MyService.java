package com.yashodainfotech.queuingapp;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yashodainfotech.queuingapp.POJOS.DashPOJO;
import com.yashodainfotech.queuingapp.Webservices.AllWebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MyService {
    public static Timer timer;
    public static int count = 0;

    public static void gettime(final Context context) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("TAG", "timer: ");
                Log.e("TAG", "gettime: ");
                StringRequest request = new StringRequest(Request.Method.GET, AllWebServices.getTime, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        count = 1;
                        Log.e("TAG", "onResponse: " + response);
                        try {
                            //  dismissLoader();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonArray = jsonObject.getJSONObject("result_array");
                            String time = jsonArray.getString("time");
                            if (time.equals("01:00:00")) {
                                Sharedpref.getmPref(context).checkLogin("Login UnSuccessfully");
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(DemoActivity.this, "Data Saved Successfully" + response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "onErrorResponse: " + error.getMessage());
                    }
                });
                RequestQueue queue=Myapplication.getInstance().getRequestQueue();
                queue.add(request);
            }
        }, 0, 1000);
    }

    public static void stopTimer() {
        try{
            timer.cancel();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
