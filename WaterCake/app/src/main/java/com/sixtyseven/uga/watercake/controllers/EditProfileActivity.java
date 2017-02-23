package com.sixtyseven.uga.watercake.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
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


        properties.getBottomTextInputLayout().getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    Log.d("CALLBACK!!!!", "CALLBACK!!!");

                    handled = true;
                }
                return handled;
            }
        });

        Log.d("registration controller", "registration controller created");
    }
}
