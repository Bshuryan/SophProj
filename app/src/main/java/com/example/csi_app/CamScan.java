package com.example.csi_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;



import java.io.IOException;

public class CamScan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_scan);


        openCam();


    }

    SurfaceView liveCam;

    public void openCam() {


        liveCam = (SurfaceView) findViewById(R.id.liveCam);
        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        final CameraSource cam = new CameraSource.Builder(this, barcodeDetector).build();
        final TextView info = (TextView)findViewById(R.id.encoded);



        liveCam.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                        return;
                    }
                    cam.start(liveCam.getHolder());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                //unsure how to implement atm, will come back if problems

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                cam.stop();

            }
        });

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
                            String s = codes.valueAt(0).displayValue;
                            info.setText("Welcome " +s);

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
                    });} }});}}



