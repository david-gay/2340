package com.sixtyseven.uga.watercake.controllers;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.sixtyseven.uga.watercake.R;

/**
 * Created by Dimitar on 2/11/2017.
 */

public class RegistrationController extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }


    public void attemptRegistration(View view) {
        Log.d("registration", "registration attempted." +
                " username: " + ((EditText) findViewById(R.id.registerUsernameTextbox)).getText() +
                " password: " + ((EditText) findViewById(R.id.registerPasswordBox)).getText() +
                " password repeat: " + ((EditText) findViewById(R.id.registerRepeatPasswordBox)).getText());
    }

    public void cancelRegistration(View view) {
        startActivity(new Intent(RegistrationController.this, LoginController.class));
        Log.d("registration", "registration canceled");
    }

}
