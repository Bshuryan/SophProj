package com.example.csi_app;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.net.Uri;

public class Vault extends AppCompatActivity implements View.OnClickListener {
    ImageButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);


        ImageButton settingsButton = (ImageButton)findViewById(R.id.settingsButton1);
        settingsButton.setOnClickListener(this);

        ImageButton uploadButton = (ImageButton)findViewById(R.id.upload);
        uploadButton.setOnClickListener(this);
    }
            public void onClick(View v) {
                switch(v.getId()){

                    case R.id.settingsButton1:
                        Intent startIntent = new Intent(getApplicationContext(),Settings.class);
                        startActivity(startIntent);
                        break;

                   case R.id.upload:
                        uploadImage();
                        break;

            }

        }

        public void uploadImage()
        {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_OPEN_DOCUMENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            String[] fileTypes = {"application/pdf", "application/doc", "image/jpeg", "image/bmp",
                    "image/gif", "image/png", "image/jpg", "application/zip"};
            i.putExtra(Intent.EXTRA_MIME_TYPES, fileTypes);
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i,"Select an Image"),101);


        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== 101 && resultCode == RESULT_OK && data!=null){

            Uri u = data.getData();
            String pathname = u.getPath();



        }
    }
}




