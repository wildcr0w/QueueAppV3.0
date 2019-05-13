package com.yashodainfotech.queuingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.yashodainfotech.queuingapp.Webservices.AllWebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    public static KProgressHUD loader;
    EditText login, password;
    Button btnsave, btn_skip;
    String type="";
    String y="",camera_Front="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getSetting_camera();
        MyService.stopTimer();
        if (Sharedpref.getmPref(getApplicationContext()).getloginstatus()!=null && Sharedpref.getmPref(getApplicationContext()).getloginstatus().equals("Login Successfully")) {
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
        }
        login = findViewById(R.id.user_login);
        password = findViewById(R.id.user_password);
        btnsave = findViewById(R.id.btn_signin);
        btn_skip = findViewById(R.id.btn_skip);
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sharedpref.getmPref(getApplicationContext()).checkLogin("Login UnSuccessfully");
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!login.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    Loader();
                    saveLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "Fields Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    private void saveLogin() {
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.adminlogin, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissLoader();
                Log.e("TAG", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    try {
                        String msg = jsonObject.getString("message");
                        try{
                            JSONObject jsonObject1=jsonObject.getJSONObject("result");
                            type=jsonObject1.getString("user_type");
                        }catch (Exception e){
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage(msg);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }

                        if(type.equals("admin")){
                            if (msg.equals("Login Successfully")) {
                                MyService.gettime(LoginActivity.this);
                                Sharedpref.getmPref(getApplicationContext()).checkLogin("Login Successfully");
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                                /*AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(msg);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();*/
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(msg);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }else if(type.equals("coordinator")){
                            if (msg.equals("Login Successfully")) {
                                MyService.gettime(LoginActivity.this);
                                Sharedpref.getmPref(getApplicationContext()).checkLogin("Login Successfully");
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                               /* AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(msg);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();*/
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(msg);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }else if(type.equals("manager")){
                            if (msg.equals("Login Successfully")) {
                                MyService.gettime(LoginActivity.this);
                                Sharedpref.getmPref(getApplicationContext()).checkLogin("Login Successfully");
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                               /* AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(msg);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MyService.gettime(LoginActivity.this);
                                        Sharedpref.getmPref(getApplicationContext()).checkLogin("Login Successfully");
                                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                        startActivity(intent);
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();*/
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(msg);
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
                    } catch (Exception e) {
                        e.printStackTrace();
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
                params.put("user_name", login.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        RequestQueue queue=Myapplication.getInstance().getRequestQueue();
        queue.add(request);
    }

    public void Loader() {
        loader = KProgressHUD.create(LoginActivity.this).
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
        AlertDialog.Builder logout = new AlertDialog.Builder(LoginActivity.this);
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
