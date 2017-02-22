package com.sixtyseven.uga.watercake.controllers;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.TextInputLayout;

import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.registration.RegistrationField;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.registration.RegistrationError;

import java.util.EnumSet;

/**
 * Controller for the Registration screen
 */
public class RegistrationController extends Activity {

    EditText passwordBox;
    EditText repeatPasswordBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);

        passwordBox = (EditText) findViewById(R.id.registerPasswordBox);
        repeatPasswordBox = (EditText) findViewById(R.id.registerRepeatPasswordBox);

        repeatPasswordBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    attemptRegistration(null);
                    handled = true;
                }
                return handled;
            }
        });
        Log.d("registration controller", "registration controller created");
    }

    /**
     * Event handler for clicking the Register button. Validates user information; if valid, then
     * registers the user. If not, then updates the Registration view with appropriate error
     * messages.
     * @param view the button that initiated this event
     */
    public void attemptRegistration(View view) {

        TextInputLayout registerUsernameInput = (TextInputLayout) findViewById(R.id.registerUsernameInputLayout);
        EditText usernameEditText = registerUsernameInput.getEditText();
        String username = usernameEditText.getText().toString();

        TextInputLayout registerPasswordInput = (TextInputLayout) findViewById(R.id.registerPasswordInputLayer);
        EditText passwordEditText = registerPasswordInput.getEditText();
        String password = passwordEditText.getText().toString();

        TextInputLayout registerRepeatPasswordInput = (TextInputLayout) findViewById(R.id.registerRepeatPasswordInputLayer);
        EditText passwordRepeatEditText = registerRepeatPasswordInput.getEditText();
        String passwordRepeat = passwordRepeatEditText.getText().toString();

        Log.d("registration", "registration attempted." +
                " username: " + username +
                " password: " + password +
                " password repeat: " + passwordRepeat);

        EnumSet<RegistrationError> errors = UserSession.currentSession()
                .registerUser(username, password, passwordRepeat);

        if (errors.isEmpty()) { //No errors = success
            Toast.makeText(getBaseContext(), "Registration successful!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegistrationController.this, LoginController.class));
        } else {
            Toast.makeText(getBaseContext(), "Registration failed!", Toast.LENGTH_SHORT).show();

            boolean focusSet = false;

            for (RegistrationError error : errors) {
                TextInputLayout target = null;
                if (error.getField() == RegistrationField.USERNAME) {
                    target = registerUsernameInput;
                } else if (error.getField() == RegistrationField.PASSWORD) {
                    target = registerPasswordInput;
                } else if (error.getField() == RegistrationField.REPEAT_PASSWORD) {
                    target = registerRepeatPasswordInput;
                }

                setError(target, error, !focusSet);
                focusSet = true;
            }
        }

    }

    private void setError(TextInputLayout target, RegistrationError error, boolean shouldFocus) {
        Log.d("registration controller", "setting error: " + error.getMessage());
        if (target != null) {
            target.setError(error.getMessage());
            if (shouldFocus) {
                target.getEditText().requestFocus();
            }
        }
    }

    /**
     * Event handler for the cancel button. Sets the current view to be the login screen.
     * @param view the button that initiated this event
     */
    public void cancelRegistration(View view) {
        startActivity(new Intent(RegistrationController.this, LoginController.class));
        Log.d("registration", "registration canceled");
    }
}
