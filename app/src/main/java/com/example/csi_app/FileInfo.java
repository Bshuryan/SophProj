package com.example.csi_app;

import android.net.Uri;
import java.util.ArrayList;

public class FileInfo {

    private Uri uri;
    private String fileName;
    private ArrayList<FileInfo> copy;



    public FileInfo(String fileName, Uri uri){

        this.uri = uri;
        this.fileName = fileName;
    }

    public Uri getUri(){
        return this.uri;
    }

    public String getName(){
        return this.fileName;
    }




    }

