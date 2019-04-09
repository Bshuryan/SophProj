package com.example.csi_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class newUserConfirm extends AppCompatActivity implements View.OnClickListener{



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

        sendMessage(lastUser);

    }

    private void sendMessage(User user) {

        String sender = "sophomoreproject123@gmail.com";
       String  pass = "test123test!";
       String  subject = "Your QR code";
       String  body = "Attached is your personalized QR code.";

        String[] recipients = {user.email_address };
        MainActivity.SendEmailAsyncTask email = new MainActivity.SendEmailAsyncTask();
        email.activity = this;
        email.m = new Mail(sender, pass);
        email.m.set_from(sender);
        email.m.setBody(body);
        email.m.set_to(recipients);
        email.m.set_subject(subject);


        FileOutputStream out = null;
        String filePath = getCacheDir()+ File.pathSeparator+"QR-Code.jpg";
        try {
            out = new FileOutputStream(filePath);

            user.getQR().compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored

            email.m.addAttachment(filePath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        email.execute();
    }


    public void onClick(View v)
    {
        startActivity(new Intent(newUserConfirm.this, MainActivity.class));
    }



}

