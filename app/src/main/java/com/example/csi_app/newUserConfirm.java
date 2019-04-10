package com.example.csi_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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


    }

    public void onClick(View v)
    {
        startActivity(new Intent(newUserConfirm.this, MainActivity.class));
    }








}

