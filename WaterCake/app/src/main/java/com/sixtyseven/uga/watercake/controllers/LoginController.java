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
 * Created by Dimitar on 2/10/2017.
 */

public class LoginController extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void attemptLogin(View view) {
        String username = ((EditText) findViewById(R.id.usernameTextbox)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordTextbox)).getText().toString();

        Log.d("login", "login attempted." +
                " username: " + username +
                " password: " + password);

        if (UserSession.currentSession().tryLogin(username, password)) {
            startActivity(new Intent(LoginController.this, WelcomeCakeController.class));
        } else {
            Toast.makeText(getBaseContext(), "Login failed!", Toast.LENGTH_SHORT).show();
            ((EditText) findViewById(R.id.passwordTextbox)).setText("");
        }
    }

    public void goToRegistration(View view) {
        Log.d("login", "go to registration");
        startActivity(new Intent(LoginController.this, RegistrationController.class));

    }

}
