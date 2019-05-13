package com.yashodainfotech.queuingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.yashodainfotech.queuingapp.Adapters.TablelistAdapter;
import com.yashodainfotech.queuingapp.POJOS.DashPOJO;
import com.yashodainfotech.queuingapp.Webservices.AllWebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {
    public static KProgressHUD loader;
    static List<DashPOJO> dashPOJOS = new ArrayList<>();
    static TablelistAdapter tablelistAdapter;
    static RecyclerView recycle_gettable;
    static String status = "", idweb = "", tabletimeweb = "", wait_time = "", table_nameweb = "";
    static DashboardAdapter dashboardAdapter;
    static Context context;
    Button changetabletime, changetablestatus, getSetting, btn_submit_queue,
            addTable, btn_add_table, btn_edit_table,
            btn_add_table_add, btn_add_table_edit, addmember, btn_member_table,
            flushTables,flushsingleuser,btn_flush,allowbackbuton;
    EditText tabletime, tableid, tablestatus, tableids, gracetime,
            queuetime, table_name_name, seat_no, table_time, table_name_nameadd,
            seat_noadd, table_time_add, table_name_nameedit, seat_noedit, table_time_edit,
            table_firstname, table_lastname, table_memberno, table_barcodeno, table_time1,entermemberid;
    TextView table_id_text, table_id_text_status, id_table_add, id_table_edit;
    Button save, btn_submitnew;
    RecyclerView recyclerView_table;
    String table_name = "", id = "", graceTime = "", queueTime = "", table_timeweb = "", seat_web = "";
    String idss = "",backButton="",camera_Front="";


    public static void getTables() {
        dashPOJOS.clear();
        StringRequest request = new StringRequest(Request.Method.GET, AllWebServices.getDashboardTable, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result_array");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        status = jsonObject1.getString("status");
                        idweb = jsonObject1.getString("id");
                        tabletimeweb = jsonObject1.getString("table_time");
                        wait_time = jsonObject1.getString("wait_time");
                        table_nameweb = jsonObject1.getString("name");
                        DashPOJO dashPOJO = new DashPOJO();
                        dashPOJO.setStatus(status);
                        dashPOJO.setTable_id(idweb);
                        dashPOJO.setStatus_new("1");
                        dashPOJO.setTable_time(tabletimeweb);
                        dashPOJO.setTable_name(table_nameweb);
                        dashPOJO.setWait_time(wait_time);
                        dashPOJOS.add(dashPOJO);
                    }
                    recycle_gettable.setAdapter(dashboardAdapter);
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
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getSetting_camera();
        context = AdminActivity.this;
        changetabletime = findViewById(R.id.increasetabletime);
        changetablestatus = findViewById(R.id.make_table_unavail);
        getSetting = findViewById(R.id.getSetting);
        addTable = findViewById(R.id.addTable);
        addmember = findViewById(R.id.addmember);
        recycle_gettable = findViewById(R.id.recycle_gettable);
        flushTables = findViewById(R.id.flushTables);
        flushsingleuser=findViewById(R.id.flushsingleuser);
        allowbackbuton=findViewById(R.id.allowbackbuton);
        /*try{
            if(Sharedpref.getmPref(getApplicationContext()).getcameraBack()!=null &&
            Sharedpref.getmPref(getApplicationContext()).getcameraBack().equals("Enable Camera Back Button")){
                allowbackbuton.setText("Enable Camera Back Button");
                Log.e("TAG", "onClick4: " );
            }else{
                Log.e("TAG", "onClick5: " );
                allowbackbuton.setText("Disable Camera Back Button");
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
        /*allowbackbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(Sharedpref.getmPref(getApplicationContext()).getcameraBack()!=null &&
                            Sharedpref.getmPref(getApplicationContext()).getcameraBack().equals("Enable Camera Back Button")){
                        Log.e("TAG", "onClick1: " );
                        allowbackbuton.setText("Disable Camera Back Button");
                        Sharedpref.getmPref(getApplicationContext()).cameraBack("Disable Camera Back Button");
                    }else{
                        Log.e("TAG", "onClick2: " );
                        allowbackbuton.setText("Enable Camera Back Button");
                        Sharedpref.getmPref(getApplicationContext()).cameraBack("Enable Camera Back Button");
                    }
                }catch (Exception e){
                    Log.e("TAG", "onClick3: " );
                    e.printStackTrace();
                }
            }
        });*/
        flushsingleuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.flush_single_user, null, false);
                btn_flush = cato.findViewById(R.id.btn_flush);
                entermemberid = cato.findViewById(R.id.entermemberid);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                alertDialog.setView(cato);
                final AlertDialog dialog1 = alertDialog.create();
                dialog1.show();
                btn_flush.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (entermemberid.getText().toString().equals("")) {
                            Toast.makeText(AdminActivity.this, "Fields cannnot be empty", Toast.LENGTH_SHORT).show();
                        } else
                            flushSingleUser(entermemberid,dialog1);
                            //updateSetting(gracetime, queuetime, dialog1);
                    }
                });
            }
        });
        flushTables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setMessage("Do you really want to flush all tables ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        flushTables();
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
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recycle_gettable.setLayoutManager(gridLayoutManager);
        dashboardAdapter = new DashboardAdapter(AdminActivity.this, dashPOJOS, dashboardAdapter);
        getTables();
        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.addmember_layout, null, false);
                table_firstname = cato.findViewById(R.id.table_firstname);
                table_lastname = cato.findViewById(R.id.table_lastname);
                table_memberno = cato.findViewById(R.id.table_memberno);
                table_barcodeno = cato.findViewById(R.id.table_barcodeno);
                final String memberId = "";
                btn_member_table = cato.findViewById(R.id.btn_member_table);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                alertDialog.setView(cato);
                final AlertDialog dialog1 = alertDialog.create();
                dialog1.show();
                btn_member_table.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (table_firstname.getText().toString().equals("") && table_lastname.getText().toString().equals("")
                                && table_memberno.getText().toString().equals("") && table_barcodeno.getText().toString().equals("")) {
                            Toast.makeText(AdminActivity.this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
                        } else {
                            addmember(table_firstname, table_lastname, table_memberno, table_barcodeno, memberId, dialog1);
                        }
                    }
                });


            }
        });
        addTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.addtable_layout, null, false);
                table_name_name = cato.findViewById(R.id.table_name_name);
                seat_no = cato.findViewById(R.id.seat_no);
                table_time = cato.findViewById(R.id.table_time);
                btn_add_table = cato.findViewById(R.id.btn_add_table);
                btn_edit_table = cato.findViewById(R.id.btn_edit_table);
                btn_add_table.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.addtablenewlayout, null, false);
                        table_name_nameadd = cato.findViewById(R.id.table_name_nameadd);
                        seat_noadd = cato.findViewById(R.id.seat_noadd);
                        table_time_add = cato.findViewById(R.id.table_time_add);
                        btn_add_table_add = cato.findViewById(R.id.btn_add_table_add);
                        id_table_add = cato.findViewById(R.id.id_table_add);
                        final String id = "";
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                        alertDialog.setView(cato);
                        final AlertDialog dialog1 = alertDialog.create();
                        dialog1.show();
                        btn_add_table_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (table_name_nameadd.getText().toString().equals("") && seat_noadd.getText().toString().equals("")
                                        && table_time_add.getText().toString().equals("")) {
                                    Toast.makeText(AdminActivity.this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
                                } else {
                                    addTable(table_name_nameadd, seat_noadd, table_time_add, id, dialog1);
                                }
                            }
                        });
                    }
                });
                btn_edit_table.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.edittable_layout, null, false);
                        table_name_nameedit = cato.findViewById(R.id.table_name_nameedit);
                        table_name_nameedit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.tablenameslist, null, false);
                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                recyclerView_table = cato.findViewById(R.id.recyclelist);
                                final LinearLayoutManager layoutManager2 = new LinearLayoutManager(AdminActivity.this);
                                layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView_table.setLayoutManager(layoutManager2);
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                                alertDialog.setView(cato);
                                final AlertDialog dialog1 = alertDialog.create();
                                dialog1.show();
                                tablelistAdapter = new TablelistAdapter(AdminActivity.this, dashPOJOS, table_name_nameedit,
                                        dialog1, id_table_edit, table_time_edit, seat_noedit, idss);
                                getTableList();
                            }
                        });
                        seat_noedit = cato.findViewById(R.id.seat_noedit);
                        table_time_edit = cato.findViewById(R.id.table_time_edit);
                        btn_add_table_edit = cato.findViewById(R.id.btn_add_table_edit);
                        id_table_edit = cato.findViewById(R.id.id_table_edit);
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                        alertDialog.setView(cato);
                        final AlertDialog dialog1 = alertDialog.create();
                        dialog1.show();
                        btn_add_table_edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (table_name_nameedit.getText().toString().equals("") && seat_noedit.getText().toString().equals("")
                                        && table_time_edit.getText().toString().equals("")) {
                                    Toast.makeText(AdminActivity.this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AdminActivity.this, "" + idss
                                            , Toast.LENGTH_SHORT).show();
                                    addTable(table_name_nameedit, seat_noedit, table_time_edit, id_table_edit.getText().toString(), dialog1);
                                }
                            }
                        });
                    }
                });
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                alertDialog.setView(cato);
                final AlertDialog dialog1 = alertDialog.create();
                dialog1.show();
            }

        });
        getSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.setting_layout, null, false);
                gracetime = cato.findViewById(R.id.gracetime);
                queuetime = cato.findViewById(R.id.queuetime);
                getSetting(gracetime, queuetime);
                btn_submit_queue = cato.findViewById(R.id.btn_submit_queue);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                alertDialog.setView(cato);
                final AlertDialog dialog1 = alertDialog.create();
                dialog1.show();
                btn_submit_queue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (gracetime.getText().toString().equals("") && queuetime.getText().toString().equals("")) {
                            Toast.makeText(AdminActivity.this, "Fields cannnot be empty", Toast.LENGTH_SHORT).show();
                        } else
                            updateSetting(gracetime, queuetime, dialog1);
                    }
                });
            }
        });
        changetabletime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.changetabletime_layout, null, false);
                tableid = cato.findViewById(R.id.table_id);
                table_id_text = cato.findViewById(R.id.table_id_text);
                table_time1 = cato.findViewById(R.id.table_time1);
                tableid.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        final int DRAWABLE_RIGHT = 2;

                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (motionEvent.getRawX() >= (tableid.getRight() - tableid.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.tablenameslist, null, false);
                                recyclerView_table = cato.findViewById(R.id.recyclelist);
                                final LinearLayoutManager layoutManager2 = new LinearLayoutManager(AdminActivity.this);
                                layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView_table.setLayoutManager(layoutManager2);
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                                alertDialog.setView(cato);
                                final AlertDialog dialog1 = alertDialog.create();
                                dialog1.show();
                                tablelistAdapter = new TablelistAdapter(AdminActivity.this, dashPOJOS, tableid, dialog1, table_id_text, table_time1, table_time1, "");
                                getTableList();
                                return true;
                            }
                        }
                        return false;
                    }
                });


                tableid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.tablenameslist, null, false);
                        recyclerView_table = cato.findViewById(R.id.recyclelist);
                        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(AdminActivity.this);
                        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView_table.setLayoutManager(layoutManager2);
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                        alertDialog.setView(cato);
                        final AlertDialog dialog1 = alertDialog.create();
                        dialog1.show();
                        tablelistAdapter = new TablelistAdapter(AdminActivity.this, dashPOJOS, tableid, dialog1, table_id_text, table_time1, table_time1, "");
                        getTableList();
                    }
                });
                tabletime = cato.findViewById(R.id.table_time);
                save = cato.findViewById(R.id.btn_submit);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                alertDialog.setView(cato);
                final AlertDialog dialog1 = alertDialog.create();
                dialog1.show();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!tableid.getText().toString().equals("") && !tabletime.getText().toString().equals("")) {
                            Loader();
                            changeTableTime(dialog1);
                        } else {
                            Toast.makeText(AdminActivity.this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
        changetablestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.changetablestatus_layout, null, false);
                tableids = cato.findViewById(R.id.table_ids);
                tablestatus = cato.findViewById(R.id.table_status);
                table_id_text_status = cato.findViewById(R.id.table_id_text_status);
                tableids.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        final int DRAWABLE_RIGHT = 2;

                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            if (motionEvent.getRawX() >= (tableids.getRight() - tableids.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.tablenameslist, null, false);
                                recyclerView_table = cato.findViewById(R.id.recyclelist);
                                final LinearLayoutManager layoutManager2 = new LinearLayoutManager(AdminActivity.this);
                                layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView_table.setLayoutManager(layoutManager2);
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                                alertDialog.setView(cato);
                                final AlertDialog dialog1 = alertDialog.create();
                                dialog1.show();
                                tablelistAdapter = new TablelistAdapter(AdminActivity.this, dashPOJOS, tableids, dialog1, table_id_text_status, tableids, tableids, "");
                                getTableList();
                                return true;
                            }
                        }
                        return false;
                    }
                });
                tableids.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View cato = LayoutInflater.from(AdminActivity.this).inflate(R.layout.tablenameslist, null, false);
                        recyclerView_table = cato.findViewById(R.id.recyclelist);
                        final LinearLayoutManager layoutManager2 = new LinearLayoutManager(AdminActivity.this);
                        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView_table.setLayoutManager(layoutManager2);
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                        alertDialog.setView(cato);
                        final AlertDialog dialog1 = alertDialog.create();
                        dialog1.show();
                        tablelistAdapter = new TablelistAdapter(AdminActivity.this, dashPOJOS, tableids, dialog1, table_id_text_status, tableids, tableids, "");
                        getTableList();
                    }
                });
                btn_submitnew = cato.findViewById(R.id.btn_submitnew);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                alertDialog.setView(cato);
                final AlertDialog dialog1 = alertDialog.create();
                dialog1.show();
                btn_submitnew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!tableids.getText().toString().equals("") && !tablestatus.getText().toString().equals("")) {
                            Loader();
                            changeTableStatus(dialog1);
                        } else {
                            Toast.makeText(AdminActivity.this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    private void flushTables() {
        StringRequest request = new StringRequest(Request.Method.GET, AllWebServices.flushTables, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                    builder.setMessage(msg);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getTables();
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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

    private void addmember(final EditText table_firstname, final EditText table_lastname,
                           final EditText table_memberno, final EditText table_barcodeno, final String memberId, final AlertDialog dialog1) {
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.addMember, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                    builder.setMessage(msg);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            getTables();
                            dialog1.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

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
                params.put("first_name", table_firstname.getText().toString());
                params.put("last_name", table_lastname.getText().toString());
                params.put("member_number", table_memberno.getText().toString());
                params.put("barcode", table_barcodeno.getText().toString());
                params.put("member_id", memberId);
                return params;
            }
        };
        RequestQueue queue=Myapplication.getInstance().getRequestQueue();
        queue.add(request);
    }


    private void addTable(final TextView tablename, final TextView seat, final TextView table_time, final String id, final AlertDialog alertDialog) {
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.addUpdateTable, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                    builder.setMessage(msg);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            getTables();
                            alertDialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

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
                params.put("name", tablename.getText().toString());
                params.put("seat", seat.getText().toString());
                params.put("table_time", table_time.getText().toString());
                params.put("table_id", id);
                return params;
            }
        };
        RequestQueue queue=Myapplication.getInstance().getRequestQueue();
        queue.add(request);
    }

    private void updateSetting(final TextView grace, final TextView queuetime, final AlertDialog alertDialog) {
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.updateSettings, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                    builder.setMessage(msg);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            getTables();
                            alertDialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

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
                params.put("grace_time", grace.getText().toString());
                params.put("queue_continue_time", queuetime.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(AdminActivity.this);
        queue.add(request);
    }

    private void flushSingleUser(final TextView memberid, final AlertDialog alertDialog) {
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.flushSingleUser, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    if(msg.equals("Member ID is Inactive.")){
                        com.yashodainfotech.queuingapp.Toast.toastnew(msg,AdminActivity.this);
                    }else if(msg.equals("User not in Queue")){
                        com.yashodainfotech.queuingapp.Toast.toastnew(msg,AdminActivity.this);
                    }else{
                        com.yashodainfotech.queuingapp.Toast.toast(msg,AdminActivity.this);
                    }
                    getTables();
                    alertDialog.dismiss();
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                    builder.setMessage(msg);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            getTables();
                            alertDialog.dismiss();
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
                params.put("member_id", memberid.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(AdminActivity.this);
        queue.add(request);
    }

    private void getSetting(final TextView grace, final TextView queuee) {
        StringRequest request = new StringRequest(Request.Method.GET, AllWebServices.getSettings, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result_array");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        graceTime = jsonObject1.getString("grace_time");
                        queueTime = jsonObject1.getString("queue_continue_time");
                        grace.setText(graceTime);
                        queuee.setText(queueTime);
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
                        camera_Front=jsonObject1.getString("camera_front");
                        Sharedpref.getmPref(getApplicationContext()).cameraBack(camera_Front);
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

    private void getTableList() {
        dashPOJOS.clear();
        StringRequest request = new StringRequest(Request.Method.GET, AllWebServices.getDashboardTable, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result_array");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        id = jsonObject1.getString("id");
                        table_name = jsonObject1.getString("name");
                        table_timeweb = jsonObject1.getString("table_time");
                        seat_web = jsonObject1.getString("seat");
                        DashPOJO dashPOJO = new DashPOJO();
                        dashPOJO.setTablenmaes(table_name);
                        dashPOJO.setTableids(id);
                        dashPOJO.setTable_timeweb(table_timeweb);
                        dashPOJO.setSeat_web(seat_web);
                        dashPOJOS.add(dashPOJO);
                    }
                    recyclerView_table.setAdapter(tablelistAdapter);
                    tablelistAdapter.notifyDataSetChanged();
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

    private void changeTableTime(final AlertDialog alertDialog) {
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.changeTableTime, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissLoader();
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                    builder.setMessage(msg);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            getTables();
                            alertDialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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
                params.put("table_time", tabletime.getText().toString());
                params.put("table_id", table_id_text.getText().toString());
                return params;
            }
        };
        RequestQueue queue=Myapplication.getInstance().getRequestQueue();
        queue.add(request);
    }

    private void changeTableStatus(final AlertDialog alertDialog) {
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.changeTableStatus, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissLoader();
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                    builder.setMessage(msg);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            getTables();
                            alertDialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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
                params.put("status", tablestatus.getText().toString());
                params.put("table_id", table_id_text_status.getText().toString());
                return params;
            }
        };
        RequestQueue queue=Myapplication.getInstance().getRequestQueue();
        queue.add(request);
    }

    public void Loader() {
        loader = KProgressHUD.create(AdminActivity.this).
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
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        builder.setMessage("Do you  want to logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Sharedpref.getmPref(getApplicationContext()).checkLogin("LoggedOut Successfully");
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
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

    }


}
