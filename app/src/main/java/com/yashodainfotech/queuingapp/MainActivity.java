package com.yashodainfotech.queuingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yashodainfotech.queuingapp.Webservices.AllWebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.yashodainfotech.queuingapp.Timeout.runAdsvideo15sec;
import static com.yashodainfotech.queuingapp.Timeout.stopalarm15sec;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static KProgressHUD loader;
    String table_name = "",table_id="",member_id="";
    JSONObject jsonObject1;
    private Button buttonScan, buttonSubmit, buttonchange;
    private EditText entermemberid;
    //qr code scanner object
    private IntentIntegrator qrScan;
    private String getTableid = "", getBarcode = "", getTableName = "", gettableid_camera = "", checkDialog = "",admin="";
    String timeLeftFormatted = "";
    long wait_time_long = 0;
    Handler handler = new Handler();
    private CountDownTimer mCountDownTimer;
     private long mTimeLeftInMillis;
     TextView text_view_countdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // added to keep screen on when qapp is open
        text_view_countdown=findViewById(R.id.text_view_countdown);
        text_view_countdown.setText("00:00:00");
        addTimer();
        try{
            stopalarm15sec();
        }catch (Exception e){
            e.printStackTrace();
        }
        runAdsvideo15sec(MainActivity.this);
        buttonScan = findViewById(R.id.buttonScan);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonchange = findViewById(R.id.buttonchange);
        Intent intent = getIntent();
        try{
            admin=intent.getStringExtra("admin");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            getTableid = intent.getStringExtra("table_id");
            if (!getTableid.equals("")) {
                Sharedpref.getmPref(getApplicationContext()).issavetableid(getTableid);
                getTableName = intent.getStringExtra("table_name");
                Sharedpref.getmPref(getApplicationContext()).iscameraid(getTableName);
                checkDialog = intent.getStringExtra("pop_dialog");
                if (checkDialog.equals("1")) {
                    Intent intent1 = new Intent(MainActivity.this, CameraActivity.class);
                    intent1.putExtra("table_id", getTableid);
                    Sharedpref.getmPref(getApplicationContext()).timeOutId(getTableid,getTableName);
                    startActivity(intent1);
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Do you have your member card ? ");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();*/
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
            getTableid = intent.getStringExtra("table_id_change");
        }
        entermemberid = findViewById(R.id.entermemberid);
        try {
            getBarcode = intent.getStringExtra("barcode");
            entermemberid.setText(getBarcode);
            if (!getBarcode.equals("")) {
                Loader();
                registerUser(entermemberid, Sharedpref.getmPref(getApplicationContext()).gettableid());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //intializing scan object
        qrScan = new IntentIntegrator(this);

        //attaching onclick listener
        buttonScan.setOnClickListener(this);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSubmit.setVisibility(View.GONE);
                entermemberid.setVisibility(View.GONE);
                buttonScan.setVisibility(View.GONE);
                if (!entermemberid.getText().toString().equals("")) {
                    Loader();
                    registerUser(entermemberid, Sharedpref.getmPref(getApplicationContext()).gettableid());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Please Enter MemberId");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            buttonSubmit.setVisibility(View.VISIBLE);
                            entermemberid.setVisibility(View.VISIBLE);
                            buttonScan.setVisibility(View.VISIBLE);

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        buttonchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!entermemberid.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("You are already in queue in table " + getTableid + ", do you want to loose your spot there and go to another table?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Loader();
                            ChangeTable();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Please Enter MemberId");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

    }

    public void addTimer(){
        timeLeftFormatted = "";
        wait_time_long = 15 * 1000;
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
                        text_view_countdown.setText(timeLeftFormatted);
                        if(text_view_countdown.getText().toString().equals("00:00:00")){
                            Intent intent= new Intent(MainActivity.this,TableActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFinish() {
                        text_view_countdown.setText("00:00:00");
                    }
                }.start();
            }
        }, 0);
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    Log.e("TAG", "onActivityResult: " + result.getContents());
                    entermemberid.setText(obj.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    entermemberid.setText(result.getContents());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
//initiating the qr code scan
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        intent.putExtra("table_id", getTableid);
        startActivity(intent);
    }

    public void registerUser(final TextView entermemberid, final String getTableid) {
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.addUserToQueue, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissLoader();
                Log.e("TAG", "onResponse:Data saved successfully " + response);
                try {
                    String current_que=null;
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    try {
                        jsonObject1 = jsonObject.getJSONObject("result_array");
                        table_name = jsonObject1.getString("table_name");
                        table_id = jsonObject1.getString("table_id");
                        member_id = jsonObject1.getString("member_id");
                        current_que = jsonObject1.getString("current_que");
                        Sharedpref.getmPref(getApplicationContext()).isusertable(member_id,table_id,table_name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (table_name.equals(Sharedpref.getmPref(getApplicationContext()).getcameraid())) {
                        com.yashodainfotech.queuingapp.Toast.toastnew(msg,MainActivity.this); Intent intent = new Intent(MainActivity.this, TableActivity.class);
                        intent.putExtra("table_id", Sharedpref.getmPref(getApplicationContext()).gettableids());
                        intent.putExtra("table_name", Sharedpref.getmPref(getApplicationContext()).gettablenames());
                        intent.putExtra("entered", "entered");
                        startActivity(intent);

                       /* AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(msg);
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(MainActivity.this, TableActivity.class);
                                    intent.putExtra("table_id", Sharedpref.getmPref(getApplicationContext()).gettableids());
                                    intent.putExtra("table_name", Sharedpref.getmPref(getApplicationContext()).gettablenames());
                                    intent.putExtra("entered", "entered");
                                    startActivity(intent);
                                    dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();*/
                    } else if (msg.equals("User Already In Queue")) {
                        if(current_que.equals("0")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("You are already in queue in table " + table_name + ", " +
                                    "do you want to loose your spot there and go to table " + Sharedpref.getmPref(getApplicationContext()).getcameraid() + "?");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Loader();
                                    ChangeTable();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(MainActivity.this, TableActivity.class);
                                    intent.putExtra("table_id", Sharedpref.getmPref(getApplicationContext()).gettimeOutId());
                                    intent.putExtra("table_name", Sharedpref.getmPref(getApplicationContext()).gettimeOutname());
                                    intent.putExtra("entered", "entered");
                                    startActivity(intent);
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }else{
                            com.yashodainfotech.queuingapp.Toast.toastnew("Sorry, you are currently occuping "+table_name+", you cannot move till your time is up on "+table_name,MainActivity.this); Intent intent = new Intent(MainActivity.this, TableActivity.class);
                        }
                    } else if (msg.equals("Member ID is Inactive.")) {

                        com.yashodainfotech.queuingapp.Toast.toastnew(msg,MainActivity.this);
                        entermemberid.setVisibility(View.VISIBLE);
                        buttonSubmit.setVisibility(View.VISIBLE);
                        buttonScan.setVisibility(View.VISIBLE);
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(msg);
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();*/
                    } else {
                        com.yashodainfotech.queuingapp.Toast.toastnew(msg,MainActivity.this);
                        Intent intent = new Intent(MainActivity.this, TableActivity.class);
                        intent.putExtra("entered", "entered");
                        intent.putExtra("table_id", Sharedpref.getmPref(getApplicationContext()).gettableids());
                        intent.putExtra("table_name", Sharedpref.getmPref(getApplicationContext()).gettablenames());
                        startActivity(intent);
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(msg);
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MainActivity.this, TableActivity.class);
                                    intent.putExtra("entered", "entered");
                                    intent.putExtra("table_id", Sharedpref.getmPref(getApplicationContext()).gettableids());
                                    intent.putExtra("table_name", Sharedpref.getmPref(getApplicationContext()).gettablenames());
                                    startActivity(intent);
                                    dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();*/
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Api-Token", "eb4d3b5b7c0bf135783e83008e7ef387");
                params.put("member_id", entermemberid.getText().toString());
                params.put("table_id", getTableid);
                return params;
            }
        };
        RequestQueue queue=Myapplication.getInstance().getRequestQueue();
        queue.add(request);
    }

    public void ChangeTable() {
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.changeTable, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissLoader();
                Log.e("TAG", "onResponsechange table:Data saved successfully " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    try {
                        jsonObject1 = jsonObject.getJSONObject("result_array");
                        table_name = jsonObject1.getString("table_name");
                        table_id = jsonObject1.getString("table_id");
                        member_id = jsonObject1.getString("member_id");
                        Sharedpref.getmPref(getApplicationContext()).isusertable(member_id,table_id,table_name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    com.yashodainfotech.queuingapp.Toast.toastnew(msg,MainActivity.this);
                    Intent intent = new Intent(MainActivity.this, TableActivity.class);
                    intent.putExtra("table_id", Sharedpref.getmPref(getApplicationContext()).gettableids());
                    intent.putExtra("table_name", Sharedpref.getmPref(getApplicationContext()).gettablenames());
                    intent.putExtra("entered", "entered");
                    startActivity(intent);
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(msg);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(MainActivity.this, TableActivity.class);
                                intent.putExtra("table_id", Sharedpref.getmPref(getApplicationContext()).gettableids());
                                intent.putExtra("table_name", Sharedpref.getmPref(getApplicationContext()).gettablenames());
                                intent.putExtra("entered", "entered");
                            startActivity(intent);
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();*/
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Api-Token", "eb4d3b5b7c0bf135783e83008e7ef387");
                params.put("member_id", entermemberid.getText().toString());
                params.put("table_id", Sharedpref.getmPref(getApplicationContext()).gettableid());
                return params;
            }
        };
        RequestQueue queue=Myapplication.getInstance().getRequestQueue();
        queue.add(request);
    }

    public void Loader() {
        loader = KProgressHUD.create(MainActivity.this).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).
                setLabel("Please Wait...").
                setCancellable(true).
                setAnimationSpeed(1).
                setDimAmount(0.5f).
                show();
    }

    public void dismissLoader() {
        try {
            if (loader.isShowing()) {
                loader.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
    }

    @Override
    protected void onStop() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            handler.removeCallbacksAndMessages(null);
            Log.e("TAG", "ajithyuo: ");
        }
        stopalarm15sec();
        super.onStop();
    }
}
