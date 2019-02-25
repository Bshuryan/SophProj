package com.example.csi_app;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.LinkedList;


public class User {  //has to be abstract? if not working change to abstract


    static LinkedList<User> accounts = new LinkedList<User>(); //the barber is drunk

    String email_address;
    String username;
    String security_question;
    String security_answer;
    Bitmap QR;

    int i = 0;

    public User(String username, String security_question, String security_answer, String email_address)
    {
        this.email_address = email_address;
        this.username = username;
        this.security_question = security_question;
        this.security_answer = security_answer;
        QR = null;
    }

    public User()
    {
        email_address = null;
        username = null;
        security_answer = null;
        security_question = null;
        QR = null;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail_address()
    {
        return email_address;
    }

    public String getSecurity_question()
    {
        return security_question;
    }

    public String getSecurity_answer()
    {
        return security_answer;
    }

    public Bitmap getQR() { return QR; }

    public void setUsername(String s)
    {
        username = s;
    }

    public void setEmailAddress(String s)
    {
        email_address = s;
    }

    public void setSecurityQuestion(String s)
    {
        security_question = s;
    }

    public void setSecurityAnswer(String s)
    {
        security_answer = s;
    }

    public LinkedList<User> getAccounts()
    {
        return accounts;
    }

    public void setQR(Bitmap b) { QR = b; }


    public void generateQR(){
        Bitmap bMap; //encoded barcode image (1D)- cannot display a 2D image
        BitMatrix bMatrix; //encoded barcode image (2D)
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {

            bMatrix = multiFormatWriter.encode("This is a test", BarcodeFormat.QR_CODE, 350, 350);
            BarcodeEncoder barEncoder= new BarcodeEncoder();
            bMap = barEncoder.createBitmap(bMatrix);
            this.setQR(bMap);


        }
        catch (WriterException e) {
            e.getCause();
        }


    }








}
