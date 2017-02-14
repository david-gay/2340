package com.sixtyseven.uga.watercake.controllers;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;

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

        if (UserSession.currentSession().registerUser(username, password, passwordRepeat).condition) {
            Toast.makeText(getBaseContext(), "Registration successful!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegistrationController.this, LoginController.class));
        } else {
            Toast.makeText(getBaseContext(), "Registration failed!", Toast.LENGTH_SHORT).show();
            ((EditText) findViewById(R.id.registerUsernameTextbox)).setText("");
            ((EditText) findViewById(R.id.registerPasswordBox)).setText("");
            ((EditText) findViewById(R.id.registerRepeatPasswordBox)).setText("");
        }

    }

    public void cancelRegistration(View view) {
        startActivity(new Intent(RegistrationController.this, LoginController.class));
        Log.d("registration", "registration canceled");
    }

}
