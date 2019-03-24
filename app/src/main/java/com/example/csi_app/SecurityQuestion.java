package com.example.csi_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageButton;
import android.view.View;

public class SecurityQuestion extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        showSecurityQuestion();


    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.scan_check_button:
                EditText securityAnswer = (EditText) findViewById(R.id.securityA);
                String myAnswer = securityAnswer.getText().toString();

                if (myAnswer.equalsIgnoreCase(User.currentUser.getSecurity_answer())) {

                    int id = User.currentUser.getId();

                    if(id == 1)
                    {
                        startActivity(new Intent(SecurityQuestion.this, Vault.class));
                    }

                    else if(id == 2)
                    {
                        startActivity(new Intent(SecurityQuestion.this, Vault2.class));
                    }

                    else
                        startActivity(new Intent(SecurityQuestion.this, Vault3.class));

                }
                else {

                    Context context = getApplicationContext();
                    Toast incorrect_answer_error = Toast.makeText(context, "Incorrect Answer", Toast.LENGTH_LONG);
                    incorrect_answer_error.show();
                }

                break;

            case R.id.security_home:
                startActivity(new Intent(SecurityQuestion.this, MainActivity.class));
                break;


        }
    }

    public void showSecurityQuestion(){

        String sQuestion = User.currentUser.getSecurity_question();
        TextView securityQuestion = (TextView)findViewById(R.id.securityQ);
        securityQuestion.setText(sQuestion);

        ImageButton security_button = (ImageButton)findViewById(R.id.scan_check_button);
        security_button.setOnClickListener(this);

        ImageButton home_button = (ImageButton)findViewById(R.id.security_home);
        home_button.setOnClickListener(this);
    }




}
