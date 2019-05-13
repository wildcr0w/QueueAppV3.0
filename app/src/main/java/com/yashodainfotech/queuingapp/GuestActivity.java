package com.yashodainfotech.queuingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.yashodainfotech.queuingapp.Webservices.AllWebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.yashodainfotech.queuingapp.DashboardActivity.Loader;
import static com.yashodainfotech.queuingapp.Timeout.runAdsvideo15sec;
import static com.yashodainfotech.queuingapp.Timeout.stopalarm15sec;

public class GuestActivity extends AppCompatActivity {
    EditText entermemberid;
    Button buttonSubmit;
    String barcodeValue="",success="",memberName="";
    public static KProgressHUD loader;
    ImageView memberScanImageView;
    ImageView manualCheckInImageView;
    ImageView memberPhotoImageView;
    TextView memberNameTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_layout);
        // runAdsvideo15sec(GuestActivity.this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        entermemberid =(EditText) findViewById(R.id.entermemberid);
        memberNameTextView=(TextView)findViewById(R.id.memberNameTextView);
        memberPhotoImageView= (ImageView)findViewById(R.id.memberPhotoImageView);

        try {
            Intent intent = getIntent();
            barcodeValue = intent.getStringExtra("barcode");
            entermemberid.setText(barcodeValue);
            if (!entermemberid.getText().toString().equals("")) {
                Loader();
                registerUser(entermemberid, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        memberScanImageView = (ImageView) findViewById(R.id.memberScanImageView);
        manualCheckInImageView = (ImageView) findViewById(R.id.manualCheckInImageView);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        memberScanImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entermemberid.setVisibility(View.GONE);
                buttonSubmit.setVisibility(View.GONE);
                Intent intent = new Intent(GuestActivity.this, CameraActivityGuest.class);
                intent.putExtra("guest", "guest");
                startActivity(intent);

            }
        });
        manualCheckInImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entermemberid.setVisibility(View.VISIBLE);
                buttonSubmit.setVisibility(View.VISIBLE);
                if(memberPhotoImageView.getVisibility()==View.VISIBLE){
                    memberPhotoImageView.setVisibility(View.GONE);
                    memberNameTextView.setVisibility(View.GONE);
                }
                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (entermemberid.getText().toString().equals("")) {
                            Toast.makeText(GuestActivity.this, "Field Cannot be empty", Toast.LENGTH_SHORT).show();
                        } else{
                            Loader();
                            registerUser(entermemberid, "6");
                        entermemberid.setText("");
                        memberNameTextView.setText(memberName);
                        entermemberid.setVisibility(View.GONE);
                        buttonSubmit.setVisibility(View.GONE);
                    }}
                });
            }
        });
    }
    public void registerUser(final TextView entermemberid, final String getTableid) {
        StringRequest request = new StringRequest(Request.Method.POST, AllWebServices.addGuestMember, new com.android.volley.Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                dismissLoader();
                Log.e("TAG", "onResponse:Data saved successfully " + response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    success=jsonObject.getString("message");
                    memberName=jsonObject.getJSONObject("response").getString("first_name")+" "+jsonObject.getJSONObject("response").getString("last_name")+" has checked in";
                    MemberImageDownloader image= new MemberImageDownloader();
                    Bitmap memberImage;
                    memberImage= image.execute(jsonObject.getJSONObject("response").getString("profile")).get();
                    memberPhotoImageView.setImageBitmap(memberImage);
                    memberNameTextView.setText(memberName);
                    Log.i("checkingSuccess",success);
                    memberPhotoImageView.setVisibility(View.VISIBLE);
                    memberNameTextView.setVisibility(View.VISIBLE);
                    com.yashodainfotech.queuingapp.Toast.toast(success,GuestActivity.this);
                   Intent intent=new Intent(GuestActivity.this,DashboardActivity.class);
        //            startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                    if(!entermemberid.getText().toString().isEmpty()){
                    memberNameTextView.setText("Ooops...Contact the manager");
                    memberPhotoImageView.setImageResource(R.drawable.error);
                    memberPhotoImageView.setVisibility(View.VISIBLE);
                    memberNameTextView.setVisibility(View.VISIBLE);}
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
                params.put("member_id",entermemberid.getText().toString());
                params.put("table_id","9");
                return params;
            }
        };
        RequestQueue queue=Myapplication.getInstance().getRequestQueue();
        queue.add(request);
    }

    public void Loader() {
        loader = KProgressHUD.create(GuestActivity.this).
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
        Intent intent=new Intent(GuestActivity.this,DashboardActivity.class);
        startActivity(intent);
    }
}
