package com.example.csi_app;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Settings extends AppCompatActivity {
    CheckBox changeUsername,changeEmail,changePassword,changeQuestion,changeAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final TextView username = (TextView) findViewById(R.id.username);
        username.setText(User.currentUser.getUsername());

        final TextView password = (TextView) findViewById(R.id.password);
        password.setText(User.currentUser.getPassword());

        final TextView email = (TextView) findViewById(R.id.email);
        email.setText(User.currentUser.getEmail_address());

        final TextView question = (TextView) findViewById(R.id.securityQuestion);
        question.setText(User.currentUser.getSecurity_question());

        final TextView answer = (TextView) findViewById(R.id.securityAnswer);
        answer.setText(User.currentUser.getSecurity_answer());

        changeUsername = (CheckBox) findViewById(R.id.changeUsername);
        changeEmail = (CheckBox) findViewById(R.id.changeEmail);
        changePassword = (CheckBox) findViewById(R.id.changePassword);
        changeQuestion = (CheckBox) findViewById(R.id.changeQuestion);
        changeAnswer = (CheckBox) findViewById(R.id.changeAnswer);

        Button updateBtn = (Button) findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(changeUsername.isChecked())
                {
                    EditText editUsername = (EditText) findViewById(R.id.editUsername);
                    String newUserName = editUsername.getText().toString();
                    User.currentUser.setUsername(newUserName);
                    username.setText(User.currentUser.getUsername());
                }

                if(changeEmail.isChecked())
                {
                    EditText editEmail = (EditText) findViewById(R.id.editEmail);
                    String newEmail = editEmail.getText().toString();
                    User.currentUser.setEmailAddress(newEmail);
                    email.setText(User.currentUser.getEmail_address());
                }

                if(changePassword.isChecked())
                {
                    EditText editPassword = (EditText) findViewById(R.id.editPassword);
                    String newPassword = editPassword.getText().toString();
                    User.currentUser.setPassword(newPassword);
                    password.setText(User.currentUser.getPassword());
                }

                if(changeQuestion.isChecked())
                {
                    EditText editQuestion = (EditText) findViewById(R.id.editQuestion);
                    String newQuestion = editQuestion.getText().toString();
                    User.currentUser.setSecurityQuestion(newQuestion);
                    question.setText(User.currentUser.getSecurity_question());
                }

                if(changeAnswer.isChecked())
                {
                    EditText editAnswer = (EditText) findViewById(R.id.editAnswer);
                    String newAnswer = editAnswer.getText().toString();
                    User.currentUser.setSecurityAnswer(newAnswer);
                    answer.setText(User.currentUser.getSecurity_answer());
                }
            }
        });

        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });



    }

    public void dialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure that you want to delete your account?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //remove account
                    }
                });
    }
}
