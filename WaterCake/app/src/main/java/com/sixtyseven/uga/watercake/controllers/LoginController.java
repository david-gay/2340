package com.sixtyseven.uga.watercake.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.response.LoginResponse;

/**
 * Created by Dimitar on 2/10/2017.
 */

public class LoginController extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        EditText editText = (EditText) findViewById(R.id.passwordTextbox);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin(null);
                    handled = true;
                }
                return handled;
            }
        });
    }

    public void attemptLogin(View view) {
        TextInputLayout usernameInput = (TextInputLayout) findViewById(R.id.usernameInputLayout);
        TextInputLayout passwordInput = (TextInputLayout) findViewById(R.id.passwordInputLayout);

        EditText usernameEditText = usernameInput.getEditText();
        EditText passwordEditText = passwordInput.getEditText();

        Log.d("login", "login attempted." +
                " username: " + usernameEditText.getText() +
                " password: " + passwordEditText.getText());

        LoginResponse response = UserSession.currentSession().tryLogin(
                usernameEditText.getText().toString(),
                passwordEditText.getText().toString());

        if (response.condition) {
            startActivity(new Intent(LoginController.this, WelcomeCakeController.class));
        } else {
            if (response.reason.equals("Wrong password")) {
                passwordInput.setError(response.reason);
                passwordEditText.requestFocus();
                passwordInput.getEditText().setText("");
            } else if (response.reason.equals("User does not exist")) {
                usernameInput.setError(response.reason);
                usernameEditText.requestFocus();
                //usernameInput.getEditText().selectAll();
            }
            //Toast.makeText(getBaseContext(), "Login failed!", Toast.LENGTH_SHORT).show();
            //((EditText) findViewById(R.id.passwordTextbox)).setText("");
        }
    }

    public void goToRegistration(View view) {
        Log.d("login", "go to registration");
        startActivity(new Intent(LoginController.this, RegistrationController.class));
    }

}
