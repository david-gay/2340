package com.sixtyseven.uga.watercake.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.userprofile.UserProfileError;
import com.sixtyseven.uga.watercake.models.userprofile.UserProfileField;

import java.util.EnumSet;

/**
 * Controller for the Registration screen
 */
public class RegistrationController extends FragmentActivity {
    UserPropertiesFragment properties;

    TextInputLayout usernameTextLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);

        properties = (UserPropertiesFragment) getSupportFragmentManager().findFragmentById(R.id
                .details_fragment);
        usernameTextLayout = (TextInputLayout) findViewById(R.id.registrationUsernameInputLayout);

        //Sets the final text box in the UserPropertiesFragment to be flagged for IME_ACTION_DONE
        //and adds a listener.
        TextInputLayout textInputLayout = properties.getBottomTextInputLayout();
        if (textInputLayout != null && textInputLayout.getEditText() != null) {
            textInputLayout.getEditText().setImeOptions(EditorInfo.IME_ACTION_DONE);

            textInputLayout.getEditText().setOnEditorActionListener(new TextView
                    .OnEditorActionListener() {

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
        }

        Log.d("registration controller", "registration controller created");
    }

    /**
     * Event handler for clicking the Register button. Validates user information; if valid, then
     * registers the user. If not, then updates the Registration view with appropriate error
     * messages.
     * @param view the button that initiated this event
     */
    public void attemptRegistration(View view) {

        Log.d("registration controller", "attemptRegistration");


        String username = usernameTextLayout.getEditText().getText().toString();
        String password = properties.getTextFieldContents(UserProfileField.PASSWORD);

        String passwordRepeat = properties.getTextFieldContents(UserProfileField.REPEAT_PASSWORD);

        Log.d("registration", "registration attempted." +
                " username: " + username +
                " password: " + password +
                " password repeat: " + passwordRepeat);

        EnumSet<UserProfileError> errors = UserSession.currentSession()
                .registerUser(username, password, passwordRepeat);

        if (errors.isEmpty()) { //No errors = success
            Toast.makeText(getBaseContext(), "Registration successful!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegistrationController.this, LoginController.class));
        } else {
            Toast.makeText(getBaseContext(), "Registration failed!", Toast.LENGTH_SHORT).show();

            boolean focusSet = false;

            // Handle username errors first, so that it takes focus
            for (UserProfileError error : errors) {
                if (error.getField() == UserProfileField.USERNAME) {
                    setError(usernameTextLayout, error, !focusSet);
                    focusSet = true;
                }
            }

            properties.setErrors(errors, !focusSet);
        }

    }

    private void setError(TextInputLayout target, UserProfileError error, boolean shouldFocus) {
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
