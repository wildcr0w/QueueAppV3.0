package com.yashodainfotech.queuingapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.service.Common;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yashodainfotech.queuingapp.AdminActivity;
import com.yashodainfotech.queuingapp.DashboardActivity;
import com.yashodainfotech.queuingapp.MainActivity;
import com.yashodainfotech.queuingapp.POJOS.DashPOJO;
import com.yashodainfotech.queuingapp.R;
import com.yashodainfotech.queuingapp.Sharedpref;
import com.yashodainfotech.queuingapp.TableActivity;
import com.yashodainfotech.queuingapp.Toast;
import com.yashodainfotech.queuingapp.Webservices.AllWebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardClass> {
    public static KProgressHUD loader;
    Context context;
    List<DashPOJO> dashPOJOS;
    String timeLeftFormatted = "";
    EditText tabletime, tableid, tablestatus, tableids;
    TextView table_id_text_status;
    boolean isRunning = false;
    DashboardAdapter dashboardAdapter;
    int timecount = 0;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private CountDownTimer mCountDownTimer;
    public int counts=0;


    public DashboardAdapter(Context context, List<DashPOJO> dashPOJOS, DashboardAdapter dashboardAdapter) {
        this.context = context;
        this.dashPOJOS = dashPOJOS;
        this.dashboardAdapter = dashboardAdapter;
    }

    @NonNull
    @Override
    public DashboardAdapter.DashboardClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dash_adapter, viewGroup, false);
        return new DashboardClass(view);
    }

