package com.sixtyseven.uga.watercake.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sixtyseven.uga.watercake.R;
import com.sixtyseven.uga.watercake.models.registration.RegistrationError;
import com.sixtyseven.uga.watercake.models.registration.RegistrationField;

import java.util.EnumSet;

public class UserPropertiesFragment extends Fragment {
    TextInputLayout passwordInput;
    TextInputLayout repeatPasswordInput;

    public static UserPropertiesFragment newInstance() {
        return new UserPropertiesFragment();
    }

    //3
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_properties, container, false);
        passwordInput = (TextInputLayout) view.findViewById(R.id
                .passwordInputLayout);

        repeatPasswordInput = (TextInputLayout) view.findViewById(R.id
                .repeatPasswordInputLayout);
        return view;
    }

    /**
     * Gets the current text in the corresponding RegistrationField
     * @param field the field to get
     * @return the contents corresponding to the RegistrationField. Will return "" if the
     * RegistrationField is not a TextField.
     */
    public String getTextFieldContents(RegistrationField field) {
        if (field == RegistrationField.PASSWORD) {
            return passwordInput.getEditText().getText().toString();
        } else if (field == RegistrationField.REPEAT_PASSWORD) {
            return repeatPasswordInput.getEditText().getText().toString();
        }

        return "";
    }

    /**
     * Sets error messages on each text field with an error. If requestFocus is true, then the first
     * field with an error will get focus.
     * @param errors the set of all validation errors
     * @param requestFocus true if the first error should cause the field to take focus
     * @return true if focus was set within this method
     */
    public boolean setErrors(EnumSet<RegistrationError> errors, boolean requestFocus) {

        boolean focusSet = !requestFocus;

        for (RegistrationError error : errors) {
            setError(error, !focusSet);
            focusSet = true;
        }

        return focusSet && requestFocus;
    }

    private void setError(RegistrationError error, boolean shouldFocus) {
        Log.d("userPropertiesFragment", "setting error: " + error.getMessage());

        TextInputLayout target = null;
        if (error.getField() == RegistrationField.PASSWORD) {
            target = passwordInput;
        } else if (error.getField() == RegistrationField.REPEAT_PASSWORD) {
            target = repeatPasswordInput;
        }

        if (target != null) {
            target.setError(error.getMessage());
            if (shouldFocus) {
                target.getEditText().requestFocus();
            }
        }
    }
}
