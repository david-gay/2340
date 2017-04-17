package com.sixtyseven.uga.watercake.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.user.UserProfileError;
import com.sixtyseven.uga.watercake.models.user.UserProfileField;
import com.sixtyseven.uga.watercake.models.user.UserType;

import java.util.EnumSet;
import java.util.Map;

/**
 * Controller for the Registration screen
 */
public class RegistrationController extends FragmentActivity {
    private UserPropertiesFragment properties;

    private TextInputLayout usernameTextLayout;
    private Spinner userTypeSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);

        properties = (UserPropertiesFragment) getSupportFragmentManager().findFragmentById(
                R.id.details_fragment);
        usernameTextLayout = (TextInputLayout) findViewById(R.id.registrationUsernameInputLayout);
        userTypeSpinner = (Spinner) findViewById(R.id.userTypeSpinner);

        ArrayAdapter<UserType> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, UserType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        //Sets the final text box in the UserPropertiesFragment to be flagged for IME_ACTION_DONE
        //and adds a listener.
        TextInputLayout textInputLayout = properties.getBottomTextInputLayout();
        if (textInputLayout != null && textInputLayout.getEditText() != null) {
            textInputLayout.getEditText().setImeOptions(EditorInfo.IME_ACTION_DONE);

            textInputLayout.getEditText().setOnEditorActionListener(
                    new TextView.OnEditorActionListener() {

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

        Map<UserProfileField, String> fieldMap = properties.getFieldMap();

        fieldMap.put(UserProfileField.USERNAME,
                usernameTextLayout.getEditText().getText().toString());
        fieldMap.put(UserProfileField.USER_TYPE,
                ((UserType) userTypeSpinner.getSelectedItem()).name());

        EnumSet<UserProfileError> errors = UserSession.currentSession(this.getApplicationContext())
                .validateUserFields(fieldMap);

        if (errors.size() != 0) {
            Toast.makeText(getBaseContext(), "Registration failed!", Toast.LENGTH_SHORT).show();
            boolean focusSet = false;
            for (UserProfileError error : errors) {
                if (error.getField() == UserProfileField.USERNAME) {
                    setError(usernameTextLayout, error, !focusSet);
                    focusSet = true;
                }
            }
            properties.setErrors(errors, !focusSet);
        } else {
            UserSession.currentSession(this.getApplicationContext()).registerUser(fieldMap,
                    new RegistrationCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getBaseContext(), "Registration successful!",
                                    Toast.LENGTH_LONG).show();
                            startActivity(
                                    new Intent(RegistrationController.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            setError(usernameTextLayout, UserProfileError.USERNAME_TAKEN, true);
                            Toast.makeText(getBaseContext(), "Registration failed: " + errorMessage,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
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
        finish();
        Log.d("registration", "registration canceled");
    }

    public interface RegistrationCallback {
        void onSuccess();

        void onFailure(String errorMessage);
    }
}
