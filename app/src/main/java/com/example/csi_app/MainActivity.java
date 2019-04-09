package com.example.csi_app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;
    private static final String TAG = "MainActivity";
    private TextView txt;
    private String user;
    private String pass;
    private String subject;
    private String body;
    private EditText recipient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = "sophprojqr@gmail.com";
        pass = "_________";
        subject = "Your QR code";
        body = "Attached is your personalized QR code.";
        recipient = findViewById(R.id.editText4);

        Button cam = (Button) findViewById(R.id.button2);
        cam.setOnClickListener(this);

        txt = findViewById(R.id.textView10);
        txt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    private void sendMessage() {
        String[] recipients = { recipient.getText().toString() };
        SendEmailAsyncTask email = new SendEmailAsyncTask();
        email.activity = this;
        email.m = new Mail(user, pass);
        email.m.set_from(user);
        email.m.setBody(body);
        email.m.set_to(recipients);
        email.m.set_subject(subject);
        email.execute();
    }

    public void displayMessage(String message) {
        Snackbar.make(findViewById(R.id.textView10), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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

    public void newUser(View view) {
        openNewUser();
    }

    public void openForgotPassword() {

        Intent intent = new Intent(this, UsernameCheck.class);
        startActivity(intent);
    }

    public void openNewUser() {
        Intent i2 = new Intent(this, FirstTimeUser.class);
        startActivity(i2);
    }



    public void onClick(View v) {

        startActivity(new Intent(this, CamScan.class));
    }


    static class  SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
        Mail m;
        Activity activity;

        public SendEmailAsyncTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d(TAG, "doInBackground() called with: params = [" + params + "]");
            try {
                if (m.send()) {
                    showMessage("Email sent.");
                } else {
                    showMessage("Email failed to send.");
                }

                return true;
            } catch (AuthenticationFailedException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
                e.printStackTrace();
                showMessage("Authentication failed.");
                return false;
            } catch (MessagingException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "Email failed");
                e.printStackTrace();
                showMessage("Email failed to send.");
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                showMessage("Unexpected error occurred.");
                return false;
            }
        }
        
        private void showMessage(final String message) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}






