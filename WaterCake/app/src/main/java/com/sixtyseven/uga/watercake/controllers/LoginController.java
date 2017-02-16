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

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.response.LoginResult;

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
        Log.d("login controller", "login controller created");
    }

    public void attemptLogin(View view) {
        TextInputLayout usernameInput = (TextInputLayout) findViewById(R.id.usernameInputLayout);
        TextInputLayout passwordInput = (TextInputLayout) findViewById(R.id.passwordInputLayout);

        EditText usernameEditText = usernameInput.getEditText();
        EditText passwordEditText = passwordInput.getEditText();

        Log.d("login", "login attempted." +
                " username: " + usernameEditText.getText() +
                " password: " + passwordEditText.getText());

        LoginResult response = UserSession.currentSession().tryLogin(
                usernameEditText.getText().toString(),
                passwordEditText.getText().toString());

        if (response.equals(LoginResult.SUCCESS)) {
            startActivity(new Intent(LoginController.this, WelcomeCakeController.class));
        } else {
            if (response == LoginResult.USER_DOES_NOT_EXIST) {
                usernameInput.setError(response.getMessage());
                usernameEditText.requestFocus();
            } else if (response == LoginResult.WRONG_PASSWORD) {
                passwordInput.setError(response.getMessage());
                passwordEditText.requestFocus();
                passwordInput.getEditText().setText("");
            }
        }
    }

    public void goToRegistration(View view) {
        Log.d("login", "go to registration");
        startActivity(new Intent(LoginController.this, RegistrationController.class));
    }

    @Override
    public void onBackPressed() {
        Log.d("back button", "always exit on back button on login screen");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(new Intent(Intent.ACTION_MAIN));
    }
}
