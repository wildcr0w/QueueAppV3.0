package com.yashodainfotech.queuingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import static com.yashodainfotech.queuingapp.Timeout.runAdsvideo;
import static com.yashodainfotech.queuingapp.Timeout.stopalarm;

public class CameraActivityGuest extends AppCompatActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    RelativeLayout mrelativelayout;
    String QRCode, table_id = "";
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private SurfaceView surfaceView;
    private Button cancel_btn, front_camera,back_camera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        try{
            stopalarm();
        }catch (Exception e){
            e.printStackTrace();
        }
        runAdsvideo(CameraActivityGuest.this);
        cancel_btn = findViewById(R.id.cancel_btn);
        surfaceView = findViewById(R.id.surface_view);
        mrelativelayout = findViewById(R.id.linear1);
        front_camera = findViewById(R.id.front_camera);
        back_camera = findViewById(R.id.back_camera);
        if (Sharedpref.getmPref(getApplicationContext()).getcameraBack() != null &&
                Sharedpref.getmPref(getApplicationContext()).getcameraBack().equals("Enable")) {
            front_camera.setVisibility(View.VISIBLE);
            barcodeDetector = new BarcodeDetector.Builder(CameraActivityGuest.this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
            cameraSource = new CameraSource.Builder(CameraActivityGuest.this, barcodeDetector).
                    setRequestedFps(15.0f).
                    setFacing(CameraSource.CAMERA_FACING_FRONT).
                    setRequestedPreviewSize(1600, 1024).
                    setAutoFocusEnabled(true).
                    build();
            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CameraActivityGuest.this, GuestActivity.class);
                    cameraSource.release();
                    startActivity(intent);
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_REQUEST_CODE);
                } else {
                    startQRScan();
                }
            } else {
                startQRScan();
            }
        }else{
            front_camera.setVisibility(View.GONE);
        }
        if(Sharedpref.getmPref(getApplicationContext()).getcameraFront() != null &&
                Sharedpref.getmPref(getApplicationContext()).getcameraFront().equals("Enable")){
            back_camera.setVisibility(View.VISIBLE);
            barcodeDetector = new BarcodeDetector.Builder(CameraActivityGuest.this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
            cameraSource = new CameraSource.Builder(CameraActivityGuest.this, barcodeDetector).
                    setRequestedFps(15.0f).
                    setFacing(CameraSource.CAMERA_FACING_BACK).
                    setRequestedPreviewSize(1600, 1024).
                    setAutoFocusEnabled(true).
                    build();
            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CameraActivityGuest.this, GuestActivity.class);
                    cameraSource.release();
                    startActivity(intent);
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_REQUEST_CODE);
                } else {
                    startQRScan();
                }
            } else {
                startQRScan();
            }
        }else {
            back_camera.setVisibility(View.GONE);
        }
        back_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sharedpref.getmPref(getApplicationContext()).iscamerabackfront("cameraback");
                Intent intent = new Intent(CameraActivityGuest.this, CameraActivityGuest.class);
                try{
                    cameraSource.release();
                }catch (Exception e){
                    e.printStackTrace();
                }
                startActivity(intent);
               /* Log.e("TAG", "onCreatecameraback: ");
                barcodeDetector = new BarcodeDetector.Builder(CameraActivity.this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
                cameraSource = new CameraSource.Builder(CameraActivity.this, barcodeDetector).
                        setRequestedFps(15.0f).
                        setFacing(CameraSource.CAMERA_FACING_BACK).
                        setRequestedPreviewSize(1600, 1024).
                        setAutoFocusEnabled(true).
                        build();
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CameraActivity.this, MainActivity.class);
                        cameraSource.release();
                        startActivity(intent);
                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_REQUEST_CODE);
                    } else {
                        startQRScan();
                    }
                } else {
                    startQRScan();
                }*/

               /* Sharedpref.getmPref(getApplicationContext()).iscamerabackfront("cameraback");
                Intent intent = new Intent(CameraActivity.this, CameraActivity.class);
                try{
                    cameraSource.release();
                }catch (Exception e){
                    e.printStackTrace();
                }
                startActivity(intent);*/
            }
        });
        front_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sharedpref.getmPref(getApplicationContext()).iscamerabackfront("camerafront");
                Intent intent = new Intent(CameraActivityGuest.this, CameraActivityGuest.class);
                try{
                    cameraSource.release();
                }catch (Exception e){
                    e.printStackTrace();
                }
                startActivity(intent);
                Log.e("TAG", "onCreatecamerafront: ");
                /*front_camera.setText("FRONT CAMERA");
                barcodeDetector = new BarcodeDetector.Builder(CameraActivity.this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
                cameraSource = new CameraSource.Builder(CameraActivity.this, barcodeDetector).
                        setRequestedFps(15.0f).
                        setFacing(CameraSource.CAMERA_FACING_FRONT).
                        setRequestedPreviewSize(1600, 1024).
                        setAutoFocusEnabled(true).
                        build();
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CameraActivity.this, MainActivity.class);
                        cameraSource.release();
                        startActivity(intent);
                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_REQUEST_CODE);
                    } else {
                        startQRScan();
                    }
                } else {
                    startQRScan();
                }*/
                /*Sharedpref.getmPref(getApplicationContext()).iscamerabackfront("camerafront");
                Intent intent = new Intent(CameraActivity.this, CameraActivity.class);
                try{
                    cameraSource.release();
                }catch (Exception e){
                    e.printStackTrace();
                }
                startActivity(intent);*/
                if (cameraSource.getCameraFacing() == CameraSource.CAMERA_FACING_FRONT) {
                  /*  Log.e("TAG", "onClickfront: " );
                    front_camera.setText("FRONT CAMERA");
                    if (cameraSource != null) {
                        cameraSource.release();
                        startQRScan();
                    }
                    barcodeDetector = new BarcodeDetector.Builder(CameraActivity.this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
                    cameraSource = new CameraSource.Builder(CameraActivity.this, barcodeDetector).
                            setRequestedFps(15.0f).
                            setFacing(CameraSource.CAMERA_FACING_BACK).
                            setRequestedPreviewSize(1600, 1024).
                            setAutoFocusEnabled(true).
                            build();*/

                } else {
                   /* Sharedpref.getmPref(getApplicationContext()).iscamerabackfront("camerafront");
                    Intent intent = new Intent(CameraActivity.this, CameraActivity.class);
                    try{
                        cameraSource.release();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    startActivity(intent);*/
                  /*  Log.e("TAG", "onClickback: " );
                    front_camera.setText("BACK CAMERA");
                    if (cameraSource != null) {
                        cameraSource.release();
                    }
                    barcodeDetector = new BarcodeDetector.Builder(CameraActivity.this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
                    cameraSource = new CameraSource.Builder(CameraActivity.this, barcodeDetector).
                            setRequestedFps(15.0f).
                            setFacing(CameraSource.CAMERA_FACING_FRONT).
                            setRequestedPreviewSize(1600, 1024).
                            setAutoFocusEnabled(true).
                            build();*/
                }
            }
        });

        barcodeDetector = new BarcodeDetector.Builder(CameraActivityGuest.this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(CameraActivityGuest.this, barcodeDetector).
                setRequestedFps(15.0f).
                setFacing(CameraSource.CAMERA_FACING_BACK).
                setRequestedPreviewSize(1600, 1024).
                setAutoFocusEnabled(true).
                build();
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CameraActivityGuest.this, GuestActivity.class);
                cameraSource.release();
                startActivity(intent);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_CAMERA_REQUEST_CODE);
            } else {
                startQRScan();
            }
        } else {
            startQRScan();
        }

        if (Sharedpref.getmPref(getApplicationContext()).getiscamerabackfront() != null &&
                Sharedpref.getmPref(getApplicationContext()).getiscamerabackfront().equals("cameraback")
        ) {
            Log.e("TAG", "onCreatecameraback: ");
            back_camera.setText("BACK CAMERA");
            barcodeDetector = new BarcodeDetector.Builder(CameraActivityGuest.this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
            cameraSource = new CameraSource.Builder(CameraActivityGuest.this, barcodeDetector).
                    setRequestedFps(15.0f).
                    setFacing(CameraSource.CAMERA_FACING_BACK).
                    setRequestedPreviewSize(1600, 1024).
                    setAutoFocusEnabled(true).
                    build();
            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CameraActivityGuest.this, GuestActivity.class);
                    cameraSource.release();
                    startActivity(intent);
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_REQUEST_CODE);
                } else {
                    startQRScan();
                }
            } else {
                startQRScan();
            }

        } else if (Sharedpref.getmPref(getApplicationContext()).getiscamerabackfront() != null &&
                Sharedpref.getmPref(getApplicationContext()).getiscamerabackfront().equals("camerafront")
        ) {
            Log.e("TAG", "onCreatecamerafront: ");
            front_camera.setText("FRONT CAMERA");
            barcodeDetector = new BarcodeDetector.Builder(CameraActivityGuest.this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
            cameraSource = new CameraSource.Builder(CameraActivityGuest.this, barcodeDetector).
                    setRequestedFps(15.0f).
                    setFacing(CameraSource.CAMERA_FACING_FRONT).
                    setRequestedPreviewSize(1600, 1024).
                    setAutoFocusEnabled(true).
                    build();
            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CameraActivityGuest.this, GuestActivity.class);
                    cameraSource.release();
                    startActivity(intent);
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_REQUEST_CODE);
                } else {
                    startQRScan();
                }
            } else {
                startQRScan();
            }

        } /*else {
            barcodeDetector = new BarcodeDetector.Builder(CameraActivityGuest.this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
            cameraSource = new CameraSource.Builder(CameraActivityGuest.this, barcodeDetector).
                    setRequestedFps(15.0f).
                    setFacing(CameraSource.CAMERA_FACING_BACK).
                    setRequestedPreviewSize(1600, 1024).
                    setAutoFocusEnabled(true).
                    build();
            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CameraActivityGuest.this, GuestActivity.class);
                    cameraSource.release();
                    startActivity(intent);
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_REQUEST_CODE);
                } else {
                    startQRScan();
                }
            } else {
                startQRScan();
            }
        }*/
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(CameraActivityGuest.this, CameraActivityGuest.class);
                startActivity(intent);
            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }
    }//e

    void startQRScan() {
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                       /* if (ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            Log.e("TAG", "surfaceCreated: ");
                            return;

                        } else {
                            Log.e("TAG", "camera opened: ");
                            cameraSource.start(surfaceView.getHolder());
                        }*/
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CameraActivityGuest.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    Log.e("TAG", "camera opened: ");
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.e("TAG", "surfaceChanged: ");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    Barcode barcode = (Barcode) barcodes.valueAt(0);
                    QRCode = barcode.rawValue;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("TAG", "run: " + QRCode);
                            // qrSuccess();
                            stopQRScan();
                            Intent intent = new Intent(CameraActivityGuest.this, GuestActivity.class);
                            intent.putExtra("barcode", QRCode);
                            intent.putExtra("table_id_camera", table_id);
                            stopalarm();
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    void stopQRScan() {
        Log.e("TAG", "stopQRScan: " + "METHOD CALLING");
        barcodeDetector.release();
        cameraSource.stop();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CameraActivityGuest.this, GuestActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        stopalarm();
        super.onStop();
    }
}
