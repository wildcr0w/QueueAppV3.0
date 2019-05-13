package com.yashodainfotech.queuingapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yashodainfotech.queuingapp.Adapters.DashboardAdapter;
import com.yashodainfotech.queuingapp.Adapters.TablequeueAdapter;
import com.yashodainfotech.queuingapp.DBHelper.DBhelper;
import com.yashodainfotech.queuingapp.POJOS.DashPOJO;
import com.yashodainfotech.queuingapp.Webservices.AllWebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

import static com.yashodainfotech.queuingapp.Ringtone.StopRingtone;
import static com.yashodainfotech.queuingapp.Timeout.runAdsvideo15sec;
import static com.yashodainfotech.queuingapp.Timeout.stopalarm;

public class TableActivity extends AppCompatActivity {
    public static KProgressHUD loader;
    static public int count = 0, count1 = 0, count2 = 0;
    public static SwipeRefreshLayout swipeRefreshLayout;
    static TextView time, tablename;
    static RecyclerView table_recycle;
    static String table_id = "", first_name = "", last_name = "",
            waitime = "", table_name = "", avail_seat = "", seat = "", grace_time = "", fromAdmin = "",
            isentered = "", isentered1 = "", refresh = "", waittime_perqueue = "", informtime = "", timeup = "";
    static long wait_time_long = 0, wait_time_long1 = 0, wait_time_long2 = 0;
    static long wait_time_wait1 = 0, wait_time_wait2 = 0, wait_time_wait3 = 0, wait_time_wait4 = 0,
            wait_time_wait5 = 0, wait_time_wait6 = 0,
            wait_time_wait7 = 0, wait_time_wait8 = 0, wait_time_wait9 = 0, wait_time_wait10 = 0,
            wait_time_wait11 = 0, wait_time_wait12 = 0, wait_time_wait13 = 0,
            wait_time_wait14 = 0, wait_time_wait15 = 0, wait_time_wait16 = 0, wait_time_wait17 = 0,
            wait_time_wait18 = 0, wait_time_wait19 = 0, wait_time_wait20 = 0,
            wait_time_wait21 = 0, wait_time_wait22 = 0, wait_time_wait23 = 0, wait_time_wait24 = 0,
            wait_time_wait25 = 0, wait_time_wait26 = 0, wait_time_wait27 = 0, wait_time_wait28 = 0, wait_time_wait29 = 0;
    String camera_front = "", camera_back = "";
    android.app.AlertDialog spotsDialog;
    AlertDialog alertDialogallnew;

    static List<DashPOJO> dashPOJOS = new ArrayList<>();
    static TablequeueAdapter tablequeueAdapter;
    static ImageView buttonScan;
    static Button buttonchangetable;
    static TextView textView, text1, text2, text3, text4;
    static TextView textView1, text12, text22, text32, text42;
    static TextView textView2, text123, text223, text323, text423;
    static TextView textView3, text124, text224, text324, text424;
    static TextView textView4, text125, text225, text325, text425;
    static TextView textView5, text126, text226, text326, text426;
    static TextView textView6, text127, text227, text327, text427;
    static TextView textView7, text128, text228, text328, text428;
    static TextView textView8, text129, text229, text329, text429;
    static TextView textView9, text1210, text2210, text3210, text4210;
    static TextView textView10, text1211, text2211, text3211, text4211;
    static TextView textView11, text1212, text2212, text3212, text4212;
    static TextView textView12, text1213, text2213, text3213, text4213;
    static TextView textView13, text1214, text2214, text3214, text4214;
    static TextView textView14, text1215, text2215, text3215, text4215;
    static TextView textView15, text1216, text2216, text3216, text4216;
    static TextView textView16, text1217, text2217, text3217, text4217;
    static TextView textView17, text1218, text2218, text3218, text4218;
    static TextView textView18, text1219, text2219, text3219, text4219;
    static TextView textView19, text1220, text2220, text3220, text4220;
    static TextView textView20, text1221, text2221, text3221, text4221;
    static TextView textView21, text1222, text2222, text3222, text4222;
    static TextView textView22, text1223, text2223, text3223, text4223;
    static TextView textView23, text1224, text2224, text3224, text4224;
    static TextView textView24, text1225, text2225, text3225, text4225;
    static TextView textView25, text1226, text2226, text3226, text4226;
    static TextView textView26, text1227, text2227, text3227, text4227;
    static TextView textView27, text1228, text2228, text3228, text4228;
    static TextView textView28, text1229, text2229, text3229, text4229;
    static CardView cardView, cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8, cardView9, cardView10, cardView11,
            cardView12, cardView13, cardView14, cardView15, cardView16, cardView17, cardView18, cardView19, cardView20, cardView21, cardView22,
            cardView23, cardView24, cardView25, cardView26, cardView27, cardView28, cardView29, cardView30;
    static String timeLeftFormatted = "", timeLeftFormatted1 = "", timeLeftFormatted3 = "";
    static String getTimeLeftFormatted_time1 = "", getTimeLeftFormatted_time2 = "", getTimeLeftFormatted_time3 = "",
            getTimeLeftFormatted_time4 = "",
            getTimeLeftFormatted_time5 = "", getTimeLeftFormatted_time6 = "", getTimeLeftFormatted_time7 = "",
            getTimeLeftFormatted_time8 = "",
            getTimeLeftFormatted_time9 = "", getTimeLeftFormatted_time10 = "", getTimeLeftFormatted_time11 = "",
            getTimeLeftFormatted_time12 = "",
            getTimeLeftFormatted_time13 = "", getTimeLeftFormatted_time14 = "", getTimeLeftFormatted_time15 = "",
            getTimeLeftFormatted_time16 = "",
            getTimeLeftFormatted_time17 = "", getTimeLeftFormatted_time18 = "", getTimeLeftFormatted_time19 = "",
            getTimeLeftFormatted_time20 = "",
            getTimeLeftFormatted_time21 = "", getTimeLeftFormatted_time22 = "", getTimeLeftFormatted_time23 = "",
            getTimeLeftFormatted_time24 = "",
            getTimeLeftFormatted_time25 = "", getTimeLeftFormatted_time26 = "",
            getTimeLeftFormatted_time27 = "", getTimeLeftFormatted_time28 = "", getTimeLeftFormatted_time29 = "";


    static Toolbar toolbar;
    static boolean isRunning = false;
    static String id_q = "";
    static Context context;
    static private EditText mEditTextInput;
    static private TextView mTextViewCountDown, mTextViewCountDown1;
    static private Button mButtonSet;
    static private Button mButtonStartPause;
    static private Button mButtonReset;
    static private CountDownTimer mCountDownTimer,
            mCountDownTimer1;
    static private CountDownTimer count_time1, count_time2, count_time3, count_time4, count_time5, count_time6, count_time7,
            count_time8, count_time9, count_time10, count_time11, count_time12, count_time13,
            count_time14, count_time15, count_time16, count_time17,
            count_time18, count_time19, count_time20, count_time21, count_time22,
            count_time23, count_time24, count_time25, count_time26, count_time27, count_time28, count_time29;
    static private boolean mTimerRunning;
    static private long mStartTimeInMillis;
    static private long mTimeLeftInMillis;
    static private long mTimeLeftInMillis1;
    static private long mTimeLeftInMillis2;
    static private long mTimeLeftInMillis_time1, mTimeLeftInMillis_time2,
            mTimeLeftInMillis_time3, mTimeLeftInMillis_time4,
            mTimeLeftInMillis_time5, mTimeLeftInMillis_time6, mTimeLeftInMillis_time7,
            mTimeLeftInMillis_time8, mTimeLeftInMillis_time9,
            mTimeLeftInMillis_time10, mTimeLeftInMillis_time11, mTimeLeftInMillis_time12,
            mTimeLeftInMillis_time13, mTimeLeftInMillis_time14,
            mTimeLeftInMillis_time15, mTimeLeftInMillis_time16, mTimeLeftInMillis_time17,
            mTimeLeftInMillis_time18, mTimeLeftInMillis_time19, mTimeLeftInMillis_time20,
            mTimeLeftInMillis_time21, mTimeLeftInMillis_time22, mTimeLeftInMillis_time23, mTimeLeftInMillis_time24,
            mTimeLeftInMillis_time25, mTimeLeftInMillis_time26, mTimeLeftInMillis_time27, mTimeLeftInMillis_time28,
            mTimeLeftInMillis_time29;
    static private long mEndTime;
    static private DBhelper mydb;
    public MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    Timer timer, timer1;
    String broadcast = "",broadcast1 = "";
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    public int countsiii = 0;
    Handler handler = new Handler();
    Handler handler1 = new Handler();
    String timebaba = "";

    TextView textwait1, textwait2, textwait3, textwait4, textwait5,
            textwait6, textwait7, textwait8, textwait9, textwait10, textwait11,
            textwait12, textwait13, textwait14, textwait15, textwait16, textwait17, textwait18,
            textwait19, textwait20, textwait21, textwait22, textwait23, textwait24, textwait25,
            textwait26, textwait27, textwait28, textwait29;

    public int timeCount = 0;

    public static void Loader() {
        loader = KProgressHUD.create(context).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel("Please Wait...").
                setCancellable(true).
                setAnimationSpeed(1).
                setDimAmount(0.5f).
                show();
    }