String cnt;
    @Override
    public void onBindViewHolder(@NonNull final DashboardAdapter.DashboardClass dashboardClass, int i) {
        final DashPOJO dashPOJO = dashPOJOS.get(i);
        dashboardClass.status.setText(dashPOJO.getStatus());
        dashboardClass.time.setText(dashPOJO.getWait_time());
        if (dashboardClass.status.getText().toString().equals("Open") && dashboardClass.time.getText().toString().equals("0")) {
            dashboardClass.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.open));
            dashboardClass.time.setText(dashPOJO.getWait_time());
            dashboardClass.status.setText(dashPOJO.getStatus());
            /* long millisInput = Long.parseLong(dashboardClass.time.getText().toString()) * 60000;
            mStartTimeInMillis = millisInput;
            int hours = (int) (mStartTimeInMillis / 1000) / 3600;
            int minutes = (int) ((mStartTimeInMillis / 1000) % 3600) / 60;
            int seconds = (int) (mStartTimeInMillis / 1000) % 60;
            String time=String.format(Locale.getDefault(),
                    "%02d:%02d:%02d", hours, minutes, seconds);*/
            dashboardClass.Timenew.setText("00:00:00");
            dashboardClass.Timenew.setVisibility(View.VISIBLE);
            dashboardClass.time.setVisibility(View.GONE);
            dashboardClass.table_time.setText(dashPOJO.getTable_time());
            dashboardClass.table_nmae.setText(dashPOJO.getTable_name());
            dashboardClass.statusnew.setText(dashPOJO.getStatus_new());
            dashboardClass.id.setText(dashPOJO.getTable_id()); dashboardClass.member_count.setText(dashPOJO.getMember_count());
            dashboardClass.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) { Shared.cnt=Integer.parseInt(dashPOJO.getSeat().toString())
;                    if (dashboardClass.statusnew.getText().toString().equals("2")) {
                        Log.e("TAG", "onClick1: " );
                        Intent intent = new Intent(view.getContext(), TableActivity.class);
                        intent.putExtra("table_id", dashboardClass.id.getText().toString());
                        intent.putExtra("table_name", dashboardClass.table_nmae.getText().toString());
                         context.startActivity(intent);
                    } else if (dashboardClass.statusnew.getText().toString().equals("1")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Do you want to make table Unavailable or flush table " + dashboardClass.table_nmae.getText().toString() + "?");
                        builder.setPositiveButton("Unavailable", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Loader();
                                String id = dashboardClass.id.getText().toString();
                                changeTableStatus("Reserved", id);
                            }
                        });
                        builder.setNegativeButton("flush table", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Loader();
                                String id = dashboardClass.id.getText().toString();
                                flushSingleTable( id);
                            }
                        });
                       /* builder.setNegativeButton("Go to Table", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Sharedpref.getmPref(context).isfromtable("yes");
                                Intent intent = new Intent(view.getContext(), TableActivity.class);
                                intent.putExtra("table_id", dashboardClass.id.getText().toString());
                                intent.putExtra("table_name", dashboardClass.table_nmae.getText().toString());
                                intent.putExtra("fromadmin", "yes");
                                intent.putExtra("entered1", "entered1");
                                context.startActivity(intent);
                                dialogInterface.dismiss();
                            }
                        });*/
                        builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to add your name to queue?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            intent.putExtra("table_id", dashboardClass.id.getText().toString());
                            context.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Added", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(view.getContext(), TableActivity.class);
                            intent.putExtra("table_id", dashboardClass.id.getText().toString());
                            context.startActivity(intent);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();*/
                }
            });
        } else if (dashboardClass.status.getText().toString().equals("Reserved")) {
            dashboardClass.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.reserved));
                dashboardClass.time.setText(dashPOJO.getWait_time());dashboardClass.seatText.setText( dashPOJO.getSeat());
            dashboardClass.table_time.setText(dashPOJO.getTable_time());
            dashboardClass.time.setVisibility(View.GONE);
            dashboardClass.table_nmae.setText(dashPOJO.getTable_name());
            dashboardClass.id.setText(dashPOJO.getTable_id());
            dashboardClass.statusnew.setText(dashPOJO.getStatus_new());
            dashboardClass.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (dashboardClass.statusnew.getText().toString().equals("2")) {

                    } else if (dashboardClass.statusnew.getText().toString().equals("1")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Do you want to make table Open ? ");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Loader();
                                String id = dashboardClass.id.getText().toString();
                                changeTableStatus("Open", id);
                            }
                        });
                       /* builder.setNegativeButton("Go to Table", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(view.getContext(), TableActivity.class);
                                intent.putExtra("table_id", dashboardClass.id.getText().toString());
                                intent.putExtra("table_name", dashboardClass.table_nmae.getText().toString());
                                intent.putExtra("fromadmin","yes");
                                context.startActivity(intent);
                                dialogInterface.dismiss();
                            }
                        });*/
                        builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
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
        } else if (dashboardClass.status.getText().toString().equals("Wait") && !dashboardClass.time.getText().toString().equals("0")) {
            dashboardClass.time.setText("00:00:00");
            dashboardClass.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.wait));
            dashboardClass.time.setText(dashPOJO.getWait_time());
            dashboardClass.Timenew.setVisibility(View.GONE);
            long millisInput = Long.parseLong(dashboardClass.time.getText().toString()) * 1000;

            /*mStartTimeInMillis = millisInput;
            int hours = (int) (mStartTimeInMillis / 1000) / 3600;
            int minutes = (int) ((mStartTimeInMillis / 1000) % 3600) / 60;
            int seconds = (int) (mStartTimeInMillis / 1000) % 60;
            String time=String.format(Locale.getDefault(),
                    "%02d:%02d:%02d", hours, minutes, seconds);*/
            int tbl_cnt = Integer.parseInt(dashPOJO.getTable_count());
            if(tbl_cnt==1){
                String ravitime = dashPOJO.getTable_time();
                String ravigt = dashPOJO.getGrace_time();
                int rtime = Integer.parseInt(ravitime);
                int rgt   = Integer.parseInt(ravigt)/60;
                int totalminute = rtime+rgt;

                int hours = (int) (totalminute*60) / 3600;
                int minutes = (int) ((totalminute*60) % 3600) / 60;
                int seconds = (int) (totalminute*60) % 60;
                timeLeftFormatted = String.format(Locale.getDefault(),
                        "%02d:%02d:%02d", hours, minutes, seconds);
                dashboardClass.time.setText(timeLeftFormatted);
            }else{
                mCountDownTimer = new CountDownTimer(millisInput, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mTimeLeftInMillis = millisUntilFinished;
                        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
                        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
                        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
                        timeLeftFormatted = String.format(Locale.getDefault(),
                                "%02d:%02d:%02d", hours, minutes, seconds);
                        dashboardClass.time.setText(timeLeftFormatted);
                    }

                    @Override
                    public void onFinish() {
                    /*if(Sharedpref.getmPref(context).getisdashboard()!=null &&
                            Sharedpref.getmPref(context).getisdashboard().equals("no")){
                        Intent intent = new Intent(context, TableActivity.class);
                        intent.putExtra("table_id", dashboardClass.id.getText().toString());
                        context.startActivity(intent);
                      *//*  new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                DashboardActivity.dashPOJOS.clear();
                                Log.e("TAG", "context: " );
                                DashboardActivity.getTablesNew(context);
                            }
                        },0);*//*
                         *//*  new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("TAG", "run: " );
                                Sharedpref.getmPref(context).isdashboard("no");
                            }
                        },0);*//*
                    }*/
                    }
                }.start();
            }
            dashboardClass.time.setVisibility(View.VISIBLE);
            dashboardClass.table_time.setText(dashPOJO.getTable_time());
            dashboardClass.statusnew.setText(dashPOJO.getStatus_new());
            dashboardClass.table_nmae.setText(dashPOJO.getTable_name());
            dashboardClass.id.setText(dashPOJO.getTable_id()); dashboardClass.member_count.setText(dashPOJO.getMember_count());
            dashboardClass.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (dashboardClass.statusnew.getText().toString().equals("2")) {
                        Intent intent = new Intent(view.getContext(), TableActivity.class);
                        intent.putExtra("table_id", dashboardClass.id.getText().toString());
                          Shared.cnt= Integer.parseInt( dashPOJO.getSeat());
                            intent.putExtra("table_name", dashboardClass.table_nmae.getText().toString());
                        context.startActivity(intent);
                    } else if (dashboardClass.statusnew.getText().toString().equals("1")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Do you want to make table Unavailable or flush table " + dashboardClass.table_nmae.getText().toString() + "?");
                        builder.setPositiveButton("Unavailable", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                Loader();
                                String id = dashboardClass.id.getText().toString();
                                 changeTableStatus("Reserved", id);
                            }
                        });
                        builder.setNegativeButton("flush table", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Loader();
                                String id = dashboardClass.id.getText().toString();
                                flushSingleTable( id);
                            }
                        });
                      /*  builder.setNegativeButton("Go to Table", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Sharedpref.getmPref(context).isfromtable("yes");
                                Intent intent = new Intent(view.getContext(), TableActivity.class);
                                intent.putExtra("table_id", dashboardClass.id.getText().toString());
                                intent.putExtra("table_name", dashboardClass.table_nmae.getText().toString());
                                intent.putExtra("fromadmin", "yes");
                                context.startActivity(intent);
                                dialogInterface.dismiss();
                            }
                        });*/
                        builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
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
    }


    @Override
    public int getItemCount() {
        return dashPOJOS.size();
    }

    private void changeTableStatus(final String status, final String id) {
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.changeTableStatus, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissLoader();
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    Toast.toast(msg,context);
                    AdminActivity.getTables();
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(msg);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            AdminActivity.getTables();
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
                params.put("status", status);
                params.put("table_id", id);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private void flushSingleTable(final String id) {
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.flushSingleTable, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissLoader();
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    Toast.toast(msg,context);
                    AdminActivity.getTables();
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
                params.put("table_id", id);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public void Loader() {
        loader = KProgressHUD.create(context).
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

    public class DashboardClass extends RecyclerView.ViewHolder {
        TextView status, time, id, table_time, table_nmae, Timenew, statusnew,member_count,seatText;
        CardView cardView;

        public DashboardClass(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status);
            time = itemView.findViewById(R.id.Time);
            cardView = itemView.findViewById(R.id.cardview);
            id = itemView.findViewById(R.id.id);
            table_time = itemView.findViewById(R.id.table_time);
            table_nmae = itemView.findViewById(R.id.table_nmae);
            Timenew = itemView.findViewById(R.id.Timenew);
            statusnew = itemView.findViewById(R.id.statusnew); member_count=itemView.findViewById(R.id.member_count);
        }
    }
}
