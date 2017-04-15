package com.sixtyseven.uga.watercake.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        // hide keyboard
        this.getCurrentFocus();
        if (view != null) {
            final InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        final TextInputLayout usernameInput = (TextInputLayout) findViewById(
                R.id.usernameInputLayout);
        final TextInputLayout passwordInput = (TextInputLayout) findViewById(
                R.id.passwordInputLayout);

        final EditText usernameEditText = usernameInput.getEditText();
        final EditText passwordEditText = passwordInput.getEditText();

        Log.d("login",
                "login attempted." + " username: " + usernameEditText.getText() + " password: "
                        + passwordEditText.getText());

        final Button loginButton = (Button) findViewById(R.id.btnLogin);
        loginButton.setText(R.string.CheckingYouUp);
        loginButton.setEnabled(false);

        final Button registerButton = (Button) findViewById(R.id.btnRegister);
        registerButton.setEnabled(false);

        UserSession.currentSession(this.getApplicationContext()).tryLogin(
                usernameEditText.getText().toString(), passwordEditText.getText().toString(),
                new UserSession.LoginCallback() {
                    @Override
                    public void onSuccess() {
                        startActivity(
                                new Intent(LoginController.this, MainActivity.class));
                        // TODO move ALL of these to onPause
                        beforeStart();
                        usernameEditText.getText().clear();
                        passwordEditText.getText().clear();
                        usernameEditText.requestFocus(); // TODO move this to onResume
                        onFinish();
                    }

                    @Override
                    public void onWrongPassword() {
                        beforeStart();
                        passwordInput.setError(LoginResult.WRONG_PASSWORD.getMessage());
                        passwordEditText.requestFocus();
                        passwordInput.getEditText().setText("");
                        onFinish();
                    }

                    @Override
                    public void onUserNotFound() {
                        beforeStart();
                        usernameInput.setError(LoginResult.USER_DOES_NOT_EXIST.getMessage());
                        usernameEditText.requestFocus();
                        onFinish();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        beforeStart();
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT)
                                .show();
                        onFinish();
                    }

                    private void beforeStart() {
                        usernameInput.setErrorEnabled(false);
                        passwordInput.setErrorEnabled(false);
                    }

                    private void onFinish() {
                        loginButton.setText(R.string.login);
                        loginButton.setEnabled(true);
                        registerButton.setEnabled(true);
                    }
                });
    }

    @Override
    protected void onResume() {
        // TODO moved code goes here
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO moved code goes here
        super.onPause();
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
