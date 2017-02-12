package com.sixtyseven.uga.watercake.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.sixtyseven.uga.watercake.R;

/**
 * Created by Dimitar on 2/10/2017.
 */

public class LoginController extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void attemptLogin(View view) {
        Log.d("login", "login attempted." +
                " username: " + ((EditText) findViewById(R.id.usernameTextbox)).getText() +
                " password: " + ((EditText) findViewById(R.id.passwordTextbox)).getText());
    }

    public void goToRegistration(View view) {
        Log.d("login", "go to registration");
        startActivity(new Intent(LoginController.this, RegistrationController.class));

    }

}
