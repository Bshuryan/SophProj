package com.example.csi_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Settings extends AppCompatActivity {
    CheckBox changeUsername,changeEmail,changePassword,changeQuestion,changeAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView username = (TextView) findViewbyId(R.id.username);
        username.setText(User.currentUser.getUsername());
        /*
        TextView password = (TextView) findViewbyId(R.id.password);
        password.setText(User.currentUser.getPassword());
        */
        TextView email = (TextView) findViewbyId(R.id.email);
        email.setText(User.currentUser.getEmail_address());

        TextView question = (TextView) findViewbyId(R.id.securityQuestion);
        question.setText(User.currentUser.getSecurity_question());

        TextView answer = (TextView) findViewbyId(R.id.securityAnswer);
        answer.setText(User.currentUser.getSecurity_answer());

        Button updateBtn = (Button) findViewbyId(R.id.updateBtn);

        Button deleteBtn = (Button) findViewbyId(R.id.deleteBtn);




    }
}
