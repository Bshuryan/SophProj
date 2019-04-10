package com.example.csi_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

public class newUserConfirm extends AppCompatActivity implements View.OnClickListener{

    private String user;
    private String pass;
    private String subject;
    private String body;
    private String recipient;
    private TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_confirm);

        ImageView qrCode = (ImageView)findViewById(R.id.imageView);
        User lastUser = User.accounts.getLast();
        Bitmap b = lastUser.getQR();
        qrCode.setImageBitmap(b);

        Context context = getApplicationContext();
        Toast new_user_welcome = Toast.makeText(context, "Welcome " +lastUser.username, Toast.LENGTH_LONG);
        new_user_welcome.show();

        ImageButton user_home_button = (ImageButton)findViewById(R.id.user_confirm_home);
        user_home_button.setOnClickListener(this);

        user = "sophprojqr@gmail.com";
        pass = "Grizzly123";
        subject = "Your QR code";
        body = "Attached is your personalized QR code.";
        recipient = "sophprojqr@gmail.com";

        txt = findViewById(R.id.textView10);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    public void sendMessage() {
        String[] recipients = { recipient};
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

    public void onClick(View v)
    {
        startActivity(new Intent(newUserConfirm.this, MainActivity.class));
    }


    class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
        Mail m;
        newUserConfirm activity;

        public SendEmailAsyncTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if (m.send()) {
                    activity.displayMessage("Email sent.");
                } else {
                    activity.displayMessage("Email failed to send.");
                }

                return true;
            } catch (AuthenticationFailedException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
                e.printStackTrace();
                activity.displayMessage("Authentication failed.");
                return false;
            } catch (MessagingException e) {
                Log.e(SendEmailAsyncTask.class.getName(), "Email failed");
                e.printStackTrace();
                activity.displayMessage("Email failed to send.");
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                activity.displayMessage("Unexpected error occurred.");
                return false;
            }
        }






}}

