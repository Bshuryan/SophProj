package com.example.csi_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.net.Uri;
import android.widget.Toast;
import android.content.Context;
import java.io.File;
import android.app.AlertDialog.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Vault extends AppCompatActivity implements View.OnClickListener {


    ImageButton settingsButton;
    String filename;


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

        AlertDialog.Builder nameFile = new AlertDialog.Builder(Vault.this);

        final EditText nameOfFile = new EditText(Vault.this);

        if(requestCode== 101 && resultCode == RESULT_OK && data!=null){

            nameFile.setView(nameOfFile);
            Uri u = data.getData();
            String pathname = u.getPath();

            nameFile.setMessage("Please choose a file name").setCancelable(false)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            filename = nameOfFile.getText().toString();

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            nameFile.show();



            if(User.currentUser.manyFiles >= 15)
           {
               Context context = getApplicationContext();
               Toast too_many_files_error = Toast.makeText(context, "Maximum number of files reached.", Toast.LENGTH_LONG);
               too_many_files_error.show();
           }
           else {
               User.currentUser.filePaths1[User.currentUser.manyFiles] = pathname;
               User.currentUser.manyFiles++;
              // storeFile(pathname,filename);
           }



        }

        else{

            Context context = getApplicationContext();
            Toast file_error = Toast.makeText(context, "ERROR: Cannot upload file", Toast.LENGTH_LONG);
            file_error.show();
        }
    }


    public void storeFile(String path, String filename){

        try {
            FileOutputStream output = openFileOutput(path, MODE_APPEND);
        }

        catch (FileNotFoundException e) {
            Context context = getApplicationContext();
            Toast file_not_found = Toast.makeText(context, "ERROR: File not found", Toast.LENGTH_LONG);
            file_not_found.show();
        }

        File file = new File(path);
        //byte[] data = new byte[];
    }
}




