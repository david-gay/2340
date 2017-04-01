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
 * Controller for the login screen
 */
public class LoginController extends Activity {

    @Override
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

    /**
     * Event handler for the login button. Attempts to log in using the supplied credentials
     * @param view the button that initiated this event
     */
    public void attemptLogin(View view) {
        final TextInputLayout usernameInput = (TextInputLayout) findViewById(
                R.id.usernameInputLayout);
        final TextInputLayout passwordInput = (TextInputLayout) findViewById(
                R.id.passwordInputLayout);

        final EditText usernameEditText = usernameInput.getEditText();
        final EditText passwordEditText = passwordInput.getEditText();

        Log.d("login",
                "login attempted." + " username: " + usernameEditText.getText() + " password: "
                        + passwordEditText.getText());

        UserSession.currentSession(this.getApplicationContext()).tryLogin(
                usernameEditText.getText().toString(), passwordEditText.getText().toString(),
                new UserSession.LoginCallback() {
                    @Override
                    public void onSuccess() {
                        startActivity(
                                new Intent(LoginController.this, WelcomeCakeController.class));
                    }

                    @Override
                    public void onWrongPassword() {
                        passwordInput.setError(LoginResult.WRONG_PASSWORD.getMessage());
                        passwordEditText.requestFocus();
                        passwordInput.getEditText().setText("");
                    }

                    @Override
                    public void onUserNotFound() {
                        usernameInput.setError(LoginResult.USER_DOES_NOT_EXIST.getMessage());
                        usernameEditText.requestFocus();
                    }
                });
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
