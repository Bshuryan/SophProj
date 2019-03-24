package com.example.csi_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.widget.ImageButton;


public class UsernameCheck extends AppCompatActivity implements View.OnClickListener {

    static String usn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username_check);

        ImageButton next_button = (ImageButton) findViewById(R.id.usn_check_button);
        next_button.setOnClickListener(this);

        ImageButton usn_home_button = (ImageButton) findViewById(R.id.usn_check_home);
        usn_home_button.setOnClickListener(this);

    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.usn_check_button:

                EditText t = (EditText) findViewById(R.id.editText6);
                usn = t.getText().toString();
                User target = User.searchUsn(usn);

                if (target != null) {
                    User.currentUser = target;
                    startActivity(new Intent(UsernameCheck.this, SecurityQuestion.class));
                } else {
                    Context context = getApplicationContext();
                    Toast unknown_usn = Toast.makeText(context, "Username not found.", Toast.LENGTH_LONG);
                    unknown_usn.show();
                }

                break;

            case R.id.usn_check_home:
                startActivity(new Intent(UsernameCheck.this, MainActivity.class));
                break;
        }


    }
}


