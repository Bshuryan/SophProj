package com.example.csi_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.net.Uri;
import android.widget.Toast;
import android.content.Context;
import java.io.File;
import android.app.AlertDialog.*;
import android.provider.*;
import android.database.Cursor;
import android.os.*;
import android.content.*;
import java.io.*;
import com.example.csi_app.FileUtil;
import org.apache.commons.io.FileUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.util.Log;

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

        Button view = (Button)findViewById(R.id.view);
        view.setOnClickListener(this);


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

                    case R.id.view:
                        try {
                            openFileInput(User.currentUser.fileNames[0]);
                        }

                        catch(FileNotFoundException e){
                            e.printStackTrace();
                        }


            }

        }

        public void uploadImage()
        {
            Intent i = new Intent(Intent.ACTION_PICK);
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

        if(requestCode== 101 && resultCode == RESULT_OK && data!=null) {

            nameFile.setView(nameOfFile);
            final Uri u = data.getData();


            nameFile.setMessage("Please choose a file name").setCancelable(false)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            filename = nameOfFile.getText().toString();
                            //filename = getFileName(u);

                            if(User.currentUser.manyFiles >= 15)
                            {
                                Context context = getApplicationContext();
                                Toast too_many_files_error = Toast.makeText(context, "Maximum number of files reached.", Toast.LENGTH_LONG);
                                too_many_files_error.show();
                            }
                            else {
                                User.currentUser.fileNames[User.currentUser.manyFiles] = filename;
                                User.currentUser.manyFiles++;

                                try {
                                    storeFile(u, filename);
                                }
                                catch(IOException e)
                                {
                                    e.printStackTrace();
                                }



                            }


                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            nameFile.show();





        }

        else{

            Context context = getApplicationContext();
            Toast file_error = Toast.makeText(context, "ERROR: Cannot upload file", Toast.LENGTH_LONG);
            file_error.show();
        }
    }


    public void storeFile(Uri u, String filename) throws IOException{


            String path = FileUtil.getPath(this, u);
            FileOutputStream output = openFileOutput(filename, MODE_APPEND);

            File file = new File(path);
            byte[] data = FileUtils.readFileToByteArray(file);
            output.write(data);
            output.close();

            Toast.makeText(getApplicationContext(), "File saved to "+ getFilesDir()+"/"+filename, Toast.LENGTH_LONG).show();
        }



        public void getFileList(){

            String[] myFiles = User.currentUser.fileNames;


            //add filenames to drop-down menu
            //on click of one of the options it should get the text and open the file
            //openFileInput(INSERT FILENAME HERE);
        }







    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }






    }






