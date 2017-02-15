package com.sixtyseven.uga.watercake.controllers;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.Registration.RegistrationField;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.Registration.RegistrationError;

import java.util.EnumSet;

/**
 * Created by Dimitar on 2/11/2017.
 */

public class RegistrationController extends Activity {

    EditText usernameBox;
    EditText passwordBox;
    EditText repeatPasswordBox;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);

        usernameBox = (EditText) findViewById(R.id.registerUsernameTextbox);
        passwordBox = (EditText) findViewById(R.id.registerPasswordBox);
        repeatPasswordBox = (EditText) findViewById(R.id.registerRepeatPasswordBox);
    }


    public void attemptRegistration(View view) {

        String username = usernameBox.getText().toString();
        String password = passwordBox.getText().toString();
        String passwordRepeat = repeatPasswordBox.getText().toString();

        Log.d("registration", "registration attempted." +
                " username: " + username +
                " password: " + password +
                " password repeat: " + passwordRepeat);

        EnumSet<RegistrationError> errors = UserSession.currentSession().registerUser(username, password, passwordRepeat);

        if (errors.isEmpty()) { //No errors = success
            Toast.makeText(getBaseContext(), "Registration successful!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegistrationController.this, LoginController.class));
        } else {
            Toast.makeText(getBaseContext(), "Registration failed!", Toast.LENGTH_SHORT).show();

            boolean focusSet = false;

            for (RegistrationError error : errors) {
                EditText target = null;
                if (error.getField() == RegistrationField.USERNAME) {
                    target = usernameBox;
                } else if (error.getField() == RegistrationField.PASSWORD) {
                    target = passwordBox;
                } else if (error.getField() == RegistrationField.REPEAT_PASSWORD) {
                    target = repeatPasswordBox;
                }

                setError(target, error, !focusSet);
                focusSet = true;
            }
        }

    }

    private void setError(EditText target, RegistrationError error, boolean shouldFocus) {
        if (target != null) {
            target.setError(error.getMessage());
            if (shouldFocus) {
                target.requestFocus();
            }
        }
    }

    public void cancelRegistration(View view) {
        startActivity(new Intent(RegistrationController.this, LoginController.class));
        Log.d("registration", "registration canceled");
    }

}
