package com.sixtyseven.uga.watercake.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.UserSession;
import com.sixtyseven.uga.watercake.models.userprofile.UserProfileField;


public class EditProfileActivity extends FragmentActivity {
    UserPropertiesFragment properties;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        properties = (UserPropertiesFragment) getSupportFragmentManager().findFragmentById(R.id
                .details_fragment);
        ((TextInputLayout) properties.getView().findViewById(R.id
                .passwordInputLayout)).setHint("Change Password");

        properties.getBottomTextInputLayout().getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    handled = true;
                }
                return handled;
            }
        });

        Log.d("EditProfileActivity", "EditProfileActivity created");
    }

    /**
     * Event handler for the confirm button. Validates user info and saves.
     * @param view
     */
    public void confirmEditProfile(View view) {
        String password = properties.getTextFieldContents(UserProfileField.PASSWORD);

        if (password != null && !"".equals(password)) {
            //TODO validate password length, etc.
            UserSession.currentSession().getCurrentUser().setPassword(password);
        }

        //TODO if errors, don't transition
        Log.d("EditProfileActivity", "Profile Updated");
        startActivity(new Intent(EditProfileActivity.this, WelcomeCakeController.class));
    }

    /**
     * Event handler for the cancel button. Sets the current view to be the login screen.
     * @param view the button that initiated this event
     */
    public void cancelEditProfile(View view) {
        Log.d("EditProfileActivity", "Canceled");
        startActivity(new Intent(EditProfileActivity.this, WelcomeCakeController.class));
    }
}
