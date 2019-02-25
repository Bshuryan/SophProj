package com.example.csi_app;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.LinkedList;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this opens the camera button and creates intent
        final Button camButton = findViewById(R.id.cameraButton);
        camButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }



    public void forgotPassword(View view) {

        openForgotPassword();


    }

    public void newUser(View view){
        openNewUser();
    }

    public void openForgotPassword(){

        Intent intent = new Intent(this,ForgotPassword.class);
        startActivity(intent);
    }

    public void openNewUser(){
        Intent i2 = new Intent(this, FirstTimeUser.class);
        startActivity(i2);
    }

    //following opens the Camera
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView = (ImageView) findViewById(R.id.myImage);
            mImageView.setImageBitmap(imageBitmap);
        }

    }

}