    public static void dismissLoader() {
        try {
            if (loader.isShowing()) {
                loader.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_layout);
        mydb = new DBhelper(this);
        Sharedpref.getmPref(getApplicationContext()).counterattack("");
        timeCount = 1;
        Intent intent1 = getIntent();
        context = TableActivity.this;
        Sharedpref.getmPref(getApplicationContext()).checkActivity("no");
        Sharedpref.getmPref(getApplicationContext()).checkfirstqueue("no");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        startDialog();
        final Intent intent = getIntent();
        table_id = intent.getStringExtra("table_id");
        table_name = intent.getStringExtra("table_name");
        /*isentered = intent.getStringExtra("entered");
        isentered1 = intent.getStringExtra("entered1");*/
        //refresh=intent.getStringExtra("refresh");
        if (table_id != null && !table_id.equals("")) {
            Sharedpref.getmPref(getApplicationContext()).checktime(table_id, table_name);
        }
        time = findViewById(R.id.Time);
       /* swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
               *//* mTextViewCountDown.setText("00:00:00");
                Intent intent2 = new Intent(TableActivity.this, TableActivity.class);
                intent.putExtra("refresh", "yes");
                intent.putExtra("table_id", table_id);
                intent.putExtra("table_name", table_name);
                startActivity(intent2);*//*
               LoadActivty();
            }
        });*/
        toolbar = findViewById(R.id.tooladdnews);
        toolbar.setTitle(Sharedpref.getmPref(getApplicationContext()).getname());
        tablename = findViewById(R.id.table_nmae);
        tablename.setText(Sharedpref.getmPref(getApplicationContext()).getname());
        buttonScan = findViewById(R.id.buttonScan);
        buttonchangetable = findViewById(R.id.buttonchangetable);
        cardView = findViewById(R.id.card_view1);
        cardView1 = findViewById(R.id.card_view2);
        cardView2 = findViewById(R.id.card_view3);
        cardView3 = findViewById(R.id.card_view4);
        cardView4 = findViewById(R.id.card_view5);
        cardView5 = findViewById(R.id.card_view6);
        cardView6 = findViewById(R.id.card_view7);
        cardView7 = findViewById(R.id.card_view8);
        cardView8 = findViewById(R.id.card_view9);
        cardView9 = findViewById(R.id.card_view10);
        cardView10 = findViewById(R.id.card_view11);
        cardView11 = findViewById(R.id.card_view12);
        cardView12 = findViewById(R.id.card_view13);
        cardView13 = findViewById(R.id.card_view14);
        cardView14 = findViewById(R.id.card_view15);
        cardView15 = findViewById(R.id.card_view16);
        cardView16 = findViewById(R.id.card_view17);
        cardView17 = findViewById(R.id.card_view18);
        cardView18 = findViewById(R.id.card_view19);
        cardView19 = findViewById(R.id.card_view20);
        cardView20 = findViewById(R.id.card_view21);
        cardView21 = findViewById(R.id.card_view22);
        cardView22 = findViewById(R.id.card_view23);
        cardView23 = findViewById(R.id.card_view24);
        cardView24 = findViewById(R.id.card_view25);
        cardView25 = findViewById(R.id.card_view26);
        cardView26 = findViewById(R.id.card_view27);
        cardView27 = findViewById(R.id.card_view28);
        cardView28 = findViewById(R.id.card_view29);

       // cardView.setVisibility(View.GONE);
        cardView1.setVisibility(View.GONE);
        cardView2.setVisibility(View.GONE);
        cardView3.setVisibility(View.GONE);
        cardView4.setVisibility(View.GONE);
        cardView5.setVisibility(View.GONE);
        cardView6.setVisibility(View.GONE);
        cardView7.setVisibility(View.GONE);
        cardView8.setVisibility(View.GONE);
        cardView9.setVisibility(View.GONE);
        cardView10.setVisibility(View.GONE);
        cardView11.setVisibility(View.GONE);
        cardView12.setVisibility(View.GONE);
        cardView13.setVisibility(View.GONE);
        cardView14.setVisibility(View.GONE);
        cardView15.setVisibility(View.GONE);
        cardView16.setVisibility(View.GONE);
        cardView17.setVisibility(View.GONE);
        cardView18.setVisibility(View.GONE);
        cardView19.setVisibility(View.GONE);
        cardView20.setVisibility(View.GONE);
        cardView21.setVisibility(View.GONE);
        cardView22.setVisibility(View.GONE);
        cardView23.setVisibility(View.GONE);
        cardView24.setVisibility(View.GONE);
        cardView25.setVisibility(View.GONE);
        cardView26.setVisibility(View.GONE);
        cardView27.setVisibility(View.GONE);
        cardView28.setVisibility(View.GONE);

        textView = findViewById(R.id.text);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);

        textView1 = findViewById(R.id.text1111z);
        text12 = findViewById(R.id.text11111z);
        text22 = findViewById(R.id.text211z);
        text32 = findViewById(R.id.text311z);

        textView2 = findViewById(R.id.text111);
        text123 = findViewById(R.id.text11);
        text223 = findViewById(R.id.text21);
        text323 = findViewById(R.id.text31);

        textView3 = findViewById(R.id.text1111);
        text124 = findViewById(R.id.text11111);
        text224 = findViewById(R.id.text211);
        text324 = findViewById(R.id.text311);

        textView4 = findViewById(R.id.text1111a);
        text125 = findViewById(R.id.text11111a);
        text225 = findViewById(R.id.text211a);
        text325 = findViewById(R.id.text311a);

        textView5 = findViewById(R.id.text1111b);
        text126 = findViewById(R.id.text11111b);
        text226 = findViewById(R.id.text211b);
        text326 = findViewById(R.id.text311b);

        textView6 = findViewById(R.id.text1111c);
        text127 = findViewById(R.id.text11111c);
        text227 = findViewById(R.id.text211c);
        text327 = findViewById(R.id.text311c);

        textView7 = findViewById(R.id.text1111d);
        text128 = findViewById(R.id.text11111d);
        text228 = findViewById(R.id.text211d);
        text328 = findViewById(R.id.text311d);

        textView8 = findViewById(R.id.text1111e);
        text129 = findViewById(R.id.text11111e);
        text229 = findViewById(R.id.text211e);
        text329 = findViewById(R.id.text311e);

        textView9 = findViewById(R.id.text1111f);
        text1210 = findViewById(R.id.text11111f);
        text2210 = findViewById(R.id.text211f);
        text3210 = findViewById(R.id.text311f);

        textView10 = findViewById(R.id.text1111g);
        text1211 = findViewById(R.id.text11111g);
        text2211 = findViewById(R.id.text211g);
        text3211 = findViewById(R.id.text311g);

        textView11 = findViewById(R.id.text1111h);
        text1212 = findViewById(R.id.text11111h);
        text2212 = findViewById(R.id.text211h);
        text3212 = findViewById(R.id.text311h);

        textView12 = findViewById(R.id.text1111i);
        text1213 = findViewById(R.id.text11111i);
        text2213 = findViewById(R.id.text211i);
        text3213 = findViewById(R.id.text311i);

        textView13 = findViewById(R.id.text1111j);
        text1214 = findViewById(R.id.text11111j);
        text2214 = findViewById(R.id.text211j);
        text3214 = findViewById(R.id.text311j);

        textView14 = findViewById(R.id.text1111k);
        text1215 = findViewById(R.id.text11111k);
        text2215 = findViewById(R.id.text211k);
        text3215 = findViewById(R.id.text311k);

        textView15 = findViewById(R.id.text1111l);
        text1216 = findViewById(R.id.text11111l);
        text2216 = findViewById(R.id.text211l);
        text3216 = findViewById(R.id.text311l);

        textView16 = findViewById(R.id.text1111m);
        text1217 = findViewById(R.id.text11111m);
        text2217 = findViewById(R.id.text211m);
        text3217 = findViewById(R.id.text311m);

        textView17 = findViewById(R.id.text1111n);
        text1218 = findViewById(R.id.text11111n);
        text2218 = findViewById(R.id.text211n);
        text3218 = findViewById(R.id.text311n);

        textView18 = findViewById(R.id.text1111o);
        text1219 = findViewById(R.id.text11111o);
        text2219 = findViewById(R.id.text211o);
        text3219 = findViewById(R.id.text311o);

        textView19 = findViewById(R.id.text1111p);
        text1220 = findViewById(R.id.text11111p);
        text2220 = findViewById(R.id.text211p);
        text3220 = findViewById(R.id.text311p);

        textView20 = findViewById(R.id.text1111q);
        text1221 = findViewById(R.id.text11111q);
        text2221 = findViewById(R.id.text211q);
        text3221 = findViewById(R.id.text311q);

        textView21 = findViewById(R.id.text1111r);
        text1222 = findViewById(R.id.text11111r);
        text2222 = findViewById(R.id.text211r);
        text3222 = findViewById(R.id.text311r);

        textView22 = findViewById(R.id.text1111s);
        text1223 = findViewById(R.id.text11111s);
        text2223 = findViewById(R.id.text211s);
        text3223 = findViewById(R.id.text311s);

        textView23 = findViewById(R.id.text1111t);
        text1224 = findViewById(R.id.text11111t);
        text2224 = findViewById(R.id.text211t);
        text3224 = findViewById(R.id.text311t);

        textView24 = findViewById(R.id.text1111u);
        text1225 = findViewById(R.id.text11111u);
        text2225 = findViewById(R.id.text211u);
        text3225 = findViewById(R.id.text311u);

        textView25 = findViewById(R.id.text1111v);
        text1226 = findViewById(R.id.text11111v);
        text2226 = findViewById(R.id.text211v);
        text3226 = findViewById(R.id.text311v);

        textView26 = findViewById(R.id.text1111w);
        text1227 = findViewById(R.id.text11111w);
        text2227 = findViewById(R.id.text211w);
        text3227 = findViewById(R.id.text311w);

        textView27 = findViewById(R.id.text1111x);
        text1228 = findViewById(R.id.text11111x);
        text2228 = findViewById(R.id.text211x);
        text3228 = findViewById(R.id.text311x);

        textView28 = findViewById(R.id.text1111y);
        text1229 = findViewById(R.id.text11111y);
        text2229 = findViewById(R.id.text211y);
        text3229 = findViewById(R.id.text311y);

        textwait1 = findViewById(R.id.time_1);
        textwait2 = findViewById(R.id.time_2);
        textwait3 = findViewById(R.id.time_3);
        textwait4 = findViewById(R.id.time_4);
        textwait5 = findViewById(R.id.time_5);
        textwait6 = findViewById(R.id.time_6);
        textwait7 = findViewById(R.id.time_7);
        textwait8 = findViewById(R.id.time_8);
        textwait9 = findViewById(R.id.time_9);
        textwait10 = findViewById(R.id.time_10);
        textwait11 = findViewById(R.id.time_11);
        textwait12 = findViewById(R.id.time_12);
        textwait13 = findViewById(R.id.time_13);
        textwait14 = findViewById(R.id.time_14);
        textwait15 = findViewById(R.id.time_15);
        textwait16 = findViewById(R.id.time_16);
        textwait17 = findViewById(R.id.time_17);
        textwait18 = findViewById(R.id.time_18);
        textwait19 = findViewById(R.id.time_19);
        textwait20 = findViewById(R.id.time_20);
        textwait21 = findViewById(R.id.time_21);
        textwait22 = findViewById(R.id.time_22);
        textwait23 = findViewById(R.id.time_23);
        textwait24 = findViewById(R.id.time_24);
        textwait25 = findViewById(R.id.time_25);
        textwait26 = findViewById(R.id.time_26);
        textwait27 = findViewById(R.id.time_27);
        textwait28 = findViewById(R.id.time_28);
        textwait29 = findViewById(R.id.time_29);


        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("table_id", Sharedpref.getmPref(getApplicationContext()).gettime());
                intent.putExtra("table_name", Sharedpref.getmPref(getApplicationContext()).getname());
                intent.putExtra("pop_dialog", "1");
                if (fromAdmin != null) {
                    intent.putExtra("admin", "yes");
                }
                startActivity(intent);
            }
        });

        buttonchangetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("table_id", table_id);
                startActivity(intent);
            }
        });
        table_recycle = findViewById(R.id.table_recycle);
        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(TableActivity.this);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        table_recycle.setLayoutManager(layoutManager2);


        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mTextViewCountDown.setText("00:00:00");

        mTextViewCountDown1 = findViewById(R.id.text_view_countdown1);
        mTextViewCountDown1.setText("00:00:00");

        mButtonSet = findViewById(R.id.button_set);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(TableActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(TableActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    //  pauseTimer();
                } else {
                    // startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  resetTimer();
            }
        });
        try {
            broadcast = intent1.getStringExtra("broadcast");
            if (broadcast != null && broadcast.equals("1")) {
                final Intent alarmRing= new Intent(this,AlarmRing.class);  //For the alarm to ring
                startService(alarmRing);
                AlertDialog.Builder builder = new AlertDialog.Builder(TableActivity.this);
                builder.setMessage("Time up, please wrap up");
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       stopService(alarmRing);              //Stop the alarm
                       StopRingtone();
                        dialogInterface.dismiss();
                        Sharedpref.getmPref(getApplicationContext()).counterattack("2");
                        LoadActivty1();
                    }
                });
                alertDialogallnew = builder.create();
                Window window = alertDialogallnew.getWindow();
                if (window != null) {
                    window.setGravity(Gravity.BOTTOM);
                }
                alertDialogallnew.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            alertDialogallnew.dismiss();
                            StopRingtone();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Intent intent2 = new Intent(TableActivity.this, TableActivity.class);
                        intent2.putExtra("table_id", table_id);
                        intent2.putExtra("table_name", table_name);
                        startActivity(intent2);
                       /*if (mCountDownTimer != null) {
                           mCountDownTimer.cancel();
                           handler.removeCallbacksAndMessages(null);
                           Log.e("TAG", "ajithyuo: ");
                       }
                       if (mCountDownTimer1 != null) {
                           mCountDownTimer1.cancel();
                           handler1.removeCallbacksAndMessages(null);
                           Log.e("TAG", "ajithyuo: ");
                       }
                       try{
                           timer.cancel();
                       }catch (Exception e){
                           e.printStackTrace();
                       }
                       if (Sharedpref.getmPref(getApplicationContext()).getActivity() != null &&
                               Sharedpref.getmPref(getApplicationContext()).getActivity().equals("yes")) {

                       }
                       try {
                           Log.e("TAG", "onStopalaram: ");
                           alarmManager.cancel(pendingIntent);
                       } catch (Exception e) {
                           e.printStackTrace();
                       }*/
                        // LoadActivty();
                    }
                }, Integer.parseInt(Sharedpref.getmPref(getApplicationContext()).getgracetime()) * 1000);

                ////////////////////////////////////   cancel timer   /////////////////////////////////////////
                /*if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                    handler.removeCallbacksAndMessages(null);
                    Log.e("TAG", "ajithyuo: ");
                }
                if (mCountDownTimer1 != null) {
                    mCountDownTimer1.cancel();
                    handler1.removeCallbacksAndMessages(null);
                    Log.e("TAG", "ajithyuo: ");
                }
                try{
                    timer.cancel();
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (Sharedpref.getmPref(getApplicationContext()).getActivity() != null &&
                        Sharedpref.getmPref(getApplicationContext()).getActivity().equals("yes")) {

                }*/
                /*try {
                    Log.e("TAG", "onStopalaram: ");
                    alarmManager.cancel(pendingIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                /*try{
                    timer.cancel();
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                //LoadActivty1();
                try {
                    timer.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Sharedpref.getmPref(getApplicationContext()).counterattack("1");
                LoadActivty1();
                Log.e("TAG", "onCreate: " + Integer.parseInt(Sharedpref.getmPref(getApplicationContext()).getgracetime()) * 1000);
            } else {
                LoadActivty();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void LoadActivty() {
        textView.setText("");
        text1.setText("");
        text2.setText("");
        text3.setText("");

        textView1.setText("");
        text12.setText("");
        text22.setText("");
        text32.setText("");

        textView2.setText("");
        text123.setText("");
        text223.setText("");
        text323.setText("");

        textView3.setText("");
        text124.setText("");
        text224.setText("");
        text324.setText("");

        textView4.setText("");
        text125.setText("");
        text225.setText("");
        text325.setText("");

        textView5.setText("");
        text126.setText("");
        text226.setText("");
        text326.setText("");

        textView6.setText("");
        text127.setText("");
        text227.setText("");
        text327.setText("");

        textView7.setText("");
        text128.setText("");
        text228.setText("");
        text328.setText("");

        textView8.setText("");
        text129.setText("");
        text229.setText("");
        text329.setText("");

        textView9.setText("");
        text1210.setText("");
        text2210.setText("");
        text3210.setText("");

        textView10.setText("");
        text1211.setText("");
        text2211.setText("");
        text3211.setText("");

        textView11.setText("");
        text1212.setText("");
        text2212.setText("");
        text3212.setText("");

        textView12.setText("");
        text1213.setText("");
        text2213.setText("");
        text3213.setText("");

        textView13.setText("");
        text1214.setText("");
        text2214.setText("");
        text3214.setText("");

        textView14.setText("");
        text1215.setText("");
        text2215.setText("");
        text3215.setText("");

        textView15.setText("");
        text1216.setText("");
        text2216.setText("");
        text3216.setText("");

        textView16.setText("");
        text1217.setText("");
        text2217.setText("");
        text3217.setText("");

        textView17.setText("");
        text1218.setText("");
        text2218.setText("");
        text3218.setText("");

        textView18.setText("");
        text1219.setText("");
        text2219.setText("");
        text3219.setText("");

        textView19.setText("");
        text1220.setText("");
        text2220.setText("");
        text3220.setText("");

        textView20.setText("");
        text1221.setText("");
        text2221.setText("");
        text3221.setText("");

        textView21.setText("");
        text1222.setText("");
        text2222.setText("");
        text3222.setText("");

        textView22.setText("");
        text1223.setText("");
        text2223.setText("");
        text3223.setText("");

        textView23.setText("");
        text1224.setText("");
        text2224.setText("");
        text3224.setText("");

        textView24.setText("");
        text1225.setText("");
        text2225.setText("");
        text3225.setText("");

        textView25.setText("");
        text1226.setText("");
        text2226.setText("");
        text3226.setText("");

        textView26.setText("");
        text1227.setText("");
        text2227.setText("");
        text3227.setText("");

        textView27.setText("");
        text1228.setText("");
        text2228.setText("");
        text3228.setText("");

        textView28.setText("");
        text1229.setText("");
        text2229.setText("");
        text3229.setText("");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getTables();
                getSetting_camera();
               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("TAG", "timer: ");
                        getTables();
                    }
                });*/
            }
        }, 1000, 10000);
    }

    private void LoadActivty1() {
        getTables();
        getSetting_camera();
    }

    private void getSetting_camera() {
        StringRequest request = new StringRequest(Request.Method.GET, AllWebServices.getSettings, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result_array");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        camera_front = jsonObject1.getString("camera_front");
                        camera_back = jsonObject1.getString("camera_back");
                        Sharedpref.getmPref(getApplicationContext()).cameraBack(camera_front);
                        Sharedpref.getmPref(getApplicationContext()).cameraFront(camera_back);
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
        RequestQueue queue = Myapplication.getInstance().getRequestQueue();
        queue.add(request);
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        //  resetTimer();
        //   closeKeyboard();
    }

    /*private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();
        mTimerRunning = true;
        updateWatchInterface();
    }*/

    /*private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }*/

   /* private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }*/

   /* private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }*/

   /* private void updateWatchInterface() {
        if (mTimerRunning) {
            mEditTextInput.setVisibility(View.GONE);
            mButtonSet.setVisibility(View.GONE);
            mButtonReset.setVisibility(View.GONE);
            mButtonStartPause.setText("Pause");
        } else {
            mEditTextInput.setVisibility(View.GONE);
            mButtonSet.setVisibility(View.GONE);
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.GONE);
            } else {
                mButtonStartPause.setVisibility(View.GONE);
            }

            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.GONE);
            } else {
                mButtonReset.setVisibility(View.GONE);
            }
        }
    }*/

   /* private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            handler.removeCallbacksAndMessages(null);
            Log.e("TAG", "ajithyuo: ");
        }
        if (mCountDownTimer1 != null) {
            mCountDownTimer1.cancel();
            handler1.removeCallbacksAndMessages(null);
            Log.e("TAG", "ajithyuo: ");
        }
        try {
            timer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            //StopRingtone();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (Sharedpref.getmPref(getApplicationContext()).getActivity() != null &&
                Sharedpref.getmPref(getApplicationContext()).getActivity().equals("yes")) {

        }
        try {
            Log.e("TAG", "onStopalaram: ");
            alarmManager.cancel(pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }*/
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* try{
            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();
                handler.removeCallbacksAndMessages(null);
                Log.e("TAG", "ajithyuo: ");
            }
            if (mCountDownTimer1 != null) {
                mCountDownTimer1.cancel();
                handler1.removeCallbacksAndMessages(null);
                Log.e("TAG", "ajithyuo: ");
            }
            try{
                timer.cancel();
            }catch (Exception e){
                e.printStackTrace();
            }
            if (Sharedpref.getmPref(getApplicationContext()).getActivity() != null &&
                    Sharedpref.getmPref(getApplicationContext()).getActivity().equals("yes")) {

            }
            try {
                Log.e("TAG", "onStopalaram: ");
                alarmManager.cancel(pendingIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }*/

       /* SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }*/
    }


    public void getTables() {
//        Toast.makeText(TableActivity.this, "1", Toast.LENGTH_SHORT).show();
        count = 0;
        count1 = 0;
        count2 = 0;
        wait_time_long = 0;
        wait_time_long1 = 0;
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.getTableQueue, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    dismissdialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
               // cardView.setVisibility(View.GONE);
                cardView1.setVisibility(View.GONE);
                cardView2.setVisibility(View.GONE);
                cardView3.setVisibility(View.GONE);
                cardView4.setVisibility(View.GONE);
                cardView5.setVisibility(View.GONE);
                cardView6.setVisibility(View.GONE);
                cardView7.setVisibility(View.GONE);
                cardView8.setVisibility(View.GONE);
                cardView9.setVisibility(View.GONE);
                cardView10.setVisibility(View.GONE);
                cardView11.setVisibility(View.GONE);
                cardView12.setVisibility(View.GONE);
                cardView13.setVisibility(View.GONE);
                cardView14.setVisibility(View.GONE);
                cardView15.setVisibility(View.GONE);
                cardView16.setVisibility(View.GONE);
                cardView17.setVisibility(View.GONE);
                cardView18.setVisibility(View.GONE);
                cardView19.setVisibility(View.GONE);
                cardView20.setVisibility(View.GONE);
                cardView21.setVisibility(View.GONE);
                cardView22.setVisibility(View.GONE);
                cardView23.setVisibility(View.GONE);
                cardView24.setVisibility(View.GONE);
                cardView25.setVisibility(View.GONE);
                cardView26.setVisibility(View.GONE);
                cardView27.setVisibility(View.GONE);
                cardView28.setVisibility(View.GONE);
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    waitime = jsonObject.getString("wait_time");
                    waittime_perqueue = jsonObject.getString("first_wait_time");
                    informtime = jsonObject.getString("first_inform_time");
                    if (waitime.equals("0")) {
                        isentered = null;
                        countsiii = 1;
                        Sharedpref.getmPref(getApplicationContext()).isusertable(null, null, null);
                        Log.e("TAG", "onResponsenull: " + isentered);
                    }


                    if (Sharedpref.getmPref(getApplicationContext()).getcounterattack() != null &&
                            Sharedpref.getmPref(getApplicationContext()).getcounterattack().equals("1") ||
                            Sharedpref.getmPref(getApplicationContext()).getcounterattack() != null &&
                                    Sharedpref.getmPref(getApplicationContext()).getcounterattack().equals("2")) {


                        }else {
                        if (mCountDownTimer != null) {
                            mCountDownTimer.cancel();
                        }
                    }
                    if (!waitime.equals("0")) {

                        JSONArray jsonArrayravi = jsonObject.getJSONArray("result_array");
                        if (jsonArrayravi.length() == 1) {
                            JSONObject jsonObjectravi = jsonArrayravi.getJSONObject(0);
                            String ravitime = jsonObjectravi.getString("time");
                            String ravigt = jsonObjectravi.getString("grace_time");
                            int rtime = Integer.parseInt(ravitime);
                            int rgt   = Integer.parseInt(ravigt)/60;
                            int totalminute = rtime+rgt;

                            int hours = (int) (totalminute*60) / 3600;
                            int minutes = (int) ((totalminute*60) % 3600) / 60;
                            int seconds = (int) (totalminute*60) % 60;
                            timeLeftFormatted = String.format(Locale.getDefault(),
                                    "%02d:%02d:%02d", hours, minutes, seconds);
                            mTextViewCountDown.setText(timeLeftFormatted);
                            mTextViewCountDown1.setText(timeLeftFormatted);
                        }
                        if (jsonArrayravi.length() != 1) {
                            timeLeftFormatted = "";
                            wait_time_long = Long.parseLong(waitime) * 1000;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mCountDownTimer = new CountDownTimer(wait_time_long, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            mTimeLeftInMillis = millisUntilFinished;
                                            int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
                                            int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
                                            int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
                                            timeLeftFormatted = String.format(Locale.getDefault(),
                                                    "%02d:%02d:%02d", hours, minutes, seconds);
                                            Log.e("TAG", "onTickdgg: ");
                                            mTextViewCountDown.setText(timeLeftFormatted);
                                        }
                                        @Override
                                        public void onFinish() {
                                            mTextViewCountDown.setText("00:00:00");
                                        }
                                    }.start();
                                }
                            }, 0);


                            if (Sharedpref.getmPref(getApplicationContext()).getcounterattack() != null &&
                                    Sharedpref.getmPref(getApplicationContext()).getcounterattack().equals("1") ||
                                    Sharedpref.getmPref(getApplicationContext()).getcounterattack() != null &&
                                            Sharedpref.getmPref(getApplicationContext()).getcounterattack().equals("2")) {


                            } else {
                                if (mCountDownTimer1 != null) {
                                    mCountDownTimer1.cancel();
                                }
                            }

                            if (!waittime_perqueue.equals("0")) {
                                timeLeftFormatted1 = "";
                                wait_time_long1 = Long.parseLong(waittime_perqueue) * 1000;
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mCountDownTimer1 = new CountDownTimer(wait_time_long1, 1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                mTimeLeftInMillis1 = millisUntilFinished;
                                                int hours = (int) (mTimeLeftInMillis1 / 1000) / 3600;
                                                int minutes = (int) ((mTimeLeftInMillis1 / 1000) % 3600) / 60;
                                                int seconds = (int) (mTimeLeftInMillis1 / 1000) % 60;
                                                timeLeftFormatted1 = String.format(Locale.getDefault(),
                                                        "%02d:%02d:%02d", hours, minutes, seconds);
                                                Log.e("TAG", "onTickdgg: ");
                                                mTextViewCountDown1.setText(timeLeftFormatted1);
                                            }

                                            @Override
                                            public void onFinish() {
                                                mTextViewCountDown1.setText("00:00:00");
                                            }
                                        }.start();
                                    }
                                }, 0);
                            }

                        }
                       /* mButtonSet.performClick();
                        mButtonStartPause.performClick();*/
                        seat = jsonObject.getString("seat");
                        grace_time = jsonObject.getString("grace_time");
                        JSONArray jsonArray = jsonObject.getJSONArray("result_array");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            timebaba = "";
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            timebaba = jsonObject1.getString("wait_time");
                            String id = jsonObject1.getString("id");
                            String gracetime = jsonObject1.getString("grace_time");
                            // String informtime = jsonObject1.getString("inform_time");

                            if (!timebaba.equals("0")) {
                                count2++;
                                final int finaltime = Integer.parseInt(informtime);
                                Log.e("TAG", "onResponse: " + finaltime);
                                if (count2 == 1) {
                                    if (Sharedpref.getmPref(getApplicationContext()).getcounterattack() != null &&
                                            Sharedpref.getmPref(getApplicationContext()).getcounterattack().equals("1") ||
                                            Sharedpref.getmPref(getApplicationContext()).getcounterattack() != null &&
                                            Sharedpref.getmPref(getApplicationContext()).getcounterattack().equals("2")) {
                                        Sharedpref.getmPref(getApplicationContext()).counterattack(" ");
                                    } else {
                                        Sharedpref.getmPref(getApplicationContext()).checkfirstqueue(id);
                                        Sharedpref.getmPref(getApplicationContext()).checkActivity("yes");
                                        Log.e("TAG", "enteredededed: " + finaltime);
                                        Calendar cal = Calendar.getInstance();
                                        // add 30 seconds to the calendar object
                                        cal.add(Calendar.SECOND, finaltime);
                                        Intent intent = new Intent(context, MyBroadcastReceiver.class);
                                        pendingIntent = PendingIntent.getBroadcast(
                                                context.getApplicationContext(), 234324243, intent, 0);
                                        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                                    }
                                }
                            }


                            avail_seat = jsonObject1.getString("available_seat");
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("member");
                            count++;
                            for (int j = 0; j < jsonArray1.length(); j++) {
                                mTimeLeftInMillis2 = Long.parseLong(timebaba) * 1000;
                                int hours = (int) (mTimeLeftInMillis2 / 1000) / 3600;
                                int minutes = (int) ((mTimeLeftInMillis2 / 1000) % 3600) / 60;
                                int seconds = (int) (mTimeLeftInMillis2 / 1000) % 60;
                                timeLeftFormatted3 = String.format(Locale.getDefault(),
                                        "%02d:%02d:%02d", hours, minutes, seconds);
                                count1++;
                                JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                first_name = jsonObject2.getString("first_name");
                                last_name = jsonObject2.getString("last_name");
                                String member_id = jsonObject2.getString("member_id");
                                if (Sharedpref.getmPref(getApplicationContext()).getmemberids() != null) {
                                    String sharedmemberids = Sharedpref.getmPref(getApplicationContext()).getmemberids();
                                    if (sharedmemberids.equals(member_id)) {

                                    } else {
                                        isentered = null;
                                        Sharedpref.getmPref(getApplicationContext()).isusertable(null, null, null);
                                    }
                                }
                                if (count == 1) {
                                    Sharedpref.getmPref(getApplicationContext()).gracetime(gracetime);
                                    if (count_time1 != null) {
                                        count_time1.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time1 = "";
                                        wait_time_wait1 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time1 = new CountDownTimer(wait_time_wait1, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time1 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time1 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time1 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time1 / 1000) % 60;
                                                        getTimeLeftFormatted_time1 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait1.setText(getTimeLeftFormatted_time1);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait1.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    Log.e("TAG", "ajifdfhdfbdbfbfjbf: " + textwait1.getText().toString());
                                    if (seat.equals("1")) {
                                        if (count1 == 1) {
                                            textView.setText(first_name + " " + last_name);
                                            text1.setVisibility(View.GONE);
                                            text2.setVisibility(View.GONE);
                                            text3.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 1)
                                            textView.setText(first_name + " " + last_name);
                                        else if (count1 == 2)
                                            text1.setText(first_name + " " + last_name);
                                        text2.setVisibility(View.GONE);
                                        text3.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 1)
                                            textView.setText(first_name + " " + last_name);
                                        else if (count1 == 2)
                                            text1.setText(first_name + " " + last_name);
                                        else if (count1 == 3)
                                            text2.setText(first_name + " " + last_name);
                                        text3.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 1)
                                            textView.setText(first_name + " " + last_name);
                                        else if (count1 == 2)
                                            text1.setText(first_name + " " + last_name);
                                        else if (count1 == 3)
                                            text2.setText(first_name + " " + last_name);
                                        else if (count1 == 4)
                                            text3.setText(first_name + " " + last_name);
                                    }
                                    cardView.setVisibility(View.VISIBLE);
                                    cardView1.setVisibility(View.GONE);
                                    cardView2.setVisibility(View.GONE);
                                    cardView3.setVisibility(View.GONE);
                                    cardView4.setVisibility(View.GONE);
                                    cardView5.setVisibility(View.GONE);
                                    cardView6.setVisibility(View.GONE);
                                    cardView7.setVisibility(View.GONE);
                                    cardView8.setVisibility(View.GONE);
                                    cardView9.setVisibility(View.GONE);
                                    cardView10.setVisibility(View.GONE);
                                    cardView11.setVisibility(View.GONE);
                                    cardView12.setVisibility(View.GONE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);

                                } else if (count == 2) {
                                    if (count_time2 != null) {
                                        count_time2.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time2 = "";
                                        wait_time_wait2 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time2 = new CountDownTimer(wait_time_wait2, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time2 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time2 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time2 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time2 / 1000) % 60;
                                                        getTimeLeftFormatted_time2 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait2.setText(getTimeLeftFormatted_time2);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait2.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 2) {
                                            textView2.setText(first_name + " " + last_name);
                                            text123.setVisibility(View.GONE);
                                            text223.setVisibility(View.GONE);
                                            text323.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 3)
                                            textView2.setText(first_name + " " + last_name);
                                        else if (count1 == 4)
                                            text123.setText(first_name + " " + last_name);
                                        text223.setVisibility(View.GONE);
                                        text323.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 4)
                                            textView2.setText(first_name + " " + last_name);
                                        else if (count1 == 5)
                                            text123.setText(first_name + " " + last_name);
                                        else if (count1 == 6)
                                            text223.setText(first_name + " " + last_name);
                                        text323.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 5)
                                            textView2.setText(first_name + " " + last_name);
                                        else if (count1 == 6)
                                            text123.setText(first_name + " " + last_name);
                                        else if (count1 == 7)
                                            text223.setText(first_name + " " + last_name);
                                        else if (count1 == 8)
                                            text323.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.GONE);
                                    cardView3.setVisibility(View.GONE);
                                    cardView4.setVisibility(View.GONE);
                                    cardView5.setVisibility(View.GONE);
                                    cardView6.setVisibility(View.GONE);
                                    cardView7.setVisibility(View.GONE);
                                    cardView8.setVisibility(View.GONE);
                                    cardView9.setVisibility(View.GONE);
                                    cardView10.setVisibility(View.GONE);
                                    cardView11.setVisibility(View.GONE);
                                    cardView12.setVisibility(View.GONE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 3) {
                                    if (count_time3 != null) {
                                        count_time3.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time3 = "";
                                        wait_time_wait3 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time3 = new CountDownTimer(wait_time_wait3, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time3 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time3 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time3 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time3 / 1000) % 60;
                                                        getTimeLeftFormatted_time3 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait3.setText(getTimeLeftFormatted_time3);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait3.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 3) {
                                            textView3.setText(first_name + " " + last_name);
                                            text124.setVisibility(View.GONE);
                                            text224.setVisibility(View.GONE);
                                            text324.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 5)
                                            textView3.setText(first_name + " " + last_name);
                                        else if (count1 == 6)
                                            text124.setText(first_name + " " + last_name);
                                        text224.setVisibility(View.GONE);
                                        text324.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 7)
                                            textView3.setText(first_name + " " + last_name);
                                        else if (count1 == 8)
                                            text124.setText(first_name + " " + last_name);
                                        else if (count1 == 9)
                                            text224.setText(first_name + " " + last_name);
                                        text324.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 9)
                                            textView3.setText(first_name + " " + last_name);
                                        else if (count1 == 10)
                                            text124.setText(first_name + " " + last_name);
                                        else if (count1 == 11)
                                            text224.setText(first_name + " " + last_name);
                                        else if (count1 == 12)
                                            text324.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.GONE);
                                    cardView4.setVisibility(View.GONE);
                                    cardView5.setVisibility(View.GONE);
                                    cardView6.setVisibility(View.GONE);
                                    cardView7.setVisibility(View.GONE);
                                    cardView8.setVisibility(View.GONE);
                                    cardView9.setVisibility(View.GONE);
                                    cardView10.setVisibility(View.GONE);
                                    cardView11.setVisibility(View.GONE);
                                    cardView12.setVisibility(View.GONE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);

                                } else if (count == 4) {
                                    if (count_time4 != null) {
                                        count_time4.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time4 = "";
                                        wait_time_wait4 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time4 = new CountDownTimer(wait_time_wait4, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time4 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time4 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time4 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time4 / 1000) % 60;
                                                        getTimeLeftFormatted_time4 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait4.setText(getTimeLeftFormatted_time4);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait4.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 4) {
                                            textView4.setText(first_name + " " + last_name);
                                            text125.setVisibility(View.GONE);
                                            text225.setVisibility(View.GONE);
                                            text325.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 7)
                                            textView4.setText(first_name + " " + last_name);
                                        else if (count1 == 8)
                                            text125.setText(first_name + " " + last_name);
                                        text225.setVisibility(View.GONE);
                                        text325.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 10)
                                            textView4.setText(first_name + " " + last_name);
                                        else if (count1 == 11)
                                            text125.setText(first_name + " " + last_name);
                                        else if (count1 == 12)
                                            text225.setText(first_name + " " + last_name);
                                        text325.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 13)
                                            textView4.setText(first_name + " " + last_name);
                                        else if (count1 == 14)
                                            text125.setText(first_name + " " + last_name);
                                        else if (count1 == 15)
                                            text225.setText(first_name + " " + last_name);
                                        else if (count1 == 16)
                                            text325.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.GONE);
                                    cardView5.setVisibility(View.GONE);
                                    cardView6.setVisibility(View.GONE);
                                    cardView7.setVisibility(View.GONE);
                                    cardView8.setVisibility(View.GONE);
                                    cardView9.setVisibility(View.GONE);
                                    cardView10.setVisibility(View.GONE);
                                    cardView11.setVisibility(View.GONE);
                                    cardView12.setVisibility(View.GONE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 5) {
                                    if (count_time5 != null) {
                                        count_time5.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time5 = "";
                                        wait_time_wait5 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time5 = new CountDownTimer(wait_time_wait5, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time5 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time5 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time5 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time5 / 1000) % 60;
                                                        getTimeLeftFormatted_time5 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait5.setText(getTimeLeftFormatted_time5);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait5.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 5) {
                                            textView5.setText(first_name + " " + last_name);
                                            text126.setVisibility(View.GONE);
                                            text226.setVisibility(View.GONE);
                                            text326.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 9)
                                            textView5.setText(first_name + " " + last_name);
                                        else if (count1 == 10)
                                            text126.setText(first_name + " " + last_name);
                                        text226.setVisibility(View.GONE);
                                        text326.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 13)
                                            textView5.setText(first_name + " " + last_name);
                                        else if (count1 == 14)
                                            text126.setText(first_name + " " + last_name);
                                        else if (count1 == 15)
                                            text226.setText(first_name + " " + last_name);
                                        text326.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 17)
                                            textView5.setText(first_name + " " + last_name);
                                        else if (count1 == 18)
                                            text126.setText(first_name + " " + last_name);
                                        else if (count1 == 19)
                                            text226.setText(first_name + " " + last_name);
                                        else if (count1 == 20)
                                            text326.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.GONE);
                                    cardView6.setVisibility(View.GONE);
                                    cardView7.setVisibility(View.GONE);
                                    cardView8.setVisibility(View.GONE);
                                    cardView9.setVisibility(View.GONE);
                                    cardView10.setVisibility(View.GONE);
                                    cardView11.setVisibility(View.GONE);
                                    cardView12.setVisibility(View.GONE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);

                                } else if (count == 6) {
                                    if (count_time6 != null) {
                                        count_time6.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time6 = "";
                                        wait_time_wait6 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time6 = new CountDownTimer(wait_time_wait6, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time6 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time6 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time6 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time6 / 1000) % 60;
                                                        getTimeLeftFormatted_time6 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait6.setText(getTimeLeftFormatted_time6);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait6.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 6) {
                                            textView5.setText(first_name + " " + last_name);
                                            text126.setVisibility(View.GONE);
                                            text226.setVisibility(View.GONE);
                                            text326.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 11)
                                            textView6.setText(first_name + " " + last_name);
                                        else if (count1 == 12)
                                            text127.setText(first_name + " " + last_name);
                                        text227.setVisibility(View.GONE);
                                        text327.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 16)
                                            textView6.setText(first_name + " " + last_name);
                                        else if (count1 == 17)
                                            text127.setText(first_name + " " + last_name);
                                        else if (count1 == 18)
                                            text227.setText(first_name + " " + last_name);
                                        text327.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 21)
                                            textView6.setText(first_name + " " + last_name);
                                        else if (count1 == 22)
                                            text127.setText(first_name + " " + last_name);
                                        else if (count1 == 23)
                                            text227.setText(first_name + " " + last_name);
                                        else if (count1 == 24)
                                            text327.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.GONE);
                                    cardView7.setVisibility(View.GONE);
                                    cardView8.setVisibility(View.GONE);
                                    cardView9.setVisibility(View.GONE);
                                    cardView10.setVisibility(View.GONE);
                                    cardView11.setVisibility(View.GONE);
                                    cardView12.setVisibility(View.GONE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 7) {
                                    if (count_time7 != null) {
                                        count_time7.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time7 = "";
                                        wait_time_wait7 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time7 = new CountDownTimer(wait_time_wait7, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time7 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time7 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time7 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time7 / 1000) % 60;
                                                        getTimeLeftFormatted_time7 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait7.setText(getTimeLeftFormatted_time7);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait7.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 7) {
                                            textView7.setText(first_name + " " + last_name);
                                            text128.setVisibility(View.GONE);
                                            text228.setVisibility(View.GONE);
                                            text328.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 13)
                                            textView7.setText(first_name + " " + last_name);
                                        else if (count1 == 14)
                                            text128.setText(first_name + " " + last_name);
                                        text228.setVisibility(View.GONE);
                                        text328.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 19)
                                            textView7.setText(first_name + " " + last_name);
                                        else if (count1 == 20)
                                            text128.setText(first_name + " " + last_name);
                                        else if (count1 == 21)
                                            text228.setText(first_name + " " + last_name);
                                        text328.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 25)
                                            textView7.setText(first_name + " " + last_name);
                                        else if (count1 == 26)
                                            text128.setText(first_name + " " + last_name);
                                        else if (count1 == 27)
                                            text228.setText(first_name + " " + last_name);
                                        else if (count1 == 28)
                                            text328.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.GONE);
                                    cardView8.setVisibility(View.GONE);
                                    cardView9.setVisibility(View.GONE);
                                    cardView10.setVisibility(View.GONE);
                                    cardView11.setVisibility(View.GONE);
                                    cardView12.setVisibility(View.GONE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 8) {
                                    if (count_time8 != null) {
                                        count_time8.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time8 = "";
                                        wait_time_wait8 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time8 = new CountDownTimer(wait_time_wait8, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time8 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time8 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time8 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time8 / 1000) % 60;
                                                        getTimeLeftFormatted_time8 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait8.setText(getTimeLeftFormatted_time8);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait8.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 8) {
                                            textView8.setText(first_name + " " + last_name);
                                            text129.setVisibility(View.GONE);
                                            text229.setVisibility(View.GONE);
                                            text329.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 15)
                                            textView8.setText(first_name + " " + last_name);
                                        else if (count1 == 16)
                                            text129.setText(first_name + " " + last_name);
                                        text229.setVisibility(View.GONE);
                                        text329.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 22)
                                            textView8.setText(first_name + " " + last_name);
                                        else if (count1 == 23)
                                            text129.setText(first_name + " " + last_name);
                                        else if (count1 == 24)
                                            text229.setText(first_name + " " + last_name);
                                        text329.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 29)
                                            textView8.setText(first_name + " " + last_name);
                                        else if (count1 == 30)
                                            text129.setText(first_name + " " + last_name);
                                        else if (count1 == 31)
                                            text229.setText(first_name + " " + last_name);
                                        else if (count1 == 32)
                                            text329.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.GONE);
                                    cardView9.setVisibility(View.GONE);
                                    cardView10.setVisibility(View.GONE);
                                    cardView11.setVisibility(View.GONE);
                                    cardView12.setVisibility(View.GONE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 9) {
                                    if (count_time9 != null) {
                                        count_time9.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time9 = "";
                                        wait_time_wait9 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time9 = new CountDownTimer(wait_time_wait9, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time9 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time9 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time9 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time9 / 1000) % 60;
                                                        getTimeLeftFormatted_time9 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait9.setText(getTimeLeftFormatted_time9);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait9.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 9) {
                                            textView9.setText(first_name + " " + last_name);
                                            text1210.setVisibility(View.GONE);
                                            text2210.setVisibility(View.GONE);
                                            text3210.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 17)
                                            textView9.setText(first_name + " " + last_name);
                                        else if (count1 == 18)
                                            text1210.setText(first_name + " " + last_name);
                                        text2210.setVisibility(View.GONE);
                                        text3210.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 25)
                                            textView9.setText(first_name + " " + last_name);
                                        else if (count1 == 26)
                                            text1210.setText(first_name + " " + last_name);
                                        else if (count1 == 27)
                                            text2210.setText(first_name + " " + last_name);
                                        text3210.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 33)
                                            textView9.setText(first_name + " " + last_name);
                                        else if (count1 == 34)
                                            text1210.setText(first_name + " " + last_name);
                                        else if (count1 == 35)
                                            text2210.setText(first_name + " " + last_name);
                                        else if (count1 == 36)
                                            text3210.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.GONE);
                                    cardView10.setVisibility(View.GONE);
                                    cardView11.setVisibility(View.GONE);
                                    cardView12.setVisibility(View.GONE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 10) {
                                    if (count_time10 != null) {
                                        count_time10.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time10 = "";
                                        wait_time_wait10 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time10 = new CountDownTimer(wait_time_wait10, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time10 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time10 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time10 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time10 / 1000) % 60;
                                                        getTimeLeftFormatted_time10 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait10.setText(getTimeLeftFormatted_time10);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait10.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 10) {
                                            textView10.setText(first_name + " " + last_name);
                                            text1211.setVisibility(View.GONE);
                                            text2211.setVisibility(View.GONE);
                                            text3211.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 19)
                                            textView10.setText(first_name + " " + last_name);
                                        else if (count1 == 20)
                                            text1211.setText(first_name + " " + last_name);
                                        text2211.setVisibility(View.GONE);
                                        text3211.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 28)
                                            textView10.setText(first_name + " " + last_name);
                                        else if (count1 == 29)
                                            text1211.setText(first_name + " " + last_name);
                                        else if (count1 == 30)
                                            text2211.setText(first_name + " " + last_name);
                                        text3211.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 33)
                                            textView10.setText(first_name + " " + last_name);
                                        else if (count1 == 34)
                                            text1211.setText(first_name + " " + last_name);
                                        else if (count1 == 35)
                                            text2211.setText(first_name + " " + last_name);
                                        else if (count1 == 36)
                                            text3211.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.GONE);
                                    cardView11.setVisibility(View.GONE);
                                    cardView12.setVisibility(View.GONE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 11) {
                                    if (count_time11 != null) {
                                        count_time11.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time11 = "";
                                        wait_time_wait11 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time11 = new CountDownTimer(wait_time_wait11, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time11 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time11 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time11 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time11 / 1000) % 60;
                                                        getTimeLeftFormatted_time11 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait11.setText(getTimeLeftFormatted_time11);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait11.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 11) {
                                            textView11.setText(first_name + " " + last_name);
                                            text1212.setVisibility(View.GONE);
                                            text2212.setVisibility(View.GONE);
                                            text3212.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 21)
                                            textView11.setText(first_name + " " + last_name);
                                        else if (count1 == 22)
                                            text1212.setText(first_name + " " + last_name);
                                        text2212.setVisibility(View.GONE);
                                        text3212.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 31)
                                            textView11.setText(first_name + " " + last_name);
                                        else if (count1 == 32)
                                            text1212.setText(first_name + " " + last_name);
                                        else if (count1 == 33)
                                            text2212.setText(first_name + " " + last_name);
                                        text3212.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 37)
                                            textView11.setText(first_name + " " + last_name);
                                        else if (count1 == 38)
                                            text1212.setText(first_name + " " + last_name);
                                        else if (count1 == 39)
                                            text2212.setText(first_name + " " + last_name);
                                        else if (count1 == 40)
                                            text3212.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.GONE);
                                    cardView12.setVisibility(View.GONE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 12) {
                                    if (count_time12 != null) {
                                        count_time12.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time12 = "";
                                        wait_time_wait12 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time12 = new CountDownTimer(wait_time_wait12, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time12 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time12 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time12 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time12 / 1000) % 60;
                                                        getTimeLeftFormatted_time12 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait12.setText(getTimeLeftFormatted_time12);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait12.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 12) {
                                            textView12.setText(first_name + " " + last_name);
                                            text1213.setVisibility(View.GONE);
                                            text2213.setVisibility(View.GONE);
                                            text3213.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 23)
                                            textView12.setText(first_name + " " + last_name);
                                        else if (count1 == 24)
                                            text1213.setText(first_name + " " + last_name);
                                        text2213.setVisibility(View.GONE);
                                        text3213.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 34)
                                            textView12.setText(first_name + " " + last_name);
                                        else if (count1 == 35)
                                            text1213.setText(first_name + " " + last_name);
                                        else if (count1 == 36)
                                            text2213.setText(first_name + " " + last_name);
                                        text3213.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 41)
                                            textView12.setText(first_name + " " + last_name);
                                        else if (count1 == 42)
                                            text1213.setText(first_name + " " + last_name);
                                        else if (count1 == 43)
                                            text2213.setText(first_name + " " + last_name);
                                        else if (count1 == 44)
                                            text3213.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.GONE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 13) {
                                    if (count_time13 != null) {
                                        count_time13.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time13 = "";
                                        wait_time_wait13 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time13 = new CountDownTimer(wait_time_wait13, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time13 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time13 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time13 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time13 / 1000) % 60;
                                                        getTimeLeftFormatted_time13 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait13.setText(getTimeLeftFormatted_time13);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait13.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 13) {
                                            textView13.setText(first_name + " " + last_name);
                                            text1214.setVisibility(View.GONE);
                                            text2214.setVisibility(View.GONE);
                                            text3214.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 25)
                                            textView13.setText(first_name + " " + last_name);
                                        else if (count1 == 26)
                                            text1214.setText(first_name + " " + last_name);
                                        text2214.setVisibility(View.GONE);
                                        text3214.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 37)
                                            textView13.setText(first_name + " " + last_name);
                                        else if (count1 == 38)
                                            text1214.setText(first_name + " " + last_name);
                                        else if (count1 == 39)
                                            text2214.setText(first_name + " " + last_name);
                                        text3214.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 45)
                                            textView13.setText(first_name + " " + last_name);
                                        else if (count1 == 46)
                                            text1214.setText(first_name + " " + last_name);
                                        else if (count1 == 47)
                                            text2214.setText(first_name + " " + last_name);
                                        else if (count1 == 48)
                                            text3214.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.GONE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 14) {
                                    if (count_time14 != null) {
                                        count_time14.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time14 = "";
                                        wait_time_wait14 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time14 = new CountDownTimer(wait_time_wait14, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time14 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time14 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time14 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time14 / 1000) % 60;
                                                        getTimeLeftFormatted_time14 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait14.setText(getTimeLeftFormatted_time14);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait14.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 14) {
                                            textView14.setText(first_name + " " + last_name);
                                            text1215.setVisibility(View.GONE);
                                            text2215.setVisibility(View.GONE);
                                            text3215.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 27)
                                            textView14.setText(first_name + " " + last_name);
                                        else if (count1 == 28)
                                            text1215.setText(first_name + " " + last_name);
                                        text2215.setVisibility(View.GONE);
                                        text3215.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 40)
                                            textView14.setText(first_name + " " + last_name);
                                        else if (count1 == 41)
                                            text1215.setText(first_name + " " + last_name);
                                        else if (count1 == 42)
                                            text2215.setText(first_name + " " + last_name);
                                        text3215.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 49)
                                            textView14.setText(first_name + " " + last_name);
                                        else if (count1 == 50)
                                            text1215.setText(first_name + " " + last_name);
                                        else if (count1 == 51)
                                            text2215.setText(first_name + " " + last_name);
                                        else if (count1 == 52)
                                            text3215.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.GONE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 15) {
                                    if (count_time15 != null) {
                                        count_time15.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time15 = "";
                                        wait_time_wait15 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time15 = new CountDownTimer(wait_time_wait15, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time15 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time15 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time15 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time15 / 1000) % 60;

                                                        getTimeLeftFormatted_time15 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait15.setText(getTimeLeftFormatted_time15);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait15.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 15) {
                                            textView15.setText(first_name + " " + last_name);
                                            text1216.setVisibility(View.GONE);
                                            text2216.setVisibility(View.GONE);
                                            text3216.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 29)
                                            textView15.setText(first_name + " " + last_name);
                                        else if (count1 == 30)
                                            text1216.setText(first_name + " " + last_name);
                                        text2216.setVisibility(View.GONE);
                                        text3216.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 43)
                                            textView15.setText(first_name + " " + last_name);
                                        else if (count1 == 44)
                                            text1216.setText(first_name + " " + last_name);
                                        else if (count1 == 45)
                                            text2216.setText(first_name + " " + last_name);
                                        text3216.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 53)
                                            textView15.setText(first_name + " " + last_name);
                                        else if (count1 == 54)
                                            text1216.setText(first_name + " " + last_name);
                                        else if (count1 == 55)
                                            text2216.setText(first_name + " " + last_name);
                                        else if (count1 == 56)
                                            text3216.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.GONE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 16) {
                                    if (count_time16 != null) {
                                        count_time16.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time16 = "";
                                        wait_time_wait16 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time16 = new CountDownTimer(wait_time_wait16, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time16 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time16 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time16 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time16 / 1000) % 60;
                                                        getTimeLeftFormatted_time16 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait16.setText(getTimeLeftFormatted_time16);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait16.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 16) {
                                            textView16.setText(first_name + " " + last_name);
                                            text1217.setVisibility(View.GONE);
                                            text2217.setVisibility(View.GONE);
                                            text3217.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 31)
                                            textView16.setText(first_name + " " + last_name);
                                        else if (count1 == 32)
                                            text1217.setText(first_name + " " + last_name);
                                        text2217.setVisibility(View.GONE);
                                        text3217.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 46)
                                            textView16.setText(first_name + " " + last_name);
                                        else if (count1 == 47)
                                            text1217.setText(first_name + " " + last_name);
                                        else if (count1 == 48)
                                            text2217.setText(first_name + " " + last_name);
                                        text3217.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 57)
                                            textView16.setText(first_name + " " + last_name);
                                        else if (count1 == 58)
                                            text1217.setText(first_name + " " + last_name);
                                        else if (count1 == 59)
                                            text2217.setText(first_name + " " + last_name);
                                        else if (count1 == 60)
                                            text3217.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.GONE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 17) {
                                    if (count_time17 != null) {
                                        count_time17.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time17 = "";
                                        wait_time_wait17 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time17 = new CountDownTimer(wait_time_wait17, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time17 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time17 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time17 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time17 / 1000) % 60;
                                                        getTimeLeftFormatted_time17 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait17.setText(getTimeLeftFormatted_time17);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait17.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 17) {
                                            textView17.setText(first_name + " " + last_name);
                                            text1218.setVisibility(View.GONE);
                                            text2218.setVisibility(View.GONE);
                                            text3218.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 33)
                                            textView17.setText(first_name + " " + last_name);
                                        else if (count1 == 34)
                                            text1218.setText(first_name + " " + last_name);
                                        text2218.setVisibility(View.GONE);
                                        text3218.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 49)
                                            textView17.setText(first_name + " " + last_name);
                                        else if (count1 == 50)
                                            text1218.setText(first_name + " " + last_name);
                                        else if (count1 == 51)
                                            text2218.setText(first_name + " " + last_name);
                                        text3218.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 61)
                                            textView17.setText(first_name + " " + last_name);
                                        else if (count1 == 62)
                                            text1218.setText(first_name + " " + last_name);
                                        else if (count1 == 63)
                                            text2218.setText(first_name + " " + last_name);
                                        else if (count1 == 64)
                                            text3218.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.GONE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 18) {
                                    if (count_time18 != null) {
                                        count_time18.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time18 = "";
                                        wait_time_wait18 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time18 = new CountDownTimer(wait_time_wait18, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time18 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time18 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time18 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time18 / 1000) % 60;
                                                        getTimeLeftFormatted_time18 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait18.setText(getTimeLeftFormatted_time18);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait18.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 18) {
                                            textView18.setText(first_name + " " + last_name);
                                            text1219.setVisibility(View.GONE);
                                            text2219.setVisibility(View.GONE);
                                            text3219.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 35)
                                            textView18.setText(first_name + " " + last_name);
                                        else if (count1 == 36)
                                            text1219.setText(first_name + " " + last_name);
                                        text2219.setVisibility(View.GONE);
                                        text3219.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 52)
                                            textView18.setText(first_name + " " + last_name);
                                        else if (count1 == 53)
                                            text1219.setText(first_name + " " + last_name);
                                        else if (count1 == 54)
                                            text2219.setText(first_name + " " + last_name);
                                        text3219.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 65)
                                            textView18.setText(first_name + " " + last_name);
                                        else if (count1 == 66)
                                            text1219.setText(first_name + " " + last_name);
                                        else if (count1 == 67)
                                            text2219.setText(first_name + " " + last_name);
                                        else if (count1 == 68)
                                            text3219.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.GONE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 19) {
                                    if (count_time19 != null) {
                                        count_time19.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time19 = "";
                                        wait_time_wait19 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time19 = new CountDownTimer(wait_time_wait19, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time19 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time19 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time19 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time19 / 1000) % 60;
                                                        getTimeLeftFormatted_time19 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait19.setText(getTimeLeftFormatted_time19);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait19.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 19) {
                                            textView19.setText(first_name + " " + last_name);
                                            text1220.setVisibility(View.GONE);
                                            text2220.setVisibility(View.GONE);
                                            text3220.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 37)
                                            textView19.setText(first_name + " " + last_name);
                                        else if (count1 == 38)
                                            text1220.setText(first_name + " " + last_name);
                                        text2220.setVisibility(View.GONE);
                                        text3220.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 55)
                                            textView19.setText(first_name + " " + last_name);
                                        else if (count1 == 56)
                                            text1220.setText(first_name + " " + last_name);
                                        else if (count1 == 57)
                                            text2220.setText(first_name + " " + last_name);
                                        text3220.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 69)
                                            textView19.setText(first_name + " " + last_name);
                                        else if (count1 == 70)
                                            text1220.setText(first_name + " " + last_name);
                                        else if (count1 == 71)
                                            text2220.setText(first_name + " " + last_name);
                                        else if (count1 == 72)
                                            text3220.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.VISIBLE);
                                    cardView19.setVisibility(View.GONE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 20) {
                                    if (count_time20 != null) {
                                        count_time20.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time20 = "";
                                        wait_time_wait20 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time20 = new CountDownTimer(wait_time_wait20, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time20 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time20 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time20 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time20 / 1000) % 60;
                                                        getTimeLeftFormatted_time20 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait20.setText(getTimeLeftFormatted_time20);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait20.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 20) {
                                            textView20.setText(first_name + " " + last_name);
                                            text1221.setVisibility(View.GONE);
                                            text2221.setVisibility(View.GONE);
                                            text3221.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 39)
                                            textView20.setText(first_name + " " + last_name);
                                        else if (count1 == 40)
                                            text1221.setText(first_name + " " + last_name);
                                        text2221.setVisibility(View.GONE);
                                        text3221.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 58)
                                            textView20.setText(first_name + " " + last_name);
                                        else if (count1 == 59)
                                            text1221.setText(first_name + " " + last_name);
                                        else if (count1 == 60)
                                            text2221.setText(first_name + " " + last_name);
                                        text3221.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 73)
                                            textView20.setText(first_name + " " + last_name);
                                        else if (count1 == 74)
                                            text1221.setText(first_name + " " + last_name);
                                        else if (count1 == 75)
                                            text2221.setText(first_name + " " + last_name);
                                        else if (count1 == 76)
                                            text3221.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.VISIBLE);
                                    cardView19.setVisibility(View.VISIBLE);
                                    cardView20.setVisibility(View.GONE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 21) {
                                    if (count_time21 != null) {
                                        count_time21.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time21 = "";
                                        wait_time_wait21 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time21 = new CountDownTimer(wait_time_wait21, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time21 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time21 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time21 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time21 / 1000) % 60;
                                                        getTimeLeftFormatted_time21 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait21.setText(getTimeLeftFormatted_time21);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait21.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 21) {
                                            textView21.setText(first_name + " " + last_name);
                                            text1222.setVisibility(View.GONE);
                                            text2222.setVisibility(View.GONE);
                                            text3222.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 41)
                                            textView21.setText(first_name + " " + last_name);
                                        else if (count1 == 42)
                                            text1222.setText(first_name + " " + last_name);
                                        text2222.setVisibility(View.GONE);
                                        text3222.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 61)
                                            textView21.setText(first_name + " " + last_name);
                                        else if (count1 == 62)
                                            text1222.setText(first_name + " " + last_name);
                                        else if (count1 == 63)
                                            text2222.setText(first_name + " " + last_name);
                                        text3222.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 77)
                                            textView21.setText(first_name + " " + last_name);
                                        else if (count1 == 78)
                                            text1222.setText(first_name + " " + last_name);
                                        else if (count1 == 79)
                                            text2222.setText(first_name + " " + last_name);
                                        else if (count1 == 80)
                                            text3222.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.VISIBLE);
                                    cardView19.setVisibility(View.VISIBLE);
                                    cardView20.setVisibility(View.VISIBLE);
                                    cardView21.setVisibility(View.GONE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 22) {
                                    if (count_time22 != null) {
                                        count_time22.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time22 = "";
                                        wait_time_wait22 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time22 = new CountDownTimer(wait_time_wait22, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time22 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time22 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time22 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time22 / 1000) % 60;
                                                        getTimeLeftFormatted_time22 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait22.setText(getTimeLeftFormatted_time22);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait22.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 22) {
                                            textView22.setText(first_name + " " + last_name);
                                            text1223.setVisibility(View.GONE);
                                            text2223.setVisibility(View.GONE);
                                            text3223.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 43)
                                            textView22.setText(first_name + " " + last_name);
                                        else if (count1 == 44)
                                            text1223.setText(first_name + " " + last_name);
                                        text2223.setVisibility(View.GONE);
                                        text3223.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 64)
                                            textView22.setText(first_name + " " + last_name);
                                        else if (count1 == 65)
                                            text1223.setText(first_name + " " + last_name);
                                        else if (count1 == 66)
                                            text2223.setText(first_name + " " + last_name);
                                        text3223.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 81)
                                            textView22.setText(first_name + " " + last_name);
                                        else if (count1 == 82)
                                            text1223.setText(first_name + " " + last_name);
                                        else if (count1 == 83)
                                            text2223.setText(first_name + " " + last_name);
                                        else if (count1 == 84)
                                            text3223.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.VISIBLE);
                                    cardView19.setVisibility(View.VISIBLE);
                                    cardView20.setVisibility(View.VISIBLE);
                                    cardView21.setVisibility(View.VISIBLE);
                                    cardView22.setVisibility(View.GONE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 23) {
                                    if (count_time23 != null) {
                                        count_time23.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time23 = "";
                                        wait_time_wait23 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time23 = new CountDownTimer(wait_time_wait23, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time23 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time23 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time23 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time23 / 1000) % 60;
                                                        getTimeLeftFormatted_time23 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait23.setText(getTimeLeftFormatted_time23);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait23.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 23) {
                                            textView23.setText(first_name + " " + last_name);
                                            text1224.setVisibility(View.GONE);
                                            text2224.setVisibility(View.GONE);
                                            text3224.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 45)
                                            textView23.setText(first_name + " " + last_name);
                                        else if (count1 == 46)
                                            text1224.setText(first_name + " " + last_name);
                                        text2224.setVisibility(View.GONE);
                                        text3224.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 67)
                                            textView23.setText(first_name + " " + last_name);
                                        else if (count1 == 68)
                                            text1224.setText(first_name + " " + last_name);
                                        else if (count1 == 69)
                                            text2224.setText(first_name + " " + last_name);
                                        text3224.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 85)
                                            textView23.setText(first_name + " " + last_name);
                                        else if (count1 == 86)
                                            text1224.setText(first_name + " " + last_name);
                                        else if (count1 == 87)
                                            text2224.setText(first_name + " " + last_name);
                                        else if (count1 == 88)
                                            text3224.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.VISIBLE);
                                    cardView19.setVisibility(View.VISIBLE);
                                    cardView20.setVisibility(View.VISIBLE);
                                    cardView21.setVisibility(View.VISIBLE);
                                    cardView22.setVisibility(View.VISIBLE);
                                    cardView23.setVisibility(View.GONE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 24) {
                                    if (count_time24 != null) {
                                        count_time24.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time24 = "";
                                        wait_time_wait24 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time24 = new CountDownTimer(wait_time_wait24, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time24 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time24 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time24 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time24 / 1000) % 60;
                                                        getTimeLeftFormatted_time24 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait24.setText(getTimeLeftFormatted_time24);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait24.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 24) {
                                            textView24.setText(first_name + " " + last_name);
                                            text1225.setVisibility(View.GONE);
                                            text2225.setVisibility(View.GONE);
                                            text3225.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 47)
                                            textView24.setText(first_name + " " + last_name);
                                        else if (count1 == 48)
                                            text1225.setText(first_name + " " + last_name);
                                        text2225.setVisibility(View.GONE);
                                        text3225.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 70)
                                            textView24.setText(first_name + " " + last_name);
                                        else if (count1 == 71)
                                            text1225.setText(first_name + " " + last_name);
                                        else if (count1 == 72)
                                            text2225.setText(first_name + " " + last_name);
                                        text3225.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 89)
                                            textView24.setText(first_name + " " + last_name);
                                        else if (count1 == 90)
                                            text1225.setText(first_name + " " + last_name);
                                        else if (count1 == 91)
                                            text2225.setText(first_name + " " + last_name);
                                        else if (count1 == 92)
                                            text3225.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.VISIBLE);
                                    cardView19.setVisibility(View.VISIBLE);
                                    cardView20.setVisibility(View.VISIBLE);
                                    cardView21.setVisibility(View.VISIBLE);
                                    cardView22.setVisibility(View.VISIBLE);
                                    cardView23.setVisibility(View.VISIBLE);
                                    cardView24.setVisibility(View.GONE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 25) {
                                    if (count_time25 != null) {
                                        count_time25.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time25 = "";
                                        wait_time_wait25 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time25 = new CountDownTimer(wait_time_wait25, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time25 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time25 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time25 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time25 / 1000) % 60;
                                                        getTimeLeftFormatted_time25 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait25.setText(getTimeLeftFormatted_time25);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait25.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 25) {
                                            textView25.setText(first_name + " " + last_name);
                                            text1226.setVisibility(View.GONE);
                                            text2226.setVisibility(View.GONE);
                                            text3226.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 49)
                                            textView25.setText(first_name + " " + last_name);
                                        else if (count1 == 50)
                                            text1226.setText(first_name + " " + last_name);
                                        text2226.setVisibility(View.GONE);
                                        text3226.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 73)
                                            textView25.setText(first_name + " " + last_name);
                                        else if (count1 == 74)
                                            text1226.setText(first_name + " " + last_name);
                                        else if (count1 == 75)
                                            text2226.setText(first_name + " " + last_name);
                                        text3226.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 93)
                                            textView25.setText(first_name + " " + last_name);
                                        else if (count1 == 94)
                                            text1226.setText(first_name + " " + last_name);
                                        else if (count1 == 95)
                                            text2226.setText(first_name + " " + last_name);
                                        else if (count1 == 96)
                                            text3226.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.VISIBLE);
                                    cardView19.setVisibility(View.VISIBLE);
                                    cardView20.setVisibility(View.VISIBLE);
                                    cardView21.setVisibility(View.VISIBLE);
                                    cardView22.setVisibility(View.VISIBLE);
                                    cardView23.setVisibility(View.VISIBLE);
                                    cardView24.setVisibility(View.VISIBLE);
                                    cardView25.setVisibility(View.GONE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 26) {
                                    if (count_time26 != null) {
                                        count_time26.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time26 = "";
                                        wait_time_wait26 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time26 = new CountDownTimer(wait_time_wait26, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time26 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time26 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time26 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time26 / 1000) % 60;
                                                        getTimeLeftFormatted_time26 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait26.setText(getTimeLeftFormatted_time26);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait26.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 26) {
                                            textView26.setText(first_name + " " + last_name);
                                            text1227.setVisibility(View.GONE);
                                            text2227.setVisibility(View.GONE);
                                            text3227.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 51)
                                            textView26.setText(first_name + " " + last_name);
                                        else if (count1 == 52)
                                            text1227.setText(first_name + " " + last_name);
                                        text2227.setVisibility(View.GONE);
                                        text3227.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 76)
                                            textView26.setText(first_name + " " + last_name);
                                        else if (count1 == 77)
                                            text1227.setText(first_name + " " + last_name);
                                        else if (count1 == 78)
                                            text2227.setText(first_name + " " + last_name);
                                        text3227.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 97)
                                            textView26.setText(first_name + " " + last_name);
                                        else if (count1 == 98)
                                            text1227.setText(first_name + " " + last_name);
                                        else if (count1 == 99)
                                            text2227.setText(first_name + " " + last_name);
                                        else if (count1 == 100)
                                            text3227.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.VISIBLE);
                                    cardView19.setVisibility(View.VISIBLE);
                                    cardView20.setVisibility(View.VISIBLE);
                                    cardView21.setVisibility(View.VISIBLE);
                                    cardView22.setVisibility(View.VISIBLE);
                                    cardView23.setVisibility(View.VISIBLE);
                                    cardView24.setVisibility(View.VISIBLE);
                                    cardView25.setVisibility(View.VISIBLE);
                                    cardView26.setVisibility(View.GONE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 27) {
                                    if (count_time27 != null) {
                                        count_time27.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time27 = "";
                                        wait_time_wait27 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time27 = new CountDownTimer(wait_time_wait27, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time27 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time27 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time27 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time27 / 1000) % 60;
                                                        getTimeLeftFormatted_time27 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait27.setText(getTimeLeftFormatted_time27);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait27.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 27) {
                                            textView27.setText(first_name + " " + last_name);
                                            text1228.setVisibility(View.GONE);
                                            text2228.setVisibility(View.GONE);
                                            text3228.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 53)
                                            textView27.setText(first_name + " " + last_name);
                                        else if (count1 == 54)
                                            text1228.setText(first_name + " " + last_name);
                                        text2228.setVisibility(View.GONE);
                                        text3228.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 79)
                                            textView27.setText(first_name + " " + last_name);
                                        else if (count1 == 80)
                                            text1228.setText(first_name + " " + last_name);
                                        else if (count1 == 81)
                                            text2228.setText(first_name + " " + last_name);
                                        text3228.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 101)
                                            textView27.setText(first_name + " " + last_name);
                                        else if (count1 == 102)
                                            text1228.setText(first_name + " " + last_name);
                                        else if (count1 == 103)
                                            text2228.setText(first_name + " " + last_name);
                                        else if (count1 == 104)
                                            text3228.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.VISIBLE);
                                    cardView19.setVisibility(View.VISIBLE);
                                    cardView20.setVisibility(View.VISIBLE);
                                    cardView21.setVisibility(View.VISIBLE);
                                    cardView22.setVisibility(View.VISIBLE);
                                    cardView23.setVisibility(View.VISIBLE);
                                    cardView24.setVisibility(View.VISIBLE);
                                    cardView25.setVisibility(View.VISIBLE);
                                    cardView26.setVisibility(View.VISIBLE);
                                    cardView27.setVisibility(View.GONE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 28) {
                                    if (count_time28 != null) {
                                        count_time28.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time28 = "";
                                        wait_time_wait28 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time28 = new CountDownTimer(wait_time_wait28, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time28 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time28 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time28 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time28 / 1000) % 60;
                                                        getTimeLeftFormatted_time28 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait28.setText(getTimeLeftFormatted_time28);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait28.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 28) {
                                            textView28.setText(first_name + " " + last_name);
                                            text1229.setVisibility(View.GONE);
                                            text2229.setVisibility(View.GONE);
                                            text3229.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 55)
                                            textView28.setText(first_name + " " + last_name);
                                        else if (count1 == 56)
                                            text129.setText(first_name + " " + last_name);
                                        text2229.setVisibility(View.GONE);
                                        text3229.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 82)
                                            textView28.setText(first_name + " " + last_name);
                                        else if (count1 == 83)
                                            text1229.setText(first_name + " " + last_name);
                                        else if (count1 == 84)
                                            text2229.setText(first_name + " " + last_name);
                                        text3229.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 105)
                                            textView28.setText(first_name + " " + last_name);
                                        else if (count1 == 106)
                                            text1229.setText(first_name + " " + last_name);
                                        else if (count1 == 107)
                                            text2229.setText(first_name + " " + last_name);
                                        else if (count1 == 108)
                                            text3229.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.VISIBLE);
                                    cardView19.setVisibility(View.VISIBLE);
                                    cardView20.setVisibility(View.VISIBLE);
                                    cardView21.setVisibility(View.VISIBLE);
                                    cardView22.setVisibility(View.VISIBLE);
                                    cardView23.setVisibility(View.VISIBLE);
                                    cardView24.setVisibility(View.VISIBLE);
                                    cardView25.setVisibility(View.VISIBLE);
                                    cardView26.setVisibility(View.VISIBLE);
                                    cardView27.setVisibility(View.VISIBLE);
                                    cardView28.setVisibility(View.GONE);
                                } else if (count == 29) {
                                    if (count_time29 != null) {
                                        count_time29.cancel();
                                    }
                                    if (!timebaba.equals("0")) {
                                        getTimeLeftFormatted_time29 = "";
                                        wait_time_wait29 = Long.parseLong(timebaba) * 1000;
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                count_time29 = new CountDownTimer(wait_time_wait29, 1000) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        mTimeLeftInMillis_time29 = millisUntilFinished;
                                                        int hours = (int) (mTimeLeftInMillis_time29 / 1000) / 3600;
                                                        int minutes = (int) ((mTimeLeftInMillis_time29 / 1000) % 3600) / 60;
                                                        int seconds = (int) (mTimeLeftInMillis_time29 / 1000) % 60;
                                                        getTimeLeftFormatted_time29 = String.format(Locale.getDefault(),
                                                                "%02d:%02d:%02d", hours, minutes, seconds);
                                                        Log.e("TAG", "onTickdgg: ");
                                                        textwait20.setText(getTimeLeftFormatted_time29);
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        textwait29.setText("00:00:00");
                                                    }
                                                }.start();
                                            }
                                        }, 0);
                                    }
                                    if (seat.equals("1")) {
                                        if (count1 == 29) {
                                            textView28.setText(first_name + " " + last_name);
                                            text1229.setVisibility(View.GONE);
                                            text2229.setVisibility(View.GONE);
                                            text3229.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 57)
                                            textView28.setText(first_name + " " + last_name);
                                        else if (count1 == 58)
                                            text129.setText(first_name + " " + last_name);
                                        text2229.setVisibility(View.GONE);
                                        text3229.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 85)
                                            textView28.setText(first_name + " " + last_name);
                                        else if (count1 == 86)
                                            text1229.setText(first_name + " " + last_name);
                                        else if (count1 == 87)
                                            text2229.setText(first_name + " " + last_name);
                                        text3229.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 109)
                                            textView28.setText(first_name + " " + last_name);
                                        else if (count1 == 110)
                                            text1229.setText(first_name + " " + last_name);
                                        else if (count1 == 111)
                                            text2229.setText(first_name + " " + last_name);
                                        else if (count1 == 112)
                                            text3229.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.VISIBLE);
                                    cardView19.setVisibility(View.VISIBLE);
                                    cardView20.setVisibility(View.VISIBLE);
                                    cardView21.setVisibility(View.VISIBLE);
                                    cardView22.setVisibility(View.VISIBLE);
                                    cardView23.setVisibility(View.VISIBLE);
                                    cardView24.setVisibility(View.VISIBLE);
                                    cardView25.setVisibility(View.VISIBLE);
                                    cardView26.setVisibility(View.VISIBLE);
                                    cardView27.setVisibility(View.VISIBLE);
                                    cardView28.setVisibility(View.VISIBLE);
                                } else if (count == 30) {
                                    if (seat.equals("1")) {
                                        if (count1 == 30) {
                                            textView1.setText(first_name + " " + last_name);
                                            text12.setVisibility(View.GONE);
                                            text22.setVisibility(View.GONE);
                                            text32.setVisibility(View.GONE);
                                        }
                                    } else if (seat.equals("2")) {
                                        if (count1 == 59)
                                            textView1.setText(first_name + " " + last_name);
                                        else if (count1 == 60)
                                            text12.setText(first_name + " " + last_name);
                                        text22.setVisibility(View.GONE);
                                        text32.setVisibility(View.GONE);
                                    } else if (seat.equals("3")) {
                                        if (count1 == 88)
                                            textView1.setText(first_name + " " + last_name);
                                        else if (count1 == 89)
                                            text12.setText(first_name + " " + last_name);
                                        else if (count1 == 90)
                                            text22.setText(first_name + " " + last_name);
                                        text32.setVisibility(View.GONE);
                                    } else if (seat.equals("4")) {
                                        if (count1 == 109)
                                            textView1.setText(first_name + " " + last_name);
                                        else if (count1 == 110)
                                            text12.setText(first_name + " " + last_name);
                                        else if (count1 == 111)
                                            text22.setText(first_name + " " + last_name);
                                        else if (count1 == 112)
                                            text32.setText(first_name + " " + last_name);
                                    }
                                    cardView1.setVisibility(View.VISIBLE);
                                    cardView2.setVisibility(View.VISIBLE);
                                    cardView3.setVisibility(View.VISIBLE);
                                    cardView4.setVisibility(View.VISIBLE);
                                    cardView5.setVisibility(View.VISIBLE);
                                    cardView6.setVisibility(View.VISIBLE);
                                    cardView7.setVisibility(View.VISIBLE);
                                    cardView8.setVisibility(View.VISIBLE);
                                    cardView9.setVisibility(View.VISIBLE);
                                    cardView10.setVisibility(View.VISIBLE);
                                    cardView11.setVisibility(View.VISIBLE);
                                    cardView12.setVisibility(View.VISIBLE);
                                    cardView13.setVisibility(View.VISIBLE);
                                    cardView14.setVisibility(View.VISIBLE);
                                    cardView15.setVisibility(View.VISIBLE);
                                    cardView16.setVisibility(View.VISIBLE);
                                    cardView17.setVisibility(View.VISIBLE);
                                    cardView18.setVisibility(View.VISIBLE);
                                    cardView19.setVisibility(View.VISIBLE);
                                    cardView20.setVisibility(View.VISIBLE);
                                    cardView21.setVisibility(View.VISIBLE);
                                    cardView22.setVisibility(View.VISIBLE);
                                    cardView23.setVisibility(View.VISIBLE);
                                    cardView24.setVisibility(View.VISIBLE);
                                    cardView25.setVisibility(View.VISIBLE);
                                    cardView26.setVisibility(View.VISIBLE);
                                    cardView27.setVisibility(View.VISIBLE);
                                    cardView28.setVisibility(View.VISIBLE);
                                }
                            }
                           /* DashPOJO dashPOJO = new DashPOJO();
                            dashPOJO.setCusname(first_name + " " + last_name);*/
                            /*else if (count == 2)
                                dashPOJO.setCusname1(first_name + " " + last_name);
                            else if (count == 3)
                                dashPOJO.setCusname2(first_name + " " + last_name);
                            else if (count == 4)*/
                            // dashPOJO.setCusname3(first_name + " " + last_name);
                           /* dashPOJO.setCount(String.valueOf(count));
                            dashPOJOS.add(dashPOJO);*/
                        }
                     /*   if (tablequeueAdapter != null) // it works second time and later
                            tablequeueAdapter.notifyDataSetChanged();
                        else { // it works first time
                            tablequeueAdapter = new TablequeueAdapter(TableActivity.this, dashPOJOS);
                            table_recycle.setAdapter(tablequeueAdapter);
                        }*/
                      /*  table_recycle.setAdapter(tablequeueAdapter);
                        Toast.makeText(TableActivity.this, ""+dashPOJOS.toString(), Toast.LENGTH_SHORT).show();
                        tablequeueAdapter.notifyDataSetChanged();*/
                       /* id = jsonObject1.getString("id");
                        tabletime = jsonObject1.getString("table_time");
                        wait_time = jsonObject1.getString("wait_time");
                        table_name = jsonObject1.getString("name");
                        DashPOJO dashPOJO = new DashPOJO();
                        dashPOJO.setStatus(status);
                        dashPOJO.setTable_id(id);
                        dashPOJO.setTable_time(tabletime);
                        dashPOJO.setTable_name(table_name);
                        dashPOJO.setWait_time(wait_time);
                        dashPOJOS.add(dashPOJO);*/

                    /*recycle_dashboard.setAdapter(dashboardAdapter);
                    dashboardAdapter.notifyDataSetChanged();*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        dismissdialog();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                //Toast.makeText(DemoActivity.this, "Data Saved Successfully" + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "onErrorResponse: " + error.getMessage());
                try {
                    dismissdialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Api-Token", "eb4d3b5b7c0bf135783e83008e7ef387");
                params.put("table_id", Sharedpref.getmPref(getApplicationContext()).gettime());
                return params;
            }
        };
        RequestQueue queue = Myapplication.getInstance().getRequestQueue();
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
      /*  if (isentered != null) {

        } else if (isentered1 != null && Sharedpref.getmPref(getApplicationContext()).getisfromtable() != null &&
                Sharedpref.getmPref(getApplicationContext()).getisfromtable().equals("yes")) {
            Intent intent = new Intent(TableActivity.this, AdminActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(TableActivity.this, DashboardActivity.class);
            startActivity(intent);
        }*/
        Intent intent = new Intent(TableActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    public void startDialog() {
        try {
            spotsDialog = new SpotsDialog.Builder().
                    setContext(TableActivity.this).
                    setMessage("Loading").
                    setCancelable(false).
                    build();
            spotsDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissdialog() {
        try {
            if (spotsDialog.isShowing()) {
                spotsDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
