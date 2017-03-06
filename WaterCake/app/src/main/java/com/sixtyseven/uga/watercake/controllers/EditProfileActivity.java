package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.user.UserProfileError;
import com.sixtyseven.uga.watercake.models.user.UserProfileField;

import java.util.EnumSet;
import java.util.Map;

public class EditProfileActivity extends FragmentActivity {
    UserPropertiesFragment properties;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        //Changes the default Password field to say Change Password
        properties = (UserPropertiesFragment) getSupportFragmentManager().findFragmentById(
                R.id.details_fragment);
        ((TextInputLayout) properties.getView().findViewById(R.id.passwordInputLayout)).setHint(
                "Change Password");

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
                                confirmEditProfile(null);
                                handled = true;
                            }
                            return handled;
                        }
                    });
        }

        Map<UserProfileField, String> fieldsMap =
                UserSession.currentSession().getCurrentUser().getFieldsMap();

        ((TextView) findViewById(R.id.userNameTextView)).setText(
                "Username: " + fieldsMap.get(UserProfileField.USERNAME));
        ((TextView) findViewById(R.id.userTypeTextView)).setText(
                "User Type: " + fieldsMap.get(UserProfileField.USER_TYPE));

        //Do not populate the password field
        fieldsMap.remove(UserProfileField.PASSWORD);

        //Populate fields from the current user's data
        for (UserProfileField f : fieldsMap.keySet()) {
            properties.setTextFieldContents(f, fieldsMap.get(f));
        }

        Log.d("EditProfileActivity", "EditProfileActivity created");
    }

    /**
     * Event handler for the confirm button. Validates user info and saves.
     * @param view the button that initiated this event
     */
    public void confirmEditProfile(View view) {
        Map<UserProfileField, String> fieldsMap = properties.getFieldMap();

        //special handling for when the user does not try to change their password
        if (fieldsMap.containsKey(UserProfileField.PASSWORD) && "".equals(
                fieldsMap.get(UserProfileField.PASSWORD))) {
            fieldsMap.remove(UserProfileField.PASSWORD);
        }

        EnumSet<UserProfileError> results = UserSession.currentSession().updateUserFields(
                fieldsMap);

        if (results.isEmpty()) {
            Log.d("EditProfileActivity", "Profile Updated");
            finish();
        } else {
            Log.d("EditProfileActivity", "Profile Update failed");
            properties.setErrors(results, true);
        }
    }

    /**
     * Event handler for the cancel button. Sets the current view to be the login screen.
     * @param view the button that initiated this event
     */
    public void cancelEditProfile(View view) {
        Log.d("EditProfileActivity", "Canceled");
        finish();
    }
}
