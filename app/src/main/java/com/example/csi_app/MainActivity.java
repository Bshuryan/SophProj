package com.example.csi_app;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;
    private static final String TAG = "MainActivity";
//Testing
    //test1
//test3
//test2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cam = (Button)findViewById(R.id.button2);
        cam.setOnClickListener(this);
    }

    public void openCamera(View view) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.i(TAG, "IOException");
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
        private File createImageFile() throws IOException {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  // prefix
                    ".jpg",         // suffix
                    storageDir      // directory
            );

            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = "file:" + image.getAbsolutePath();
            return image;
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                try {
                    mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                    mImageView.setImageBitmap(mImageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    public void forgotPassword(View view) {

        openForgotPassword();


    }

    public void newUser(View view){
        openNewUser();
    }

    public void openForgotPassword(){

        Intent intent = new Intent(this,UsernameCheck.class);
        startActivity(intent);
    }

    public void openNewUser(){
        Intent i2 = new Intent(this, FirstTimeUser.class);
        startActivity(i2);
    }

    /*public void openCamScan(View view)
    {
        Intent i = new Intent(this, CamScan.class);
        startActivity(i);
    }*/

    public void onClick(View v){

        startActivity(new Intent(this, CamScan.class));
    }
}

