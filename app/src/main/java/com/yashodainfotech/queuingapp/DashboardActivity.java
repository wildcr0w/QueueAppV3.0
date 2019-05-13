package com.yashodainfotech.queuingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
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
import com.yashodainfotech.queuingapp.POJOS.DashPOJO;
import com.yashodainfotech.queuingapp.Webservices.AllWebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardActivity extends AppCompatActivity {
    public static List<DashPOJO> dashPOJOS = new ArrayList<>();
    public static KProgressHUD loader;
    public static SwipeRefreshLayout swipeRefreshLayout;
    public static int tablesCount = 0;
    public static int currentItems, scrolledOutItems, totalItems, pageno = 2, isComingFrom = 0;
    static RecyclerView recycle_dashboard;
    static String status = "", id = "", tabletime = "", wait_time = "", table_name = "",member_count="",table_count="",grace_time="";
    static DashboardAdapter dashboardAdapter;
    static Context context;
    public Boolean isScrolling = false;
    public LinearLayoutManager manager;
    public Handler handler = new Handler();
    Button buttonScan;
    Toolbar toolbar;
    Timer timer;
    protected int calculateNumberOfColumns(int base){
        int columns = base;
        String screenSize = getScreenSizeCategory();

        if(screenSize.equals("small")){
            if(base!=1){
                columns = columns-1;
            }
        }else if (screenSize.equals("normal")){
           // Do nothing
        }else if(screenSize.equals("large")){
            columns += 2;
        }else if (screenSize.equals("xlarge")){
            columns= 4;
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ){
            columns=(int)(columns*1.5);


        }

        return columns;
    }

    // Custom method to get screen current orientation
    protected String getScreenOrientation(){
        String orientation = "undefined";

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            orientation = "landscape";
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            orientation = "portrait";
        }

        return orientation;
    }

    // Custom method to get screen size category
    protected String getScreenSizeCategory(){
        int screenLayout = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenLayout){
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                // small screens are at least 426dp x 320dp
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                // normal screens are at least 470dp x 320dp
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                // large screens are at least 640dp x 480dp
                return "large";
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                // xlarge screens are at least 960dp x 720dp
                return "xlarge";
            default:
                return "undefined";
        }
    }
static String seat="";
    public static void getTables(final Context context) {
        Log.e("TAG", "getTables: " + tablesCount);
        //  Loader();
        dashPOJOS.clear();
        StringRequest request = new StringRequest(Request.Method.GET, AllWebServices.getDashboardTable, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: " + response);
                try {
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    //  dismissLoader();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result_array");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        status = jsonObject1.getString("status");
                        id = jsonObject1.getString("id"); seat=jsonObject1.getString("seat");
                        tabletime = jsonObject1.getString("table_time");
                        wait_time = jsonObject1.getString("wait_time");
                        table_name = jsonObject1.getString("name");
                        member_count=jsonObject1.getString("member_count");
                        table_count=jsonObject1.getString("table_count");
                        grace_time=jsonObject1.getString("grace_time");
                        DashPOJO dashPOJO = new DashPOJO();
                        dashPOJO.setStatus(status);
                        dashPOJO.setTable_id(id);
                        dashPOJO.setStatus_new("2");
                        dashPOJO.setTable_time(tabletime);
                        dashPOJO.setTable_name(table_name);
                        dashPOJO.setWait_time(wait_time);
                        dashPOJO.setMember_count(member_count);
                        dashPOJO.setSeat(seat);
                        dashPOJO.setTable_count(table_count);
                        dashPOJO.setGrace_time(grace_time);
                        dashPOJOS.add(dashPOJO);
                    }
                    recycle_dashboard.setAdapter(dashboardAdapter);
                    dashboardAdapter.notifyDataSetChanged();
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
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
        timer.cancel();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        MyService.gettime(DashboardActivity.this);
       /* if (Sharedpref.getmPref(getApplicationContext()).gettableids() != null &&
                Sharedpref.getmPref(getApplicationContext()).gettablenames() != null &&
                Sharedpref.getmPref(getApplicationContext()).getmemberids() != null) {
            Intent intent = new Intent(DashboardActivity.this, TableActivity.class);
            intent.putExtra("entered", "entered");
            intent.putExtra("table_id", Sharedpref.getmPref(getApplicationContext()).gettableids());
            intent.putExtra("table_name", Sharedpref.getmPref(getApplicationContext()).gettablenames());
            startActivity(intent);
        }*/
        Sharedpref.getmPref(getApplicationContext()).isfromtable("no");
        Sharedpref.getmPref(getApplicationContext()).isdashboard("no");
        context = DashboardActivity.this;
        toolbar = findViewById(R.id.tooladdnews);
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTables(context);
            }
        });
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        recycle_dashboard = findViewById(R.id.recycle_dashboard);
        buttonScan = findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);*/
            }
        });
        dashboardAdapter = new DashboardAdapter(DashboardActivity.this, dashPOJOS, dashboardAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), calculateNumberOfColumns(2));
        recycle_dashboard.setLayoutManager(gridLayoutManager);
        manager = new LinearLayoutManager(context);
        recycle_dashboard.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                    timer.cancel();
                    handler.removeCallbacksAndMessages(null);
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadActivty();
                        }
                    }, 10000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrolledOutItems = manager.findFirstVisibleItemPosition();
                if (isScrolling && (currentItems + scrolledOutItems == totalItems)) {

                }
            }
        });
        LoadActivty();
    }

    private void LoadActivty() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("TAG", "timer: ");
                getTables(context);
            }
        }, 0, 5000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem landscapeMode=menu.findItem(R.id.landscape_mode);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            landscapeMode.setTitle("Portrait View");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                if (Sharedpref.getmPref(getApplicationContext()).getloginstatus() != null &&
                        Sharedpref.getmPref(getApplicationContext()).getloginstatus().equals("Login Successfully")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                    builder.setMessage("Do you want to logout ?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Sharedpref.getmPref(getApplicationContext()).checkLogin("Login UnSuccessfully");
                            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                            startActivity(intent);
                           /* AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                            builder.setMessage("Logout Successfully");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Sharedpref.getmPref(getApplicationContext()).checkLogin("Login UnSuccessfully");
                                    dialogInterface.dismiss();
                                    Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();*/
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                break;
            case R.id.membercheckin:
                Intent intent = new Intent(DashboardActivity.this, GuestActivity.class);
                startActivity(intent);
                break;
            case R.id.landscape_mode:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


                /*case R.id.adminnew:
                if (Sharedpref.getmPref(getApplicationContext()).getloginstatus() != null && !Sharedpref.getmPref(getApplicationContext()).getloginstatus().equals("Login Successfully")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                    builder.setMessage("You are not loggedIn ?");
                    builder.setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(DashboardActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
                break;*/

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder logout = new AlertDialog.Builder(DashboardActivity.this);
        logout.setTitle("Do you want to close App ?");
        logout.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        logout.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog create = logout.create();
        create.show();
    }
}
