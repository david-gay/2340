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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);
    }


    public void attemptRegistration(View view) {
        String username = ((EditText) findViewById(R.id.registerUsernameTextbox)).getText().toString();
        String password = ((EditText) findViewById(R.id.registerPasswordBox)).getText().toString();
        String passwordRepeat = ((EditText) findViewById(R.id.registerRepeatPasswordBox)).getText().toString();

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

            EditText usernameBox = (EditText) findViewById(R.id.registerUsernameTextbox);
            EditText passwordBox = (EditText) findViewById(R.id.registerPasswordBox);
            EditText repeatPasswordBox = (EditText) findViewById(R.id.registerRepeatPasswordBox);

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
