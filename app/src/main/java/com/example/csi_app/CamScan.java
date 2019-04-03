package com.example.csi_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import static android.Manifest.permission.CAMERA;



import java.io.IOException;

public class CamScan extends AppCompatActivity {


    SurfaceView liveCam;
    SurfaceHolder camHolder;
    CameraSource cam;
    BarcodeDetector barcodeDetector;
    TextView info;
    private String[] neededPermissions = new String[]{CAMERA};
    boolean hasPerm = false;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_scan);


           setCam();
           openCam();
           setScan();



        }





    public void setCam() {


                liveCam = (SurfaceView) findViewById(R.id.liveCam);
                barcodeDetector = new BarcodeDetector.Builder(CamScan.this).setBarcodeFormats(Barcode.QR_CODE).build();
                cam = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(1920,1080).build();
                info = (TextView) findViewById(R.id.encoded);


    }


    public void openCam(){


                liveCam.getHolder().addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                        try {
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT > 22) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.

                                reqCam();

                                return;
                            }

                            else {

                                cam.start(liveCam.getHolder());
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {

                        cam.stop();

                    }
                });}



        public void setScan(){

                barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                    @Override
                    public void release() {

                    }

                    @Override
                    public void receiveDetections(Detector.Detections<Barcode> detections) {

                        final SparseArray<Barcode> codes = detections.getDetectedItems();

                        if (codes.size() != 0) {

                            info.post(new Runnable() {
                                @Override
                                public void run() {
                                    s = codes.valueAt(0).displayValue;
                                    info.setText("Welcome " + s);


                                   User u = User.searchUsn(s);


                                   if (u != null) {


                                        User.currentUser = u;
                                        int id = User.currentUser.getId();

                                        if (id == 1) {
                                           startActivity(new Intent(CamScan.this, Vault.class));
                                        } else if (id == 2) {
                                            startActivity(new Intent(CamScan.this, Vault2.class));
                                        } else
                                            startActivity(new Intent(CamScan.this, Vault3.class));
                                   }
                                }
                            });


                        }




                    }


                });}



        public void reqCam(){

            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                if(Build.VERSION.SDK_INT < 23){
                    Toast not_supported = Toast.makeText(this.getApplicationContext(), "Error: Must have SDK version 23 or higher!", Toast.LENGTH_LONG);
                    not_supported.show();
                }

                else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }

            }
        }






}














