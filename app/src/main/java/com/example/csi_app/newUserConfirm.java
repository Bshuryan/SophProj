package com.example.csi_app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class newUserConfirm extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_confirm);

        ImageView qrCode = (ImageView)findViewById(R.id.imageView);
        User lastUser = User.accounts.getLast();
        Bitmap b = lastUser.getQR();
        qrCode.setImageBitmap(b);

        Context context = getApplicationContext();
        Toast new_user_welcome = Toast.makeText(context, "Welcome" +lastUser.username, Toast.LENGTH_LONG);
        new_user_welcome.show();

    }






}
