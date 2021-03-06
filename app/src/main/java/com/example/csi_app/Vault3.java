package com.example.csi_app;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Vault3 extends AppCompatActivity implements View.OnClickListener {



    ImageButton settingsButton;
    String filename;
    FileInfo newFile;

    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);

        ImageButton v3_home_button = (ImageButton)findViewById(R.id.v1_home);
        v3_home_button.setOnClickListener(this);

        ImageButton settingsButton = (ImageButton)findViewById(R.id.settingsButton1);
        settingsButton.setOnClickListener(this);

        ImageButton uploadButton = (ImageButton)findViewById(R.id.upload);
        uploadButton.setOnClickListener(this);

        final ArrayList<String> myFiles = User.currentUser.fileNames;

        list = (ListView) findViewById(R.id.display);


        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, myFiles);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = myFiles.get(position);
                //try {


                openFile(fileName);
                // }

               /* catch(FileNotFoundException e)
                {
                    e.printStackTrace();
                }
*/
            }
        });

        // Button view = (Button)findViewById(R.id.view);
        // view.setOnClickListener(this);


    }
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.settingsButton1:
                Intent startIntent = new Intent(getApplicationContext(), Settings.class);
                startActivity(startIntent);
                break;

            case R.id.upload:
                uploadImage();
                break;

            case R.id.v3_home:
                startActivity(new Intent(Vault3.this, MainActivity.class));
                break;
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

        Builder nameFile = new Builder(Vault3.this);

        final EditText nameOfFile = new EditText(Vault3.this);

        if(requestCode== 101 && resultCode == RESULT_OK && data!=null) {

            nameFile.setView(nameOfFile);
            final Uri u = data.getData();


            nameFile.setMessage("Please choose a file name").setCancelable(false)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            filename = nameOfFile.getText().toString();
                            //filename = getFileName(u);
                            newFile = new FileInfo(filename, u);

                            if(User.currentUser.manyFiles >= 15)
                            {
                                Context context = getApplicationContext();
                                Toast too_many_files_error = Toast.makeText(context, "Maximum number of files reached.", Toast.LENGTH_LONG);
                                too_many_files_error.show();
                            }
                            else {
                                User.currentUser.files.add(newFile);
                                User.currentUser.fileNames.add(filename);
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


    public void openFile(String filename){



        FileInfo selected = User.currentUser.searchFile(filename);

        String type = getContentResolver().getType(selected.getUri());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selected.getUri(), type);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);


    }














}






